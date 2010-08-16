package com.thoughtworks.thoughtferret.test.unit;

import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverages;

public class RatingAveragesTests extends TestCase {

	public void testShouldCalculateAveragesWhenAllPeriodsHaveRatings() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating("04-06-2010 09:45", 3),
				new MoodRating("22-07-2010 16:28", 3),
				new MoodRating("17-08-2010 16:03", 1),
				new MoodRating("09-09-2010 18:16", 2));
		RatingAverages averages = new RatingAverages(ratings, 30);
		assertEquals(4, averages.getAverages().size());
	}

	public void testShouldCalculateAveragesWhenAPeriodHasNoRatings() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating("04-06-2010 09:45", 3),
				new MoodRating("17-08-2010 16:03", 1),
				new MoodRating("09-09-2010 18:16", 2));
		RatingAverages averages = new RatingAverages(ratings, 30);
		assertEquals(4, averages.getAverages().size());
		assertFalse(averages.getAverages().get(1).hasRatings());
	}

}
