package com.thoughtworks.thoughtferret.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.model.map.Places;

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
        mc.animateTo(getMapCenter());
        mc.setZoom(5);
        
        mapView.invalidate();
        
//        TelephonyManager tm  = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
//        GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
//        int cellId = location.getCid();
//        location.getLac();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	private GeoPoint getMapCenter() {
		int lat = (int) (Places.CENTER_AUSTRALIA.getLatitude() * 1E6);
		int lon = (int) (Places.CENTER_AUSTRALIA.getLongitude() * 1E6);
		return new GeoPoint(lat, lon);
	}

}
