package trippingo.model;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	
    @ElementCollection
	@JoinTable(name = "travel_plan_attractions", joinColumns = @JoinColumn(name = "travel_plan_id"))
	private Set<Long> selectedAttractionIds;
	
    
    @ElementCollection
	@JoinTable(name = "travel_plan_promotions", joinColumns = @JoinColumn(name = "travel_plan_id"))
	private Set<Long> selectedPromotionIds;
	
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
	public Set<Long> getSelectedAttractionIds() {
		return selectedAttractionIds;
	}
	public void setSelectedAttractionIds(Set<Long> selectedAttractionIds) {
		this.selectedAttractionIds = selectedAttractionIds;
	}
	public Set<Long> getSelectedPromotionIds() {
		return selectedPromotionIds;
	}
	public void setSelectedPromotionIds(Set<Long> selectedPromotionIds) {
		this.selectedPromotionIds = selectedPromotionIds;
	}
	
	
	
	
	
	
	
	

}
