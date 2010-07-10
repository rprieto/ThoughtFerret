package com.thoughtworks.thoughtferret.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.thoughtworks.thoughtferret.MathUtils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Bundle;

public class MoodGraph extends Activity {

	int pointSpacing = 40;
	int yBase = 350;
	private Panel panel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		List<Point> points = createPoints(50);
		
		panel = new Panel(this, points);
		panel.setFullSize(pointSpacing * (points.size() - 1), 400);
		setContentView(panel);
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


	class Panel extends Scroll  {
		 
		private int backgroundColor = 0xFFAAAAAA;
		private int happyColor = 0x6600FF00;
		private int sadColor = 0x66FF6600;
	 
		private List<Point> points;
		
		private Paint gradientPaint;
		private Paint textPaint;
		private Paint contourPaint;
	 		
	    public Panel(Context context, List<Point> points) {
	        super(context, null);
	        
	        this.points = points;
	        
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
			
			contourPaint = new Paint() {{
				setStyle(Paint.Style.STROKE);
				setAntiAlias(true);
				setStrokeWidth(2.0f);
				setStrokeCap(Cap.BUTT);
				setColor(0xFF000000);
			}};
	    }
	    
	    @Override
	    protected void drawFullCanvas(Canvas canvas, Rect visibleRect) {
	    	canvas.drawColor(backgroundColor);
	    
	    	canvas.drawText("March 2010", 150, 400, textPaint);
	    	
        	Path path = new Path();	 
        	Path contour = new Path();
        	
        	Point first = points.get(0);
        	Point last = points.get(points.size() - 1);
        	
        	path.moveTo(first.x, yBase);
        	path.lineTo(first.x, first.y);
        	contour.moveTo(first.x, first.y);
        	
        	for (int i=0; i<points.size() - 1; i++) {
        		
        		Point current = points.get(i);
        		Point next = points.get(i+1);
        		Point mid = MathUtils.getMiddle(current, next);
        		
        		Point controlPoint1 = new Point(mid.x, current.y);
        		Point controlPoint2 = new Point(mid.x, next.y);
        		
        		path.quadTo(controlPoint1.x, controlPoint1.y, mid.x, mid.y);
        		path.quadTo(controlPoint2.x, controlPoint2.y, next.x, next.y);
        		
        		contour.quadTo(controlPoint1.x, controlPoint1.y, mid.x, mid.y);
        		contour.quadTo(controlPoint2.x, controlPoint2.y, next.x, next.y);
        	}
        	
        	path.lineTo(last.x, yBase);
        	path.close();
        	
        	canvas.drawPath(path, gradientPaint); 
        	canvas.drawPath(contour, contourPaint);
        	
        	super.drawFullCanvas(canvas, visibleRect);
	    }
	}
	
}