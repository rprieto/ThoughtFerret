package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;

public class Grid {

	private List<Rect> lines;
	
	public Grid(List<RatingPeriod> periods, Timeline timeline, Rect surface, int daySize) {
		lines = new ArrayList<Rect>();
		calculatePeriodLines(periods, timeline, surface, daySize);
		calculateValueLines(timeline, surface);
	}
	
	private void calculatePeriodLines(List<RatingPeriod> periods, Timeline timeline, Rect surface, int daySize) {
		int x = 0;
		for (RatingPeriod period : periods) {
			x += period.getDays() * daySize;
			lines.add(new Rect(x, surface.top + timeline.getHeight(), x, surface.bottom - timeline.getHeight()));
		}
	}
	
	private void calculateValueLines(Timeline timeline, Rect surface) {
		int start = timeline.getHeight() * 2;
		int end = surface.height() - timeline.getHeight() * 2;
		int verticalSpace = surface.height() - timeline.getHeight() * 4;
		final int intervalSize = verticalSpace / (MoodRating.BEST_RATING - 1);
    	for (int y = start; y <= end; y += intervalSize) {
    		lines.add(new Rect(surface.left, y, surface.right, y));
    	}
	}
	
	public List<Rect> getLines() {
		return lines;
	}
	
}
