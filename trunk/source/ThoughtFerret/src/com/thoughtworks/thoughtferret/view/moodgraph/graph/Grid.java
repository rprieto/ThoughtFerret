package com.thoughtworks.thoughtferret.view.moodgraph.graph;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.RatingPeriod;

public class Grid {

	private List<Rect> lines;
	
	public Grid(List<RatingPeriod> periods, Chronology chronology, Rect surface) {
		lines = new ArrayList<Rect>();
		calculatePeriodLines(periods, chronology, surface);
		calculateValueLines(surface);
	}
	
	private void calculatePeriodLines(List<RatingPeriod> periods, Chronology chronology, Rect surface) {
		for (RatingPeriod period : periods) {
			int x = chronology.getX(period.getEndDate());
			lines.add(new Rect(x, surface.top, x, surface.bottom));
		}
	}
	
	private void calculateValueLines(Rect surface) {
		final int intervalSize = surface.height() / (MoodRating.BEST_RATING - 1);
    	for (int y = surface.top; y <= surface.bottom; y += intervalSize) {
    		lines.add(new Rect(surface.left, y, surface.right, y));
    	}
	}
	
	public List<Rect> getLines() {
		return lines;
	}
	
}
