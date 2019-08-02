package trippingo.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

@Entity
@Table(name="attraction")
public class TouristAttraction {
	
	@Id
	private String id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private AttractionCategory category;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	private LocalTime openingTime;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	private LocalTime closingTime;
	
	private Double recommendedDuration;
	
	private BigDecimal price;
	
	private String postalCode;
	
	private String keywords;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Review>reviews;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public AttractionCategory getCategory() {
		return category;
	}
	public void setCategory(AttractionCategory category) {
		this.category = category;
	}
	public LocalTime getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(LocalTime openingTime) {
		this.openingTime = openingTime;
	}
	public LocalTime getClosingTime() {
		return closingTime;
	}
	public void setClosingTime(LocalTime closingTime) {
		this.closingTime = closingTime;
	}
	public Double getRecommendedDuration() {
		return recommendedDuration;
	}
	public void setRecommendedDuration(Double recommendedDuration) {
		this.recommendedDuration = recommendedDuration;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Set<Review> getReviews() {
		return reviews;
	}
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	
	
	
	
	
	
	

}
