package com.thoughtworks.thoughtferret.view.moodgraph;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;

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
	    	cachedBitmap = Bitmap.createBitmap(visualRatings.getFullRect().width(), visualRatings.getFullRect().height(), Bitmap.Config.ARGB_8888);
	    	cachedCanvas = new Canvas(cachedBitmap);
	    	setFullSize(visualRatings.getFullRect());
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
	    	BarChart barChart = new BarChart(appBackground.getFadeOverlayPaint(), contourPaint);
	    	barChart.draw(cachedCanvas, visualRatings.getChartArea(), visualRatings.getPoints());
	    }
	    
	    private void drawLineChart() {
	    	LineChart lineChart = new LineChart(appBackground.getFadeOverlayPaint(), contourPaint);
	    	lineChart.draw(cachedCanvas, visualRatings.getChartArea(), visualRatings.getPoints());
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
