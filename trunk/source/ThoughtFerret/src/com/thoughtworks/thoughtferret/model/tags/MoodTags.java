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
	
}
