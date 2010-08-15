package com.thoughtworks.thoughtferret.view.preferences;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class FerretFrequency {

	public enum Frequency {
		EVERY_DAY,
		EVERY_FEW_DAYS,
		EVERY_WEEK
	}
	
	private Frequency frequency;
	
	private static HashMap<String, Frequency> converter = new HashMap<String, Frequency>() {{
		put("everyday", Frequency.EVERY_DAY);
		put("everyfewdays", Frequency.EVERY_FEW_DAYS);
		put("everyweek", Frequency.EVERY_WEEK);
	}};
	
	public FerretFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	
	public FerretFrequency(Context context) {
		SharedPreferences savedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		String value = savedPrefs.getString(SavedPreferences.KEY_AGENT_FREQUENCY, "");
		if (converter.containsKey(value)) {
			frequency = converter.get(value);
		} else {
			throw new RuntimeException("Invalid saved preference for " + SavedPreferences.KEY_AGENT_FREQUENCY); 
		}
	}
	
	public Frequency getFrequency() {
		return frequency;
	}
	
}
