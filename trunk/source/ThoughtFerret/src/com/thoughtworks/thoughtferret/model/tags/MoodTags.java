package com.thoughtworks.thoughtferret.model.tags;

import java.util.Arrays;
import java.util.List;

public class MoodTags {

	private List<MoodTag> tags;
	
	public MoodTags(MoodTag...moodTags) {
		this.tags = Arrays.asList(moodTags);
	}

	public MoodTags(List<MoodTag> moodTags) {
		this.tags = moodTags;
	}
	
	public List<MoodTag> getValues() {
		return tags;
	}
	
	public int getMinOccurencesOfSingleTag() {
		int minCount = Integer.MAX_VALUE;
		for (MoodTag tag : tags) {
			if (tag.getCount() < minCount) {
				minCount = tag.getCount();
			}
		}
		return minCount;
	}
	
	public int getMaxOccurencesOfSingleTag() {
		int maxCount = 0;
		for (MoodTag tag : tags) {
			if (tag.getCount() > maxCount) {
				maxCount = tag.getCount();
			}
		}
		return maxCount;
	}
	
}
