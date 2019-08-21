package trippingo.optaplanner.resources;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class TripPlanner {
	private long id;
	
	private List <TimeGrain> timeGrainSlots;
	private List <PlanAttraction> attraction;
	
	private HardSoftScore score;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@ValueRangeProvider(id = "timeGrainRange")
	@ProblemFactCollectionProperty
	public List<TimeGrain> getTimeGrainSlots() {
		return timeGrainSlots;
	}
	public void setTimeGrainSlots(List<TimeGrain> timeGrainSlots) {
		this.timeGrainSlots = timeGrainSlots;
	}
	@PlanningEntityCollectionProperty
	public List<PlanAttraction> getAttraction() {
		assignSerialNo();
		return attraction;
	}
	public void setAttraction(List<PlanAttraction> attraction) {
		this.attraction = attraction;
	}
	
	@PlanningScore
	public HardSoftScore getScore() {
		return score;
	}
	
	public void setScore(HardSoftScore score) {
		this.score = score;
	}
	
	public void assignSerialNo() {
		Map<Integer, List<PlanAttraction>> dayPlans = attraction.stream().collect(Collectors.groupingBy(this::dataKey));
		
		int dayIndex = 0;
		for (Map.Entry<Integer, List<PlanAttraction>> dayPlan: dayPlans.entrySet()) {
			assignSerialNo(dayPlan.getValue(), dayPlan.getKey()*100);
			dayIndex++;
//			System.out.println("Day: " + dayPlan.getKey() + "No. of attractions: " + dayPlan.getValue().size());
		}
	}
	
	private Integer dataKey(PlanAttraction attraction) {
		if(attraction.getStartingTimeGrain() == null || attraction.getStartingTimeGrain().getDay() == null)
			return -1;
		return attraction.getStartingTimeGrain().getDay().getDayOfYear();
	}
	
	
	private void assignSerialNo(List<PlanAttraction> attractions, int initialSerialNo) {
		int i = initialSerialNo;
		attractions.sort(Comparator.comparingInt(p -> p.getStartingTimeGrain().getGrainIndex()));
		for (PlanAttraction attraction: attractions) {
			attraction.setSerialNo(i++);
//			System.out.println("Assigned:" + i);
		}
	}
	
	
	
}
