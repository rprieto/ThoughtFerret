package com.thoughtworks.thoughtferret.scheduler;

import com.thoughtworks.thoughtferret.view.preferences.FerretFrequency;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		FerretFrequency frenquency = new FerretFrequency(context);
		Scheduler scheduler = new Scheduler();
		scheduler.registerNextRandom(context, frenquency);
	}
	
}
