package com.thoughtworks.thoughtferret.view.moodgraph.graph;

import android.graphics.Rect;

import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverages;
import com.thoughtworks.thoughtferret.view.Screen;
import com.thoughtworks.thoughtferret.view.moodgraph.ChartOptions;

public class FullGraph {

	private static final int PERIOD_IN_PIXELS = 100;
	
	private Screen screen;
	private Chronology chronology;
	private ChartOptions options;
	
	private MoodRatings ratings;
	private Timeline timeline;
	private Grid grid;
	private Data data;
	
	public FullGraph(Screen screen, MoodRatings ratings, ChartOptions options) {
		this.screen = screen;
		this.ratings = ratings;
		this.options = options;
		calculateGraph();

	}
	
	public Timeline getTimeline() {
		return timeline;
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public Data getData() {
		return data;
	}
	
	public void setOptions(ChartOptions options) {
		this.options = options;
		calculateGraph();
	}
	
	public ChartOptions getOptions() {
		return options;
	}
	
	public Rect getFullRect() {
		return new Rect(0, 0, timeline.getWidth(), screen.height());
	}
	
	public Rect getChartRect() {
		return new Rect(0, timeline.getHeight(), timeline.getWidth(), screen.height() - timeline.getHeight());
	}
	
	private void calculateGraph() {
		int daySize = screen.width() / options.getDaysOnScreen();
		int nbDaysInPeriod = PERIOD_IN_PIXELS / daySize;
        RatingAverages averages = new RatingAverages(ratings, nbDaysInPeriod);
		chronology = new Chronology(averages.getFirst().getStartDate(), daySize);
        timeline = new Timeline(ratings, chronology);
        grid = new Grid(averages.getAverages(), chronology, getChartRect());
        data = new Data(averages, chronology, getChartRect());
	}
	
}
