package com.thoughtworks.thoughtferret.activities;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.View;
import android.widget.Toast;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.integration.agent.Scheduler;
import com.thoughtworks.thoughtferret.model.agent.FerretFrequency;
import com.thoughtworks.thoughtferret.view.ApplicationBackground;

public class EditPreferences extends PreferenceActivity {

	public static final String KEY_AGENT_ENABLED = "agentEnabled";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		addPreferencesFromResource(R.xml.preferences);
        setBackground();
	}
	
	private void setBackground() {
		View root = findViewById(android.R.id.content);
		ApplicationBackground appBackground = new ApplicationBackground(this, ApplicationBackground.GradientDirection.HORIZONTAL, true);
		appBackground.setBackground(root);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,	Preference preference) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if (preference.getKey().equals(KEY_AGENT_ENABLED)) {
			Scheduler scheduler = new Scheduler();
			scheduler.cancelPendingAlarms(this);
			boolean agentEnabled = preferences.getBoolean(KEY_AGENT_ENABLED, false);
			if (agentEnabled) {
				FerretFrequency frequency = FerretFrequency.fromSavedPreferences(this);
				scheduler.registerNextRandom(this, frequency);
				Toast.makeText(this, "Ferret agent enabled", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Ferret agent disabled", Toast.LENGTH_SHORT).show();
			}
		}
		
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
	@Override
    public void onBackPressed() {
		this.finish();
		overridePendingTransition(0, 0);
		return;
    }
	
}
