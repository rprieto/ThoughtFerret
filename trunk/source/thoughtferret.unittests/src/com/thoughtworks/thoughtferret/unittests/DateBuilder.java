package com.thoughtworks.thoughtferret.unittests;

import org.joda.time.DateTime;

public class DateBuilder {

	public static DateTime date(int day, int month, int year) {
		return new DateTime(year, month, day, 0, 0, 0, 0);
	}

	public static long timestamp(int day, int month, int year) {
		return new DateTime(year, month, day, 0, 0, 0, 0).getMillis();
	}
	
}
