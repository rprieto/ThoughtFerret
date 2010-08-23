package com.thoughtworks.thoughtferret.activities;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.integration.database.MoodRatingDao;
import com.thoughtworks.thoughtferret.model.map.Office;
import com.thoughtworks.thoughtferret.model.map.Offices;
import com.thoughtworks.thoughtferret.model.map.Places;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.FontPaint;
import com.thoughtworks.thoughtferret.view.paints.LinePaint;

public class Map extends MapActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        MapController mc = mapView.getController();        
        mc.animateTo(Places.SYDNEY_OFFICE.toGeoPoint());
        mc.setZoom(6);
        
        MoodRatingDao dao = new MoodRatingDao(this);
        MoodRatings ratings = dao.findAll();
        Offices offices = new Offices(ratings);
        
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        for (Office office : offices.getOffices()) {
        	OfficeOverlay overlay = new OfficeOverlay(office);
        	listOfOverlays.add(overlay);
        }
        
        mapView.invalidate();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
    public void onBackPressed() {
		this.finish();
		overridePendingTransition(0, 0);
		return;
    }
	
	class OfficeOverlay extends Overlay
    {
		Office office;
		Paint markerEdge = new LinePaint(0xFF000000, 4);
		Paint markerFill = new FillPaint(0XFFFFFFFF);
		Paint textPaint = new FontPaint(0xFF000000, 20, Align.CENTER);
		
		public OfficeOverlay(Office office) {
			this.office = office;
		}
		
        @Override
        public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            Point target = new Point();
            mapView.getProjection().toPixels(office.getCoordinates().toGeoPoint(), target);
 
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
            
            String average = String.format("%.1f", office.getAverage().doubleValue());
            
            canvas.drawOval(oval, markerEdge);
            canvas.drawOval(oval, markerFill);
            canvas.drawPath(marker, markerEdge);
            canvas.drawPath(marker, markerFill);
            canvas.drawText(office.getName(), box.centerX(), box.top + 30, textPaint);
            canvas.drawText(average, box.centerX(), box.bottom - 15, textPaint);
            
            return true;
        }
    } 
	
}
