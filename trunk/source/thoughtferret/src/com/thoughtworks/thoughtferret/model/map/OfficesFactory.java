package com.thoughtworks.thoughtferret.model.map;

import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.map.locations.Countries;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;

public class OfficesFactory {
	
	public Offices cityOffices(MoodRatings ratings) {
		
//		HashMap<Coordinates, List<MoodRating>> map
//		
//		Coordinate c = getClosest(rating)
//		map.get(c).add(rating)
//		
//		MoodRatings ratings = new MoodRatings(map.get(c))
//		new Office(c, ratings)
//		
		
		
		return new Offices(ratings, 
				new Office("Sydney", Cities.SYDNEY, ratings),
				new Office("Melbourne", Cities.MELBOURNE, ratings),
				new Office("Brisbane", Cities.BRISBANE, ratings),
				new Office("Perth", Cities.PERTH, ratings)
		);
	}
	
	public Offices countryOffices(MoodRatings ratings) {
		return new Offices(ratings, 
				new Office("Australia", Countries.AUSTRALIA, ratings),
				new Office("China", Countries.CHINA, ratings),
				new Office("India", Countries.INDIA, ratings),
				new Office("UK", Countries.UK, ratings),
				new Office("USA", Countries.USA, ratings),
				new Office("Germany", Countries.GERMANY, ratings),
				new Office("Canada", Countries.CANADA, ratings)		
		);
	}
	
}
