package com.thoughtworks.thoughtferret.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.integration.agent.Scheduler;
import com.thoughtworks.thoughtferret.integration.database.DatabaseHelper;
import com.thoughtworks.thoughtferret.integration.database.FakeData;

public class DemoHacks extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demohacks);
	}
	
	public void triggerFerretClick(View view) {
		Scheduler scheduler = new Scheduler();
		scheduler.registerNextVerySoon(this);
	}
	
	public void deleteDatabaseClick(View view) {
		deleteDatabase(DatabaseHelper.DATABASE_NAME);
	}
	
	public void populateDatabaseClick(View view) {
		FakeData fakeData = new FakeData(this);
		fakeData.createHistory();
	}
	
}
