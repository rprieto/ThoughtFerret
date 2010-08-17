package com.thoughtworks.thoughtferret.model.ratings;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;

import com.thoughtworks.thoughtferret.model.ratings.MoodRating;

public class RatingPeriod {
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private List<MoodRating> ratings;
	
	public RatingPeriod(LocalDateTime startDate, LocalDateTime endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		ratings = new ArrayList<MoodRating>();
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public boolean contains(LocalDateTime date) {
		return date.isAfter(startDate) && date.isBefore(endDate);
	}
	
	public void addRating(MoodRating rating) {
		if (contains(rating.getLoggedDate()) == false) {
			throw new IllegalArgumentException("Rating is outside of the target period");
		}
		ratings.add(rating);
	}
	
	public boolean hasRatings() {
		return ratings.size() > 0;
	}
	
	public RatingAverage getAverage() {
		if (hasRatings()) {
			double sum = 0;
			for (MoodRating rating : ratings) {
				sum += rating.getRating();
			}
			return new RatingAverage(sum / ratings.size());
		}
		else {
			return new RatingAverage(0d);
		}
	}
	
	public int getDays() {
		return Days.daysBetween(startDate, endDate).getDays();
	}

	@Override
	public String toString() {
		return "RatingPeriod [avg=" + getAverage() + "]";
	}
}

