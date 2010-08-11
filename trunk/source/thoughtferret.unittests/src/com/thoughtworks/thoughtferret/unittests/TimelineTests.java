package com.thoughtworks.thoughtferret.unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;
import com.thoughtworks.thoughtferret.view.moodgraph.Timeline;

public class TimelineTests {

	@Test
	public void timelineShouldCreateOneUnitPerMonth() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating("05-06-2010 09:45", 1),
				new MoodRating("20-09-2010 12:56", 1));
		Timeline timeline = new Timeline(ratings, 1);
		assertEquals(4, timeline.getUnits().size());
	}
	
	@Test
	public void timelineShouldCalculateTheRightSizeBasedOnTheNumberOfDaysInAMonth() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating("05-06-2010 09:45", 1),
				new MoodRating("20-09-2010 12:56", 1));
		Timeline timeline = new Timeline(ratings, 1);
		int expectedWidth = 30 + 31 + 31 + 30;
		assertEquals(expectedWidth, timeline.getWidth());
	}
	
}
