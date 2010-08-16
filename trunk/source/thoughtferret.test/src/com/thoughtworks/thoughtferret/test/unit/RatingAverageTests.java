package com.thoughtworks.thoughtferret.test.unit;

import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.ensure.Ensure;
import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;

public class RatingAverageTests extends TestCase {

	public void testRatingAveragesShouldBeEqualBasedOnOneDecimalDigit() {
		verifyRatingAverageEquals(3, 3, true);
		verifyRatingAverageEquals(3.1, 3.12, true);
		verifyRatingAverageEquals(3.99, 3.76, false);
		verifyRatingAverageEquals(0.111, 0.123, true);
	}

	public void testRatingAveragesShouldBeRoundedHalfUp() {
		verifyRatingAverageEquals(0.10, 0.19, false);
		verifyRatingAverageEquals(0.16, 0.17, true);
		verifyRatingAverageEquals(1.19, 1.21, true);
	}
	
	public void testRatingAverageShouldBeInTheBoundariesOfMoodRating() {
		Ensure.thatBreaksContract(new Runnable() {
			public void run() {	new RatingAverage(-1d);	}
		});
		Ensure.thatBreaksContract(new Runnable() {
			public void run() {	new RatingAverage(MoodRating.BEST_RATING + 1d);	}
		});
	}
	
	private void verifyRatingAverageEquals(double value1, double value2, boolean expectedEquals) {
		String message = value1 + " equals " + value2;
		assertEquals(message, expectedEquals, new RatingAverage(value1).equals(new RatingAverage(value2)));
	}
	
}
