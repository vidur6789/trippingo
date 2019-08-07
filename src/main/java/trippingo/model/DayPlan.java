package trippingo.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DayPlan {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "dayplan_generator")
	private Long id;
	
	private LocalDate travelDate;
	
	private Integer serialNo;
	
	private String attractionIds;
	
	public LocalDate getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(LocalDate travelDate) {
		this.travelDate = travelDate;
	}
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAttractionIds() {
		return attractionIds;
	}
	public void setAttractionIds(String attractionIds) {
		this.attractionIds = attractionIds;
	}
	
	
	
	
}
