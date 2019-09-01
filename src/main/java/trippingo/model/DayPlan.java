package trippingo.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class DayPlan {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "dayplan_generator")
	private Long id;
	
	private LocalDate travelDate;
	
	private Integer serialNo;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name="travel_plan_id")
	private Set<AttractionVisit> attractionVisits;
	
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
	public Set<AttractionVisit> getAttractionVisits() {
		return attractionVisits;
	}
	public void setAttractionVisits(Set<AttractionVisit> attractionVisits) {
		this.attractionVisits = attractionVisits;
	}
	
	
}
