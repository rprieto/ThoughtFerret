package com.thoughtworks.thoughtferret.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.presenter.MoodGraphPresenter;
import com.thoughtworks.thoughtferret.view.paints.DottedEffect;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.FontPaint;
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
	
	@Override
	  public void onAttachedToWindow() {
	    super.onAttachedToWindow();
	    Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	    window.addFlags(WindowManager.LayoutParams.FLAG_DITHER);
	  }

	class Panel extends Scroll  {
		 
		private MoodGraphPresenter presenter;
		
		private int backgroundColor = 0xFFDDDDDD;
		private int happyColor = 0x9900FF00;
		private int sadColor = 0x99FF3300;
	 
    	int minorGridStep = 60;
    	int majorGridStep = 240;
		
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
			
			textPaint = new FontPaint(0xFF000000, 22, Paint.Align.CENTER);
			contourPaint = new LinePaint(0xFF000000, 2f);
			gridMajorPaint = new LinePaint(0xFF666666, 1.5f);
			gridMinorPaint = new LinePaint(0xFFAAAAAA, 1f);
			gridMinorPaint.setPathEffect(new DottedEffect());

			bannerPaint = new FillPaint(0x66666666);
			
			Shader gradient = new LinearGradient(0, presenter.getTimelineBanner().top, 0, presenter.getClientsBanner().bottom, sadColor, happyColor, Shader.TileMode.CLAMP);
			gradientPaint = new FillPaint(0xFF000000, gradient);
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
	    	
	    	int yBase = presenter.getTimelineBanner().top;
	    	
	    	Path path = new Path();	 
        	Path contour = new Path();
        	
        	Point first = presenter.getPoints().get(0);
        	Point last = presenter.getPoints().get(presenter.getPoints().size() - 1);
        	
        	path.moveTo(first.x, yBase);
        	path.lineTo(first.x, first.y);
        	
        	contour.moveTo(first.x, yBase);
        	contour.lineTo(first.x, first.y);
        	
        	for (int i=0; i<presenter.getPoints().size() - 1; ++i) {
        		
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
	    	canvas.drawText("Beach", 75,  banner.centerY() + 5, textPaint);
	    	canvas.drawLine(150, presenter.getClientsBanner().top, 150, presenter.getClientsBanner().bottom, gridMajorPaint);
	    	canvas.drawText("e*Trade", 290, banner.centerY() + 5, textPaint);
	    	canvas.drawLine(430, presenter.getClientsBanner().top, 430, presenter.getClientsBanner().bottom, gridMajorPaint);
	    	canvas.drawText("Suncorp", 552, banner.centerY() + 5, textPaint);
	    	canvas.drawLine(675, presenter.getClientsBanner().top, 675, presenter.getClientsBanner().bottom, gridMajorPaint);
	    	canvas.drawText("Beach", 697, banner.centerY() + 5, textPaint);
	    	canvas.drawLine(720, presenter.getClientsBanner().top, 720, presenter.getClientsBanner().bottom, gridMajorPaint);
	    	canvas.drawText("Telstra Media", 885, banner.centerY() + 5, textPaint);
	    	canvas.drawLine(1050, presenter.getClientsBanner().top, 1050, presenter.getClientsBanner().bottom, gridMajorPaint);
	    }
	    
	    private void drawTimeline(Canvas canvas) {
	    	Rect banner = presenter.getTimelineBanner();
	    	canvas.drawRect(banner, bannerPaint);
	    	canvas.drawLine(banner.left, banner.top, banner.right, banner.top, contourPaint);
	    	for (int x = 0; x < presenter.getGraphRect().width(); x += majorGridStep) {
	    		canvas.drawLine(x, presenter.getTimelineBanner().top, x, presenter.getTimelineBanner().bottom, gridMajorPaint);
	    	}
	    	canvas.drawText("January 2010",   majorGridStep * 0 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("February 2010",  majorGridStep * 1 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("March 2010",     majorGridStep * 2 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("April 2010",     majorGridStep * 3 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("May 2010",       majorGridStep * 4 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("June 2010",      majorGridStep * 5 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("July 2010",      majorGridStep * 6 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("August 2010",    majorGridStep * 7 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("September 2010", majorGridStep * 8 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("October 2010",   majorGridStep * 9 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("November 2010",  majorGridStep * 10 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    	canvas.drawText("December 2010",  majorGridStep * 11 + (majorGridStep / 2f), banner.centerY() + 5, textPaint);
	    }
	    
	    private void drawGrid(Canvas canvas) {	    	
	    	for (int x = 0; x < presenter.getGraphRect().width(); x += minorGridStep) {
	    		canvas.drawLine(x, presenter.getClientsBanner().bottom, x, presenter.getTimelineBanner().top, gridMinorPaint);
	    	}
	    	for (int y = 0; y < super.display.getHeight(); y += minorGridStep) {
	    		canvas.drawLine(0, y, presenter.getGraphRect().width(), y, gridMinorPaint);
	    	}
	    }
	    
	}
	
}
