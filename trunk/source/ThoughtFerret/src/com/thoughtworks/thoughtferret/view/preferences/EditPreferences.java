package com.thoughtworks.thoughtferret.view.preferences;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.view.ApplicationBackground;
import com.thoughtworks.thoughtferret.view.Screen;

public class EditPreferences extends PreferenceActivity {

	Screen screen;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		screen = new Screen(this);
        setBackground();
	}
	
	private void setBackground() {
		ApplicationBackground appBackground = new ApplicationBackground(this, ApplicationBackground.GradientDirection.HORIZONTAL, true);
        Bitmap fullBackground = Bitmap.createBitmap(screen.width(), screen.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(fullBackground);
        appBackground.draw(canvas);
        
        View content = findViewById(android.R.id.content);
        BitmapDrawable drawable = new BitmapDrawable(fullBackground); 
        content.setBackgroundDrawable(drawable);
	}
	
}
