package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.thoughtworks.thoughtferret.integration.database.MoodRatingDao;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.view.ApplicationBackground;
import com.thoughtworks.thoughtferret.view.Screen;
import com.thoughtworks.thoughtferret.view.Scroll;
import com.thoughtworks.thoughtferret.view.moodgraph.charts.BarChart;
import com.thoughtworks.thoughtferret.view.moodgraph.charts.Chart;
import com.thoughtworks.thoughtferret.view.moodgraph.charts.ChartType;
import com.thoughtworks.thoughtferret.view.moodgraph.charts.LineChart;
import com.thoughtworks.thoughtferret.view.moodgraph.graph.FullGraph;
import com.thoughtworks.thoughtferret.view.moodgraph.graph.TimeUnit;
import com.thoughtworks.thoughtferret.view.paints.DottedEffect;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.FontPaint;
import com.thoughtworks.thoughtferret.view.paints.LinePaint;

public class Panel extends Scroll {
	 
	private FullGraph fullGraph;
	private ChartOptions options;

	private Paint nopPaint;
	private Paint textPaint;
	private Paint edgePaint;
	private Paint bannerPaint;
	private Paint gridMinorPaint;

	private ApplicationBackground appBackground;
	private Bitmap cachedBitmap;
	private Canvas cachedCanvas;
	private Screen screen;
	
	private HashMap<ChartType, Chart> charts;
	
    public Panel(Context context) {
        super(context, null);
        
        screen = new Screen(context);
        
        MoodRatingDao dao = new MoodRatingDao(context);
        MoodRatings ratings = dao.findAll();
        options = new ChartOptions(ChartType.BAR, ChartOptions.SEMESTER);
        fullGraph = new FullGraph(screen, ratings, options); 
        
        appBackground = new ApplicationBackground(context, ApplicationBackground.GradientDirection.VERTICAL, false);
       
        nopPaint = new Paint();
		textPaint = new FontPaint(0xFF000000, 22, Paint.Align.CENTER);
		edgePaint = new LinePaint(0xAA000000, 1f);
		gridMinorPaint = new LinePaint(0x99AAAAAA, 1f);
		gridMinorPaint.setPathEffect(new DottedEffect());
		bannerPaint = new FillPaint(0xAACCCCCC);
		
		charts = new HashMap<ChartType, Chart>();
		charts.put(ChartType.BAR, new BarChart(appBackground.getFadeOverlayPaint(), edgePaint));
		charts.put(ChartType.LINE, new LineChart(appBackground.getFadeOverlayPaint(), edgePaint));
		
		createCachedGraph();
    }
    
	private void createCachedGraph() {
    	if (cachedBitmap != null) {
    		cachedBitmap.recycle();
    		cachedBitmap = null;
    	}	    	
    	cachedBitmap = Bitmap.createBitmap(fullGraph.getFullRect().width(), fullGraph.getFullRect().height(), Bitmap.Config.ARGB_8888);
    	cachedCanvas = new Canvas(cachedBitmap);
    	setFullSize(fullGraph.getFullRect());
    	cachedBitmap.eraseColor(0x00000000);
		drawGrid();
    	drawGraph();	    	
    	drawTimeline();
    }
    
	public ChartOptions getChartOptions() {
		return fullGraph.getOptions();
	}
	
	public void setChartOptions(ChartOptions options) {
		fullGraph.setOptions(options);
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
    	Chart chart = charts.get(fullGraph.getOptions().getType());
    	chart.draw(cachedCanvas, fullGraph.getChartRect(), fullGraph.getData().getPoints());
    }
    
    private void drawTimeline() {
    	for (TimeUnit unit : fullGraph.getTimeline().getUnits()) {
    		Rect bottomRect = new Rect(unit.getRect());
    		bottomRect.offset(0, screen.height() - unit.getRect().height());
    		drawTimeUnit(unit.getRect(), unit.getText());
    		drawTimeUnit(bottomRect, unit.getText());
		}
    }
    
    private void drawTimeUnit(Rect rect, String text) {
		cachedCanvas.drawRect(rect, bannerPaint);
		cachedCanvas.drawRect(rect, edgePaint);
		cachedCanvas.drawText(text, rect.centerX(), rect.centerY() + 5, textPaint);
    }
    
    private void drawGrid() {
    	for (Rect line : fullGraph.getGrid().getLines()) {
    		cachedCanvas.drawLine(line.left, line.top, line.right, line.bottom, gridMinorPaint);
		}
    }
    
}