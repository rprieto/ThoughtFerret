package com.thoughtworks.thoughtferret.model.map;

import java.util.Arrays;
import java.util.List;

public class Offices {

	private List<Office> offices;
	
	public Offices() {
		offices = Arrays.asList(
				new Office("Sydney", Places.SYDNEY_OFFICE),
				new Office("Melbourne", Places.MELBOURNE_OFFICE),
				new Office("Brisbane", Places.BRISBANE_OFFICE),
				new Office("Perth", Places.PERTH_OFFICE)
				);
	}
	
	public List<Office> getOffices() {
		return offices;
	}
	
	public void setMoodRatings() {
		
	}
	
}
