package com.thoughtworks.thoughtferret.model.map;

import java.util.Arrays;
import java.util.List;

import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;

public class Offices {

	private final List<Office> offices;
	
	protected Offices(MoodRatings ratings, Office ... offices) {
		this.offices = Arrays.asList(offices);
		for (MoodRating rating : ratings.getValues()) {
			Office closest = getClosestOffice(rating);
			//closest.addRating(rating);
		}
	}
	
	public List<Office> getOffices() {
		return offices;
	}
	
	public void setMoodRatings() {
		
	}

	public Office getClosestOffice(MoodRating rating) {
		Office closest = null;
		float minDistance = Integer.MAX_VALUE;
		for (Office office : offices) {
			float distance = office.getCoordinates().getDistanceTo(rating.getCoordinates());
			if (distance < minDistance) {
				minDistance = distance;
				closest = office;
			}
		}
		return closest;
	}
	
}
