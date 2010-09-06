package com.thoughtworks.thoughtferret.view.map;

import java.util.List;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.thoughtworks.thoughtferret.model.map.Office;
import com.thoughtworks.thoughtferret.model.map.Offices;
import com.thoughtworks.thoughtferret.model.map.OfficesFactory;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;

public class Markers {

	public enum Mode {
		COUNTRIES,
		CITIES,
	}

	private static final int SWITCH_LEVEL = 5;
	
	private Mode currentMode;
	private MapView mapView;
	private MoodRatings ratings;
	
	public Markers(MapView mapView, MoodRatings ratings) {
		this.mapView = mapView;
		this.ratings = ratings;
		mapView.getOverlays().clear();
		mapView.getOverlays().add(new ZoomDetector());	
	}
	
	public void setZoom(int zoomLevel) {
		if (getMode(zoomLevel) != currentMode) {
			currentMode = getMode(zoomLevel);
	        createMarkers(createOffices());
		}
	}
	
	public Mode getMode(int zoomLevel) {
		return zoomLevel > SWITCH_LEVEL ? Mode.CITIES : Mode.COUNTRIES;
	}
	
	public Offices createOffices() {
		if (currentMode == Mode.COUNTRIES) {
			return new OfficesFactory().countryOffices(ratings);
		} else {
			return new OfficesFactory().cityOffices(ratings);
		}
	}
	
	private void createMarkers(Offices offices) {
		List<Overlay> overlays = mapView.getOverlays();
        overlays.clear();
        overlays.add(new ZoomDetector());
        for (Office office : offices.getOffices()) {
        	OfficeOverlay overlay = new OfficeOverlay(office);
        	overlays.add(overlay);
        }
	}
	
	public class ZoomDetector extends Overlay
	{
		int currentZoom = -1;
		
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			if (mapView.getZoomLevel() != currentZoom) {
				Log.i("Markers", String.format("Changed zoom! Was %d, now %d", currentZoom, mapView.getZoomLevel()));
				currentZoom = mapView.getZoomLevel();
				
				new Handler().post(new Runnable() {
					@Override
					public void run() {
						setZoom(currentZoom);
					}
				});
				
			}
		}
	}
	
}
