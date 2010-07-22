package com.thoughtworks.thoughtferret.model;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;

public class FakeData {
	
	MoodRatingDao dao;
	
	public FakeData(Context context) {
		dao = new MoodRatingDao(context);
	}
	
	public void createHistory() {
		List<MoodRating> ratings = Arrays.asList(
				newMood("16-07-2010 09:45", 3),
				newMood("18-07-2010 16:28", 3),
				newMood("19-07-2010 11:45", 2),
				newMood("25-07-2010 08:39", 4),
				newMood("26-07-2010 15:20", 5),
				newMood("29-07-2010 12:11", 4),
				newMood("01-08-2010 17:43", 2),
				newMood("02-08-2010 09:18", 1),
				newMood("06-08-2010 11:59", 3));
		dao.deleteAll();
		dao.persist(ratings);
	}
	
	private MoodRating newMood(String loggedDate, int rating) {
		DateTimeFormatter parser = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
		DateTime date = parser.parseDateTime(loggedDate);
		return new MoodRating(date.getMillis(), rating);
	}
	
}
