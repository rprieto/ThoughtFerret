package com.thoughtworks.thoughtferret.model.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.map.locations.Countries;
import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;

public class OfficesFactory {
	
	public Offices cityOffices(MoodRatings ratings) {
		List<Location> locations = Arrays.asList(
				new Location("Sydney", Cities.SYDNEY),
				new Location("Melbourne", Cities.MELBOURNE),
				new Location("Brisbane", Cities.BRISBANE),
				new Location("Perth", Cities.PERTH));
		return createOffices(locations, ratings);
	}
	
	public Offices countryOffices(MoodRatings ratings) {
		List<Location> locations = Arrays.asList(
				new Location("Australia", Countries.AUSTRALIA),
				new Location("Brazil", Countries.BRAZIL),
				new Location("China", Countries.CHINA),
				new Location("India", Countries.INDIA),
				new Location("UK", Countries.UK),
				new Location("USA", Countries.USA),
				new Location("Germany", Countries.GERMANY),
				new Location("Canada", Countries.CANADA));
		return createOffices(locations, ratings);
	}
	
	private Offices createOffices(List<Location> locations, MoodRatings ratings) {
		HashMap<Location, List<MoodRating>> map = new HashMap<Location, List<MoodRating>>();
		for (Location location : locations) {
			map.put(location, new ArrayList<MoodRating>());
		}
		
		for (MoodRating rating : ratings.getValues()) {
			Location closest = getClosestLocation(rating, locations);
			map.get(closest).add(rating);
		}
		
		List<Office> results = new ArrayList<Office>();
		for (Location location : map.keySet()) {
			MoodRatings subset = new MoodRatings(map.get(location));
			Office office = new Office(location, subset);
			results.add(office);
		}
		
		return new Offices(results);
	}
	
	private Location getClosestLocation(MoodRating rating, List<Location> locations) {
		Location closest = null;
		float minDistance = Integer.MAX_VALUE;
		for (Location location : locations) {
			float distance = location.getCoordinates().getDistanceTo(rating.getCoordinates());
			if (distance < minDistance) {
				minDistance = distance;
				closest = location;
			}
		}
		return closest;
	}
	
}
