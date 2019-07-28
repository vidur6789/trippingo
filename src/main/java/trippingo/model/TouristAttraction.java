package trippingo.model;

import java.math.BigDecimal;

import org.joda.time.LocalTime;

public class TouristAttraction {
	
	private String id;
	private String name;
	private String description;
	private AttractionCategory category;
	private LocalTime openingTime;
	private LocalTime closingTime;
	private Double recommendedDuration;
	private BigDecimal price;

}
