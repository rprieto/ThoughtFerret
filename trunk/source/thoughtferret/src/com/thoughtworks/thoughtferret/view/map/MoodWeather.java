package com.thoughtworks.thoughtferret.view.map;

import java.util.HashMap;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.model.ratings.RatingAverage;

public class MoodWeather {

	private int resourceId;

	private HashMap<Integer, Integer> imageIds = new HashMap<Integer, Integer>() {{
		put(0, R.drawable.weather0);
		put(1, R.drawable.weather1);
		put(2, R.drawable.weather2);
		put(3, R.drawable.weather3);
		put(4, R.drawable.weather4);
		put(5, R.drawable.weather5);
	}};
	
	public MoodWeather(RatingAverage average) {
		resourceId = imageIds.get(average.getRounded());
	}
	
	public int getResourceId() {
		return resourceId;
	}
	
}
