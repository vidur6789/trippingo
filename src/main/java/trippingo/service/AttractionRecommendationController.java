package trippingo.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import trippingo.model.AttractionCategory;
import trippingo.model.TouristAttraction;
import trippingo.model.TravellerType;
import trippingo.repository.TouristAttractionRepository;
import trippingo.utils.CommonUtils;



@RestController
@RequestMapping(path="/recommendations")
public class AttractionRecommendationController {

	
	@Autowired 
	private TouristAttractionRepository attractionRepository;
	
	@GetMapping
	public Iterable<TouristAttraction> fetchRecommendations(@RequestParam(value="travellerType", required = false, defaultValue = "Friends") TravellerType travellerType,
															@RequestParam(value="keywords", required = false) List<String> keywords,
															@RequestParam(value="count", required = false, defaultValue = "10") int resultCount,
															@RequestParam(value="detailed", required = false, defaultValue = "false") boolean detailed) {
		
		Set<Long> attractionIds = new HashSet<Long>();
		List<TouristAttraction> attractions;
		if(keywords!=null && !keywords.isEmpty()){
			Predicate<String> isCategory = value -> Arrays.stream(AttractionCategory.values()).anyMatch(cat -> cat.name().equals(value));
			List<String> categories  = keywords.stream().filter(isCategory).collect(Collectors.toList());
			List<String> otherKeywords = keywords.stream().filter(isCategory.negate()).collect(Collectors.toList());
			if(!categories.isEmpty()) {
				attractionIds.addAll(attractionRepository.findAllByCategory(categories));
			}
			if(!otherKeywords.isEmpty()) {
				otherKeywords.stream()
							 .map(attractionRepository::findAllByKeyword)
							 .flatMap(List::stream)
							 .map(TouristAttraction::getId)
							 .forEach(attractionIds::add);
			}
			attractions = (List<TouristAttraction>) attractionRepository.findAllById(attractionIds);
		}
		else {
			attractions = (List<TouristAttraction>) attractionRepository.findAll();
		}
		
		
		//ORDER BY RANK
		Collections.sort(attractions, getComparator(travellerType));
		//REMOVE DETAILS
		if(!detailed)
			attractions.stream().forEach(CommonUtils::removeDetails);
		return attractions.subList(0, resultCount);
	}

	private Comparator<TouristAttraction> getComparator(TravellerType travellerType) {
		switch(travellerType) {
		case  Family:
			return (a1, a2) -> a1.getAttractionRank().getFamilyRank() - a2.getAttractionRank().getFamilyRank();
		case  Business:
			return (a1, a2) -> a1.getAttractionRank().getBusinessRank() - a2.getAttractionRank().getBusinessRank();
		case  Solo:
			return (a1, a2) -> a1.getAttractionRank().getSoloRank() - a2.getAttractionRank().getSoloRank();
		case Couple:
			return (a1, a2) -> a1.getAttractionRank().getCoupleRank() - a2.getAttractionRank().getCoupleRank();
		case  Friends:
		default:
			return (a1, a2) -> a1.getAttractionRank().getFriendsRank() - a2.getAttractionRank().getFriendsRank();
		}
	}

}
