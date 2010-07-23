package com.thoughtworks.thoughtferret.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatingDao;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;
import com.thoughtworks.thoughtferret.view.moodgraph.TimeUnit;

public class MoodGraphPresenter {

	private static final int MONTH_SIZE = 240;
	private static final int WEEK_SIZE = 60;
	
	private Rect graphSize;
	
	private int yBase = 0;
	private int pointSpacing = 60;
	private int bannerHeight = 50;
		
	private MoodRatings moodRatings;
	List<Point> points;
	
	public MoodGraphPresenter(MoodRatingDao moodRatingDao, int displayX, int displayY) {
		moodRatings = moodRatingDao.findAll();
		yBase = displayY - bannerHeight;
		createPoints(50, displayY - bannerHeight * 2);
		graphSize = new Rect(0, 0, pointSpacing * (points.size() - 1), displayY);		
	}
	
	private void createPoints(int nbPoints, int maxHeight) {
		for (MoodRating moodRating : moodRatings.getValues()) {
			Log.i("Graph", String.format("%s : %d", moodRating.getLoggedDate(), moodRating.getRating()));
		}
		
    	Random rnd = new Random();
		points = new ArrayList<Point>();

    	int pointX = 0;
    	for (int i = 0; i < nbPoints; i++) {
    		int pointY = (int) (yBase - (rnd.nextInt(5) + 1) * (maxHeight / 6f));
    		points.add(new Point(pointX, pointY));
    		pointX += pointSpacing;
    	}
	}

	public Rect getGraphRect() {
		return new Rect(0, bannerHeight, graphSize.width(), graphSize.height() - bannerHeight);
	}
	
	public Rect getTotalRect() {
		return graphSize;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public List<TimeUnit> getBottomTimeline() {
		return getTimeline(graphSize.height() - bannerHeight, graphSize.height());
	}
	
	public List<TimeUnit> getTopTimeline() {
		return getTimeline(0, bannerHeight);
	}
	
	private List<TimeUnit> getTimeline(int top, int bottom) {
		List<TimeUnit> units = new ArrayList<TimeUnit>();
		int x = 0;
		for (String unitName : moodRatings.getMonths()) {
			Rect rect = new Rect(x, top, x + MONTH_SIZE, bottom);
			TimeUnit unit = new TimeUnit(rect, unitName);
			units.add(unit);
			x += MONTH_SIZE;
		}
		return units;
	}
	
	
}
