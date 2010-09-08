package com.thoughtworks.thoughtferret.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.integration.Preferences;
import com.thoughtworks.thoughtferret.integration.agent.Scheduler;
import com.thoughtworks.thoughtferret.integration.database.DatabaseHelper;
import com.thoughtworks.thoughtferret.integration.database.FakeData;

public class DemoHacks extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demohacks);
        loadNextFerretDate();
	}

	@Override
    public void onBackPressed() {
		this.finish();
		overridePendingTransition(0, 0);
		return;
    }
	
	public void triggerFerretClick(View view) {
		new Scheduler(this).registerNextVerySoon();
		finish();
		overridePendingTransition(0, 0);
	}
	
	public void deleteDatabaseClick(View view) {
		deleteDatabase(DatabaseHelper.DATABASE_NAME);
	}
	
	public void populateDatabaseClick(View view) {
		FakeData fakeData = new FakeData(this);
		fakeData.createHistory();
	}
	
	private void loadNextFerretDate() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String date = preferences.getString(Preferences.KEY_AGENT_NEXTALARM, "");
        TextView nextFerretDate = (TextView) findViewById(R.id.nextFerretDate);
        nextFerretDate.setText(date);
	}

	
}
