package com.thoughtworks.thoughtferret.model;

import org.joda.time.LocalDateTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public enum FerretFrequency {
	
	EVERY_DAY,
	EVERY_FEW_DAYS,
	EVERY_WEEK;

	public static final String KEY_AGENT_FREQUENCY = "agentFrequency";
	
	public static FerretFrequency fromSavedPreferences(Context context) {
		SharedPreferences savedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		String value = savedPrefs.getString(KEY_AGENT_FREQUENCY, "");
		return FerretFrequency.valueOf(value);
	}

	public LocalDateTime getNext(LocalDateTime date) {
		return null;
	}
	
}
