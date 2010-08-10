package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;

public class RatingPeriod {
	
	private DateTime startDate;
	private DateTime endDate;
	private List<MoodRating> ratings;
	
	public RatingPeriod(DateTime startDate, DateTime endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		ratings = new ArrayList<MoodRating>();
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public boolean contains(DateTime date) {
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

