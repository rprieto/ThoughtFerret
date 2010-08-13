package com.thoughtworks.thoughtferret.scheduler;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Scheduler {

	private static final int REQUEST_CODE = 1786459;
	
	public void registerNextRandom(Context context) {
		LocalDateTime next = new LocalDateTime().plusSeconds(20);
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
	
}
