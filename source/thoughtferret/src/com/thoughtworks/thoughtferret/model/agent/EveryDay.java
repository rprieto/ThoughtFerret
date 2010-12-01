package com.thoughtworks.thoughtferret.model.agent;

import static com.thoughtworks.thoughtferret.DateUtils.*;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class EveryDay implements ReminderStrategy {

	@Override
	public LocalDateTime getNext(LocalDateTime current) {
		LocalDateTime next = current.plusDays(1);
		LocalTime targetTime = randomTime(MIN_TIME, MAX_TIME);		
		return dateWithTime(next, targetTime);
	}

}
