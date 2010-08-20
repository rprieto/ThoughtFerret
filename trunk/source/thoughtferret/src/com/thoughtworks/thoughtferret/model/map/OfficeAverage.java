package com.thoughtworks.thoughtferret.model.map;

import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;

public class OfficeAverage {

	GeoZone office;
	
	MoodRatings ratings;
	
	public RatingAverage getAverage() {
		return new RatingAverage(3.0);
	}
	
	public Trend getTrend() {
		return Trend.UP;
	}
	
}
