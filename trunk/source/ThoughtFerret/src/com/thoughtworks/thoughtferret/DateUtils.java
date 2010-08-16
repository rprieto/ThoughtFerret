package com.thoughtworks.thoughtferret;

import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

public final class DateUtils {

	public static LocalDateTime startOfMonth(LocalDateTime date) {
		return date.withDayOfMonth(1)
				   .withHourOfDay(0)
				   .withMinuteOfHour(0)
				   .withSecondOfMinute(0)
				   .withMillisOfSecond(0);
	}
	
	public static LocalDateTime endOfMonth(LocalDateTime date) {
		return date.plusMonths(1)
				   .withDayOfMonth(1)
				   .withHourOfDay(0)
				   .withMinuteOfHour(0)
				   .withSecondOfMinute(0)
				   .withMillisOfSecond(0)
				   .minus(Seconds.ONE);
	}
	
	public static LocalDateTime date(int day, int month, int year) {
		return new LocalDateTime(year, month, day, 0, 0, 0, 0);
	}

	public static long timestamp(int day, int month, int year) {
		return new LocalDateTime(year, month, day, 0, 0, 0, 0).toDateTime().getMillis();
	}
	
}
