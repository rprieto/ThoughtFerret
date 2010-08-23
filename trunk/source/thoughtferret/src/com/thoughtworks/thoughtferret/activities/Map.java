package com.thoughtworks.thoughtferret.activities;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
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

public class Map extends MapActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        
        MapController mc = mapView.getController();        
        mc.animateTo(Places.CENTER_AUSTRALIA.toGeoPoint());
        mc.setZoom(5);
        
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
		
		public OfficeOverlay(Office office) {
			this.office = office;
		}
		
        @Override
        public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            Point screenPts = new Point();
            mapView.getProjection().toPixels(office.getCoordinates().toGeoPoint(), screenPts);
 
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y, null);         
            return true;
        }
    } 
	
}
