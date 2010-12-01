package com.thoughtworks.thoughtferret.view.moodgraph.charts;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

public class BarChart implements Chart {

	private Paint chartPaint;
	private Paint edgePaint;
	
	public BarChart(Paint chartPaint, Paint edgePaint) {
		this.chartPaint = chartPaint;
		this.edgePaint = edgePaint;
	}
	
	public void draw(Canvas canvas, Rect chartArea, List<Point> points) {
		Path path = new Path();	 
		Path contour = new Path();

		Point last = points.get(points.size() - 1);
		path.moveTo(0, chartArea.top);

		for (int i = 0; i < points.size(); ++i) {
			Point current = points.get(i);
			path.lineTo(current.x, current.y);
			contour.lineTo(current.x, current.y);
		}

		path.lineTo(last.x, last.y);
		path.lineTo(last.x, chartArea.bottom);
		path.lineTo(chartArea.right, chartArea.bottom);
		path.lineTo(chartArea.right, chartArea.top);

		contour.lineTo(last.x, last.y);
		contour.lineTo(last.x, chartArea.bottom);

		path.close();

		canvas.drawPath(path, chartPaint);
		canvas.drawPath(contour, edgePaint);
	}
	
}
