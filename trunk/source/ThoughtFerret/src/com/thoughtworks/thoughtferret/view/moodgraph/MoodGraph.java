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
		 
		private MonthlyRatings monthlyRatings;

		private Paint nopPaint;
		private Paint textPaint;
		private Paint contourPaint;
		private Paint bannerPaint;
		private Paint gridMinorPaint;

		ApplicationBackground appBackground;
		Bitmap cachedBitmap;
		private Canvas cachedCanvas;
	    
	    public Panel(Context context) {
	        super(context, null);
	        
	        Rect screen = new Rect(0, 0, super.display.getWidth(), super.display.getHeight());
	        
	        MoodRatingDao dao = new MoodRatingDao(context);
	        MoodRatings ratings = dao.findAll();
	        monthlyRatings = new MonthlyRatings(ratings, screen);
	        
	        appBackground = new ApplicationBackground(getResources(), screen.width(), screen.height(), ApplicationBackground.GradientDirection.VERTICAL, false);
	       
	        nopPaint = new Paint();
			textPaint = new FontPaint(0xFF000000, 22, Paint.Align.CENTER);
			contourPaint = new LinePaint(0xFF000000, 2f);
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
	    	cachedBitmap = Bitmap.createBitmap(monthlyRatings.getGraphRect().width(), monthlyRatings.getGraphRect().height(), Bitmap.Config.ARGB_8888);
	    	cachedCanvas = new Canvas(cachedBitmap);
	    	setFullSize(monthlyRatings.getGraphRect());
	    	cachedBitmap.eraseColor(0x00000000);
			drawGrid();
	    	drawGraph();	    	
	    	drawTimeline();
	    }
	    
		public void setZoom(ZoomLevel zoomLevel) {
	    	monthlyRatings.setZoom(zoomLevel);
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
	    	
	    	int yBase = monthlyRatings.getGraphRect().bottom;
	    	
	    	Path path = new Path();	 
        	Path contour = new Path();
        	
        	Point first = monthlyRatings.getPoints().get(0);
        	Point last = monthlyRatings.getPoints().get(monthlyRatings.getPoints().size() - 1);
        	
        	path.moveTo(first.x, 0);
        	path.lineTo(first.x, first.y);
        	
        	contour.moveTo(first.x, yBase);
        	contour.lineTo(first.x, first.y);
        	
        	for (int i=0; i<monthlyRatings.getPoints().size() - 1; ++i) {
        		
        		Point current = monthlyRatings.getPoints().get(i);
        		Point next = monthlyRatings.getPoints().get(i+1);
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
        	
        	cachedCanvas.drawPath(path, appBackground.getFadeOverlayPaint()); 
        	cachedCanvas.drawPath(contour, contourPaint);
	    }
	    
	    private void drawTimeline() {
	    	for (TimeUnit unit : monthlyRatings.getTimeUnits()) {
	    		cachedCanvas.drawRect(unit.getRect(), bannerPaint);
	    		cachedCanvas.drawRect(unit.getRect(), contourPaint);
	    		cachedCanvas.drawText(unit.getText(), unit.getRect().centerX(), unit.getRect().centerY() + 5, textPaint);
			}
	    }
	    
	    private void drawGrid() {
	    	for (Rect line : monthlyRatings.getGrid()) {
	    		cachedCanvas.drawLine(line.left, line.top, line.right, line.bottom, gridMinorPaint);
			}
	    }
	    
	}
	
}
