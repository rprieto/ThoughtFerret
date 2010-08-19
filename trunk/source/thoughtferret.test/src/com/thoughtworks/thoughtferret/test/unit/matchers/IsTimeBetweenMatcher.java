package com.thoughtworks.thoughtferret.test.unit.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.internal.matchers.TypeSafeMatcher;

public class IsTimeBetweenMatcher {
	public static Matcher<LocalDateTime> isTimeBetween(final LocalTime start, final LocalTime end) {
		return new TypeSafeMatcher<LocalDateTime>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("should between").appendValue(start);
				description.appendText("and").appendValue(end);
			}
			
			@Override
			public boolean matchesSafely(LocalDateTime other) {
				return other.getHourOfDay() >= start.getHourOfDay()
					&& other.getMinuteOfHour() >= start.getMinuteOfHour()
					&& other.getHourOfDay() <= end.getHourOfDay()
					&& other.getMinuteOfHour() <= end.getMinuteOfHour();
			}
		};
	}
}