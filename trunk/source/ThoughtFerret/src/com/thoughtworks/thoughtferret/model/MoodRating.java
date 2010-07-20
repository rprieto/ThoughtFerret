package com.thoughtworks.thoughtferret.model;

import org.joda.time.DateTime;

public class MoodRating {

	private DateTime loggedDate;
	
	private int rating;
	
	public MoodRating(int rating) {
		loggedDate = new DateTime();
		this.rating = rating;
	}

	public MoodRating(long timestamp, int rating) {
		loggedDate = new DateTime(timestamp);
		this.rating = rating;
	}

	public DateTime getLoggedDate() {
		return loggedDate;
	}
	
	public int getRating() {
		return rating;
	}
	
}
