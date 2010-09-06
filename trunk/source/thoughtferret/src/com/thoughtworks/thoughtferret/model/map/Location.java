package com.thoughtworks.thoughtferret.model.map;

public class Location {
	
	private final String name;
	private final String shortName;
	private final Coordinates coords;

	public Location(String name, String shortName, Coordinates coords) {
		this.name = name;
		this.shortName = shortName;
		this.coords = coords;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}
	
	public Coordinates getCoordinates() {
		return coords;
	}
	
}
