package com.thoughtworks.thoughtferret.test.unit;

import static com.thoughtworks.thoughtferret.test.unit.DateBuilder.date;
import static com.thoughtworks.thoughtferret.test.unit.DateBuilder.timestamp;
import static com.thoughtworks.thoughtferret.test.unit.matchers.HasAverageMatcher.hasAverage;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.view.moodgraph.RatingPeriod;

public class RatingPeriodTests extends TestCase {

	public void testShouldNotAcceptRatingOutsideOfPeriod() {
		RatingPeriod period = new RatingPeriod(date(13, 7, 2010), date(21, 8, 2010));
		try {
			period.addRating(new MoodRating(timestamp(4, 9, 2010), 3));
			assertFalse("Should not have added the rating", true);
		} catch (IllegalArgumentException ex) {
			assertTrue(true);
		}
	}

	public void testShouldCalculateAverageWithNoRatings() {
		RatingPeriod period = new RatingPeriod(date(13, 7, 2010), date(21, 8, 2010));
		assertFalse(period.hasRatings());
		assertThat(period, hasAverage(0));
	}
	
	public void testShouldCalculateAverageWithOneRating() {
		RatingPeriod period = new RatingPeriod(date(13, 7, 2010), date(21, 8, 2010));
		period.addRating(new MoodRating(timestamp(4, 8, 2010), 3));
		assertTrue(period.hasRatings());
		assertThat(period, hasAverage(3));
	}
	
	public void testShouldCalculateAverageWithMultipleRatings() {
		RatingPeriod period = new RatingPeriod(date(13, 7, 2010), date(21, 8, 2010));
		period.addRating(new MoodRating(timestamp(20, 7, 2010), 3));
		period.addRating(new MoodRating(timestamp(28, 7, 2010), 4));
		period.addRating(new MoodRating(timestamp(6, 8, 2010), 4));
		period.addRating(new MoodRating(timestamp(12, 8, 2010), 2));
		assertTrue(period.hasRatings());
		assertThat(period, hasAverage(3.3));
	}

}
