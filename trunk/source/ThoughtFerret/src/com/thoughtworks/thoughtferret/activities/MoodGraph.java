package com.thoughtworks.thoughtferret.activities;


import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;

import com.thoughtworks.thoughtferret.view.moodgraph.Panel;
import com.thoughtworks.thoughtferret.view.moodgraph.OptionsPopup;

public class MoodGraph extends Activity {

	private Panel panel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		panel = new Panel(this);
		setContentView(panel);
	}
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Graph options");
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		final OptionsPopup options = new OptionsPopup(this, null);
		options.setOptions(panel.getChartOptions());
		
		final Dialog dialog = new Dialog(this);
		dialog.setTitle("Graph options");
		dialog.setContentView(options);
		
		options.setValidateListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						panel.setChartOptions(options.getOptions());
						dialog.dismiss();
					}
				});
		
		dialog.show();
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
	    Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	    window.addFlags(WindowManager.LayoutParams.FLAG_DITHER);
	}

	@Override
    public void onBackPressed() {
		this.finish();
		overridePendingTransition(0, 0);
		return;
    }
	
}
