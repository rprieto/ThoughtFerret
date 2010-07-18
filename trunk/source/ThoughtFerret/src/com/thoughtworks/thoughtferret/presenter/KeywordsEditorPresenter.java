package com.thoughtworks.thoughtferret.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
}
