package com.thoughtworks.thoughtferret.test.unit;

import org.joda.time.LocalDateTime;

public class DateBuilder {

	public static LocalDateTime date(int day, int month, int year) {
		return new LocalDateTime(year, month, day, 0, 0, 0, 0);
	}

	public static long timestamp(int day, int month, int year) {
		return new LocalDateTime(year, month, day, 0, 0, 0, 0).toDateTime().getMillis();
	}
	
}
