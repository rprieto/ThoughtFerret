package com.thoughtworks.thoughtferret.model.mood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.joda.time.Period;

public class MoodRatings {

	private List<MoodRating> ratings;
	
	public MoodRatings(MoodRating...moodRatings) {
		this.ratings = Arrays.asList(moodRatings);
	}

	public MoodRatings(List<MoodRating> moodRatings) {
		this.ratings = moodRatings;
	}
	
	public List<MoodRating> getValues() {
		return ratings;
	}

	public MoodRating getFirst() {
		return ratings.get(0);
	}
	
	public MoodRating getLast() {
		return ratings.get(ratings.size() - 1);
	}
	
	public List<String> getMonths() {
		LocalDateTime firstDate = ratings.get(0).getLoggedDate();
		LocalDateTime lastDate = ratings.get(ratings.size() - 1).getLoggedDate();
		
		List<String> months = new ArrayList<String>();
		int nbMonths = Months.monthsBetween(firstDate, lastDate).getMonths();
		for (int i = 0 ; i < nbMonths + 2; ++i) {
			LocalDateTime unit = firstDate.plus(Period.months(i));
			months.add(unit.toString("MMMM yyyy"));
		}
		
		return months;
	}
	
}
