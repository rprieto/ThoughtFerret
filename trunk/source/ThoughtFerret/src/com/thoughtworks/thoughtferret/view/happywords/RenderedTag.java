package com.thoughtworks.thoughtferret.view.happywords;

import android.graphics.Point;

import com.thoughtworks.thoughtferret.model.tags.MoodTag;


public class RenderedTag {

	private String text;
	private Point position;
	private int size;

	public RenderedTag(String text, Point position, int size) {
		this.text = text;
		this.position = position;
		this.size = size;
	}

	public String getText() {
		return text;
	}

	public Point getPosition() {
		return position;
	}

	public int getSize() {
		return size;
	}

}
