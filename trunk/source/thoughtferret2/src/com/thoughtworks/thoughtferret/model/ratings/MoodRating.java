package com.thoughtworks.thoughtferret.model.ratings;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MoodRating {

	private LocalDateTime loggedDate;
	
	private int rating;
	
	public static final int BEST_RATING = 5;  
	
	public MoodRating(int rating) {
		loggedDate = new LocalDateTime();
		this.rating = rating;
	}

	public MoodRating(long timestamp, int rating) {
		loggedDate = new LocalDateTime(timestamp);
		this.rating = rating;
	}
	
	public MoodRating(String date, int rating) {
		DateTimeFormatter parser = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
		loggedDate = new LocalDateTime(parser.parseDateTime(date));
		this.rating = rating;
	}

	public LocalDateTime getLoggedDate() {
		return loggedDate;
	}
	
	public int getRating() {
		return rating;
	}
	
}
