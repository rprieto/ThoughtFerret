package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;

import android.graphics.Point;
import android.graphics.Rect;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;

public class MonthlyRatings {

	private static final int TIMELINE_HEIGHT = 50;
	
	private MoodRatings moodRatings;

	private List<TimeUnit> timeUnits;
	private List<Point> points;
	
	private Rect graphRect;
	private Rect screenRect;
	
	private ZoomLevel currentZoom = ZoomLevel.QUARTER;

	public MonthlyRatings(MoodRatings ratings, Rect screenRect) {
		this.moodRatings = ratings;
		this.screenRect = screenRect;
		calculateGraph(screenRect.height());
	}

	private void calculateGraph(int graphHeight) {
		createTimeline(graphHeight);
		calculateGraphSize(graphHeight);
		createPoints();		
	}
	
	private void createTimeline(int graphHeight) {
		timeUnits = new ArrayList<TimeUnit>();
		createTimeline(graphHeight - TIMELINE_HEIGHT, graphHeight);
		createTimeline(0, TIMELINE_HEIGHT);
	}
	
	private void createTimeline(int top, int bottom) {
		int x = 0;		
		for (String unitName : moodRatings.getMonths()) {
			Rect rect = new Rect(x, top, x + getMonthSize(), bottom);
			TimeUnit unit = new TimeUnit(rect, unitName);
			timeUnits.add(unit);
			x += getMonthSize();
		}
	}
	
	private void calculateGraphSize(int graphHeight) {
		graphRect = new Rect(0, 0, getMonthSize() * timeUnits.size() / 2, graphHeight);
	}
	
	private void createPoints() {
		int bestY = TIMELINE_HEIGHT * 2;
		int worstY = graphRect.height() - TIMELINE_HEIGHT * 2;
		int interval = (worstY - bestY) / (MoodRating.BEST_RATING - 1);
		
		points = new ArrayList<Point>();
		for (MoodRating moodRating : moodRatings.getValues()) {
			Days difference = Days.daysBetween(moodRatings.getFirst().getLoggedDate(), moodRating.getLoggedDate());
			int x = (int) (difference.getDays() * (getMonthSize() / 30f));
			int y = worstY - (moodRating.getRating() - 1) * interval;
			points.add(new Point(x, y));
		}
		
		int lastX = points.get(points.size() - 1).x;
		int lastY = graphRect.height() - TIMELINE_HEIGHT;
		points.add(new Point(lastX, lastY));
		
		lastX = timeUnits.get(timeUnits.size() - 1).getRect().right;
		lastY = graphRect.height() - TIMELINE_HEIGHT;
		points.add(new Point(lastX, lastY));
	}
	
	public Rect getGraphRect() {
		return graphRect;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public List<TimeUnit> getTimeUnits() {
		return timeUnits;
	}
	
	public List<Rect> getGrid() {
		final int subdibisions = getMonthSize() / 4;
		List<Rect> grid = new ArrayList<Rect>();
    	for (int x = graphRect.left; x < graphRect.right; x += subdibisions) {
    		grid.add(new Rect(x, graphRect.top + TIMELINE_HEIGHT, x, graphRect.bottom - TIMELINE_HEIGHT));
    	}
    	for (int y = graphRect.top + TIMELINE_HEIGHT; y < graphRect.bottom - TIMELINE_HEIGHT; y += subdibisions) {
    		grid.add(new Rect(graphRect.left, y, graphRect.right, y));
    	}
		return grid;
	}
	
	public void setZoom(ZoomLevel level) {
		currentZoom = level;
		calculateGraph(screenRect.height());
	}
	
	private int getMonthSize() {
		return currentZoom.getMonthSize(screenRect.width());
	}
	
}
