package trippingo.optaplanner.resources;

import java.util.List;

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
	
	
	
}
