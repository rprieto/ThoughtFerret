package com.thoughtworks.thoughtferret.view;

import com.thoughtworks.thoughtferret.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
	}
	
	public void updateClick(View view) {
		startActivity(new Intent(this, MoodUpdate.class));
	}

	public void graphClick(View view) {
		startActivity(new Intent(this, MoodGraph.class));
	}
	
	public void wordsClick(View view) {
		startActivity(new Intent(this, HappyWords.class));
	}
	
	public void preferencesClick(View view) {
		startActivity(new Intent(this, EditPreferences.class));
	}
	
	public void demoHacksClick(View view) {
		startActivity(new Intent(this, DemoHacks.class));
	}
	
}
