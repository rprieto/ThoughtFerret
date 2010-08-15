package com.thoughtworks.thoughtferret.scheduler;

import org.joda.time.LocalDateTime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.thoughtworks.thoughtferret.model.FerretFrequency;

public class Scheduler {

	private static final int REQUEST_CODE = 1786459;
	
	public void registerNextRandom(Context context, FerretFrequency frequency) {
		LocalDateTime current = new LocalDateTime();
		LocalDateTime next = getNextRandomDate(current, frequency);
		registerNext(context, next);
	}

	public void registerNextVerySoon(Context context) {
		LocalDateTime next = new LocalDateTime().plusSeconds(10);
		registerNext(context, next);
	}
	
	public void registerNext(Context context, LocalDateTime targetDate){
		long timestamp = targetDate.toDateTime().getMillis();
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
	}
	
	public LocalDateTime getNextRandomDate(LocalDateTime current, FerretFrequency frequency) {
		switch (frequency) {
			case EVERY_DAY:
				return current.plusDays(1);
			case EVERY_FEW_DAYS:
				return current.plusDays(3);
			case EVERY_WEEK:
				return current.plusDays(7);
			default:
				throw new IllegalArgumentException("Invalid ferret frequency");
		}
	}
	
	public void cancelPendingAlarms(Context context) {
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}
	
}
