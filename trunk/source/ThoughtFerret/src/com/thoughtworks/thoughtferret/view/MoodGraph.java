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
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.LinePaint;

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
	        
			presenter = new MoodGraphPresenter(super.display.getWidth(), super.display.getHeight());
	        setFullSize(presenter.getGraphRect());
			
			textPaint = new LinePaint(0xFFFFFFFF, 1f);
			contourPaint = new LinePaint(0xFF000000, 2f);
			gridMajorPaint = new LinePaint(0xFF666666, 1.5f);
			gridMinorPaint = new LinePaint(0xFFAAAAAA, 1f);

			bannerPaint = new FillPaint(0x66666666, 1f);
			gradientPaint = new FillPaint(0xFF000000, 1f);
			gradientPaint.setShader(new LinearGradient(0, super.display.getHeight() - bannerHeight, 0, 100, sadColor, happyColor, Shader.TileMode.CLAMP));
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
        	
        	canvas.drawPath(path, gradientPaint); 
        	canvas.drawPath(contour, contourPaint);
	    }
	    
	    private void drawEngagements(Canvas canvas) {
	    	Rect banner = presenter.getClientsBanner();
	    	canvas.drawRect(banner, bannerPaint);
	    	canvas.drawLine(banner.left, banner.bottom, banner.right, banner.bottom, contourPaint);
	    }
	    
	    private void drawTimeline(Canvas canvas) {
	    	Rect banner = presenter.getTimelineBanner();
	    	canvas.drawRect(banner, bannerPaint);
	    	canvas.drawLine(banner.left, banner.top, banner.right, banner.top, contourPaint);
	    	canvas.drawText("March 2010", 50, banner.centerY(), textPaint);
	    }
	    
	    private void drawGrid(Canvas canvas) {	    	
	    	for (int x = 0; x < presenter.getGraphRect().width(); x += minorGridStep) {
	    		canvas.drawLine(x, 0, x, super.display.getHeight(), gridMinorPaint);
	    	}
	    	for (int y = 0; y < super.display.getHeight(); y += minorGridStep) {
	    		canvas.drawLine(0, y, presenter.getGraphRect().width(), y, gridMinorPaint);
	    	}
	    	for (int x = 0; x < presenter.getGraphRect().width(); x += majorGridStep) {
	    		canvas.drawLine(x, 0, x, super.display.getHeight(), gridMajorPaint);
	    	}
	    }
	    
	}
	
}
