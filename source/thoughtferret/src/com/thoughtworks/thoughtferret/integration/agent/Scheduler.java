package com.thoughtworks.thoughtferret.integration.agent;

import org.joda.time.LocalDateTime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.thoughtworks.thoughtferret.integration.Preferences;
import com.thoughtworks.thoughtferret.model.agent.FerretFrequency;
import static com.thoughtworks.thoughtferret.DateUtils.*;

public class Scheduler {

	private static final int REQUEST_CODE = 1786459;
	
	private Context context;
	
	public Scheduler(Context context) {
		this.context = context;
	}
	
	public void registerNextRandom(FerretFrequency frequency) {
		LocalDateTime current = new LocalDateTime();
		LocalDateTime next = frequency.getNext(current);
		registerNext(next);
	}

	public void registerNextVerySoon() {
		LocalDateTime next = new LocalDateTime().plusSeconds(20);
		registerNext(next);
	}
	
	public void registerNext(LocalDateTime targetDate){
		saveNextAlarmForDebugging(targetDate);
		long timestamp = targetDate.toDateTime().getMillis();
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
	}
	
	public void cancelPendingAlarms() {
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}
	
	private void saveNextAlarmForDebugging(LocalDateTime date) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Preferences.KEY_AGENT_NEXTALARM, asReadable(date));
		editor.commit();
	}
	
}
