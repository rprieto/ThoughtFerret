package com.thoughtworks.thoughtferret.test.unit;

import static com.thoughtworks.thoughtferret.DateUtils.*;
import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;

public class MoodRatingsTests extends TestCase {

	public void testShouldReturnAnAverageOfZeroWhenItDoesntHaveAnyRatings() {
		MoodRatings ratings = new MoodRatings();
		assertEquals(new RatingAverage(0d), ratings.getAverage());
	}
	
	public void testShouldCalculateAverageOfMultipleRatings() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(today(), 3, Cities.SYDNEY),
				new MoodRating(today(), 3, Cities.SYDNEY),
				new MoodRating(today(), 1, Cities.SYDNEY),
				new MoodRating(today(), 2, Cities.SYDNEY));
		assertEquals(new RatingAverage(2.3), ratings.getAverage());
	}

	public void testShouldFilterRatingsByDatePeriod() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(date("25/12/2009"), 3, Cities.SYDNEY),
				new MoodRating(date("12/01/2010"), 3, Cities.SYDNEY),
				new MoodRating(date("19/01/2010"), 1, Cities.SYDNEY),
				new MoodRating(date("03/02/2010"), 2, Cities.SYDNEY));
		MoodRatings subset = ratings.getSubset(date("29/12/2009"), date("01/02/2010"));
		assertEquals(2, subset.getValues().size());
	}

}
