package com.thoughtworks.thoughtferret.model.mood;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MoodRating {

	private DateTime loggedDate;
	
	private int rating;
	
	public static final int BEST_RATING = 5;  
	
	public MoodRating(int rating) {
		loggedDate = new DateTime();
		this.rating = rating;
	}

	public MoodRating(long timestamp, int rating) {
		loggedDate = new DateTime(timestamp);
		this.rating = rating;
	}
	
	public MoodRating(String date, int rating) {
		DateTimeFormatter parser = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
		loggedDate = parser.parseDateTime(date);
		this.rating = rating;
	}

	public DateTime getLoggedDate() {
		return loggedDate;
	}
	
	public int getRating() {
		return rating;
	}
	
}
