package com.thoughtworks.thoughtferret.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Point;
import android.graphics.Rect;

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
	}
	
	public HappyWordsPresenter(int displayX, int displayY) {
		graphSize = new Rect(0, 0, displayX, displayY);
		
		words = new ArrayList<Word>();
		words.add(new Word("Politics"));
		words.add(new Word("Scrum"));
		words.add(new Word("Debugging"));
		words.add(new Word("Testing"));
		words.add(new Word("Pairing"));
		words.add(new Word("Coaching"));
		words.add(new Word("The big bang theory"));
		words.add(new Word("Android"));
		words.add(new Word("Claim center"));
		words.add(new Word("Brisbane"));
		words.add(new Word("Meetings"));
		words.add(new Word("Pizza"));
		words.add(new Word("Open source"));
		words.add(new Word("Interviews"));
		words.add(new Word("SOW"));
		words.add(new Word("Web services"));
		words.add(new Word("Social club"));		
}

	private Point getRandomPosition() {
		return new Point(random.nextInt(getGraphRect().width()), random.nextInt(getGraphRect().height()));
	}
	
	private int getRandomWeight() {
		return random.nextInt(4);
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
