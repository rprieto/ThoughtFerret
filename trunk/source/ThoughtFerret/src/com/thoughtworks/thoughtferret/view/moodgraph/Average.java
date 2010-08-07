package com.thoughtworks.thoughtferret.view.moodgraph;

import org.joda.time.DateTime;

public class Average {
	
	private DateTime startDate;
	private DateTime endDate;
	private int rating;
	
	public Average(DateTime startDate, DateTime endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
}

