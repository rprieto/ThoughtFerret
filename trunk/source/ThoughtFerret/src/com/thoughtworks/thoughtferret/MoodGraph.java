package com.thoughtworks.thoughtferret;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;

public class MoodGraph extends Activity {

	Paint linePaint;
	Paint lineBorderPaint;
	
	int happyColor = 0xFF00FF00;
	int happyColorBorder = 0xFF009900; 
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 	createPaintBrushes();
	 	setContentView(new Panel(this));
	 }
	
	private void createPaintBrushes() {
		linePaint = new Paint() {{
				setStyle(Paint.Style.STROKE);
				setAntiAlias(true);
				setStrokeWidth(1.0f);
				setColor(happyColor);
			}};

		lineBorderPaint = new Paint() {{
				setStyle(Paint.Style.STROKE);
				setAntiAlias(true);
				setStrokeWidth(3.0f);
				setStrokeCap(Cap.ROUND);
				setColor(happyColorBorder);
			}};
	}
	
//	private int interpolateColor(int value, int min, int max) {
//		float ratio = (max - min) / (float)value;
//		return (int) (sadColor + (happyColor - sadColor) * ratio);
//	}
	

	 class Panel extends View {
		    public Panel(Context context) {
		        super(context);
		    }
		 
		    @Override
		    public void onDraw(Canvas canvas) {
		    	
		    	canvas.drawColor(0xFF000000);
		    	
	        	Path p = new Path();
	        	Point mid = new Point();
	        	
	        	List<Point> points = new ArrayList<Point>();
	        	
	        	points.add(new Point(0, 200));
	        	points.add(new Point(50, 120));
	        	points.add(new Point(100, 80));
	        	points.add(new Point(150, 160));
	        	points.add(new Point(200, 150));
	        	points.add(new Point(250, 100));
	        	points.add(new Point(300, 185));
	        	
	        	for (int i=0; i<points.size() - 1; i++) {
	        		Point start = points.get(i);
	        		Point end = points.get(i+1);
	        		
		        	mid.set((start.x + end.x) / 2, (start.y + end.y) / 2);

		        	p.reset();
		        	p.moveTo(start.x, start.y);
		        	p.quadTo((start.x + mid.x) / 2, start.y, mid.x, mid.y);
		        	p.quadTo((mid.x + end.x) / 2, end.y, end.x, end.y);

		        	canvas.drawPath(p, lineBorderPaint);
		        	canvas.drawPath(p, linePaint);	        		
	        	} 	
		    }
		}
	 
}
