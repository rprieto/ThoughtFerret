package com.thoughtworks.thoughtferret.view.map;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Paint.Align;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.thoughtworks.thoughtferret.model.map.Office;
import com.thoughtworks.thoughtferret.model.map.Trend;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.FontPaint;
import com.thoughtworks.thoughtferret.view.paints.LinePaint;

public class OfficeOverlay extends Overlay
{
	Office office;
	
	private Paint nopPaint = new Paint();
	private Paint markerEdge = new LinePaint(0xFF333333, 1);
	private Paint shadowedEdge = new LinePaint(0xFF000000, 2);
	private Paint textPaint = new FontPaint(0xFF000000, 20, Align.CENTER);
	private Paint fillPaint = new FillPaint(0XFFFAFAFA);
	
	private HashMap<Trend, Bitmap> trendImages;

	private static final int BOX_HEIGHT = 80;
	private static final int BOX_WIDTH = 130;
	
	public OfficeOverlay(Context context, Office office) {
		this.office = office;
		trendImages = new HashMap<Trend, Bitmap>();
		trendImages.put(Trend.UP, BitmapFactory.decodeResource(context.getResources(), Trend.UP.getResourceId()));
		trendImages.put(Trend.DOWN, BitmapFactory.decodeResource(context.getResources(), Trend.DOWN.getResourceId()));
		trendImages.put(Trend.STABLE, BitmapFactory.decodeResource(context.getResources(), Trend.STABLE.getResourceId()));
		
        shadowedEdge.setShadowLayer(2f, 1.5f, 2.5f, 0xFF333333);
        fillPaint.setShadowLayer(2f, 1.5f, 2.5f, 0xFFDDDDDD);
	}
	
    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) 
    {
        Point target = new Point();
        mapView.getProjection().toPixels(office.getLocation().getCoordinates().toGeoPoint(), target);

        RectF box = new RectF(target.x - BOX_WIDTH / 2, target.y - BOX_HEIGHT / 2, target.x + BOX_WIDTH / 2, target.y + BOX_HEIGHT / 2);

        canvas.drawRoundRect(box, 15f, 15f, shadowedEdge);
        canvas.drawRoundRect(box, 15f, 15f, fillPaint);
        canvas.drawRoundRect(box, 15f, 15f, markerEdge);
        
        MoodWeather weather = new MoodWeather(office.getAverage());
        Bitmap weatherImage = BitmapFactory.decodeResource(mapView.getResources(), weather.getResourceId());
        
        canvas.drawText(office.getLocation().getShortName(), box.left + 25, box.top + 20, textPaint);        
        canvas.drawBitmap(weatherImage, box.centerX() - 32, box.centerY() - 32, nopPaint);
        canvas.drawBitmap(trendImages.get(office.getTrend()), box.right - 30, box.bottom - 30, nopPaint);
    }
    
}
