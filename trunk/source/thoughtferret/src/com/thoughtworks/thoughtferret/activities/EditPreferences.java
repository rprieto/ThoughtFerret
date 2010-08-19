package com.thoughtworks.thoughtferret.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.integration.Preferences;
import com.thoughtworks.thoughtferret.integration.agent.Scheduler;
import com.thoughtworks.thoughtferret.model.agent.FerretFrequency;
import com.thoughtworks.thoughtferret.view.ApplicationBackground;

public class EditPreferences extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
        setBackground();
	}
	
	@Override
	protected void onResume() {
		SharedPreferences prefs = getPreferenceScreen().getSharedPreferences();
		prefs.registerOnSharedPreferenceChangeListener(this);
		super.onResume();
	}
		
	@Override
	protected void onPause() {
		SharedPreferences prefs = getPreferenceScreen().getSharedPreferences();
		prefs.unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}
	
	@Override
    public void onBackPressed() {
		this.finish();
		overridePendingTransition(0, 0);
		return;
    }
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
		if (key.equals(Preferences.KEY_AGENT_NEXTALARM) == false) {
			boolean agentEnabled = preferences.getBoolean(Preferences.KEY_AGENT_ENABLED, false);
			FerretFrequency frequency = FerretFrequency.fromSavedPreferences(EditPreferences.this);
			refreshSchedule(agentEnabled, frequency);
			String message = getUserMessage(agentEnabled, frequency);
			Toast.makeText(EditPreferences.this, message,  Toast.LENGTH_SHORT).show();
		}
	}
	
	private void setBackground() {
		View root = findViewById(android.R.id.content);
		ApplicationBackground appBackground = new ApplicationBackground(this, ApplicationBackground.GradientDirection.HORIZONTAL, true);
		appBackground.setBackground(root);
	}
	
	private void refreshSchedule(boolean enabled, FerretFrequency frequency) {
		Scheduler scheduler = new Scheduler(EditPreferences.this);
		scheduler.cancelPendingAlarms();
		if (enabled) {
			scheduler.registerNextRandom(frequency);
		}
	}
	
	private String getUserMessage(boolean enabled, FerretFrequency frequency) {
		if (enabled) {
			return "Ferret agent enabled " + frequency.toString();
		} else {
			return "Ferret agent disabled";
		}
	}
	
}
