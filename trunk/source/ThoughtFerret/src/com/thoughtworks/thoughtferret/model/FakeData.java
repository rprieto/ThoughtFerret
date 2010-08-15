package com.thoughtworks.thoughtferret.model;

import android.content.Context;

import com.thoughtworks.thoughtferret.integration.database.MoodRatingDao;
import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;

public class FakeData {
	
	MoodRatingDao dao;
	
	public FakeData(Context context) {
		dao = new MoodRatingDao(context);
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
		dao.deleteAll();
		dao.persist(ratings);
	}
	
}
