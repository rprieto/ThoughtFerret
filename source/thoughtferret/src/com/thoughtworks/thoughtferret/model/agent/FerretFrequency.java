package com.thoughtworks.thoughtferret.model.agent;

import org.joda.time.LocalDateTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public enum FerretFrequency {
	
	EVERY_DAY(new EveryDay(), "daily"),
	EVERY_FEW_DAYS(new EveryDay(), "every few days"),
	EVERY_WEEK(new EveryDay(), "weekly");

	public static final String KEY_AGENT_FREQUENCY = "agentFrequency";
	
	private ReminderStrategy strategy;
	private String name;
	
	private FerretFrequency(ReminderStrategy strategy, String name) {
		this.strategy = strategy;
		this.name = name;
	}
	
	public static FerretFrequency fromSavedPreferences(Context context) {
		SharedPreferences savedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		String value = savedPrefs.getString(KEY_AGENT_FREQUENCY, "");
		return FerretFrequency.valueOf(value);
	}

	public LocalDateTime getNext(LocalDateTime date) {
		return strategy.getNext(date);
	}

	@Override
	public String toString() {
		return name;
	}
	
}
