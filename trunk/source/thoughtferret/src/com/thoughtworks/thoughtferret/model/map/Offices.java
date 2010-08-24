package com.thoughtworks.thoughtferret.model.map;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.Months;

import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;

public abstract class Offices {

	private List<Office> offices;
	
	public Offices(MoodRatings ratings) {
		offices = createOffices();
		for (MoodRating rating : ratings.getValues()) {
			Office closest = getClosestOffice(rating);
		}
	}
	
	protected abstract List<Office> createOffices();
	
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
