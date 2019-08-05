package trippingo.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class TravellerPreferences {
	
	private Boolean flexibleTime;
	
	@Enumerated(EnumType.STRING)
	private TravellerType travellerType;
	
	private Integer noOfTravelDays;
	
	private Integer travelHoursPerDay;
	
	@Enumerated(EnumType.STRING)
	private MealPreference mealPreference;
	
	public Boolean getFlexibleTime() {
		return flexibleTime;
	}
	public void setFlexibleTime(Boolean flexibleTime) {
		this.flexibleTime = flexibleTime;
	}
	public TravellerType getTravellerType() {
		return travellerType;
	}
	public void setTravellerType(TravellerType travellerType) {
		this.travellerType = travellerType;
	}
	public Integer getNoOfTravelDays() {
		return noOfTravelDays;
	}
	public void setNoOfTravelDays(Integer noOfTravelDays) {
		this.noOfTravelDays = noOfTravelDays;
	}
	public Integer getTravelHoursPerDay() {
		return travelHoursPerDay;
	}
	public void setTravelHoursPerDay(Integer travelHoursPerDay) {
		this.travelHoursPerDay = travelHoursPerDay;
	}
	public MealPreference getMealPreference() {
		return mealPreference;
	}
	public void setMealPreference(MealPreference mealPreference) {
		this.mealPreference = mealPreference;
	}
	
	

}
