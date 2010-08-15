package com.thoughtworks.thoughtferret.scheduler;

import com.thoughtworks.thoughtferret.model.FerretFrequency;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		FerretFrequency frenquency = FerretFrequency.fromSavedPreferences(context);
		new FerretNotifier().show(context);
		new Scheduler().registerNextRandom(context, frenquency);
	}
	
}
