package com.thoughtworks.thoughtferret.model;

import android.content.Context;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatingDao;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;

public class FakeData {
	
	MoodRatingDao dao;
	
	public FakeData(Context context) {
		dao = new MoodRatingDao(context);
	}
	
	public void createHistory() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating("16-07-2010 09:45", 3),
				new MoodRating("18-07-2010 16:28", 3),
				new MoodRating("19-07-2010 11:45", 2),
				new MoodRating("25-07-2010 08:39", 4),
				new MoodRating("26-07-2010 15:20", 5),
				new MoodRating("29-07-2010 12:11", 4),
				new MoodRating("01-08-2010 17:43", 2),
				new MoodRating("02-08-2010 09:18", 1),
				new MoodRating("06-08-2010 11:59", 3),
				new MoodRating("17-08-2010 17:04", 3),
				new MoodRating("23-08-2010 12:38", 4),
				new MoodRating("24-08-2010 10:41", 2),
				new MoodRating("29-08-2010 16:03", 1),
				new MoodRating("02-09-2010 18:16", 2));
		dao.deleteAll();
		dao.persist(ratings);
	}
	
}
