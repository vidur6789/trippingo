package trippingo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import trippingo.model.TouristAttraction;
import trippingo.repository.TouristAttractionRepository;
import trippingo.utils.CommonUtils;
import trippingo.utils.StringUtils;

@RestController
@RequestMapping(path="/attractions")
public class TouristAttractionController {
	
	@Autowired 
	private TouristAttractionRepository attractionRepository;
	
	@Autowired
	private PromotionController promotionService;
	
	@GetMapping("/{id}")
	public TouristAttraction fetchAttraction(@PathVariable Long id,
											 @RequestParam(value="detailed", defaultValue = "false") boolean detailed) {
		TouristAttraction attraction =  attractionRepository.findById(id).orElse(null);
		//REMOVE DETAILS
		if(!detailed)
			CommonUtils.removeDetails(attraction);
		else {
			attraction.setPromotions(promotionService.fetchPromotionsById(attraction.getId()));
		}
			
		return attraction;
		
	}
	
	@GetMapping
	public List<TouristAttraction> fetchAttractions(@RequestParam(value="name", required = false) String name,
													@RequestParam(value="keyword", required = false) String keyword) {
		List<TouristAttraction> attractions;
		if(StringUtils.isNotNull(name)) {
			attractions = attractionRepository.findAllByName(name);
		}
		else if (StringUtils.isNotNull(keyword)) {
			attractions = attractionRepository.findAllByKeyword(keyword);
		}
		else {
			attractions = (List<TouristAttraction>) attractionRepository.findAll();
		}
		attractions.stream().forEach(CommonUtils::removeDetails);
		return attractions;
	}
	
	@PutMapping
	public TouristAttraction saveAttraction(@RequestBody TouristAttraction touristAttraction) {
		attractionRepository.save(touristAttraction);
		return touristAttraction;
	}
}
