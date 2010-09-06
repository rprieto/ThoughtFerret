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
				new Location("Sydney", "SYD", Cities.SYDNEY),
				new Location("Melbourne", "MEL", Cities.MELBOURNE),
				new Location("Brisbane", "BRI", Cities.BRISBANE),
				new Location("Perth", "PER", Cities.PERTH),
				new Location("Porto Alegre", "PAL", Cities.PORTO_ALEGRE),
				new Location("Calgary", "CAL", Cities.CALGARY),
				new Location("Toronto", "TOR", Cities.TORONTO),
				new Location("Beijing", "BEI", Cities.BEIJING),
				new Location("Hamburg", "HAM", Cities.HAMBURG),
				new Location("Bangalore", "BAN", Cities.BANGALORE),
				new Location("Chennai", "CHE", Cities.CHENNAI),
				new Location("Delhi", "DEH", Cities.DELHI),
				new Location("Pune", "PUN", Cities.PUNE),
				new Location("Stockholm", "STK", Cities.STOCKHOLM),
				new Location("London", "LON", Cities.LONDON),
				new Location("Manchester", "MAN", Cities.MANCHESTER),
				new Location("Chicago", "CHI", Cities.CHICAGO),
				new Location("Atlanta", "ATL", Cities.ATLANTA),
				new Location("San Francisco", "SF", Cities.SAN_FRANCISCO),
				new Location("New York", "NY", Cities.NEW_YORK),
				new Location("Dallas", "DAL", Cities.DALLAS)
				);
		return createOffices(locations, ratings);
	}
	
	public Offices countryOffices(MoodRatings ratings) {
		List<Location> locations = Arrays.asList(
				new Location("Australia", "AUS", Countries.AUSTRALIA),
				new Location("Brazil", "BRA", Countries.BRAZIL),
				new Location("Canada", "CAN", Countries.CANADA),
				new Location("China", "CHN", Countries.CHINA),
				new Location("Germany", "GER", Countries.GERMANY),
				new Location("India", "IND", Countries.INDIA),
				new Location("Sweden", "SWE", Countries.SWEDEN),
				new Location("UK", "UK", Countries.UK),
				new Location("USA", "USA", Countries.USA)
				);
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
