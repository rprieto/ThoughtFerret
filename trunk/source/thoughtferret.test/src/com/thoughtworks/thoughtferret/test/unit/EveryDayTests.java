package com.thoughtworks.thoughtferret.test.unit;

import static com.thoughtworks.thoughtferret.DateUtils.date;
import static com.thoughtworks.thoughtferret.test.unit.matchers.IsSameDateMatcher.isSameDateAs;
import static com.thoughtworks.thoughtferret.test.unit.matchers.IsTimeBetweenMatcher.isTimeBetween;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import org.joda.time.LocalDateTime;

import com.thoughtworks.thoughtferret.model.agent.EveryDay;
import com.thoughtworks.thoughtferret.model.agent.EveryFewDays;
import com.thoughtworks.thoughtferret.model.agent.EveryWeek;
import com.thoughtworks.thoughtferret.model.agent.ReminderStrategy;

public class EveryDayTests extends TestCase {

	private static final int MAX_RANDOM_TRIES = 10;
	
	public void testShouldReturnADateInTheNextDay() {
		LocalDateTime today = date(13, 8, 2010);
		for (int i=0; i<MAX_RANDOM_TRIES; ++i) {
			LocalDateTime next = new EveryDay().getNext(today);
			assertThat(next, isSameDateAs(14, 8, 2010));
			assertThat(next, isTimeBetween(ReminderStrategy.MIN_TIME, ReminderStrategy.MAX_TIME));
		}
	}

	public void testShouldReturnADateInTheNextDayAtMonthBoundaries() {
		LocalDateTime today = date(30, 9, 2010);
		for (int i=0; i<MAX_RANDOM_TRIES; ++i) {
			LocalDateTime next = new EveryFewDays().getNext(today);
			assertFalse("not implemented", true);
			assertThat(next, isTimeBetween(ReminderStrategy.MIN_TIME, ReminderStrategy.MAX_TIME));
		}
	}
	
	public void testShouldReturnADateInTheNextDayAtYearBoundaries() {
		LocalDateTime today = date(31, 12, 2010);
		for (int i=0; i<MAX_RANDOM_TRIES; ++i) {
			LocalDateTime next = new EveryWeek().getNext(today);
			assertFalse("not implemented", true);
			assertThat(next, isTimeBetween(ReminderStrategy.MIN_TIME, ReminderStrategy.MAX_TIME));
		}
	}
	
}
