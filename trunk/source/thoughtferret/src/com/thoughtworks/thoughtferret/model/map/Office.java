package com.thoughtworks.thoughtferret.model.map;

import static com.thoughtworks.thoughtferret.DateUtils.oneMonthAgo;
import static com.thoughtworks.thoughtferret.DateUtils.today;
import static com.thoughtworks.thoughtferret.DateUtils.twoMonthsAgo;

import org.joda.time.LocalDateTime;

import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;

public class Office {

	private final String name;
	private final Coordinates coords;
	private final MoodRatings ratings;
	
	public Office(String name, Coordinates coords, MoodRatings ratings) {
		this.name = name;
		this.coords = coords;
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
		RatingAverage previous = getAverage(twoMonthsAgo(), oneMonthAgo());
		RatingAverage current = getAverage(oneMonthAgo(), today());
		if (previous.doubleValue() < current.doubleValue()) {
			return Trend.UP;
		} else if (previous.equals(current)) {
			return Trend.STABLE;
		} else {
			return Trend.DOWN;
		}
	}
	
	private RatingAverage getAverage(LocalDateTime start, LocalDateTime end) {
		MoodRatings subset = ratings.getSubset(start, end);
		return subset.getAverage();
	}
	
}
