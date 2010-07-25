package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class MonthlyRatings {

	private static final int MONTH_SIZE = 240;
	private static final int TIMELINE_HEIGHT = 50;
	private static final int POINT_SPACING = 60;
	
	private MoodRatings moodRatings;

	private List<TimeUnit> timeUnits;
	private List<Point> points;
	
	private Rect graphRect;
	
	public MonthlyRatings(MoodRatings ratings, int graphHeight) {
		this.moodRatings = ratings;
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
			Rect rect = new Rect(x, top, x + MONTH_SIZE, bottom);
			TimeUnit unit = new TimeUnit(rect, unitName);
			timeUnits.add(unit);
			x += MONTH_SIZE;
		}
	}
	
	private void calculateGraphSize(int graphHeight) {
		graphRect = new Rect(0, 0, MONTH_SIZE * timeUnits.size(), graphHeight);
	}
	
	private void createPoints() {
		points = new ArrayList<Point>();
		for (MoodRating moodRating : moodRatings.getValues()) {
			Log.i("Graph", String.format("%s : %d", moodRating.getLoggedDate(), moodRating.getRating()));
		}
		
		Random rnd = new Random();
		points = new ArrayList<Point>();
		
		int maxHeight = graphRect.height() - TIMELINE_HEIGHT * 2;
		int yBase = graphRect.height() - TIMELINE_HEIGHT;
		int pointX = 0;
		
		for (int i = 0; i < 50; i++) {
			int pointY = (int) (yBase - (rnd.nextInt(5) + 1) * (maxHeight / 6f));
			points.add(new Point(pointX, pointY));
			pointX += POINT_SPACING;
		}
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
		final int subdibisions = MONTH_SIZE / 4;
		List<Rect> grid = new ArrayList<Rect>();
    	for (int x = graphRect.left; x < graphRect.right; x += subdibisions) {
    		grid.add(new Rect(x, graphRect.top + TIMELINE_HEIGHT, x, graphRect.bottom - TIMELINE_HEIGHT));
    	}
    	for (int y = graphRect.top + TIMELINE_HEIGHT; y < graphRect.bottom - TIMELINE_HEIGHT; y += subdibisions) {
    		grid.add(new Rect(graphRect.left, y, graphRect.right, y));
    	}
		return grid;
	}
	
}
