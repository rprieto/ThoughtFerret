package com.thoughtworks.thoughtferret.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.model.DatabaseHelper;
import com.thoughtworks.thoughtferret.model.FakeData;
import com.thoughtworks.thoughtferret.scheduler.Scheduler;

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
