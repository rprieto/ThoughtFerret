package com.thoughtworks.thoughtferret.model.agent;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public interface ReminderStrategy {

	public static final LocalTime MIN_TIME = new LocalTime(8, 30);
	public static final LocalTime MAX_TIME = new LocalTime(18, 30);
	
	LocalDateTime getNext(LocalDateTime current);

}
