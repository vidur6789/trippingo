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
import trippingo.model.TravellerPreferences;
import trippingo.optaplanner.resources.Day;
import trippingo.optaplanner.resources.PlanAttraction;
import trippingo.optaplanner.resources.TimeGrain;
import trippingo.optaplanner.resources.TripPlanner;
import trippingo.service.TouristAttractionController;


@Component
public class TripSolver implements  CommandLineRunner {
	

	
	@Autowired
	private TouristAttractionController service;
	
	
	public TripPlanner optimizeItinerary(List<TouristAttraction> attractions, TravellerPreferences travellerPreferences) {
		 // Build the Solver
        SolverFactory<TripPlanner> solverFactory = SolverFactory.createFromXmlResource("OptaplannerConfig.xml");
        Solver<TripPlanner> solver = solverFactory.buildSolver();
        TripPlanner unsolvedTripSolver = new TripPlanner ();
        List<PlanAttraction> planAttractions=  attractions.stream()
        		.map(this::mapToPlanAttraction)
        		.collect(Collectors.toList());
		unsolvedTripSolver.setAttraction(planAttractions);
		unsolvedTripSolver.setTimeGrainSlots(generateTimeGrains(4, 32, 80));

		// Solve the problem
		TripPlanner solvedTripSolver = solver.solve(unsolvedTripSolver);
		return solvedTripSolver;
	}
	
	
	private List<TimeGrain> generateTimeGrains(int noOfDays, int startTimeSlot, int lastTravelSlot) {
		List<Day> days  = new ArrayList<Day>();
		for(int i=1; i < 6; i++) {
			Day day = new Day();
			day.setDayOfYear(i);
			days.add(day);
		}
		
		days.stream().forEach(d -> {d.setFirstTravelSlot(32); d.setLastTravelSlot(80);});
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
		planAttr.setService(service);
		return planAttr;
		
	}

	
	@Override
	public void run(String... args) throws Exception {
//		List<TouristAttraction> attractions = service.fetchAttractions(null, null).subList(0, 15);
//		TravellerPreferences preferences = new TravellerPreferences();
//		TripPlanner solvedTripPlanner = optimizeItinerary(attractions, preferences);
//		System.out.println(solvedTripPlanner.toString());
	}


}



