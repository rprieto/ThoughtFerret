package com.thoughtworks.thoughtferret.model.map;

public class Location {
	
	private final String name;
	private final Coordinates coords;

	public Location(String name, Coordinates coords) {
		this.name = name;
		this.coords = coords;
	}

	public String getName() {
		return name;
	}

	public Coordinates getCoordinates() {
		return coords;
	}
	
}
