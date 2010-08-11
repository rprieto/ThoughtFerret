package com.thoughtworks.thoughtferret.view.moodgraph.charts;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public interface Chart {

	void draw(Canvas canvas, Rect chartArea, List<Point> points);
	
}
