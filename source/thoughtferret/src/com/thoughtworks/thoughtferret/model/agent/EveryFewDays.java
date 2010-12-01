package com.thoughtworks.thoughtferret.model.agent;

import org.joda.time.LocalDateTime;

public class EveryFewDays implements ReminderStrategy {

	@Override
	public LocalDateTime getNext(LocalDateTime current) {
		// draw random number of days to skip (1 to 3?)
		// get next week day after skipping that many
		// get random appropriate time
		return current.plusDays(3);
	}

}
