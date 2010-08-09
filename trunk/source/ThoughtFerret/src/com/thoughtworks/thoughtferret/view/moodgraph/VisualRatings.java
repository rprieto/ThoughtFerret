package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.graphics.Rect;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;
import com.thoughtworks.thoughtferret.view.Screen;

public class VisualRatings {

	private static final int PERIOD_IN_PIXELS = 100;
	
	private MoodRatings moodRatings;
	private RatingAverages averages;
	private Timeline timeline;
	private List<Point> points;
	
	private Rect graphRect;
	private Screen screen;
	
	private ZoomLevel currentZoom = ZoomLevel.QUARTER;

	public VisualRatings(MoodRatings ratings, Screen screen) {
		this.screen = screen;	
		this.moodRatings = ratings;
		refresh();
	}

	private void refresh() {
        int daySize = currentZoom.getPixelsPerDay(screen.width());
        int nbDaysInPeriod = PERIOD_IN_PIXELS / daySize;
        averages = new RatingAverages(moodRatings, nbDaysInPeriod);
        timeline = new Timeline(moodRatings, daySize);
		createPoints();		
		calculateGraphSize();
	}
	
	private void calculateGraphSize() {
		Point lastPoint = points.get(points.size() - 1);
		graphRect = new Rect(0, 0, lastPoint.x, screen.height());
	}
	
	private void createPoints() {		
		points = new ArrayList<Point>();
		points.add(new Point(0, getY(averages.getFirst())));
		Point lastPoint = null;
		
		int x = 0;
		for (RatingPeriod period : averages.getAverages()) {
			int periodSize = period.getDays() * getDaySize();
			if (period.hasRatings()) {
				int y = getY(period);
				lastPoint = new Point(x + periodSize, y);
				points.add(new Point(x + periodSize / 2, y));
			}
			x += periodSize;
		}

		points.add(lastPoint);
	}
	
	private int getY(RatingPeriod period) {
		int bestY = timeline.getHeight() * 2;
		int worstY = screen.height() - timeline.getHeight() * 2;
		int interval = (worstY - bestY) / (MoodRating.BEST_RATING - 1);
		return worstY - (period.getAverageTimesTen() - 10) * interval / 10;
	}
	
	public Rect getGraphRect() {
		return graphRect;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public Timeline getTimeline() {
		return timeline;
	}
	
	public List<Rect> getGrid() {
		
		List<Rect> grid = new ArrayList<Rect>();
		final int verticalGridSize = 60;
    	
		int x = 0;
		for (RatingPeriod period : averages.getAverages()) {
			x += period.getDays() * getDaySize();
			grid.add(new Rect(x, graphRect.top + timeline.getHeight(), x, graphRect.bottom - timeline.getHeight()));
		}
		
    	for (int y = graphRect.top + timeline.getHeight(); y < graphRect.bottom - timeline.getHeight(); y += verticalGridSize) {
    		grid.add(new Rect(graphRect.left, y, graphRect.right, y));
    	}
    	
		return grid;
	}
	
	public void setZoom(ZoomLevel level) {
		currentZoom = level;
		refresh();
	}

	private int getDaySize() {
		return currentZoom.getPixelsPerDay(screen.width());
	}
	
}
