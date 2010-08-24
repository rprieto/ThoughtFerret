package com.thoughtworks.thoughtferret;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.thoughtworks.thoughtferret.model.map.Coordinates;

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

	public static LocalDateTime date(String dateAndTime) {
		DateTimeFormatter parser = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
		return new LocalDateTime(parser.parseDateTime(dateAndTime));
	}
	
	public static long timestamp(int day, int month, int year) {
		return new LocalDateTime(year, month, day, 0, 0, 0, 0).toDateTime().getMillis();
	}

	public static LocalTime time(int hours, int minutes) {
		return new LocalTime(hours, minutes);
	}

	public static LocalTime randomTime(LocalTime min, LocalTime max) {
		Minutes range = Minutes.minutesBetween(min, max);
		int numberOfMinutes = MathUtils.getRandom(0, range.getMinutes());
		return min.plusMinutes(numberOfMinutes);
	}
	
	public static LocalDateTime dateWithTime(LocalDateTime date, LocalTime time) {
		return date.withHourOfDay(time.getHourOfDay())
		   		   .withMinuteOfHour(time.getMinuteOfHour());
	}
	
	public static String asReadable(LocalDateTime date) {
		return date.toString("dd/MM/yyyy hh:mm:ss aa");
	}
	
	public static LocalDateTime today() {
		return new LocalDateTime();
	}
	
	public static LocalDateTime oneMonthAgo() {
		return new LocalDateTime().minus(Months.ONE);	
	}
	
	public static LocalDateTime twoMonthsAgo() {
		return new LocalDateTime().minus(Months.ONE);	
	}
	
}

