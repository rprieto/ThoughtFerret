package com.thoughtworks.thoughtferret.view.map;

import java.util.Arrays;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Align;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.thoughtworks.thoughtferret.model.map.Office;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.FontPaint;
import com.thoughtworks.thoughtferret.view.paints.LinePaint;


public class OfficeOverlay extends Overlay
{
	Office office;
	Paint markerEdge = new LinePaint(0xFF000000, 4);
	Paint markerFill = new FillPaint(0XFFFFFFFF);
	Paint textPaint = new FontPaint(0xFF000000, 20, Align.CENTER);
	
	List<FillPaint> fills = Arrays.asList(
			new FillPaint(0XFFEEEEEE),
			new FillPaint(0XFFFFEEEE),
			new FillPaint(0XFFFAF2EE),
			new FillPaint(0XFFF6F6EE),
			new FillPaint(0XFFF2FAEE),
			new FillPaint(0XFFEEFFEE)
			);
	
	public OfficeOverlay(Office office) {
		this.office = office;
	}
	
    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) 
    {
        super.draw(canvas, mapView, shadow);                   

        Point target = new Point();
        mapView.getProjection().toPixels(office.getLocation().getCoordinates().toGeoPoint(), target);

        int triangleHeight = 12;
        int triangleWidth = 15;
        int boxHeight = 70;
        int boxWidth = 150;
        int ovalHeight = 10;
        int ovalWidth = 20;

        Path marker = new Path();
        marker.moveTo(target.x - boxWidth / 2, target.y - triangleHeight - boxHeight);
        marker.lineTo(target.x + boxWidth / 2, target.y - triangleHeight - boxHeight);
        marker.lineTo(target.x + boxWidth / 2, target.y - triangleHeight);
        marker.lineTo(target.x + triangleWidth / 2, target.y - triangleHeight);
        marker.lineTo(target.x, target.y);
        marker.lineTo(target.x - triangleWidth / 2, target.y - triangleHeight);
        marker.lineTo(target.x - boxWidth / 2, target.y - triangleHeight);
        marker.close();
        
        Rect box = new Rect(target.x - boxWidth / 2, target.y - triangleHeight - boxHeight, target.x + boxWidth / 2, target.y - triangleHeight);
        RectF oval = new RectF(target.x - ovalWidth / 2, target.y - ovalHeight / 2, target.x + ovalWidth / 2, target.y + ovalHeight / 2);
        
        int fillIndex = (int) Math.round(office.getAverage().doubleValue());
        Paint background = fills.get(fillIndex);
        
        canvas.drawOval(oval, markerEdge);
        canvas.drawOval(oval, background);
        canvas.drawPath(marker, markerEdge);
        canvas.drawPath(marker, background);
        canvas.drawText(office.getLocation().getName(), box.centerX(), box.top + 30, textPaint);
        canvas.drawText(office.getAverage().stringValue(), box.centerX(), box.bottom - 15, textPaint);
    }
}
