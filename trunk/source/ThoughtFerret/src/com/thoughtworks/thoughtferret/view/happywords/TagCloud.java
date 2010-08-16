package com.thoughtworks.thoughtferret.view.happywords;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.util.Log;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.tags.MoodTag;
import com.thoughtworks.thoughtferret.model.tags.MoodTags;
import com.thoughtworks.thoughtferret.view.Screen;

public class TagCloud {

	private static final int TOP_BOTTOM_MARGIN = 50;
	private static final int LEFT_RIGHT_MARGIN = 80;
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
		int xMin = LEFT_RIGHT_MARGIN;
		int xMax = screen.width() - LEFT_RIGHT_MARGIN;
		int x = (int) MathUtils.project(1, MoodRating.BEST_RATING, xMin, xMax, moodTag.getRatingAverage());
		int y = MathUtils.getRandom(TOP_BOTTOM_MARGIN, screen.height() - TOP_BOTTOM_MARGIN);
		return new Point(x, y);
	}
	
	private int getTagSize(MoodTag tag, int minCount, int maxCount) {
		return MathUtils.project(minCount, maxCount, 1, SIZE_LEVELS, tag.getCount());
	}

}
