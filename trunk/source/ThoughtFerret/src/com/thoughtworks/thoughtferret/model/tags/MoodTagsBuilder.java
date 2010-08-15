package com.thoughtworks.thoughtferret.model.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.thoughtferret.model.tags.MoodTag;
import com.thoughtworks.thoughtferret.model.tags.MoodTags;

public class MoodTagsBuilder {

	List<String> keywords;
	
	public MoodTagsBuilder() {
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
	
	public void merge(String source, String target) {
		int sourcePos = keywords.indexOf(source);
		int targetPos = keywords.indexOf(target);
		keywords.remove(source);
		keywords.remove(target);
		int newPos = targetPos;
		if (sourcePos < targetPos) {
			newPos -= 1;
		}
		//newPos = MathUtils.clamp(newPos, 0, keywords.size() - 1);
        keywords.add(newPos, source + " " + target);
	}
	
	public MoodTags build(int rating) {
		List<MoodTag> tags = new ArrayList<MoodTag>();
		for (String word : keywords) {
			tags.add(new MoodTag(word, 1, rating));
		}
		return new MoodTags(tags);
	}
	
}
