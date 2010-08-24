package com.thoughtworks.thoughtferret.model.map;

import java.util.Arrays;
import java.util.List;

import com.thoughtworks.thoughtferret.model.map.locations.Countries;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;

public class CountryOffices extends Offices {

	public CountryOffices(MoodRatings ratings) {
		super(ratings);
	}

	@Override
	protected List<Office> createOffices() {
		return Arrays.asList(
			new Office("Australia", Countries.AUSTRALIA),
			new Office("China", Countries.CHINA),
			new Office("India", Countries.INDIA),
			new Office("UK", Countries.UK),
			new Office("USA", Countries.USA),
			new Office("Germany", Countries.GERMANY),
			new Office("Canada", Countries.CANADA)
		);
	}

}
