package com.thoughtworks.thoughtferret.test.unit;

import org.joda.time.LocalDateTime;

import junit.framework.TestCase;

import com.thoughtworks.thoughtferret.model.FerretFrequency;

import static com.thoughtworks.thoughtferret.test.unit.DateBuilder.*;

public class FerretFrequencyTests extends TestCase {

	public void testShouldReturnADateInTheNextDay() {
		LocalDateTime next = FerretFrequency.EVERY_DAY.getNext(date(13, 8, 2010));
		assertIsReasonableTime(next);
	}

	public void testShouldReturnADateAFewDaysLater() {
		LocalDateTime next = FerretFrequency.EVERY_FEW_DAYS.getNext(date(13, 8, 2010));
		assertIsReasonableTime(next);
	}
	
	public void testShouldReturnADateSometimeNextWeek() {
		LocalDateTime next = FerretFrequency.EVERY_WEEK.getNext(date(13, 8, 2010));
		assertIsReasonableTime(next);
	}
	
	private void assertIsReasonableTime(LocalDateTime date) {
		assertTrue(date.getHourOfDay() > 9);
		assertTrue(date.getHourOfDay() < 18);
	}
	
}
