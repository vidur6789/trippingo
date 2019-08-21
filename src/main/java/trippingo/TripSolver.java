/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package trippingo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import trippingo.model.TouristAttraction;
import trippingo.optaplanner.resources.Day;
import trippingo.optaplanner.resources.PlanAttraction;
import trippingo.optaplanner.resources.TimeGrain;
import trippingo.optaplanner.resources.TripPlanner;
import trippingo.service.TouristAttractionController;


@Component
public class TripSolver implements  CommandLineRunner {
	

	
	@Autowired
	private TouristAttractionController service;
	
	private void solve() {
		
		 // Build the Solver
        SolverFactory<TripPlanner> solverFactory = SolverFactory.createFromXmlResource(
                "OptaplannerConfig.xml");
        Solver<TripPlanner> solver = solverFactory.buildSolver();

        TripPlanner unsolvedTripSolver = new TripPlanner ();
        List<TouristAttraction> attractions = service.fetchAttractions(null, null).subList(0, 10);
        
        List<PlanAttraction> planAttractions=  attractions.stream()
        		.map(this::mapToPlanAttraction)
        		.collect(Collectors.toList());
		unsolvedTripSolver.setAttraction(planAttractions);
		
		unsolvedTripSolver.setTimeGrainSlots(generateTimeGrains());
        // Solve the problem
        
		TripPlanner solvedTripSolver = solver.solve(unsolvedTripSolver);

        // Display the result
        toDisplayString(solvedTripSolver);
		
	}
	
	private void toDisplayString(TripPlanner solvedTripSolver) {
		//all attractions
		solvedTripSolver.getAttraction()
						.stream()
						.map(this::todisplayPlannedAttractionString)
						.forEach(System.out::println);
		
		Map<Integer, List<PlanAttraction>> dayPlans = solvedTripSolver.getAttraction()
						.stream()
						.collect(Collectors.groupingBy(this::dataKey));
		
		//day plans
		dayPlans.entrySet().stream().forEach(this::displayDayPlan);
	}
	
	private void displayDayPlan(Map.Entry<Integer, List<PlanAttraction>> dayPlan) {
		System.out.println("Day " + dayPlan.getKey() +" Plan:");
		dayPlan.getValue()
			   .stream()
			   .sorted(Comparator.comparingInt(p -> p.getStartingTimeGrain().getGrainIndex()))
			   .map(this::todisplayPlannedAttractionString)
			   .forEach(System.out::println);
	}
	
	private Integer dataKey(PlanAttraction attraction) {
		return attraction.getStartingTimeGrain().getDay().getDayOfYear();
	}
	
	private String todisplayPlannedAttractionString(PlanAttraction attraction) {
		double duration = attraction.getAttraction().getRecommendedDuration()!=null ? attraction.getAttraction().getDurationTimeGrains(): 0;
		return "Time slot for " + attraction.getAttraction().getName() + ": " + attraction.getStartingTimeGrain().getGrainIndex()+
				". Recommened Duration: " + duration + "serialNo:" + attraction.getSerialNo();
	}

	private List<TimeGrain> generateTimeGrains() {
		
		Day day1 = new Day();
		day1.setDayOfYear(1);
		Day day2 = new Day();
		day2.setDayOfYear(2);
		Day[] days = new Day[] {day1, day2};
		
		Arrays.stream(days).forEach(d -> {d.setFirstTravelSlot(32); d.setLastTravelSlot(80);});
		List <TimeGrain> timeGrainSlots = new ArrayList<TimeGrain>();
		for(Day day: days) {
			for(int i=0; i <24*4;i++) {
				TimeGrain timeGrain = new TimeGrain();
				timeGrain.setDay(day);
				timeGrain.setGrainIndex(i);
				timeGrainSlots.add(timeGrain);
			}
			
		}
		return timeGrainSlots;	
	}

	private PlanAttraction mapToPlanAttraction(TouristAttraction attraction) {
		PlanAttraction  planAttr = new PlanAttraction();
		planAttr.setAttraction(attraction);
		planAttr.setStartingTimeGrain(new TimeGrain());
		planAttr.getStartingTimeGrain().setGrainIndex(0);
		return planAttr;
		
	}

	@Override
	public void run(String... args) throws Exception {
		solve();
	}


}



