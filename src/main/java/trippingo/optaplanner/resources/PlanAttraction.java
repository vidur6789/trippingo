package trippingo.optaplanner.resources;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import trippingo.model.TouristAttraction;

@PlanningEntity
public class PlanAttraction {
	
	private TouristAttraction attraction;
	private TimeGrain startingTimeGrain;
	
	@PlanningVariable(valueRangeProviderRefs = {"timeGrainRange"})
	public TimeGrain getStartingTimeGrain() {
		return startingTimeGrain;
	}

	public void setStartingTimeGrain(TimeGrain startingTimeGrain) {
		this.startingTimeGrain = startingTimeGrain;
	}

	
	public TouristAttraction getAttraction() {
		return attraction;
	}

	public void setAttraction(TouristAttraction attraction) {
		this.attraction = attraction;
	}

	public int calculateOverlap(PlanAttraction other) {
	    if (startingTimeGrain == null || other.getStartingTimeGrain() == null) {
	        return 0;
	    }
	    int start = startingTimeGrain.getGrainIndex();
	    int end = start + getAttraction().getDurationTimeGrains();
	    int otherStart = other.startingTimeGrain.getGrainIndex();
	    int otherEnd = otherStart + other.getAttraction().getDurationTimeGrains();

	    if (end < otherStart) {
	        return 0;
	    } else if (otherEnd < start) {
	        return 0;
	    }
	    return Math.min(end, otherEnd) - Math.max(start, otherStart);
	}
	
	public int travelHoursPenaltyScore() {
		if(startingTimeGrain == null || startingTimeGrain.getDay() == null)
			return 0;
	    int start = startingTimeGrain.getGrainIndex();
	    int end = start + getAttraction().getDurationTimeGrains();
	    if (end < startingTimeGrain.getDay().getLastTravelSlot()) {
	        return 0;
	    } else if (start > startingTimeGrain.getDay().getFirstTravelSlot()) {
	        return 0;
	    }
	    return (int) Math.pow((end - startingTimeGrain.getDay().getLastTravelSlot() +  startingTimeGrain.getDay().getFirstTravelSlot() - start), 10);
	}
	
	

}
