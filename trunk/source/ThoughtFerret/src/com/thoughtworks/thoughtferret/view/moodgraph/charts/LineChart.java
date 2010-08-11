package com.thoughtworks.thoughtferret.view.moodgraph.charts;

import java.util.List;

import com.thoughtworks.thoughtferret.MathUtils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

public class LineChart implements Chart {

	private Paint chartPaint;
	private Paint edgePaint;
	
	public LineChart(Paint chartPaint, Paint edgePaint) {
		this.chartPaint = chartPaint;
		this.edgePaint = edgePaint;
	}
	
	public void draw(Canvas canvas, Rect chartArea, List<Point> points) {
		Point first = points.get(0);
    	Point last = points.get(points.size() - 1);
    	
    	Path curve = new Path();
    	Path contour = new Path();
    	
    	curve.moveTo(0, chartArea.top);
    	curve.lineTo(0, chartArea.bottom);
    	curve.lineTo(first.x, chartArea.bottom);
    	curve.lineTo(first.x, first.y);
    	
    	contour.moveTo(0, chartArea.top);
    	contour.lineTo(0, chartArea.bottom);
    	contour.lineTo(first.x, chartArea.bottom);
    	contour.lineTo(first.x, first.y);
    	
    	for (int i = 0; i < points.size() - 3; i += 2) {
    		Point periodStart = points.get(i);
    		Point periodEnd = points.get(i + 1);
    		Point nextPeriodStart = points.get(i + 2);
    		Point nextPeriodEnd = points.get(i + 3);        	
    		Point start = MathUtils.getMiddle(periodStart, periodEnd);
    		Point end = MathUtils.getMiddle(nextPeriodStart, nextPeriodEnd);
    		Point mid = MathUtils.getMiddle(start, end);
    		Point controlPoint1 = new Point(mid.x, start.y);
    		Point controlPoint2 = new Point(mid.x, end.y);
    		
    		curve.quadTo(controlPoint1.x, controlPoint1.y, mid.x, mid.y);
    		curve.quadTo(controlPoint2.x, controlPoint2.y, end.x, end.y);

    		contour.quadTo(controlPoint1.x, controlPoint1.y, mid.x, mid.y);
    		contour.quadTo(controlPoint2.x, controlPoint2.y, end.x, end.y);
    	}
    	
    	curve.lineTo(last.x, last.y);
    	curve.lineTo(last.x, chartArea.bottom);
    	curve.lineTo(chartArea.right, chartArea.bottom);
    	curve.lineTo(chartArea.right, chartArea.top);
    	
    	contour.lineTo(last.x, last.y);
    	contour.lineTo(last.x, chartArea.bottom);
    	
    	curve.close();
    	
    	canvas.drawPath(contour, edgePaint);
    	canvas.drawPath(curve, chartPaint);
	}
	
}
