package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.List;

import org.joda.time.DateTime;

import com.thoughtworks.thoughtferret.model.mood.MoodRatings;

public class RatingAverages {
	
	List<RatingPeriod> periods;

	public RatingAverages(MoodRatings moodRatings, int nbDaysInPeriod) {
		calculatePeriods(moodRatings, nbDaysInPeriod);
		calculateAverages(moodRatings);
	}
	
	private void calculatePeriods(MoodRatings moodRatings, int nbDaysInPeriod) {
		DateTime startDate = moodRatings.getFirst().getLoggedDate();
		DateTime endDate = moodRatings.getFirst().getLoggedDate();
		
		DateTime startMonth = startDate.withDayOfMonth(0);
		DateTime endMonth = endDate.plusMonths(1).withDayOfMonth(0);
		
		for (DateTime start = startMonth; start.isBefore(endMonth); start = start.plusDays(nbDaysInPeriod)) {
			RatingPeriod period = new RatingPeriod(start, start.plusDays(nbDaysInPeriod));
			periods.add(period);
		}
	}
	
	private void calculateAverages(MoodRatings moodRatings) {
		int currentPeriod = 0;
		int currentRating = 0;
		
	}
	
	public List<RatingPeriod> getAverages() {
		return periods;
	}
	
}
