package com.thoughtworks.thoughtferret.model.agent;

import org.joda.time.LocalDateTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public enum FerretFrequency {
	
	EVERY_DAY(new EveryDay()),
	EVERY_FEW_DAYS(new EveryDay()),
	EVERY_WEEK(new EveryDay());

	public static final String KEY_AGENT_FREQUENCY = "agentFrequency";
	
	private ReminderStrategy strategy;
	
	private FerretFrequency(ReminderStrategy strategy) {
		this.strategy = strategy;
	}
	
	public static FerretFrequency fromSavedPreferences(Context context) {
		SharedPreferences savedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		String value = savedPrefs.getString(KEY_AGENT_FREQUENCY, "");
		return FerretFrequency.valueOf(value);
	}

	public LocalDateTime getNext(LocalDateTime date) {
		return strategy.getNext(date);
	}
	
}
