package com.thoughtworks.thoughtferret.test.unit;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;

public class MoodRatingsTests extends TestCase {

	public void testShouldGetMonthsFromListOfRatings() {
		MoodRatings ratings = new MoodRatings(
				new MoodRating("16-07-2010 09:45", 3),
				new MoodRating("18-07-2010 16:28", 3),
				new MoodRating("25-07-2010 16:28", 3),
				new MoodRating("02-09-2010 16:28", 3),
				new MoodRating("06-09-2010 11:45", 2));
		
		List<String> months = ratings.getMonths();
		
		assertEquals(Arrays.asList("July 2010", "August 2010", "September 2010"), months);
	}
	
}
