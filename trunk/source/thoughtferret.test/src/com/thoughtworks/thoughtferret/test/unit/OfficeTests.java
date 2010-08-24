package com.thoughtworks.thoughtferret.test.unit;

import static com.thoughtworks.thoughtferret.DateUtils.today;
import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.map.Office;
import com.thoughtworks.thoughtferret.model.map.Trend;
import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;


public class OfficeTests extends TestCase {

	public void testShouldCalculateAverageBasedOnTheLastMonth() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(today().minusDays(40), 1, Cities.SYDNEY),
				new MoodRating(today().minusDays(20), 2, Cities.SYDNEY),
				new MoodRating(today().minusDays(10), 3, Cities.SYDNEY));
		Office sydney = new Office("Sydney", Cities.SYDNEY, ratings);
		
		assertEquals(new RatingAverage(2.5), sydney.getAverage());
	}
	
	public void testShouldCalculateTrendBasedOnTheTwoLastMonths() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(today().minusDays(70), 5, Cities.SYDNEY),
				new MoodRating(today().minusDays(40), 1, Cities.SYDNEY),
				new MoodRating(today().minusDays(20), 2, Cities.SYDNEY),
				new MoodRating(today().minusDays(10), 3, Cities.SYDNEY));
		Office sydney = new Office("Sydney", Cities.SYDNEY, ratings);
		
		assertEquals(Trend.UP, sydney.getTrend());
	}
	
	public void testThatTrendShouldBeStableBasedOnTheTwoLastMonths() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating(today().minusDays(45), 5, Cities.SYDNEY),
				new MoodRating(today().minusDays(40), 1, Cities.SYDNEY),
				new MoodRating(today().minusDays(20), 3, Cities.SYDNEY),
				new MoodRating(today().minusDays(10), 3, Cities.SYDNEY));
		Office sydney = new Office("Sydney", Cities.SYDNEY, ratings);
		
		assertEquals(Trend.STABLE, sydney.getTrend());
	}

	
	//	public void testShouldAllocateRatingsToTheRightOfficeWhenInTheOffice() {
//		MoodRatings ratings = new MoodRatings(
//				new MoodRating(today(), 2, Cities.SYDNEY),
//				new MoodRating(today(), 3, Cities.MELBOURNE));
//		Office sydney = new Office("Sydney", Cities.SYDNEY, ratings, 10km);
//		Office melbourne = new Office("Melbourne", Cities.MELBOURNE, ratings, 20km);
//		Office brisbane = new Office("Brisbane", Cities.BRISBANE, ratings);
//		assertEquals(new RatingAverage(2d), sydney.getAverage());
//		assertEquals(new RatingAverage(3d), melbourne.getAverage());
//		assertEquals(new RatingAverage(0d), brisbane.getAverage());
//	}
	
}
