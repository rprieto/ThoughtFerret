package com.thoughtworks.thoughtferret.view.happywords;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.util.Log;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.tags.MoodTag;
import com.thoughtworks.thoughtferret.model.tags.MoodTags;
import com.thoughtworks.thoughtferret.view.Screen;

public class TagCloud {

	public static final int SIZE_LEVELS = 5;

	private List<RenderedTag> renderedTags;
	private Screen screen;

	public TagCloud(MoodTags moodTags, Screen screen) {
		this.screen = screen;
		int minCount = moodTags.getMinOccurencesOfSingleTag();
		int maxCount = moodTags.getMaxOccurencesOfSingleTag();
		Log.i("TagCloud", String.format("Min count: %d", minCount));
		Log.i("TagCloud", String.format("Max count: %d", maxCount));
		renderedTags = new ArrayList<RenderedTag>();
		for (MoodTag moodTag : moodTags.getValues()) {
			Point position = getTagPosition(moodTag);
			int size = getTagSize(moodTag, minCount, maxCount);
			renderedTags.add(new RenderedTag(moodTag.getText(), position, size));
		}
	}

	public List<RenderedTag> getTags() {
		return renderedTags;
	}
	
	private Point getTagPosition(MoodTag moodTag) {
		int x = (int) (moodTag.getRatingAverage() * screen.width() / MoodRating.BEST_RATING);
		int y = MathUtils.getRandom(0, screen.height());
		return new Point(x, y);
	}
	
	private int getTagSize(MoodTag tag, int minCount, int maxCount) {
		return MathUtils.project(minCount, maxCount, 1, SIZE_LEVELS, tag.getCount());
	}

}

