package com.thoughtworks.thoughtferret.test.integration;

import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;
import com.thoughtworks.thoughtferret.view.moodgraph.graph.Timeline;

public class TimelineTests extends TestCase {

//	public void testTimelineShouldCreateOneUnitPerMonth() {
//		MoodRatings ratings = new MoodRatings(
//				new MoodRating("05-06-2010 09:45", 1),
//				new MoodRating("20-09-2010 12:56", 1));
//		Timeline timeline = new Timeline(ratings, 1);
//		assertEquals(4, timeline.getUnits().size());
//	}
//	
//	public void testTimelineShouldCalculateTheRightSizeBasedOnTheNumberOfDaysInAMonth() {
//		MoodRatings ratings = new MoodRatings(
//				new MoodRating("05-06-2010 09:45", 1),
//				new MoodRating("20-09-2010 12:56", 1));
//		Timeline timeline = new Timeline(ratings, 1);
//		int expectedWidth = 30 + 31 + 31 + 30;
//		assertEquals(expectedWidth, timeline.getWidth());
//	}
//	
//	public void testTimelineShouldCalculateTheRightSizeInARealisticScreenSize() {
//		MoodRatings ratings = new MoodRatings(
//				new MoodRating("05-06-2010 09:45", 1),
//				new MoodRating("20-09-2010 12:56", 1));
//		int screenSize = 800;
//		int pixelsPerDay = screenSize / (4 * 30);
//		Timeline timeline = new Timeline(ratings, pixelsPerDay);
//		int expectedWidth = (30 + 31 + 31 + 30) * pixelsPerDay;
//		assertEquals(expectedWidth, timeline.getWidth());
//	}
//	
//	public void testTimelineShouldUseLongMonthNamesWhenThereIsRoom() {
//		MoodRatings ratings = new MoodRatings(
//				new MoodRating("05-06-2010 09:45", 1),
//				new MoodRating("20-09-2010 12:56", 1));
//		Timeline timeline = new Timeline(ratings, 10);
//		assertEquals("June 2010", timeline.getUnits().get(0).getText());
//	}
//	
//	public void testTimelineShouldUseShortMonthNamesWhenOutOfSpace() {
//		MoodRatings ratings = new MoodRatings(
//				new MoodRating("05-06-2010 09:45", 1),
//				new MoodRating("20-09-2010 12:56", 1));
//		Timeline timeline = new Timeline(ratings, 2);
//		assertEquals("06-10", timeline.getUnits().get(0).getText());
//	}
	
}
