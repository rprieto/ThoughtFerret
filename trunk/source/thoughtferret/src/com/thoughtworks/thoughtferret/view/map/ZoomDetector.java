package com.thoughtworks.thoughtferret.view.map;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class ZoomDetector extends Overlay
{
	
	private Runnable onZoomChangedListener;
	private int currentZoom = -1;
	
	public ZoomDetector(Runnable onZoomChangedListener) {
		this.onZoomChangedListener = onZoomChangedListener;
	}
	
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		if (mapView.getZoomLevel() != currentZoom) {
			Log.i("Markers", String.format("Changed zoom! Was %d, now %d", currentZoom, mapView.getZoomLevel()));
			currentZoom = mapView.getZoomLevel();
			new Handler().post(onZoomChangedListener);				
		}
	}
	
}
