package com.thoughtworks.thoughtferret;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class HappyWords extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.happywords);
	}
	
}