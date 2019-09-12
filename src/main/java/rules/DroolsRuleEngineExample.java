package rules;
import trippingo.model.TouristAttraction;
import trippingo.model.*;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class DroolsRuleEngineExample {
	public static final void main(String[] args) {

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("ksession-rules");
        
        TouristAttraction att1 = new TouristAttraction();
        TouristAttraction att2 = new TouristAttraction();
        att1.setName("The Helix Bridge");
        att2.setName("Gardens by the Bay");
        kieSession.insert(att1);
        kieSession.insert(att2);
        
        TravelPlan plan = new TravelPlan();
        plan.setTotalDuration(20);
        TravellerPreferences pref = new TravellerPreferences();
        pref.setNoOfTravelDays(5);
        pref.setTravelHoursPerDay(8);
        plan.setTravelPreferences(pref);
        //plan.setSetOverallValidation(true);
        DayPlan dp = new DayPlan();
        dp.setDailyTotalDuration(10);
        kieSession.insert(dp);
        kieSession.insert(plan);
        kieSession.fireAllRules();

    }
		
}
