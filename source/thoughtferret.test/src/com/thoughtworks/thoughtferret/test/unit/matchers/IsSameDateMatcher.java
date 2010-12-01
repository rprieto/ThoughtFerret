package com.thoughtworks.thoughtferret.test.unit.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.joda.time.LocalDateTime;
import org.junit.internal.matchers.TypeSafeMatcher;

public class IsSameDateMatcher {
	public static Matcher<LocalDateTime> isSameDateAs(final int day, final int month, final int year) {
		return new TypeSafeMatcher<LocalDateTime>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("date").appendValue(day);
				description.appendText("/").appendValue(month);
				description.appendText("/").appendValue(year);
			}
			
			@Override
			public boolean matchesSafely(LocalDateTime other) {
				return other.getDayOfMonth() == day
					&& other.getMonthOfYear() == month
					&& other.getYear() == year;
			}
		};
	}
}
