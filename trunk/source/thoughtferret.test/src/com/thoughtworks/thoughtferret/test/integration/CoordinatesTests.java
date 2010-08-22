package com.thoughtworks.thoughtferret.test.integration;

import junit.framework.TestCase;

import android.util.Log;

import com.thoughtworks.thoughtferret.model.map.Places;

public class CoordinatesTests extends TestCase {

	public void testShouldCalculateDistanceToSameLocationAsZero() {
		float distance = Places.SYDNEY_OFFICE.getDistanceTo(Places.SYDNEY_OFFICE);
		assertTrue(distance < 0.1);
	}

	public void testShouldCalculateSmallDistanceCorrectly() {
		float distance = Places.SYDNEY_OFFICE.getDistanceTo(Places.SYDNEY_ETRADE);
		assertTrue(distance > meters(800));
		assertTrue(distance < meters(900));
	}

	public void testShouldCalculateMediumDistanceCorrectly() {
		float distance = Places.BRISBANE_OFFICE.getDistanceTo(Places.BRISBANE_AIRPORT);
		assertTrue(distance > km(11));
		assertTrue(distance < km(13));
	}
	
	public void testShouldCalculateLongDistanceCorrectly() {
		float distance = Places.BRISBANE_OFFICE.getDistanceTo(Places.MELBOURNE_OFFICE);
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
