package com.thoughtworks.thoughtferret.model.map;

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
	
}
