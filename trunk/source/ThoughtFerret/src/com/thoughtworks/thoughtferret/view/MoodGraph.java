package com.thoughtworks.thoughtferret.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
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
		
//		private int happyColor = 0x5500FF00;
//		private int sadColor = 0xCCFF3300;
	 
    	int minorGridStep = 60;
    	int majorGridStep = 240;
		
		//private Paint fadeOverlayPaint;
		private Paint textPaint;
		private Paint contourPaint;
		private Paint bannerPaint;
		private Paint gridMajorPaint;
		private Paint gridMinorPaint;
	 		
//		Bitmap backgroundBitmap;
//	    Paint backgroundPaint;
		
//	    private Paint backgroundGradientPaint;
		
		GraphPaints graphPaints;
	    
	    public Panel(Context context) {
	        super(context, null);
	        
			presenter = new MoodGraphPresenter(super.display.getWidth(), super.display.getHeight());
	        setFullSize(presenter.getGraphRect());
			
	        Point gradientStart = new Point(0, presenter.getBottomBanner().top);
	        Point gradientEnd = new Point(0, presenter.getTopBanner().bottom);
	        graphPaints = new GraphPaints(getResources(), gradientStart, gradientEnd);
	        
			textPaint = new FontPaint(0xFF000000, 22, Paint.Align.CENTER);
			contourPaint = new LinePaint(0xFF000000, 2f);
			gridMajorPaint = new LinePaint(0xFF666666, 1.5f);
			gridMinorPaint = new LinePaint(0x99AAAAAA, 1f);
			gridMinorPaint.setPathEffect(new DottedEffect());

			bannerPaint = new FillPaint(0xAACCCCCC);
			
//	        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homebackground);
//	        backgroundPaint = new FillPaint(0xAA000000);
			
//			Shader gradient = new LinearGradient(0, presenter.getBottomBanner().top, 0, presenter.getTopBanner().bottom, sadColor, happyColor, Shader.TileMode.CLAMP);
//			backgroundGradientPaint = new FillPaint(0xFFDDDDDD, gradient);
	        
//			fadeOverlayPaint = new FillPaint(0x44DDDDDD);
	    }
	    
	    @Override
	    protected void drawFullCanvas(Canvas canvas, Rect visibleRect) {
	    	drawBackground(canvas, visibleRect);			
			drawGrid(canvas);
	    	drawGraph(canvas);	    	
	    	drawTimeline(canvas, presenter.getTopBanner());
	    	drawTimeline(canvas, presenter.getBottomBanner());
        	super.drawFullCanvas(canvas, visibleRect);
	    }

		private void drawBackground(Canvas canvas, Rect visibleRect) {
			graphPaints.drawBackground(canvas, super.getFullSize(), visibleRect);
		}
	    
	    private void drawGraph(Canvas canvas) {
	    	
	    	int yBase = presenter.getBottomBanner().top;
	    	
	    	Path path = new Path();	 
        	Path contour = new Path();
        	
        	Point first = presenter.getPoints().get(0);
        	Point last = presenter.getPoints().get(presenter.getPoints().size() - 1);
        	
        	path.moveTo(first.x, 0);
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
        	
        	path.lineTo(last.x, 0);
        	path.close();
        	
        	contour.lineTo(last.x, yBase);
        	
        	canvas.drawPath(path, graphPaints.getFadeOverlayPaint()); 
        	canvas.drawPath(contour, contourPaint);
	    }
	    
	    private void drawTimeline(Canvas canvas, Rect banner) {
	    	canvas.drawRect(banner, bannerPaint);
	    	canvas.drawRect(banner, contourPaint);
	    	for (int x = 0; x < presenter.getGraphRect().width(); x += majorGridStep) {
	    		canvas.drawLine(x, banner.top, x, banner.bottom, gridMajorPaint);
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
	    		canvas.drawLine(x, presenter.getTopBanner().bottom, x, presenter.getBottomBanner().top, gridMinorPaint);
	    	}
	    	for (int y = 0; y < super.display.getHeight(); y += minorGridStep) {
	    		canvas.drawLine(0, y, presenter.getGraphRect().width(), y, gridMinorPaint);
	    	}
	    }
	    
	}
	
}
