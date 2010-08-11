package com.thoughtworks.thoughtferret.view.moodgraph;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.model.mood.MoodRatingDao;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;
import com.thoughtworks.thoughtferret.view.ApplicationBackground;
import com.thoughtworks.thoughtferret.view.Screen;
import com.thoughtworks.thoughtferret.view.Scroll;
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
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Graph options");
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		final OptionsPopup options = new OptionsPopup(this, null);
		options.setOptions(panel.getChartOptions());
		
		final Dialog dialog = new Dialog(this);
		dialog.setTitle("Graph options");
		dialog.setContentView(options);
		
		options.setValidateListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						panel.setChartOptions(options.getOptions());
						dialog.dismiss();
					}
				});
		
		dialog.show();
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
	    Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	    window.addFlags(WindowManager.LayoutParams.FLAG_DITHER);
	}

	@Override
    public void onBackPressed() {
		this.finish();
		overridePendingTransition(0, 0);
		return;
    }
	
	class Panel extends Scroll {
		 
		private VisualRatings visualRatings;

		private Paint nopPaint;
		private Paint textPaint;
		private Paint contourPaint;
		private Paint bannerPaint;
		private Paint gridMinorPaint;

		private ApplicationBackground appBackground;
		private Bitmap cachedBitmap;
		private Canvas cachedCanvas;
		private Screen screen;
		
	    public Panel(Context context) {
	        super(context, null);
	        
	        screen = new Screen(context);
	        
	        MoodRatingDao dao = new MoodRatingDao(context);
	        MoodRatings ratings = dao.findAll();
	        visualRatings = new VisualRatings(ratings, screen); 
	        
	        appBackground = new ApplicationBackground(context, ApplicationBackground.GradientDirection.VERTICAL, false);
	       
	        nopPaint = new Paint();
			textPaint = new FontPaint(0xFF000000, 22, Paint.Align.CENTER);
			contourPaint = new LinePaint(0xAA000000, 1f);
			gridMinorPaint = new LinePaint(0x99AAAAAA, 1f);
			gridMinorPaint.setPathEffect(new DottedEffect());
			bannerPaint = new FillPaint(0xAACCCCCC);
			
			createCachedGraph();
	    }
	    
		private void createCachedGraph() {
	    	if (cachedBitmap != null) {
	    		cachedBitmap.recycle();
	    		cachedBitmap = null;
	    	}	    	
	    	cachedBitmap = Bitmap.createBitmap(visualRatings.getGraphRect().width(), visualRatings.getGraphRect().height(), Bitmap.Config.ARGB_8888);
	    	cachedCanvas = new Canvas(cachedBitmap);
	    	setFullSize(visualRatings.getGraphRect());
	    	cachedBitmap.eraseColor(0x00000000);
			drawGrid();
	    	drawGraph();	    	
	    	drawTimeline();
	    }
	    
		public ChartOptions getChartOptions() {
			return visualRatings.getOptions();
		}
		
		public void setChartOptions(ChartOptions options) {
	    	visualRatings.setOptions(options);
	    	createCachedGraph();
			invalidate();
		}
	    
	    @Override
	    protected void drawFullCanvas(Canvas canvas, Rect visibleRect) {
	    	drawBackground(canvas, visibleRect);	
	    	canvas.drawBitmap(cachedBitmap, 0, 0, nopPaint);
	    }

		private void drawBackground(Canvas canvas, Rect visibleRect) {
			canvas.save();
	    	canvas.translate(-visibleRect.left, -visibleRect.top);
			appBackground.draw(canvas);
			canvas.restore();
		}
	    
	    private void drawGraph() {
	    	if (visualRatings.getOptions().getType() == ChartType.BAR) {
	    		drawBarChart();
	    	} else {
	    		drawLineChart();
	    	}
	    }
	    
	    private void drawBarChart() {
	    	Path path = new Path();	 
        	Path contour = new Path();
        	
        	int yBase = visualRatings.getGraphRect().height() - visualRatings.getTimeline().getHeight();
        	Point last = visualRatings.getPoints().get(visualRatings.getPoints().size() - 1);
        	path.moveTo(0, visualRatings.getTimeline().getHeight());
        
        	for (int i = 0; i < visualRatings.getPoints().size(); ++i) {
        		Point current = visualRatings.getPoints().get(i);
        		path.lineTo(current.x, current.y);
        		contour.lineTo(current.x, current.y);
        	}
        	
        	path.lineTo(last.x, last.y);
        	path.lineTo(last.x, yBase);
        	path.lineTo(visualRatings.getGraphRect().width(), yBase);
        	path.lineTo(visualRatings.getGraphRect().width(), visualRatings.getTimeline().getHeight());
        	
        	contour.lineTo(last.x, last.y);
        	contour.lineTo(last.x, yBase);
        	
        	path.close();
        	
        	cachedCanvas.drawPath(path, appBackground.getFadeOverlayPaint()); 
        	cachedCanvas.drawPath(contour, contourPaint);
	    }
	    
	    private void drawLineChart() {
	    	int yBase = visualRatings.getGraphRect().height() - visualRatings.getTimeline().getHeight();
        	Point first = visualRatings.getPoints().get(0);
        	Point last = visualRatings.getPoints().get(visualRatings.getPoints().size() - 1);
        	
        	Path curve = new Path();
        	Path contour = new Path();
        	
        	curve.moveTo(0, visualRatings.getTimeline().getHeight());
        	curve.lineTo(0, yBase);
        	curve.lineTo(first.x, yBase);
        	curve.lineTo(first.x, first.y);
        	
        	contour.moveTo(0, visualRatings.getTimeline().getHeight());
        	contour.lineTo(0, yBase);
        	contour.lineTo(first.x, yBase);
        	contour.lineTo(first.x, first.y);
        	
        	for (int i = 0; i < visualRatings.getPoints().size() - 3; i += 2) {
        		Point periodStart = visualRatings.getPoints().get(i);
        		Point periodEnd = visualRatings.getPoints().get(i + 1);
        		Point nextPeriodStart = visualRatings.getPoints().get(i + 2);
        		Point nextPeriodEnd = visualRatings.getPoints().get(i + 3);        	
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
        	curve.lineTo(last.x, yBase);
        	curve.lineTo(visualRatings.getGraphRect().width(), yBase);
        	curve.lineTo(visualRatings.getGraphRect().width(), visualRatings.getTimeline().getHeight());
        	
        	contour.lineTo(last.x, last.y);
        	contour.lineTo(last.x, yBase);
        	
        	curve.close();
        	
        	cachedCanvas.drawPath(contour, contourPaint);
        	cachedCanvas.drawPath(curve, appBackground.getFadeOverlayPaint());
	    }
	    
	    private void drawTimeline() {
	    	for (TimeUnit unit : visualRatings.getTimeline().getUnits()) {
	    		Rect bottomRect = new Rect(unit.getRect());
	    		bottomRect.offset(0, screen.height() - unit.getRect().height());
	    		drawTimeUnit(unit.getRect(), unit.getText());
	    		drawTimeUnit(bottomRect, unit.getText());
			}
	    }
	    
	    private void drawTimeUnit(Rect rect, String text) {
    		cachedCanvas.drawRect(rect, bannerPaint);
    		cachedCanvas.drawRect(rect, contourPaint);
    		cachedCanvas.drawText(text, rect.centerX(), rect.centerY() + 5, textPaint);
	    }
	    
	    private void drawGrid() {
	    	for (Rect line : visualRatings.getGrid().getLines()) {
	    		cachedCanvas.drawLine(line.left, line.top, line.right, line.bottom, gridMinorPaint);
			}
	    }
	    
	}
	
}
