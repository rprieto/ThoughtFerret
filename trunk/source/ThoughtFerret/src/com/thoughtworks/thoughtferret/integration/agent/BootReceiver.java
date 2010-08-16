package com.thoughtworks.thoughtferret.integration.agent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.thoughtworks.thoughtferret.model.agent.FerretFrequency;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		FerretFrequency frenquency = FerretFrequency.fromSavedPreferences(context);
		Scheduler scheduler = new Scheduler();
		scheduler.registerNextRandom(context, frenquency);
	}
	
}
