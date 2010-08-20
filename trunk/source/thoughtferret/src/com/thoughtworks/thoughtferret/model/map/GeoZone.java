package com.thoughtworks.thoughtferret.model.map;

import android.graphics.Point;
import android.graphics.Rect;

public class GeoZone {

	private String name;
	
	private Rect area;
	
	public boolean contains(Point coordinates) {
		return true;
	}
	
}
