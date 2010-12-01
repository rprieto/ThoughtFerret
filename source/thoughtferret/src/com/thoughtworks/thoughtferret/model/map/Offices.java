package com.thoughtworks.thoughtferret.model.map;

import java.util.List;

public class Offices {

	private final List<Office> offices;
	
	protected Offices(List<Office> offices) {
		this.offices = offices;
	}
	
	public List<Office> getOffices() {
		return offices;
	}
	
}
