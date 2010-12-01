package com.thoughtworks.thoughtferret.test.unit.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;
import com.thoughtworks.thoughtferret.model.ratings.RatingPeriod;

public class HasAverageMatcher {
	public static Matcher<RatingPeriod> hasAverage(final double expectedRatingAverageValue) {
		return new TypeSafeMatcher<RatingPeriod>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("average").appendValue(expectedRatingAverageValue);
			}
			
			@Override
			public boolean matchesSafely(RatingPeriod other) {
				RatingAverage ratingAverage = other.getAverage();
				RatingAverage expectedRatingAverage = new RatingAverage(expectedRatingAverageValue);
				return ratingAverage.equals(expectedRatingAverage);
			}
		};
	}
}