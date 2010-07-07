package com.thoughtworks.thoughtferret;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;

public class MoodGraph extends Activity {

	@Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 	setContentView(new Panel(this));
	 }
	

	 class Panel extends View {
		 
			int happyColor = 0xFF00FF00;
			int happyColorBorder = 0xFF009900; 
			int sadColor = 0xFFFF0000;
		 
			Paint gradientPaint;
		 
		    public Panel(Context context) {
		        super(context);
		        
		        gradientPaint = new Paint() {{
					setStyle(Paint.Style.FILL);
					setAntiAlias(true);
					setStrokeWidth(3.0f);
					setStrokeCap(Cap.ROUND);
					setColor(0xff800000);
				}};
		    }
		 
		    @Override
		    public void onDraw(Canvas canvas) {
		    	
		    	canvas.drawColor(0xFF000000);
	
		    	int xBase = 0;
		    	
	        	Path path = new Path();	        	
	        	List<Point> points = new ArrayList<Point>();

	        	points.add(new Point(100, 0));
	        	points.add(new Point(150, 50));
	        	points.add(new Point(210,100));
	        	points.add(new Point(120,150));
	        	points.add(new Point(195,200));
	        	points.add(new Point(140,250));
	        	points.add(new Point(260,300));
	        	points.add(new Point(210,350));
	        	points.add(new Point(100,400));
	        	points.add(new Point(150,430));
	        	
	        	Point first = points.get(0);
	        	Point last = points.get(points.size() - 1);
	        	
	        	path.moveTo(xBase, first.y);
	        	path.lineTo(first.x, first.y);
	        	
	        	for (int i=0; i<points.size() - 1; i++) {
	        		
	        		Point current = points.get(i);
	        		Point next = points.get(i+1);
	        		Point mid = new Point((current.x + next.x) / 2, (current.y + next.y) / 2);
	        		
	        		Point controlPoint1 = new Point(current.x, mid.y);
	        		Point controlPoint2 = new Point(next.x, mid.y);
	        		
	        		path.quadTo(controlPoint1.x, controlPoint1.y, mid.x, mid.y);
	        		path.quadTo(controlPoint2.x, controlPoint2.y, next.x, next.y);
	        		
	        		//path.lineTo(next.x, next.y);
	        	}
	        	
	        	path.lineTo(xBase, last.y);
	        	path.close();
	        	
	        	canvas.drawPath(path, gradientPaint); 
       	

	        	
	        	
	        	
		    }
		}
	 
}
