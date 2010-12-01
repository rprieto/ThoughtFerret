package com.thoughtworks.thoughtferret.model.agent;

import org.joda.time.LocalDateTime;

public class EveryWeek implements ReminderStrategy {

	@Override
	public LocalDateTime getNext(LocalDateTime current) {
		// find week number of current date
		// increase by one (careful with last week of year)
		// get random week day in this week
		// get random appropriate time
		return current.plusDays(7);
	}

}
