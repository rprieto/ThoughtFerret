package com.thoughtworks.thoughtferret.integration.database;

import static com.thoughtworks.thoughtferret.DateUtils.dateAndTime;
import android.content.Context;

import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.tags.MoodTag;
import com.thoughtworks.thoughtferret.model.tags.MoodTags;

public class FakeData {
	
	MoodRatingDao ratingsDao;
	MoodTagsDao tagsDao;
	
	public FakeData(Context context) {
		ratingsDao = new MoodRatingDao(context);
		tagsDao = new MoodTagsDao(context);
	}
	
	public void createHistory() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(dateAndTime("18/01/2010 10:41"), 5, Cities.SYDNEY),
				new MoodRating(dateAndTime("19/01/2010 10:41"), 5, Cities.SYDNEY),
				new MoodRating(dateAndTime("23/01/2010 10:41"), 4, Cities.SYDNEY),
				new MoodRating(dateAndTime("04/02/2010 16:03"), 5, Cities.SYDNEY),
				new MoodRating(dateAndTime("13/02/2010 04:53"), 3, Cities.SYDNEY),
				new MoodRating(dateAndTime("17/02/2010 12:27"), 4, Cities.SYDNEY),
				new MoodRating(dateAndTime("07/03/2010 16:31"), 2, Cities.SYDNEY),
				new MoodRating(dateAndTime("14/03/2010 11:05"), 1, Cities.SYDNEY),
				new MoodRating(dateAndTime("16/03/2010 20:09"), 2, Cities.SYDNEY),
				new MoodRating(dateAndTime("29/03/2010 02:22"), 2, Cities.SYDNEY),
				new MoodRating(dateAndTime("04/04/2010 08:35"), 4, Cities.SYDNEY),
				new MoodRating(dateAndTime("17/04/2010 13:06"), 3, Cities.SYDNEY),
				new MoodRating(dateAndTime("05/05/2010 14:04"), 4, Cities.SYDNEY),
				new MoodRating(dateAndTime("06/05/2010 09:08"), 2, Cities.SYDNEY),
				new MoodRating(dateAndTime("22/05/2010 13:17"), 5, Cities.SYDNEY),
				new MoodRating(dateAndTime("27/05/2010 12:56"), 4, Cities.SYDNEY),
				new MoodRating(dateAndTime("05/06/2010 09:45"), 3, Cities.SYDNEY),
				new MoodRating(dateAndTime("17/06/2010 16:28"), 3, Cities.BRISBANE),
				new MoodRating(dateAndTime("26/06/2010 11:45"), 4, Cities.BRISBANE),
				new MoodRating(dateAndTime("27/06/2010 08:39"), 5, Cities.BRISBANE),
				new MoodRating(dateAndTime("16/07/2010 09:45"), 4, Cities.BRISBANE),
				new MoodRating(dateAndTime("18/07/2010 16:28"), 3, Cities.BRISBANE),
				new MoodRating(dateAndTime("19/07/2010 11:45"), 3, Cities.BRISBANE),
				new MoodRating(dateAndTime("25/07/2010 08:39"), 4, Cities.BRISBANE),
				new MoodRating(dateAndTime("26/07/2010 15:20"), 3, Cities.BRISBANE),
				new MoodRating(dateAndTime("29/07/2010 12:11"), 1, Cities.BRISBANE),
				new MoodRating(dateAndTime("01/08/2010 17:43"), 2, Cities.BRISBANE),
				new MoodRating(dateAndTime("02/08/2010 09:18"), 2, Cities.BRISBANE),
				new MoodRating(dateAndTime("06/08/2010 11:59"), 3, Cities.BRISBANE),
				new MoodRating(dateAndTime("17/08/2010 17:04"), 4, Cities.BRISBANE),
				new MoodRating(dateAndTime("23/08/2010 12:38"), 5, Cities.BRISBANE),
				new MoodRating(dateAndTime("24/08/2010 10:41"), 4, Cities.BRISBANE),
				new MoodRating(dateAndTime("29/08/2010 16:03"), 3, Cities.BRISBANE),
				new MoodRating(dateAndTime("02/09/2010 18:16"), 2, Cities.BRISBANE),
				new MoodRating(dateAndTime("07/09/2010 17:04"), 3, Cities.BRISBANE),
				new MoodRating(dateAndTime("08/09/2010 12:38"), 2, Cities.BRISBANE)
				);
		
		ratingsDao.deleteAll();
		ratingsDao.persist(ratings);
		
		MoodTags tags = new MoodTags(
				new MoodTag("android", 		15, (int) (15 * 4.6)),
				new MoodTag("coaching", 	12, (int) (12 * 3.8)),
				new MoodTag("3g modem", 	12, (int) (12 * 1.7)),
				new MoodTag("open source", 	10, (int) (10 * 4.2)),
				new MoodTag("agile", 		9,  (int) ( 9 * 3.9)),
				new MoodTag("qantas", 		9,  (int) ( 9 * 2.8)),
				new MoodTag("java", 		8,  (int) ( 8 * 3.0)),
				new MoodTag("debugging", 	7,  (int) ( 7 * 1.3)),
				new MoodTag("brisbane", 	5,  (int) ( 5 * 4.1)),
				new MoodTag("macbook", 		4,  (int) ( 4 * 3.2)),
				new MoodTag("travelling", 	3,  (int) ( 3 * 2.5)),
				new MoodTag("weather", 		2,  (int) ( 2 * 3.7)),
				new MoodTag("twitter", 		2,  (int) ( 2 * 2.4)),
				new MoodTag("nandos", 		1,  (int) ( 1 * 4.7)));
		
		
		tagsDao.deleteAll();
		tagsDao.persist(tags);
	}
	
}
