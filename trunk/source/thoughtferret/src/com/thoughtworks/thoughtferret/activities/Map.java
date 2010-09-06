package com.thoughtworks.thoughtferret.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ZoomButtonsController;
import android.widget.ZoomButtonsController.OnZoomListener;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.integration.database.MoodRatingDao;
import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.view.map.Markers;

public class Map extends MapActivity /*implements OnZoomListener*/ {
	
	private MapView mapView;
	private Markers markers;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        MoodRatingDao dao = new MoodRatingDao(this);
        MoodRatings ratings = dao.findAll();
        markers = new Markers(mapView, ratings);

//        ZoomButtonsController zoomButton = mapView.getZoomButtonsController();
//		zoomButton.setOnZoomListener(this);
        
        MapController mc = mapView.getController();
        mc.animateTo(Cities.SYDNEY.toGeoPoint());
        mc.setZoom(6);
        
        mapView.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
//	@Override
//	public void onVisibilityChanged(boolean visible) {
//	}

//	@Override
//	public void onZoom(boolean zoomIn) {
//		markers.setZoom(mapView.getZoomLevel());
//	} 
	
	@Override
    public void onBackPressed() {
		this.finish();
		overridePendingTransition(0, 0);
		return;
    }
	
}
