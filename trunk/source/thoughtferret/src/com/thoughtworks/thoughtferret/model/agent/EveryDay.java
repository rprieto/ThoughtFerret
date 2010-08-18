package com.thoughtworks.thoughtferret.model.agent;

import org.joda.time.LocalDateTime;

public class EveryDay implements ReminderStrategy {

	@Override
	public LocalDateTime getNext(LocalDateTime current) {
		// get next week day
		// get appropriate time
		return current.plusDays(1);
	}

}
