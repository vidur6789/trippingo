package trippingo.service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trippingo.TripSolver;
import trippingo.dto.AttractionVisitDTO;
import trippingo.dto.DayPlanDTO;
import trippingo.dto.ItineraryDTO;
import trippingo.model.AttractionVisit;
import trippingo.model.DayPlan;
import trippingo.model.Itinerary;
import trippingo.model.Promotion;
import trippingo.model.TouristAttraction;
import trippingo.model.TravelPlan;
import trippingo.model.TravellerPreferences;
import trippingo.optaplanner.resources.PlanAttraction;
import trippingo.optaplanner.resources.TripPlanner;
import trippingo.repository.ItineraryRepository;
import trippingo.repository.TravelPlanRepository;

@RestController
@RequestMapping(path="/travelPlans")
public class TravelPlanController {
	
	@Autowired
	private TravelPlanRepository repository;
	
	@Autowired
	private ItineraryRepository itineraryRepository;
	
	@Autowired
	private TouristAttractionController attractionService;
	
	@Autowired
	private PromotionController promotionService;
	
	private static final Logger logger = LogManager.getLogger(PromotionController.class);
	
	
	@GetMapping("/{id}")
	public TravelPlan fetchTravelPlan(@PathVariable Long id) {
		return repository.findById(id).orElse(null);
	}
	
	@GetMapping("/{id}/itinerary")
	public ItineraryDTO fetchItinerary(@PathVariable Long id) {
		return repository.findById(id).map(TravelPlan::getItinerary).map(this::mapToItineraryDTO).orElseThrow(() -> new EntityNotFoundException());
	}
	
	private ItineraryDTO mapToItineraryDTO(Itinerary itinerary) {
		ItineraryDTO itineraryDTO = new ItineraryDTO();
		itineraryDTO.setItineraryId(itinerary.getId());
		DayPlanDTO[] dayPlans = itinerary.getDayPlans().stream().map(this::mapToDayPlaDTO).toArray(DayPlanDTO[]::new);
		itineraryDTO.setDayPlans(dayPlans);
		return itineraryDTO;
	}
	
	private DayPlanDTO mapToDayPlaDTO(DayPlan dayPlan) {
		DayPlanDTO dayPlanDTO = new DayPlanDTO();
		dayPlanDTO.setId(dayPlan.getId());
		dayPlanDTO.setTravelDate(dayPlan.getTravelDate());
		dayPlanDTO.setSerialNo(dayPlan.getSerialNo());
		AttractionVisitDTO[] attractionVisits = dayPlan.getAttractionVisits().stream().map(this::mapToAttractionVisitDTO).toArray(AttractionVisitDTO[]::new);
		dayPlanDTO.setAttractionVisit(attractionVisits);
		return dayPlanDTO;
	}
	
	private AttractionVisitDTO mapToAttractionVisitDTO(AttractionVisit attractionVisit) {
		AttractionVisitDTO attactionVisitDTO = new AttractionVisitDTO();
		attactionVisitDTO.setAttraction(attractionService.fetchAttraction(attractionVisit.getAttractionId(), false));
		attactionVisitDTO.setSerialNo(attractionVisit.getSerialNo());
		attactionVisitDTO.setTimeofDay(attractionVisit.getVisitStartTime());
		return attactionVisitDTO;
	}
	
	
	@GetMapping("/{id}/preferences")
	public TravellerPreferences fetchTravelPreferences(@PathVariable Long id) {
		return repository.findById(id).map(TravelPlan::getTravelPreferences).orElse(null);
	}
	
	@GetMapping("/{id}/selectedAttractions")
	public List<TouristAttraction> fetchSelectedAttractions(@PathVariable Long id) {
		return repository.findById(id).map(TravelPlan::getSelectedAttractionIds).map(attractionService::fetchAttractionsByIds).orElse(Collections.emptyList());
	}
	
	@GetMapping("/{id}/selectedPromotions")
	public List<Promotion> fetchSelectedPromotions(@PathVariable Long id) {
		return repository.findById(id).map(TravelPlan::getSelectedPromotionIds).map(promotionService::fetchPromotionsByIds).orElse(Collections.emptyList());
	}
	
	@PostMapping("/{id}/itinerary")
	public ItineraryDTO planItinerary(@PathVariable Long id) {
		
		TravelPlan travelPlan = repository.findById(id).orElse(null);
		List<TouristAttraction> attractions = fetchSelectedAttractions(id);
		TravellerPreferences preferences = travelPlan.getTravelPreferences();
		TripPlanner solvedTripPlanner = new TripSolver().optimizeItinerary(attractions, preferences);
		Itinerary itinerary = mapToItinerary(solvedTripPlanner);
		travelPlan.setItinerary(itinerary);
		repository.save(travelPlan);
		ItineraryDTO itineraryDTO = fetchItinerary(id);
		return itineraryDTO;
		
	}
	
	private Itinerary mapToItinerary(TripPlanner solvedTripPlanner) {
		Itinerary itinerary = new Itinerary();
		Set<DayPlan> dayPlans = solvedTripPlanner.getDayPlans().entrySet().stream().map(this::mapToDayPlan).collect(Collectors.toSet());
		itinerary.setDayPlans(dayPlans);
		return itinerary;
	}
	
	private DayPlan mapToDayPlan(Map.Entry<Integer, List<PlanAttraction>> dayPlanEntry) {
		DayPlan dayPlan = new DayPlan();
		dayPlan.setSerialNo(dayPlanEntry.getKey());
		Set<AttractionVisit> attractionVisits = dayPlanEntry.getValue().stream().map(this::mapToAttractionVisit).collect(Collectors.toSet());
		dayPlan.setAttractionVisits(attractionVisits );
		return dayPlan;
		
	}
	
	private AttractionVisit mapToAttractionVisit(PlanAttraction planAttraction) {
		AttractionVisit attractionVisit = new AttractionVisit();
		attractionVisit.setAttractionId(planAttraction.getAttraction().getId());
		attractionVisit.setSerialNo(planAttraction.getSerialNo());
		attractionVisit.setVisitStartTime(planAttraction.getStartingTimeGrain().getTime());
		return attractionVisit;
		
	}

	@PostMapping
	public TravelPlan createTravelPlan(@RequestBody TravelPlan travelPlan) {
		return repository.save(travelPlan);
	}
	
	@PutMapping("/{id}")
	public TravelPlan saveTravelPreferences(@PathVariable Long id, @RequestBody TravellerPreferences travellerPreferences) {
		return repository.findById(id).map(tp -> addTravellerPreferences(tp, travellerPreferences)).map(repository::save).orElse(null);
	}
	
	private TravelPlan addTravellerPreferences(TravelPlan travelPlan, TravellerPreferences preferences) {
		travelPlan.setTravelPreferences(preferences);
		return travelPlan;
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
		logger.error("\nException in TravelPlanController:", e);
		return new ResponseEntity<String>(e.getMessage()+". Check application.log", HttpStatus.BAD_REQUEST);
    }


}
