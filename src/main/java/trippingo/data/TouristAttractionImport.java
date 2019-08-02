package trippingo.data;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import trippingo.model.AttractionCategory;
import trippingo.model.Review;
import trippingo.model.TouristAttraction;
import trippingo.model.TravellerType;
import trippingo.repository.TouristAttractionRepository;
import trippingo.service.TouristAttractionController;
import trippingo.utils.StringUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TouristAttractionImport {
	
	@Autowired
	private TouristAttractionRepository repository ;
	
	@Test
	public void importData() {
		JSONParser parser = new JSONParser();
		Set<String> errors = new HashSet<String>();
		
		try {
			String filePath = new File("").getCanonicalPath() + "\\data\\sentosa.json";
			Reader reader = new FileReader(filePath);
			JSONArray attractions = (JSONArray) parser.parse(reader);
            Iterator<JSONObject> iterator = attractions.iterator();
            while (iterator.hasNext()) {
				JSONObject object = iterator.next();
                TouristAttraction attraction;
				try {
					attraction = parseAttraction(object);
					repository.save(attraction);
				} catch (Exception e) {
					e.printStackTrace();
					String name =(object.get("attractions").toString());
					errors.add("Exception for attractionName:"+name+ "\n" + e.getMessage());
				}
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
		}
		
	}

	private static TouristAttraction parseAttraction(JSONObject object)  throws Exception{
		TouristAttraction attraction = new TouristAttraction();
		attraction.setId(UUID.randomUUID().toString());
		/**
		 * NAME, DESCRIPTION, KEYWORDS
		 */
		attraction.setName(object.get("attractions").toString());
		attraction.setDescription(object.get("about").toString());
		List<String> keywords = (JSONArray)object.get("keywords");
		attraction.setKeywords(keywords.stream().map(String::trim).collect(Collectors.joining("#")));
		/**
		 * CATEGORY
		 * one or more categories? predefined limited categories, or unlimited categories?
		 */
		attraction.setCategory(parseCategory(object.get("categories").toString()));
		
		/**
		 * POSTAL CODE
		 * Do postal codes need correction?  Locations without postal codes(Fort Siloso, Tanjong Beach,Sentosa Boardalk)
		 */
		attraction.setPostalCode(parsePostalCode(object.get("postal").toString()));
		/**
		 * OPERATING HOURS
		 */
		attraction.setOpeningTime(parseTime(object.get("opening hours").toString()));
		attraction.setClosingTime(parseTime(object.get("closing hours").toString()));
		/**
		 * DURATION
		 * Take mean?
		 */
		attraction.setRecommendedDuration(parseDuration(object.get("duration").toString()));
		/**
		 * REVIEWS
		 */
		List<JSONObject> reviewsArr = (JSONArray) object.get("review");
		Set<Review> reviews = reviewsArr.stream().map(TouristAttractionImport::parseReview).collect(Collectors.toSet());
		attraction.setReviews(reviews);
			
		return attraction;
	}
	
	private static Review parseReview(JSONObject object) {
		Review review = new Review();
		review.setSerialNo(Integer.valueOf(object.get("no").toString()));
		review.setRating(Integer.valueOf(object.get("rating").toString()));
		review.setUsername(object.get("username").toString());
		review.setVisitDate(parseDate(object.get("visitdate").toString()));
		review.setTravellerType(TravellerType.fromTripadvisorValue(object.get("traveltype").toString()));
		return review;
	}
	
	private static LocalDate parseDate(String dateString) {
		String[] splitDateStr = dateString.split(" ");
    	Date monthDate;
		try {
			monthDate = new SimpleDateFormat("MMMM").parse(splitDateStr[0]);
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTime(monthDate);
	    	int month = cal.get(Calendar.MONTH);
			return new LocalDate(Integer.parseInt(splitDateStr[1]), month+1, 1);
		} catch (java.text.ParseException e) {
			return LocalDate.now();
		}
    }

	private static Double parseDuration(String duration) {
		if(StringUtils.isNotNull(duration) && duration!= "-") {
			return new Double(0);
		}
		return null;
	}

	private static AttractionCategory parseCategory(String categories) {
		return AttractionCategory.Landmark;
	}

	private static LocalTime parseTime(String time) {
		if(StringUtils.isNotNull(time) && time.trim()!= "-") {
			time = time.trim();
			boolean isPM = time.endsWith("PM");
			int additive = isPM? 12 : 0;
			int middleIndex = time.indexOf(":");
			String hour = time.substring(0,middleIndex);
			String minutes = time.substring(middleIndex+1,middleIndex+3);
			return new LocalTime(Integer.parseInt(hour)+additive, Integer.parseInt(minutes));
		}
		return null;
			
	}
	
	private static String parsePostalCode(String location) {
		String[] postalSplit = location.split(" ");
		String postalCode = postalSplit[postalSplit.length - 1];
		if(postalCode.matches("\\d*") && postalCode.trim().length() == 6) 
			return postalCode;
		
		else 
			return null;
			
	}

}
