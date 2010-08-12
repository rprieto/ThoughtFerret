package com.thoughtworks.thoughtferret.view.moodgraph;

import static com.thoughtworks.thoughtferret.DateUtils.endOfMonth;
import static com.thoughtworks.thoughtferret.DateUtils.startOfMonth;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

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
		LocalDateTime current = startOfMonth(ratings.getFirst().getLoggedDate());
		LocalDateTime lastDate = endOfMonth(ratings.getLast().getLoggedDate());		
		while (current.isBefore(lastDate)) {
			LocalDateTime next = endOfMonth(current).plus(Seconds.ONE);
			String label = getMonthName(current);
			int nbDays = Days.daysBetween(current, next).getDays();
			int monthSize = nbDays * pixelsPerDay;
			Rect rect = new Rect(x, top, x + monthSize, bottom);
			timeUnits.add(new TimeUnit(rect, label));
			current = next;
			x += monthSize;
		}
	}
	
	private String getMonthName(LocalDateTime date) {
		if (pixelsPerDay * 30 > 150) {
			return date.toString("MMM yyyy");
		} else {
			return date.toString("MM-yy");
		}
	}
	
}
