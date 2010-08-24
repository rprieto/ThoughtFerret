package com.thoughtworks.thoughtferret.model.ratings;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ensure.Ensure;

public class RatingAverage {

	private final BigDecimal value;
	
	public RatingAverage(Double value) {
		Ensure.that(value).isBetween(0, MoodRating.BEST_RATING);
		this.value = new BigDecimal(value).setScale(1, RoundingMode.HALF_UP);
	}

	@Override
	public boolean equals(Object otherObject) {
		RatingAverage other = (RatingAverage) otherObject;
		return other != null && other.value.equals(this.value);
	}

	@Override
	public String toString() {
		return "RatingAverage [" + value + "]";
	}

	public double doubleValue() {
		return value.doubleValue();
	}
	
	public String stringValue() {
		return String.format("%.1f", value.doubleValue());
	}

}
