package rules;
import java.util.Arrays;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import trippingo.dto.AssociationRecommendationDTO;
import trippingo.model.TouristAttraction;


public class AssociationRulesRecommender {
	public static AssociationRecommendationDTO[] fetchAssociatedAttractions(AssociationRecommendationDTO[] inputs) {
		if (inputs == null || inputs.length == 0)
			return inputs;

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession("ksession-rules");
        
        TouristAttraction[] attractions = Arrays.stream(inputs).map(AssociationRecommendationDTO::getAttractionNames).flatMap(Arrays::stream)
        		.map(AssociationRulesRecommender::mapToTouristAttraction)
        		.toArray(TouristAttraction[]::new);
        
        for (TouristAttraction attractionName: attractions) {
    		kieSession.insert(attractionName);
    	}
        
        AssociationRecommendationDTO recommendation = new AssociationRecommendationDTO();
        kieSession.insert(recommendation);
        kieSession.fireAllRules();
        return new AssociationRecommendationDTO[] {recommendation};

    }
	
	private static TouristAttraction mapToTouristAttraction(String name) {
		TouristAttraction attraction  = new TouristAttraction();
		attraction.setName(name);
		return attraction;
	}
		
}
