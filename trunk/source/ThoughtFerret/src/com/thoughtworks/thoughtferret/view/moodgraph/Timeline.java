package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import android.graphics.Rect;

import com.thoughtworks.thoughtferret.model.mood.MoodRatings;

public class Timeline {

	private static final int TIMELINE_HEIGHT = 50;
	
	private List<TimeUnit> timeUnits;
	private int pixelsPerDay;
	
	public Timeline(MoodRatings ratings, int pixelsPerDay) {
		timeUnits = new ArrayList<TimeUnit>();
		this.pixelsPerDay = pixelsPerDay;
		createTimeline(ratings, 0, TIMELINE_HEIGHT);
	}
		
	public List<TimeUnit> getUnits() {
		return timeUnits;
	}
	
	public int getWidth() {
		TimeUnit last = timeUnits.get(timeUnits.size() - 1);
		return last.getRect().right;
	}
	
	public int getHeight() {
		return TIMELINE_HEIGHT;
	}
	
	private void createTimeline(MoodRatings ratings, int top, int bottom) {
		int x = 0;
		DateTime current = startOfMonth(ratings.getFirst().getLoggedDate());
		DateTime lastDate = startOfNextMonth(ratings.getLast().getLoggedDate());		
		while (current.isBefore(lastDate)) {
			DateTime next = startOfNextMonth(current);
			String label = current.toString("MMMM yyyy");
			int nbDays = Days.daysBetween(current, next).getDays();
			int monthSize = nbDays * pixelsPerDay;
			Rect rect = new Rect(x, top, x + monthSize, bottom);
			timeUnits.add(new TimeUnit(rect, label));
			current = next;
			x += monthSize;
		}
	}
	
	private DateTime startOfMonth(DateTime date) {
		return date.withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
	}
	
	private DateTime startOfNextMonth(DateTime date) {
		return date.plusMonths(1).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
	}
	
}
