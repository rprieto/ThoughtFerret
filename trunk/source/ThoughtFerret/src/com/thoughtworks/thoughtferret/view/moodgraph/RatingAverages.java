package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.List;

import org.joda.time.DateTime;

import com.thoughtworks.thoughtferret.model.mood.MoodRatings;

public class RatingAverages {
	
	List<Average> averages;

	public RatingAverages(MoodRatings moodRatings, int nbDaysInPeriod) {
		
		DateTime startDate = moodRatings.getFirst().getLoggedDate();
		DateTime endDate = moodRatings.getFirst().getLoggedDate();
		
		// loop over the mood ratings, and group them by periods
		
		
	}
	
}

