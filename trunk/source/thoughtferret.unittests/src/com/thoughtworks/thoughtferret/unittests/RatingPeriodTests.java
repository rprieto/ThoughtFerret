package com.thoughtworks.thoughtferret.unittests;

import static com.thoughtworks.thoughtferret.unittests.DateBuilder.date;
import static com.thoughtworks.thoughtferret.unittests.DateBuilder.timestamp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.view.moodgraph.RatingPeriod;

public class RatingPeriodTests {

	@Test
	public void shouldNotAcceptRatingOutsideOfPeriod() {
		RatingPeriod period = new RatingPeriod(date(13, 7, 2010), date(21, 8, 2010));
		try {
			period.addRating(new MoodRating(timestamp(4, 9, 2010), 3));
			assertFalse("Should not have added the rating", true);
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void shouldCalculateAverageWithNoRatings() {
		RatingPeriod period = new RatingPeriod(date(13, 7, 2010), date(21, 8, 2010));
		assertFalse(period.hasRatings());
		assertEquals(0, period.getAverageTimesTen());
	}
	
	@Test
	public void shouldCalculateAverageWithOneRating() {
		RatingPeriod period = new RatingPeriod(date(13, 7, 2010), date(21, 8, 2010));
		period.addRating(new MoodRating(timestamp(4, 8, 2010), 3));
		assertTrue(period.hasRatings());
		assertEquals(30, period.getAverageTimesTen());
	}
	
	@Test
	public void shouldCalculateAverageWithMultipleRatings() {
		RatingPeriod period = new RatingPeriod(date(13, 7, 2010), date(21, 8, 2010));
		period.addRating(new MoodRating(timestamp(20, 7, 2010), 3));
		period.addRating(new MoodRating(timestamp(28, 7, 2010), 4));
		period.addRating(new MoodRating(timestamp(6, 8, 2010), 4));
		period.addRating(new MoodRating(timestamp(12, 8, 2010), 2));
		assertTrue(period.hasRatings());
		assertEquals(32, period.getAverageTimesTen());
	}
	
}
