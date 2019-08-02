package trippingo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import trippingo.model.TouristAttraction;
import trippingo.repository.TouristAttractionRepository;
import trippingo.utils.StringUtils;

@RestController
@RequestMapping(path="/attraction")
public class TouristAttractionController {
	
	@Autowired 
	private TouristAttractionRepository attractionRepository;
	
	@GetMapping
	public TouristAttraction fetchAttraction(@RequestParam(value="id") String id) {
		return attractionRepository.findById(id).orElse(null);
	}
	
	@PutMapping
	public TouristAttraction saveAttraction(@RequestBody TouristAttraction touristAttraction) {
		if(StringUtils.isNotNull(touristAttraction.getId()))
			touristAttraction.setId(UUID.randomUUID().toString());
		attractionRepository.save(touristAttraction);
		return touristAttraction;
	}
}
