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
        String coordinates[] = {"1.352566007", "103.78921587"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
 
        GeoPoint p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
 
        mc.animateTo(p);
        mc.setZoom(17); 
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

}
