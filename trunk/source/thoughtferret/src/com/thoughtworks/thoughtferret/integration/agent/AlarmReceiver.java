package com.thoughtworks.thoughtferret.integration.agent;

import com.thoughtworks.thoughtferret.model.agent.FerretFrequency;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		FerretFrequency frenquency = FerretFrequency.fromSavedPreferences(context);
		new FerretNotifier().show(context);
		new Scheduler(context).registerNextRandom(frenquency);
	}
	
}
