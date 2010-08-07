package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

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
	
	public int getAverageTimesTen() {
		if (hasRatings()) {
			int sum = 0;
			for (MoodRating rating : ratings) {
				sum += rating.getRating();
			}
			return sum * 10 / ratings.size();
		}
		else {
			return 0;
		}
	}
	
}

