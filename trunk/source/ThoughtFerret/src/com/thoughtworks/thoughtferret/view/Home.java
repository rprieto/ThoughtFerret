package com.thoughtworks.thoughtferret.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.thoughtworks.thoughtferret.R;

public class Home extends Activity {

	private ApplicationBackground appBackground;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        LinearLayout homeBackground = (LinearLayout) findViewById(R.id.homeBackground);
        
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Rect screen = new Rect(0, 0, display.getWidth(), display.getHeight());
        
        appBackground = new ApplicationBackground(getResources(), screen.width(), screen.height(), ApplicationBackground.GradientDirection.HORIZONTAL, true);
        BitmapDrawable drawable = new BitmapDrawable(appBackground.getBitmap());
        
        homeBackground.setBackgroundDrawable(drawable);
	}
	
	public void updateClick(View view) {
		startActivity(new Intent(this, MoodUpdate.class));
	}

	public void graphClick(View view) {
		startActivity(new Intent(this, MoodGraph.class));
		overridePendingTransition(0, 0);
	}
	
	public void wordsClick(View view) {
		startActivity(new Intent(this, HappyWords.class));
		overridePendingTransition(0, 0);
	}
	
	public void preferencesClick(View view) {
		startActivity(new Intent(this, EditPreferences.class));
	}
	
	public void demoHacksClick(View view) {
		startActivity(new Intent(this, DemoHacks.class));
	}
	

//	public void drawFerret() {
//        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//		Rect screen = new Rect(0, 0, display.getWidth(), display.getHeight());
//
//        ferretBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homebackground);
//
//		Point topLeft = new Point(display.getWidth() - ferretBitmap.getWidth(), display.getHeight() - ferretBitmap.getHeight());
//		Rect target = new Rect(topLeft.x, topLeft.y, ferretBitmap.getWidth(), ferretBitmap.getHeight());
//		canvas.drawBitmap(ferretBitmap, null, target, ferretPaint);
//	}
	
}
