package com.thoughtworks.thoughtferret.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class MoodGraphPresenter {

	private Rect graphSize;
	
	private int yBase = 0;
	private int pointSpacing = 40;
	private int bannerHeight = 50;
	
	List<Point> points;
	
	public MoodGraphPresenter(int displayX, int displayY) {
		yBase = displayY - bannerHeight;
		createPoints(50, displayY - bannerHeight * 2);
		graphSize = new Rect(0, 0, pointSpacing * (points.size() - 1), displayY);
	}
	
	private void createPoints(int nbPoints, int maxHeight) {
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
		return graphSize;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public Rect getClientsBanner() {
		return new Rect(0, 0, graphSize.width(), bannerHeight);
	}
	
	public Rect getTimelineBanner() {
		return new Rect(0, graphSize.height() - bannerHeight, graphSize.width(), graphSize.height());
	}
	
}
