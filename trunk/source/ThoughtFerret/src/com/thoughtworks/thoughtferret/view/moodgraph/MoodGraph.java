package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.HashMap;

import android.app.Activity;
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
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.model.mood.MoodRatingDao;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;
import com.thoughtworks.thoughtferret.view.ApplicationBackground;
import com.thoughtworks.thoughtferret.view.Screen;
import com.thoughtworks.thoughtferret.view.Scroll;
import com.thoughtworks.thoughtferret.view.paints.DottedEffect;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.FontPaint;
import com.thoughtworks.thoughtferret.view.paints.LinePaint;

public class MoodGraph extends Activity implements OnCreateContextMenuListener {

	private Panel panel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		panel = new Panel(this);
		setContentView(panel);
		registerForContextMenu(panel);
	}
	
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.graphzoom, menu);
        menu.setHeaderTitle("Zoom level");        
    }
	
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	HashMap<Integer, ZoomLevel> zoomLevels = new HashMap<Integer, ZoomLevel>();
    	zoomLevels.put(R.id.zoomMonth, ZoomLevel.MONTH);
    	zoomLevels.put(R.id.zoomQuarter, ZoomLevel.QUARTER);
    	zoomLevels.put(R.id.zoomSemester, ZoomLevel.SEMESTER);
    	panel.setZoom(zoomLevels.get(item.getItemId()));
		return true;
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
	    
		public void setZoom(ZoomLevel zoomLevel) {
	    	visualRatings.setZoom(zoomLevel);
	    	createCachedGraph();
			invalidate();
		}

	    @Override
	    protected void onContextMenu() {
	    	openContextMenu(this);
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
	    	drawHistogram();
	    	//drawCurves();
	    }
	    
	    private void drawHistogram() {
	    	Path path = new Path();	 
        	Path contour = new Path();
        	path.moveTo(0, visualRatings.getTimeline().getHeight());
        
        	for (int i = 0; i < visualRatings.getPoints().size(); ++i) {
        		Point current = visualRatings.getPoints().get(i);
        		path.lineTo(current.x, current.y);
        		contour.lineTo(current.x, current.y);
        	}
        	
        	path.close();
        	contour.close();      	
        	
        	cachedCanvas.drawPath(path, appBackground.getFadeOverlayPaint()); 
        	cachedCanvas.drawPath(contour, contourPaint);
	    }
	    
	    private void drawCurves() {
	    	int yBase = visualRatings.getGraphRect().bottom;
        	Point first = visualRatings.getPoints().get(0);
        	Point last = visualRatings.getPoints().get(visualRatings.getPoints().size() - 1);
        	
        	Path curve = new Path();
        	Path contour = new Path();
        	
        	curve.moveTo(0, 0);
        	curve.lineTo(0, visualRatings.getPoints().get(1).y);
        	curve.lineTo(first.x, first.y);
        	for (int i = 1; i < visualRatings.getPoints().size() - 3; i += 2) {
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
        	
        	curve.lineTo(last.x, yBase);
        	curve.lineTo(last.x, 0);
        	    	
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
