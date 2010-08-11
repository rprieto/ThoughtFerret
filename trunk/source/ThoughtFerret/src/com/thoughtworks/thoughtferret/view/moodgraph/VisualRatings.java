package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;
import com.thoughtworks.thoughtferret.view.Screen;

public class VisualRatings {

	private static final int PERIOD_IN_PIXELS = 100;
	
	private ChartOptions chartOptions;
	private MoodRatings moodRatings;
	private RatingAverages averages;
	private Timeline timeline;
	private Grid grid;
	private List<Point> points;
	
	private Rect graphRect;
	private Screen screen;
	
	public VisualRatings(MoodRatings ratings, Screen screen) {
		this.screen = screen;	
		this.moodRatings = ratings;
		chartOptions = new ChartOptions(ChartType.BAR, 30 * 4);
		refresh();
	}

	private void refresh() {
        int nbDaysInPeriod = PERIOD_IN_PIXELS / getDaySize();
        averages = new RatingAverages(moodRatings, nbDaysInPeriod);
        timeline = new Timeline(moodRatings, getDaySize());
        graphRect = new Rect(0, 0, timeline.getWidth(), screen.height());
        grid = new Grid(averages.getAverages(), timeline, graphRect, getDaySize());
		createPoints();		
	}

	private void createPoints() {		
		points = new ArrayList<Point>();
		points.add(new Point(0, timeline.getHeight()));
		
		int x = 0;
		for (RatingPeriod period : averages.getAverages()) {
			int periodSize = period.getDays() * getDaySize();
			if (period.hasRatings()) {
				int y = getY(period);
				points.add(new Point(x, y));
				points.add(new Point(x + periodSize, y));
			} else {
				points.add(new Point(x, screen.height() - timeline.getHeight()));
				points.add(new Point(x + periodSize, screen.height() - timeline.getHeight()));
			}
			x += periodSize;
		}
		
		points.add(new Point(x, timeline.getHeight()));
	}
	
	private int getY(RatingPeriod period) {
		int bestY = timeline.getHeight() * 2;
		int worstY = screen.height() - timeline.getHeight() * 2;
		return (int) MathUtils.projectReversed(1, 5, worstY, bestY, period.getAverage().doubleValue());
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
	
	public Grid getGrid() {
		return grid;
	}

	private int getDaySize() {
		return screen.width() / chartOptions.getDaysOnScreen();
	}

	public void setOptions(ChartOptions options) {
		chartOptions = options;
		refresh();
	}
	
	public ChartOptions getOptions() {
		return chartOptions;
	}
	
}
