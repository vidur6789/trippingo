package trippingo.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class TravelPlan {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "plan_generator")
	private Long id;
	
	@Embedded
	@AttributeOverrides({
		  @AttributeOverride( name = "flexibleTime", column = @Column(name = "flexible_time")),
		  @AttributeOverride( name = "noOfTravelDays", column = @Column(name = "num_travel_days")),
		  @AttributeOverride( name = "travellerType", column = @Column(name = "traveller_type")),
		  @AttributeOverride( name = "travelHoursPerDay", column = @Column(name = "travel_hours_per_day")),
		  @AttributeOverride( name = "mealPreference", column = @Column(name = "meal_pref"))
	})
	private TravellerPreferences travelPreferences;
	private String hotelLocation;
	private Itinerary itinerary;
	private String[] selectedAttractionIds;
	private String[] selectedPromotionIds;

}
