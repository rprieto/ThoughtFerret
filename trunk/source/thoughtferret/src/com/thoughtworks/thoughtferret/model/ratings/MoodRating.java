package com.thoughtworks.thoughtferret.model.ratings;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.thoughtworks.thoughtferret.model.map.Coordinates;

public class MoodRating {

	public static final int BEST_RATING = 5;  

	private LocalDateTime loggedDate;
	private int rating;
	private Coordinates coordinates;
	
	public MoodRating(LocalDateTime loggedDate, int rating, Coordinates coordinates) {
		this.loggedDate = loggedDate;
		this.rating = rating;
		this.coordinates = coordinates;
	}
	
	public MoodRating(int rating, Coordinates coordinates) {
		this(new LocalDateTime(), rating, coordinates);
	}

	public MoodRating(long timestamp, int rating, Coordinates coordinates) {
		this(new LocalDateTime(timestamp), rating, coordinates);
	}
	
	public MoodRating(String date, int rating, Coordinates coordinates) {
		DateTimeFormatter parser = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
		loggedDate = new LocalDateTime(parser.parseDateTime(date));
		this.rating = rating;
		this.coordinates = coordinates;
	}

	public LocalDateTime getLoggedDate() {
		return loggedDate;
	}
	
	public int getRating() {
		return rating;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}
	
}
