package trippingo.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


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
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
	private Itinerary itinerary;
	
	private String selectedAttractionIds;
	
	private String selectedPromotionIds;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TravellerPreferences getTravelPreferences() {
		return travelPreferences;
	}
	public void setTravelPreferences(TravellerPreferences travelPreferences) {
		this.travelPreferences = travelPreferences;
	}
	public String getHotelLocation() {
		return hotelLocation;
	}
	public void setHotelLocation(String hotelLocation) {
		this.hotelLocation = hotelLocation;
	}
	public Itinerary getItinerary() {
		return itinerary;
	}
	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}
	public String getSelectedAttractionIds() {
		return selectedAttractionIds;
	}
	public void setSelectedAttractionIds(String selectedAttractionIds) {
		this.selectedAttractionIds = selectedAttractionIds;
	}
	public String getSelectedPromotionIds() {
		return selectedPromotionIds;
	}
	public void setSelectedPromotionIds(String selectedPromotionIds) {
		this.selectedPromotionIds = selectedPromotionIds;
	}
	
	
	
	

}
