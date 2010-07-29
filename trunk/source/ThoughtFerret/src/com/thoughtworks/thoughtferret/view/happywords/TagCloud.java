package com.thoughtworks.thoughtferret.view.happywords;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.graphics.Rect;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.tags.MoodTag;
import com.thoughtworks.thoughtferret.model.tags.MoodTags;

public class TagCloud {

	public static final int SIZE_LEVELS = 5;

	private List<RenderedTag> renderedTags;
	private Rect screen;

	public TagCloud(MoodTags moodTags, Rect screen) {
		this.screen = screen;
		renderedTags = new ArrayList<RenderedTag>();
		for (MoodTag moodTag : moodTags.getValues()) {
			Point position = getTagPosition(moodTag);
			int size = getTagSize(moodTags, moodTag);
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
	
	private int getTagSize(MoodTags allTags, MoodTag tag) {
		int minCount = allTags.getMinOccurencesOfSingleTag();
		int maxCount = allTags.getMaxOccurencesOfSingleTag();
		return MathUtils.project(minCount, maxCount, 1, SIZE_LEVELS, tag.getCount());
	}

}

