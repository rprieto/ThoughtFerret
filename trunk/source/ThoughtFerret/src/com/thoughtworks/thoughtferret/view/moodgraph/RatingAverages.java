package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.List;

import org.joda.time.DateTime;

import com.thoughtworks.thoughtferret.model.mood.MoodRatings;

public class RatingAverages {
	
	List<Average> averages;

	public RatingAverages(MoodRatings moodRatings, int nbDaysInPeriod) {
		calculatePeriods(moodRatings, nbDaysInPeriod);
		calculateAverages(moodRatings);
	}
	
	private void calculatePeriods(MoodRatings moodRatings, int nbDaysInPeriod) {
		DateTime startDate = moodRatings.getFirst().getLoggedDate();
		DateTime endDate = moodRatings.getFirst().getLoggedDate();
		
		DateTime startMonth = startDate.withDayOfMonth(0);
		DateTime endMonth = endDate.plusMonths(1).withDayOfMonth(0);
		
		for (DateTime period = startMonth; period.isBefore(endMonth); period = period.plusDays(nbDaysInPeriod)) {
			Average average = new Average(period, period.plusDays(nbDaysInPeriod));
			averages.add(average);
		}
	}
	
	private void calculateAverages(MoodRatings moodRatings) {
		averages.get(0).setRating(0);
	}
	
	public List<Average> getAverages() {
		return averages;
	}
	
}
