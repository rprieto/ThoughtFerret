package com.thoughtworks.thoughtferret.model.tags;


public class MoodTag {

	private final String text;
	private final int count;
	private final int ratingSum;

	public MoodTag(String text, int count, int ratingSum) {
		this.text = text;
		this.count = count;
		this.ratingSum = ratingSum;
	}

	public String getText() {
		return text;
	}
	
	public int getCount() {
		return count;
	}

	public int getRatingSum() {
		return ratingSum;
	}

	public int getRatingAverage() {
		return ratingSum / count;
	}
	
}
