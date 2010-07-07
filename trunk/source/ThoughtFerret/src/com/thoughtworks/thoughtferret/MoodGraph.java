package com.thoughtworks.thoughtferret;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MoodGraph extends Activity {
	
	int pointSpacing = 30;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		List<Point> points = createPoints(60);
	 	setContentView(new Panel(this, points));
	 }

	private List<Point> createPoints(int nbPoints) {
		
    	Random rnd = new Random();
		List<Point> points = new ArrayList<Point>();

    	int pointY = 0;
    	for (int i = 0; i < nbPoints; i++) {
    		int pointX = (rnd.nextInt(5) + 1) * 50;
    		points.add(new Point(pointX, pointY));
    		pointY += pointSpacing;
    	}
    	
    	return points;
	}
	
	 class Panel extends View  {
		 
		 	int backgroundColor = 0xFF333333;
			int happyColor = 0xFF00FF00;
			int sadColor = 0xFFFF0000;
		 
			List<Point> points;
			
			Paint gradientPaint;
		 
			int maxBufferSizeX = 320;
			int maxBufferSizeY = 1000;
			
			Bitmap bufferBitmap;
		    Canvas bufferCanvas;
		    
		    float lastTouchY;
		    int scrollPosY;

			
		    public Panel(Context context, List<Point> points) {
		        super(context);
		        
		        this.points = points;
		        this.maxBufferSizeY = points.size() * pointSpacing;
		        
		        gradientPaint = new Paint() {{
					setStyle(Paint.Style.FILL);
					setAntiAlias(true);
					setStrokeWidth(1.0f);
					setStrokeCap(Cap.ROUND);
					setColor(0xFFFFFFFF);
				}};
				
				gradientPaint.setShader(new LinearGradient(0, 0, 300, 0, sadColor, happyColor, Shader.TileMode.CLAMP));
				
				bufferBitmap = Bitmap.createBitmap(maxBufferSizeX, maxBufferSizeY, Bitmap.Config.ARGB_8888);
			    bufferCanvas = new Canvas(bufferBitmap);
		    }
		    
		    @Override
		    public boolean onTouchEvent(MotionEvent event) {
		    	final int action = event.getAction();
		        switch (action) {
			        case MotionEvent.ACTION_DOWN: {
			            lastTouchY = event.getY();
			            break;
			        }			            
			        case MotionEvent.ACTION_MOVE: {
			            scrollPosY += lastTouchY - event.getY();
			            
			            if (scrollPosY < 0) {
			            	scrollPosY = 0;
			            }
			            if (scrollPosY > maxBufferSizeY - getHeight()) {
			            	scrollPosY = maxBufferSizeY - getHeight();
			            }
			            
			            lastTouchY = event.getY();
			            invalidate();
			            break;
			        }
		        }
		        
		        return true;
		    }
		    		    
		    @Override
		    public void onDraw(Canvas canvas) {
		    	super.onDraw(canvas);

		    	drawGraph(bufferCanvas);
		    	
		    	Rect source = new Rect(0, scrollPosY, getWidth(), scrollPosY + getHeight());
		    	Rect dest = new Rect(0, 0, getWidth(), getHeight());
		    	canvas.drawBitmap(bufferBitmap, source, dest, null);		    	
		    }
		    
		    private void drawGraph(Canvas canvas) {
		    	
		    	canvas.drawColor(backgroundColor);
	
		    	int xBase = 0;
		    	
	        	Path path = new Path();	        	
	        	
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
