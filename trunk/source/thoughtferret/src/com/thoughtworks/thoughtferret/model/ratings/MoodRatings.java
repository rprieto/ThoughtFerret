package com.thoughtworks.thoughtferret.model.ratings;

import java.util.Arrays;
import java.util.List;

public class MoodRatings {

	private List<MoodRating> ratings;
	
	public MoodRatings(MoodRating...moodRatings) {
		this.ratings = Arrays.asList(moodRatings);
	}

	public MoodRatings(List<MoodRating> moodRatings) {
		this.ratings = moodRatings;
	}
	
	public List<MoodRating> getValues() {
		return ratings;
	}

	public MoodRating getFirst() {
		return ratings.get(0);
	}
	
	public MoodRating getLast() {
		return ratings.get(ratings.size() - 1);
	}
	
}
