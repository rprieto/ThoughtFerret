package com.thoughtworks.thoughtferret.model.ratings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDateTime;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

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
	
	public RatingAverage getAverage() {
		double sum = 0;
		for (MoodRating rating : ratings) {
			sum += rating.getRating();
		}
		double average = sum / ratings.size();
		return new RatingAverage(average);
	}
	
	public MoodRatings getSubset(final LocalDateTime start, final LocalDateTime end) {
		Collection<MoodRating> subset = Collections2.filter(ratings, new Predicate<MoodRating>() {
			@Override
			public boolean apply(MoodRating rating) {
				return rating.getLoggedDate().isAfter(start)
					&& rating.getLoggedDate().isBefore(end);
			}
		});
		return new MoodRatings(new ArrayList<MoodRating>(subset));
	}
	
}
