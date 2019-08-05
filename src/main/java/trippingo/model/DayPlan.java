package trippingo.model;

import java.time.LocalDate;

import javax.persistence.Entity;

@Entity
public class DayPlan {

	private LocalDate travelDate;
	private Integer serialNo;
	private String[] attractionIds;
}
