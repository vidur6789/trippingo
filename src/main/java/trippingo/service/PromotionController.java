package trippingo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import trippingo.model.Promotion;
import trippingo.repository.PromotionRepository;

@RestController
@RequestMapping(path="/promotions")
public class PromotionController {

	
	@Autowired
	private PromotionRepository repository;
	
	@Autowired
	private TouristAttractionController attractionService;
	
	
	@GetMapping
	public List<Promotion> fetchPromotionsById(@RequestParam(value="attractionId") Long attractionId) {
		
		List<Long> promotionIds = repository.findAllIdsByAttractionId(attractionId);
		return (List<Promotion>) repository.findAllById(promotionIds);
	}
}
