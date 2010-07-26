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
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.view.moodgraph.MoodGraph;

public class Home extends Activity {
	
	LinearLayout homeBackground;
	ImageView starImage;
	Rect screen;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        homeBackground = (LinearLayout) findViewById(R.id.homeBackground);
        starImage = (ImageView) findViewById(R.id.homestar);

        calculateScreen();
        setBackground();
        createAnimation();
        
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
	
	private void calculateScreen() {
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        screen = new Rect(0, 0, display.getWidth(), display.getHeight());
	}
	
	private void setBackground() {
		ApplicationBackground appBackground = new ApplicationBackground(getResources(), screen.width(), screen.height(), ApplicationBackground.GradientDirection.HORIZONTAL, true);
        Bitmap ferretBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homeferret);       
        Bitmap smallFerret = Bitmap.createScaledBitmap(ferretBitmap, 200, 200, true);
        
        Bitmap fullBackground = Bitmap.createBitmap(screen.width(), screen.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(fullBackground);
        appBackground.draw(canvas);
        
        Paint paint = new Paint(); 
        paint.setXfermode(new PorterDuffXfermode(Mode.MULTIPLY)); 

        int left = (screen.width() - smallFerret.getWidth()) / 2;
        int top = (screen.height() - smallFerret.getHeight()) / 2;
        Point topLeft = new Point(left, top);
        canvas.drawBitmap(smallFerret, topLeft.x, topLeft.y, paint);
        
        BitmapDrawable drawable = new BitmapDrawable(fullBackground);        
        homeBackground.setBackgroundDrawable(drawable);
	}
	
	public void createAnimation() {
		int x = (int) (Math.random() * screen.width());
		TranslateAnimation fallingStar = new TranslateAnimation(x, x, 0, screen.bottom);
		fallingStar.setStartOffset(0);
		fallingStar.setDuration(5000);
		fallingStar.setFillAfter(true);
		fallingStar.setAnimationListener(animListener);		
		starImage.startAnimation(fallingStar);
	}
	
	private AnimationListener animListener = new LocalAnimationListener();
	
	class LocalAnimationListener implements AnimationListener
    {
        public void onAnimationEnd(Animation animation)
        {
            Handler curHandler = new Handler();
            curHandler.postDelayed( new Runnable()
            {
                public void run()
                {
                	createAnimation();
                }
            }, 1);
        }
        public void onAnimationRepeat(Animation animation)
        {
        }
        public void onAnimationStart(Animation animation)
        {
        }
    };

}
