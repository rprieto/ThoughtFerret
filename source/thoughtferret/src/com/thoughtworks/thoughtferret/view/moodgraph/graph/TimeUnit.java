package com.thoughtworks.thoughtferret.view.moodgraph.graph;

import android.graphics.Rect;

public class TimeUnit {

	private Rect rect;
	private String text;

	public TimeUnit(Rect rect, String text) {
		this.rect = rect;
		this.text = text;
	}
	
	public Rect getRect() {
		return rect;
	}
	
	public String getText() {
		return text;
	}
	
}
