package com.thoughtworks.thoughtferret;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Bundle;

public class MoodGraph extends Activity {
	
	int pointSpacing = 30;
	int yBase = 350;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		List<Point> points = createPoints(60);
	 	setContentView(new Panel(this, points));
	 }

	private List<Point> createPoints(int nbPoints) {
		
    	Random rnd = new Random();
		List<Point> points = new ArrayList<Point>();

    	int pointX = 0;
    	for (int i = 0; i < nbPoints; i++) {
    		int pointY = yBase - (rnd.nextInt(5) + 1) * 50;
    		points.add(new Point(pointX, pointY));
    		pointX += pointSpacing;
    	}
    	
    	return points;
	}
	
	 class Panel extends ScrollablePanel  {
		 
		 	int backgroundColor = 0xFF333333;
			int happyColor = 0xFF00FF00;
			int sadColor = 0xFFFF0000;
		 
			List<Point> points;
			
			Paint gradientPaint;
			Paint textPaint;
			Paint bottomPanelPaint;
		 
			int maxBufferSizeX = 1000;
			int maxBufferSizeY = 430;

			
		    public Panel(Context context, List<Point> points) {
		        super(context, 1000, 430);
		        
		        this.points = points;
		        
		        this.maxBufferSizeX = (points.size() - 1) * pointSpacing;
		        
		        gradientPaint = new Paint() {{
					setStyle(Paint.Style.FILL);
					setAntiAlias(true);
					setStrokeWidth(1.0f);
					setStrokeCap(Cap.ROUND);
					setColor(0xFF000000);
				}};
				
				gradientPaint.setShader(new LinearGradient(0, yBase, 0, 100, sadColor, happyColor, Shader.TileMode.CLAMP));
				
				textPaint = new Paint() {{
					setStyle(Paint.Style.STROKE);
					setAntiAlias(true);
					setStrokeWidth(1.0f);
					setStrokeCap(Cap.BUTT);
					setColor(0xFFFFFFFF);
				}};
				
				bottomPanelPaint = new Paint() {{
					setStyle(Paint.Style.FILL);
					setAntiAlias(true);
					setStrokeWidth(1.0f);
					setStrokeCap(Cap.BUTT);
					setColor(0xFF000000);
				}};
				

		    }
		    
		    protected void drawFullCanvas(Canvas canvas) {
		    	canvas.drawColor(backgroundColor);
		    
		    	canvas.drawRect(0, yBase, maxBufferSizeX, getHeight(), bottomPanelPaint);
		    	canvas.drawText("March 2010", 150, 400, textPaint);
		    	
	        	Path path = new Path();	        	
	        	
	        	Point first = points.get(0);
	        	Point last = points.get(points.size() - 1);
	        	
	        	path.moveTo(first.x, yBase);
	        	path.lineTo(first.x, first.y);
	        	
	        	for (int i=0; i<points.size() - 1; i++) {
	        		
	        		Point current = points.get(i);
	        		Point next = points.get(i+1);
	        		Point mid = MathUtils.getMiddle(current, next);
	        		
	        		Point controlPoint1 = new Point(mid.x, current.y);
	        		Point controlPoint2 = new Point(mid.x, next.y);
	        		
	        		path.quadTo(controlPoint1.x, controlPoint1.y, mid.x, mid.y);
	        		path.quadTo(controlPoint2.x, controlPoint2.y, next.x, next.y);
	        		//path.lineTo(next.x, next.y);
	        	}
	        	
	        	path.lineTo(last.x, yBase);
	        	path.close();
	        	
	        	canvas.drawPath(path, gradientPaint); 
		    }
		}
	 
}
