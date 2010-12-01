package com.thoughtworks.thoughtferret.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

public class Screen {

	private Display display;
	private Rect rect;
	
	public Screen(Context context) {
		display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		rect = new Rect(0, 0, display.getWidth(), display.getHeight());
	}

	public Rect getRect() {
		return rect;
	}
	
	public int height() {
		return rect.height();
	}
	
	public int width() {
		return rect.width();
	}

}
