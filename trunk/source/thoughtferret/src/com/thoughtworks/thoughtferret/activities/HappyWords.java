package com.thoughtworks.thoughtferret.activities;


import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.thoughtworks.thoughtferret.view.happywords.Panel;


public class HappyWords extends Activity {
	
private Panel panel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		panel = new Panel(this);
		setContentView(panel);
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