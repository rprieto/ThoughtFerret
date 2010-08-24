package com.thoughtworks.thoughtferret.model.map;

import static com.thoughtworks.thoughtferret.DateUtils.oneMonthAgo;
import static com.thoughtworks.thoughtferret.DateUtils.today;

import org.joda.time.LocalDateTime;

import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;

public class Office {

	private String name;
	private Coordinates coords;
	private MoodRatings ratings;
	
	public Office(String name, Coordinates coords) {
		this.name = name;
		this.coords = coords;
	}
	
	public void setRatings(MoodRatings ratings) {
		this.ratings = ratings;
	}
	
	public String getName() {
		return name;
	}
	
	public Coordinates getCoordinates() {
		return coords;
	}
	
	public RatingAverage getAverage() {
		return getAverage(oneMonthAgo(), today());
	}
	
	public Trend getTrend() {
		RatingAverage previous = getAverage(oneMonthAgo(), today());
		RatingAverage current = getAverage();
		if (previous.doubleValue() < current.doubleValue()) {
			return Trend.UP;
		} else if (previous.equals(current)) {
			return Trend.STABLE;
		} else {
			return Trend.DOWN;
		}
	}
	
	private RatingAverage getAverage(LocalDateTime start, LocalDateTime end) {
		int sum = 0;
		for (MoodRating rating : ratings.getValues()) {
			if (rating.getLoggedDate().isAfter(start) && rating.getLoggedDate().isBefore(end)) {
				sum += rating.getRating();
			}
		}
		double average = sum / (double) ratings.getValues().size();
		return new RatingAverage(average);
	}
	
}
