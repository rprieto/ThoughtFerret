package com.thoughtworks.thoughtferret.view;

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

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.presenter.MoodGraphPresenter;

public class MoodGraph extends Activity {

	private Panel panel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		panel = new Panel(this);
		setContentView(panel);
	}

	class Panel extends Scroll  {
		 
		private MoodGraphPresenter presenter;
		
		private int backgroundColor = 0xFFDDDDDD;
		private int happyColor = 0x6600FF00;
		private int sadColor = 0x66FF0000;
	 
		private int bannerHeight = 50;
    	int minorGridStep = 50;
    	int majorGridStep = 200;
		
		private Paint gradientPaint;
		private Paint textPaint;
		private Paint contourPaint;
		private Paint bannerPaint;
		private Paint gridMajorPaint;
		private Paint gridMinorPaint;
	 		
	    public Panel(Context context) {
	        super(context, null);
	        
			presenter = new MoodGraphPresenter();
	        setFullSize(presenter.getGraphWidth(), super.display.getHeight());
	        
	        gradientPaint = new Paint() {{
				setStyle(Paint.Style.FILL);
				setAntiAlias(true);
				setStrokeWidth(1.0f);
				setStrokeCap(Cap.ROUND);
				setColor(0xFF000000);
			}};
			
			gradientPaint.setShader(new LinearGradient(0, super.display.getHeight() - bannerHeight, 0, 100, sadColor, happyColor, Shader.TileMode.CLAMP));
			
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
			
			bannerPaint = new Paint() {{
				setStyle(Paint.Style.FILL_AND_STROKE);
				setAntiAlias(true);
				setStrokeWidth(1.0f);
				setStrokeCap(Cap.BUTT);
				setColor(0x66666666);
			}};
			
			gridMajorPaint = new Paint() {{
				setStyle(Paint.Style.STROKE);
				setAntiAlias(true);
				setStrokeWidth(1.5f);
				setStrokeCap(Cap.BUTT);
				setColor(0xFF666666);
			}};
			
			gridMinorPaint = new Paint() {{
				setStyle(Paint.Style.STROKE);
				setAntiAlias(true);
				setStrokeWidth(1.0f);
				setStrokeCap(Cap.BUTT);
				setColor(0xFFAAAAAA);
			}};
	    }
	    
	    @Override
	    protected void drawFullCanvas(Canvas canvas, Rect visibleRect) {
	    	canvas.drawColor(backgroundColor);
	   	    	
	    	drawGrid(canvas);
	    	drawGraph(canvas);
	    	drawEngagements(canvas);
	    	drawTimeline(canvas);
        	
        	super.drawFullCanvas(canvas, visibleRect);
	    }
	    
	    private void drawGraph(Canvas canvas) {
	    	
	    	int yBase = super.display.getHeight() - bannerHeight;
	    	
	    	Path path = new Path();	 
        	Path contour = new Path();
        	
        	Point first = presenter.getPoints().get(0);
        	Point last = presenter.getPoints().get(presenter.getPoints().size() - 1);
        	
        	path.moveTo(first.x, yBase);
        	path.lineTo(first.x, first.y);
        	
        	contour.moveTo(first.x, yBase);
        	contour.lineTo(first.x, first.y);
        	
        	for (int i=0; i<presenter.getPoints().size() - 1; i++) {
        		
        		Point current = presenter.getPoints().get(i);
        		Point next = presenter.getPoints().get(i+1);
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
        	
        	contour.lineTo(last.x, yBase);
        	//contour.close();
        	
        	canvas.drawPath(path, gradientPaint); 
        	canvas.drawPath(contour, contourPaint);
	    }
	    
	    private void drawEngagements(Canvas canvas) {
	    	canvas.drawRect(0, 0, presenter.getGraphWidth(), bannerHeight, bannerPaint);
	    	canvas.drawLine(0, bannerHeight, presenter.getGraphWidth(), bannerHeight, contourPaint);
	    }
	    
	    private void drawTimeline(Canvas canvas) {
	    	canvas.drawRect(0, super.display.getHeight() - bannerHeight, presenter.getGraphWidth(), super.display.getHeight(), bannerPaint);
	    	canvas.drawLine(0, super.display.getHeight() - bannerHeight, presenter.getGraphWidth(), super.display.getHeight() - bannerHeight, contourPaint);
	    	canvas.drawText("March 2010", 50, super.display.getHeight() - (int) (bannerHeight / 2.0), textPaint);
	    }
	    
	    private void drawGrid(Canvas canvas) {	    	
	    	for (int x = 0; x < presenter.getGraphWidth(); x += minorGridStep) {
	    		canvas.drawLine(x, 0, x, super.display.getHeight(), gridMinorPaint);
	    	}
	    	for (int y = 0; y < super.display.getHeight(); y += minorGridStep) {
	    		canvas.drawLine(0, y, presenter.getGraphWidth(), y, gridMinorPaint);
	    	}
	    	for (int x = 0; x < presenter.getGraphWidth(); x += majorGridStep) {
	    		canvas.drawLine(x, 0, x, super.display.getHeight(), gridMajorPaint);
	    	}
	    }
	    
	}
	
}