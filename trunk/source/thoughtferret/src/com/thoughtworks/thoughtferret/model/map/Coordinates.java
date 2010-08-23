package com.thoughtworks.thoughtferret.model.map;

import com.google.android.maps.GeoPoint;

import android.location.Location;

public class Coordinates {

	private final double longitude;
	private final double latitude;
	
	public Coordinates(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	public float getDistanceTo(Coordinates target) {
		float[] results = new float[3];
		Location.distanceBetween(latitude, longitude, target.latitude, target.longitude, results);
		return results[0];
	}
	
	public GeoPoint toGeoPoint() {
		int lat = (int) (latitude * 1E6);
		int lon = (int) (longitude * 1E6);
		return new GeoPoint(lat, lon);
	}
	
}
