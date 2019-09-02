package trippingo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import trippingo.model.MealPreference;
import trippingo.model.TravelPlan;
import trippingo.model.TravellerPreferences;
import trippingo.model.TravellerType;
import trippingo.service.TravelPlanController;

@Component
public class TravelPlanTestDataUtility implements CommandLineRunner{

	@Autowired
	private TravelPlanController travelPlanService;
	
	@Value("${trippingo.testData}")
	private boolean testData;
	
	
	@Override
	public void run(String... args) throws Exception {
		if(testData)
			createTestData();
	}
	
	private void createTestData() {
		TravelPlan travelPlan = new TravelPlan();
		travelPlan.setHotelLocation("018956"); // Marina Bay Sands
		travelPlan.setTravelDate(LocalDate.of(2019, 9, 22)); // MR-RS Submission Date
		travelPlan= travelPlanService.createTravelPlan(travelPlan);
		travelPlanService.saveTravelPreferences(travelPlan.getId(), testTravelPreferences());
		travelPlanService.saveSelectedAttractions(travelPlan.getId(), testSelectedAttractions());
		travelPlanService.saveSelectedPromotions(travelPlan.getId(), testSelectedPromotions());
		travelPlanService.planItinerary(travelPlan.getId());
	}


	private Set<Long> testSelectedPromotions() {
		return Collections.emptySet();
	}


	private Set<Long> testSelectedAttractions() {
		return LongStream.rangeClosed(1,9).collect(HashSet::new, HashSet::add, (o1,o2) -> o1.addAll(o2));
	}


	private TravellerPreferences testTravelPreferences() {
		TravellerPreferences preferences = new TravellerPreferences();
		preferences.setFlexibleTime(false);
		preferences.setMealPreference(MealPreference.LongMeal);
		preferences.setNoOfTravelDays(3);
		preferences.setTravelHoursPerDay(12);
		preferences.setTravellerType(TravellerType.Couple);
		return preferences;
	}

}
