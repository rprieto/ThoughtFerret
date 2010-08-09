package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;

public class RatingAverages {
	
	List<RatingPeriod> periods;

	public RatingAverages(MoodRatings moodRatings, int nbDaysInPeriod) {
		periods = new ArrayList<RatingPeriod>();
		calculatePeriods(moodRatings, nbDaysInPeriod);
		calculateAverages(moodRatings);
	}
	
	private void calculatePeriods(MoodRatings moodRatings, int nbDaysInPeriod) {
		DateTime startDate = moodRatings.getFirst().getLoggedDate();
		DateTime endDate = moodRatings.getLast().getLoggedDate();
		
		DateTime startMonth = startDate.withDayOfMonth(1);
		DateTime endMonth = endDate.plusMonths(1).withDayOfMonth(1);
		
		for (DateTime start = startMonth; start.isBefore(endMonth); start = start.plusDays(nbDaysInPeriod)) {
			RatingPeriod period = new RatingPeriod(start, start.plusDays(nbDaysInPeriod));
			periods.add(period);
		}
	}
	
	private void calculateAverages(MoodRatings moodRatings) {
		int currentPeriod = 0;
		for (MoodRating rating : moodRatings.getValues()) {
			while (periods.get(currentPeriod).contains(rating.getLoggedDate()) == false) {
				++currentPeriod;
			}
			periods.get(currentPeriod).addRating(rating);
		}
	}
	
	public List<RatingPeriod> getAverages() {
		return periods;
	}
	
	public RatingPeriod getFirst() {
		return periods.get(0);
	}
	
	public RatingPeriod getLast() {
		return periods.get(periods.size() - 1);
	}
	
}
