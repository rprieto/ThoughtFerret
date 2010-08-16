package com.thoughtworks.thoughtferret.integration.database;

import android.content.Context;

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
				new MoodRating("05-06-2010 09:45", 3),
				new MoodRating("17-06-2010 16:28", 5),
				new MoodRating("26-06-2010 11:45", 5),
				new MoodRating("27-06-2010 08:39", 4),
				new MoodRating("16-07-2010 09:45", 5),
				new MoodRating("18-07-2010 16:28", 3),
				new MoodRating("19-07-2010 11:45", 4),
				new MoodRating("25-07-2010 08:39", 4),
				new MoodRating("26-07-2010 15:20", 2),
				new MoodRating("29-07-2010 12:11", 1),
				new MoodRating("01-08-2010 17:43", 2),
				new MoodRating("02-08-2010 09:18", 2),
				new MoodRating("06-08-2010 11:59", 3),
				new MoodRating("17-08-2010 17:04", 3),
				new MoodRating("23-08-2010 12:38", 4),
				new MoodRating("24-08-2010 10:41", 4),
				new MoodRating("29-08-2010 16:03", 5),
				new MoodRating("02-09-2010 18:16", 2),
				new MoodRating("07-09-2010 17:04", 3),
				new MoodRating("08-09-2010 12:38", 3),
				new MoodRating("18-09-2010 10:41", 4),
				new MoodRating("24-09-2010 16:03", 5),
				new MoodRating("01-10-2010 04:53", 3),
				new MoodRating("05-10-2010 12:27", 2),
				new MoodRating("20-10-2010 16:31", 1),
				new MoodRating("22-10-2010 11:05", 4),
				new MoodRating("16-11-2010 20:09", 3),
				new MoodRating("16-11-2010 02:22", 3),
				new MoodRating("04-12-2010 08:35", 5),
				new MoodRating("05-12-2010 13:06", 4),
				new MoodRating("10-12-2010 14:04", 3),
				new MoodRating("12-12-2010 09:08", 4),
				new MoodRating("17-12-2010 13:17", 2),
				new MoodRating("20-12-2010 12:56", 4));
		
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
