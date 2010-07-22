package com.thoughtworks.thoughtferret.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtworks.thoughtferret.R;

public class Home extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        BitmapDrawable drawable = new BitmapDrawable(createBackground());        
        LinearLayout homeBackground = (LinearLayout) findViewById(R.id.homeBackground);
        homeBackground.setBackgroundDrawable(drawable);
        
//        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/handmadetypewriter.ttf");
//        TextView homeUpdate = (TextView) findViewById(R.id.homeUpdate);
//        homeUpdate.setTypeface(typeFace);
	}
	
	public void updateClick(View view) {
		startActivity(new Intent(this, MoodUpdate.class));
		overridePendingTransition(0, 0);
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
	
	public Bitmap createBackground() {
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Rect screen = new Rect(0, 0, display.getWidth(), display.getHeight());        
        ApplicationBackground appBackground = new ApplicationBackground(getResources(), screen.width(), screen.height(), ApplicationBackground.GradientDirection.HORIZONTAL, true);
        Bitmap ferretBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homeferret);       
        
        Bitmap fullBackground = Bitmap.createBitmap(screen.width(), screen.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(fullBackground);
        appBackground.draw(canvas);
        
        Paint paint = new Paint(); 
        paint.setXfermode(new PorterDuffXfermode(Mode.MULTIPLY)); 

        Point topLeft = new Point(display.getWidth() - ferretBitmap.getWidth(), display.getHeight() - ferretBitmap.getHeight());
        canvas.drawBitmap(ferretBitmap, topLeft.x, topLeft.y, paint);
        
        return fullBackground;
	}
	
	
}
