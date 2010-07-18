package com.thoughtworks.thoughtferret.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

public class KeywordsEditorPresenter {

	List<String> keywords;
	
	public KeywordsEditorPresenter() {
		keywords = new ArrayList<String>();
	}
	
	public void addKeywords(List<String> words) {
		keywords.addAll(words);
	}
	
	public void clearAll() {
		keywords.clear();
	}
	
	public void deleteKeyword(String word) {
		Iterator<String> iter = keywords.iterator();
        while (iter.hasNext()) {
           if (iter.next() == word) {
              iter.remove();
           }
        }
	}
	
	public List<String> getKeywords() {
		return keywords;
	}
	
	public void merge(String word1, String word2) {
		Log.i("Presenter", "Merging " + word1 + " and " + word2);
		
		int i = 0;
		int targetPosition = 0;
		
		Iterator<String> iter = keywords.iterator();
        while (iter.hasNext()) {
        	String current = iter.next();
			if (current == word1) {
				targetPosition = i;
				iter.remove();
			}
			else if (current == word2) { 
				iter.remove();
			}
			++i;
        }
        
        keywords.add(targetPosition, word1 + " " + word2);
	}
	
}
