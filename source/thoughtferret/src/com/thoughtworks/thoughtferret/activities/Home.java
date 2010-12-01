package com.thoughtworks.thoughtferret.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.view.ApplicationBackground;
import com.thoughtworks.thoughtferret.view.Screen;
import com.thoughtworks.thoughtferret.view.home.FallingStars;

public class Home extends Activity {
	
	LinearLayout homeBackground;
	ImageView starImage;
	Screen screen;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        homeBackground = (LinearLayout) findViewById(R.id.homeBackground);

        screen = new Screen(this);
        setBackground();
        
        LinearLayout starContainer = (LinearLayout) findViewById(R.id.starContainer); 
        FallingStars stars = new FallingStars(this, starContainer);
        stars.startAnimation();
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
		overridePendingTransition(0, 0);
	}

	public void mapClick(View view) {
		startActivity(new Intent(this, Map.class));
		overridePendingTransition(0, 0);
	}
	
	private void setBackground() {
		ApplicationBackground appBackground = new ApplicationBackground(this, ApplicationBackground.GradientDirection.HORIZONTAL, true);
        Bitmap fullBackground = Bitmap.createBitmap(screen.width(), screen.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(fullBackground);
        appBackground.draw(canvas);
                
		drawFerret(canvas);
        drawLogo(canvas);
        
        BitmapDrawable drawable = new BitmapDrawable(fullBackground);        
        homeBackground.setBackgroundDrawable(drawable);
	}

	private void drawFerret(Canvas canvas) {
        Bitmap ferretBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homeferret);       
        Bitmap smallFerret = Bitmap.createScaledBitmap(ferretBitmap, 200, 200, true);

        Paint paint = new Paint(); 
        paint.setXfermode(new PorterDuffXfermode(Mode.MULTIPLY)); 

        int left = (screen.width() - smallFerret.getWidth()) / 2;
        int top = (screen.height() - smallFerret.getHeight()) / 2;
        Point topLeft = new Point(left, top);
        canvas.drawBitmap(smallFerret, topLeft.x, topLeft.y, paint);
	}

	private void drawLogo(Canvas canvas) {
		Paint logoPaint = new Paint();
        logoPaint.setAlpha(120);
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        canvas.drawBitmap(logo, screen.width() - logo.getWidth(), 0, logoPaint);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.homemenu, menu);
	    return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        	case R.id.demoHacks:
        		startActivity(new Intent(this, DemoHacks.class));
        		break;
        	case R.id.preferences:
        		startActivity(new Intent(this, EditPreferences.class));
        		break;
        }
		return true;
	}

}
