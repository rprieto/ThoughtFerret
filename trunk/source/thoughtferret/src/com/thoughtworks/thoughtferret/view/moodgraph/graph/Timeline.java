package com.thoughtworks.thoughtferret.view.moodgraph.graph;

import static com.thoughtworks.thoughtferret.DateUtils.endOfMonth;
import static com.thoughtworks.thoughtferret.DateUtils.startOfMonth;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

import android.graphics.Rect;

import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;

public class Timeline {

	public static final int HEIGHT = 50;
	
	private List<TimeUnit> timeUnits;
	private Chronology chronology;
	
	public Timeline(MoodRatings ratings, Chronology chronology) {
		timeUnits = new ArrayList<TimeUnit>();
		this.chronology = chronology;
		createTimeline(ratings, 0, HEIGHT);
	}
		
	public List<TimeUnit> getUnits() {
		return timeUnits;
	}
	
	public int getWidth() {
		TimeUnit last = timeUnits.get(timeUnits.size() - 1);
		return last.getRect().right;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	private void createTimeline(MoodRatings ratings, int top, int bottom) {
		LocalDateTime start = startOfMonth(ratings.getFirst().getLoggedDate());
		LocalDateTime lastDate = endOfMonth(ratings.getLast().getLoggedDate());		
		while (start.isBefore(lastDate)) {
			LocalDateTime end = endOfMonth(start).plus(Seconds.ONE);
			String label = getMonthName(start);
			int x1 = chronology.getX(start);
			int x2 = chronology.getX(end);
			Rect rect = new Rect(x1, top, x2, bottom);
			timeUnits.add(new TimeUnit(rect, label));
			start = end;
		}
	}
	
	private String getMonthName(LocalDateTime date) {
		//if (pixelsPerDay * 30 > 150) {
			return date.toString("MMM yyyy");
		//} else {
		//	return date.toString("MM-yy");
		//}
	}
	
}
