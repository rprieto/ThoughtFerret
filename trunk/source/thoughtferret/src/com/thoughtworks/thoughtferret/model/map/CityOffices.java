package com.thoughtworks.thoughtferret.model.map;

import java.util.Arrays;
import java.util.List;

import com.thoughtworks.thoughtferret.model.map.locations.Cities;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;

public class CityOffices extends Offices {
	
	public CityOffices(MoodRatings ratings) {
		super(ratings);
	}

	@Override
	protected List<Office> createOffices() {
		return Arrays.asList(
			new Office("Sydney", Cities.SYDNEY),
			new Office("Melbourne", Cities.MELBOURNE),
			new Office("Brisbane", Cities.BRISBANE),
			new Office("Perth", Cities.PERTH)
		);
	}
	
}
