package com.thoughtworks.thoughtferret.model.map;

import com.thoughtworks.thoughtferret.R;

public enum Trend {
	
	UP(R.drawable.trendup),
	DOWN(R.drawable.trenddown),
	STABLE(R.drawable.trendstable);

	private int resourceId;
	
	Trend(int resourceId) {
		this.resourceId = resourceId;
	}
	
	public int getResourceId() {
		return resourceId;
	}
	
}
