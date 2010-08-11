package com.thoughtworks.thoughtferret.view.moodgraph;

public class ChartOptions {

	public static final int MONTH = 30;
	public static final int SEMESTER = 30 * 4;
	public static final int YEAR = 365;
	
	private final int daysOnScreen;
	private final ChartType type;
	
	public ChartOptions(ChartType type, int daysOnScreen) {
		this.type = type;
		this.daysOnScreen = daysOnScreen;		
	}
	
	public int getDaysOnScreen() {
		return daysOnScreen;
	}
	
	public ChartType getType() {
		return type;
	}
	
}
