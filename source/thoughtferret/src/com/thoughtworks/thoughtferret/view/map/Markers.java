package com.thoughtworks.thoughtferret.view.map;

import java.util.List;

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
	private ZoomDetector zoomDetector;
	
	public Markers(MapView mapView, MoodRatings ratings) {
		this.mapView = mapView;
		this.ratings = ratings;
		this.zoomDetector = new ZoomDetector(zoomChanged);
		mapView.getOverlays().clear();
		mapView.getOverlays().add(zoomDetector);
	}
	
	public Mode getMode(int zoomLevel) {
		return zoomLevel > SWITCH_LEVEL ? Mode.CITIES : Mode.COUNTRIES;
	}
	
	private Runnable zoomChanged = new Runnable() {
		@Override
		public void run() {
			Mode newMode = getMode(mapView.getZoomLevel());
			if (newMode != currentMode) {
				currentMode = newMode;
		        createMarkers(createOffices());
			}
		}
	};
	
	private void createMarkers(Offices offices) {
		List<Overlay> overlays = mapView.getOverlays();
		overlays.clear();
		overlays.add(zoomDetector);
		for (Office office : offices.getOffices()) {
			OfficeOverlay overlay = new OfficeOverlay(mapView.getContext(), office);
			overlays.add(overlay);
		}
	}

	private Offices createOffices() {
		if (currentMode == Mode.COUNTRIES) {
			return new OfficesFactory().countryOffices(ratings);
		} else {
			return new OfficesFactory().cityOffices(ratings);
		}
	}

}
