package com.thoughtworks.thoughtferret.view.moodgraph.graph;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;

public class Chronology {

	private int pixelsPerDay;
	private LocalDateTime startDate;
	
	public Chronology(LocalDateTime startDate, int pixelsPerDay) {
		this.startDate = startDate;
		this.pixelsPerDay = pixelsPerDay;
	}
	
	public int getX(LocalDateTime date) {
		int nbDays = Days.daysBetween(startDate, date).getDays();
		return nbDays * pixelsPerDay;
	}
	
}
