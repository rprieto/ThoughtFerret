package com.thoughtworks.thoughtferret.model.map;

import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;

public class Office {

	private static final int RADIUS_IN_METERS = 100 * 1000;

	private String name;
	private Coordinates coords;
	private MoodRatings ratings;
	
	public Office(String name, Coordinates coords) {
		this.name = name;
		this.coords = coords;
	}
	
	public String getName() {
		return name;
	}
	
	public Coordinates getCoordinates() {
		return coords;
	}
	
	public RatingAverage getAverage() {
		return new RatingAverage(3.0);
	}
	
	public Trend getTrend() {
		return Trend.UP;
	}
	
}
