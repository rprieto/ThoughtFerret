package com.thoughtworks.thoughtferret.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Point;

public class MoodGraphPresenter {

	int pointSpacing = 40;
	int yBase = 350;
	
	List<Point> points;
	
	public MoodGraphPresenter() {
		createPoints(50);
	}
	
	private void createPoints(int nbPoints) {
    	Random rnd = new Random();
		points = new ArrayList<Point>();

    	int pointX = 0;
    	for (int i = 0; i < nbPoints; i++) {
    		int pointY = yBase - (rnd.nextInt(5) + 1) * 50;
    		points.add(new Point(pointX, pointY));
    		pointX += pointSpacing;
    	}
	}

	public List<Point> getPoints() {
		return points;
	}
	
	public int getGraphWidth() {
		return pointSpacing * (points.size() - 1);
	}
	
}
