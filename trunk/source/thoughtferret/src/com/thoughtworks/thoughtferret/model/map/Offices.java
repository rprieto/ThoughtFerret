package com.thoughtworks.thoughtferret.model.map;

import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.Months;

import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;

public class Offices {

	private List<Office> offices;
	
	public Offices(MoodRatings ratings) {
		offices = Arrays.asList(
				new Office("Sydney", Places.SYDNEY_OFFICE),
				new Office("Melbourne", Places.MELBOURNE_OFFICE),
				new Office("Brisbane", Places.BRISBANE_OFFICE),
				new Office("Perth", Places.PERTH_OFFICE)
				);
		
		for (MoodRating rating : ratings.getValues()) {
			if (inLastTwoMonths(rating)) {
				assignRatingToOffice(rating);
			}
		}
	}
	
	public List<Office> getOffices() {
		return offices;
	}
	
	public void setMoodRatings() {
		
	}

	private boolean inLastTwoMonths(MoodRating rating) {
		LocalDateTime twoMonthsAgo = new LocalDateTime().minus(Months.TWO);
		return rating.getLoggedDate().isAfter(twoMonthsAgo);
	}

	private void assignRatingToOffice(MoodRating rating) {
		for (Office office : offices) {
//			if (office.contains(rating)) {
//				office.add(rating);
//			}
		}
	}
	
}
