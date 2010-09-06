package com.thoughtworks.thoughtferret.test.unit;

import static com.thoughtworks.thoughtferret.DateUtils.today;
import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.map.Location;
import com.thoughtworks.thoughtferret.model.map.Office;
import com.thoughtworks.thoughtferret.model.map.Trend;
import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;


public class OfficeTests extends TestCase {

	private Location sydney = new Location("Sydney", "SYD", Cities.SYDNEY);
	
	public void testShouldCalculateAverageBasedOnTheLastMonth() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(today().minusDays(40), 1, Cities.SYDNEY),
				new MoodRating(today().minusDays(20), 2, Cities.SYDNEY),
				new MoodRating(today().minusDays(10), 3, Cities.SYDNEY));
		Office office = new Office(sydney, ratings);
		assertEquals(new RatingAverage(2.5), office.getAverage());
	}
	
	public void testShouldCalculateTrendBasedOnTheTwoLastMonths() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(today().minusDays(70), 5, Cities.SYDNEY),
				new MoodRating(today().minusDays(40), 1, Cities.SYDNEY),
				new MoodRating(today().minusDays(20), 2, Cities.SYDNEY),
				new MoodRating(today().minusDays(10), 3, Cities.SYDNEY));
		Office office = new Office(sydney, ratings);
		assertEquals(Trend.UP, office.getTrend());
	}
	
	public void testThatTrendShouldBeStableBasedOnTheTwoLastMonths() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(today().minusDays(45), 5, Cities.SYDNEY),
				new MoodRating(today().minusDays(40), 1, Cities.SYDNEY),
				new MoodRating(today().minusDays(20), 3, Cities.SYDNEY),
				new MoodRating(today().minusDays(10), 3, Cities.SYDNEY));
		Office office = new Office(sydney, ratings);
		assertEquals(Trend.STABLE, office.getTrend());
	}
	
}
