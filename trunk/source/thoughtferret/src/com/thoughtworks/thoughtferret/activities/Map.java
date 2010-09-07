package com.thoughtworks.thoughtferret.activities;

import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.integration.database.MoodRatingDao;
import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;
import com.thoughtworks.thoughtferret.view.map.Markers;

public class Map extends MapActivity {
	
	private MapView mapView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        MoodRatingDao dao = new MoodRatingDao(this);
        MoodRatings ratings = dao.findAll();
        new Markers(mapView, ratings);

        MapController mc = mapView.getController();
        mc.animateTo(Cities.SYDNEY.toGeoPoint());
        mc.setZoom(6);
        
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
	
}
