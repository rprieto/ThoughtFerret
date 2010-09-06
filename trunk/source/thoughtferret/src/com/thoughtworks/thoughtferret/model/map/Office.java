package com.thoughtworks.thoughtferret.model.map;

import static com.thoughtworks.thoughtferret.DateUtils.oneMonthAgo;
import static com.thoughtworks.thoughtferret.DateUtils.today;
import static com.thoughtworks.thoughtferret.DateUtils.twoMonthsAgo;

import org.joda.time.LocalDateTime;

import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;

public class Office {

	private Location location;
	private final MoodRatings ratings;
	
	public Office(Location location, MoodRatings ratings) {
		this.location = location;
		this.ratings = ratings;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public boolean hasAverage() {
		return ratings.getValues().size() > 0;
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
