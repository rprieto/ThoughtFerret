package com.thoughtworks.thoughtferret.view.moodgraph;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

import com.thoughtworks.thoughtferret.model.mood.MoodRating;
import com.thoughtworks.thoughtferret.model.mood.MoodRatings;
import static com.thoughtworks.thoughtferret.DateUtils.*;

public class RatingAverages {
	
	List<RatingPeriod> periods;

	public RatingAverages(MoodRatings moodRatings, int nbDaysInPeriod) {
		periods = new ArrayList<RatingPeriod>();
		calculatePeriods(moodRatings, nbDaysInPeriod);
		//calculateAverages(moodRatings);
	}
	
//	private void calculatePeriods(MoodRatings moodRatings, int nbDaysInPeriod) {
//		DateTime startDate = moodRatings.getFirst().getLoggedDate();
//		DateTime endDate = moodRatings.getLast().getLoggedDate();
//		
//		DateTime startMonth = startOfMonth(startDate);
//		DateTime endMonth = endOfMonth(endDate);
//		
//		for (DateTime start = startMonth; start.isBefore(endMonth); start = start.plusDays(nbDaysInPeriod)) {
//			RatingPeriod period = new RatingPeriod(start, start.plusDays(nbDaysInPeriod));
//			periods.add(period);
//		}
//	}
//	
//	private void calculateAverages(MoodRatings moodRatings) {
//		int currentPeriod = 0;
//		for (MoodRating rating : moodRatings.getValues()) {
//			while (periods.get(currentPeriod).contains(rating.getLoggedDate()) == false) {
//				++currentPeriod;
//			}
//			periods.get(currentPeriod).addRating(rating);
//		}
//	}
	
	private void calculatePeriods(MoodRatings moodRatings, int nbDaysInPeriod) {
		LocalDateTime startMonth = startOfMonth(moodRatings.getFirst().getLoggedDate());
		//DateTime endMonth = endOfMonth(moodRatings.getLast().getLoggedDate());
		
		LocalDateTime current = startMonth;
		addPeriod(current, nbDaysInPeriod);

		for (MoodRating rating : moodRatings.getValues()) {
			while (!getLast().contains(rating.getLoggedDate())) {
				current = current.plusDays(nbDaysInPeriod);
				addPeriod(current, nbDaysInPeriod);
			}
			getLast().addRating(rating);
		}
	}
	
	public List<RatingPeriod> getAverages() {
		return periods;
	}
	
	public RatingPeriod getFirst() {
		return periods.get(0);
	}
	
	public RatingPeriod getLast() {
		return periods.get(periods.size() - 1);
	}
	
	private void addPeriod(LocalDateTime start, int nbDaysPerPeriod) {
		RatingPeriod period = new RatingPeriod(start, start.plusDays(nbDaysPerPeriod).minus(Seconds.ONE));
		periods.add(period);
	}
	
}
