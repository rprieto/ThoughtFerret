package com.thoughtworks.thoughtferret.test.integration;

import junit.framework.TestCase;

import android.util.Log;

import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.map.locations.Clients;
import com.thoughtworks.thoughtferret.model.map.locations.Landmarks;

public class CoordinatesTests extends TestCase {

	public void testShouldCalculateDistanceToSameLocationAsZero() {
		float distance = Cities.SYDNEY.getDistanceTo(Cities.SYDNEY);
		assertTrue(distance < 0.1);
	}

	public void testShouldCalculateSmallDistanceCorrectly() {
		float distance = Cities.SYDNEY.getDistanceTo(Clients.SYDNEY_CLIENTX);
		assertTrue(distance > meters(800));
		assertTrue(distance < meters(900));
	}

	public void testShouldCalculateMediumDistanceCorrectly() {
		float distance = Cities.BRISBANE.getDistanceTo(Landmarks.BRISBANE_AIRPORT);
		assertTrue(distance > km(11));
		assertTrue(distance < km(13));
	}
	
	public void testShouldCalculateLongDistanceCorrectly() {
		float distance = Cities.BRISBANE.getDistanceTo(Cities.MELBOURNE);
		Log.i("x", "dist = " + distance);
		assertTrue(distance > km(1300));
		assertTrue(distance < km(1500));
	}
	
	private int meters(int length) {
		return length;
	}
	
	private int km(int length) {
		return length * 1000;
	}
	
}
