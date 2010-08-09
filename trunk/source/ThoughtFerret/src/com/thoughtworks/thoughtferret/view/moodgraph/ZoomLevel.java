package com.thoughtworks.thoughtferret.view.moodgraph;

public enum ZoomLevel {
	
	MONTH(1),
	QUARTER(3),
	SEMESTER(6);
	
	private final int numberOfMonths;
	
	private ZoomLevel(int numberOfMonths) {
		this.numberOfMonths = numberOfMonths;
	}
	
	public int getMonthSize(int screenWidth) {
		return screenWidth / numberOfMonths;
	}
	
	public int getPixelsPerDay(int screenWidth) {
		return screenWidth / numberOfMonths / 30;
	}
	
}
