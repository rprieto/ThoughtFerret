package com.thoughtworks.thoughtferret.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.thoughtworks.thoughtferret.model.tags.MoodTag;
import com.thoughtworks.thoughtferret.model.tags.MoodTags;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class HappyWordsPresenter {

	private Rect graphSize;
	private Random random = new Random();
	private List<Word> words;
	private int sizeLevels = 5;
	
	public class Word {
		public String text;
		public Point position;
		public int weight;
		
		public Word(String text, Point position, int weight) {
			this.text = text;
			this.position = position;
			this.weight = weight;
		}
		
		public Word(String text) {
			this.text = text;
			this.position = getRandomPosition();
			this.weight = getRandomWeight();
		}

		public Word(MoodTag tag) {
			this.text = tag.getText();
			this.position = getRandomPosition();
			this.weight = getRandomWeight();
		}
		
		private Point getRandomPosition() {
			return new Point(random.nextInt(getGraphRect().width()), random.nextInt(getGraphRect().height()));
		}
		
		private int getRandomWeight() {
			return random.nextInt(4);
		}
	}
	
	public HappyWordsPresenter(int displayX, int displayY, MoodTags moodTags) {
		graphSize = new Rect(0, 0, displayX, displayY);
		words = new ArrayList<Word>();
		for (MoodTag tag : moodTags.getValues()) {
			words.add(new Word(tag));
		}	
	}
	
	public Rect getGraphRect() {
		return graphSize;
	}
	
	public List<Word> getWords() {
		return words;
	}
	
	public int getSizeLevels() {
		return sizeLevels;
	}
	
}
