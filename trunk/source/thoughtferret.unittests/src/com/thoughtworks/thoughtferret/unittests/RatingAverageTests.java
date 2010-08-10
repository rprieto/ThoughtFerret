package com.thoughtworks.thoughtferret.unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thoughtworks.thoughtferret.view.moodgraph.RatingAverage;

public class RatingAverageTests {

	@Test
	public void ratingAveragesShouldBeEqualBasedOnOneDecimalDigit() {
		verifyRatingAverageEquals(3, 3, true);
		verifyRatingAverageEquals(3.1, 3.12, true);
		verifyRatingAverageEquals(3.99, 3.76, false);
		verifyRatingAverageEquals(0.111, 0.123, true);
	}

	@Test
	public void ratingAveragesShouldBeRoundedHalfUp() {
		verifyRatingAverageEquals(0.10, 0.19, false);
		verifyRatingAverageEquals(0.16, 0.17, true);
		verifyRatingAverageEquals(1.19, 1.21, true);
	}
	
	private void verifyRatingAverageEquals(double value1, double value2, boolean expectedEquals) {
		String message = value1 + " equals " + value2;
		assertEquals(message, expectedEquals, new RatingAverage(value1).equals(new RatingAverage(value2)));
	}
	
}
