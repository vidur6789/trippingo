package trippingo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.joda.time.LocalDate;

@Entity
public class Review {
	@Id
	private Integer serialNo;
	private String username;
	private Integer rating;
	private LocalDate visitDate;
	private TravellerType travellerType;
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public LocalDate getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(LocalDate visitDate) {
		this.visitDate = visitDate;
	}
	public TravellerType getTravellerType() {
		return travellerType;
	}
	public void setTravellerType(TravellerType travellerType) {
		this.travellerType = travellerType;
	}
	
	
	
	
}
