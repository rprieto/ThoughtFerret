package com.thoughtworks.thoughtferret.test.unit;

import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverages;
import static com.thoughtworks.thoughtferret.DateUtils.*;

public class RatingAveragesTests extends TestCase {

	public void testShouldCalculateAveragesWhenAllPeriodsHaveRatings() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(date("04-06-2010 09:45"), 3, Cities.SYDNEY),
				new MoodRating(date("22-07-2010 16:28"), 3, Cities.SYDNEY),
				new MoodRating(date("17-08-2010 16:03"), 1, Cities.SYDNEY),
				new MoodRating(date("09-09-2010 18:16"), 2, Cities.SYDNEY));
		RatingAverages averages = new RatingAverages(ratings, 30);
		assertEquals(4, averages.getAverages().size());
	}

	public void testShouldCalculateAveragesWhenAPeriodHasNoRatings() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(date("04-06-2010 09:45"), 3, Cities.SYDNEY),
				new MoodRating(date("17-08-2010 16:03"), 1, Cities.SYDNEY),
				new MoodRating(date("09-09-2010 18:16"), 2, Cities.SYDNEY));
		RatingAverages averages = new RatingAverages(ratings, 30);
		assertEquals(4, averages.getAverages().size());
		assertFalse(averages.getAverages().get(1).hasRatings());
	}

}
