package com.thoughtworks.thoughtferret.test.integration;

import static com.thoughtworks.thoughtferret.DateUtils.*;
import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.view.moodgraph.graph.Chronology;
import com.thoughtworks.thoughtferret.view.moodgraph.graph.Timeline;

public class TimelineTests extends TestCase {

	public void testTimelineShouldCreateOneUnitPerMonth() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(dateAndTime("05/06/2010 09:45"), 1, Cities.SYDNEY),
				new MoodRating(dateAndTime("20/09/2010 12:56"), 1, Cities.SYDNEY));
		Chronology chronology = new Chronology(date("01/06/2010"), 1);
		Timeline timeline = new Timeline(ratings, chronology);
		assertEquals(4, timeline.getUnits().size());
	}
	
	public void testTimelineShouldCalculateTheRightSizeBasedOnTheNumberOfDaysInAMonth() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(dateAndTime("05/06/2010 09:45"), 1, Cities.SYDNEY),
				new MoodRating(dateAndTime("20/09/2010 12:56"), 1, Cities.SYDNEY));
		Chronology chronology = new Chronology(date("01/06/2010"), 1);
		Timeline timeline = new Timeline(ratings, chronology);
		int expectedWidth = 30 + 31 + 31 + 30;
		assertEquals(expectedWidth, timeline.getWidth());
	}
	
	public void testTimelineShouldCalculateTheRightSizeInARealisticScreenSize() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(dateAndTime("05/06/2010 09:45"), 1, Cities.SYDNEY),
				new MoodRating(dateAndTime("20/09/2010 12:56"), 1, Cities.SYDNEY));
		int screenSize = 800;
		int pixelsPerDay = screenSize / (4 * 30);
		Chronology chronology = new Chronology(date("01/06/2010"), pixelsPerDay);
		Timeline timeline = new Timeline(ratings, chronology);
		int expectedWidth = (30 + 31 + 31 + 30) * pixelsPerDay;
		assertEquals(expectedWidth, timeline.getWidth());
	}
	
}
