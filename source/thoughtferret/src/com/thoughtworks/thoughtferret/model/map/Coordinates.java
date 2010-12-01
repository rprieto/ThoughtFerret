package com.thoughtworks.thoughtferret.model.map;

import com.google.android.maps.GeoPoint;

import android.location.Location;

public class Coordinates {

	private final double latitude;
	private final double longitude;
	
	public Coordinates(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Coordinates(Location location) {
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
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
