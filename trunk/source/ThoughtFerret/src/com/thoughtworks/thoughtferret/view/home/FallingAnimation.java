package com.thoughtworks.thoughtferret.view.home;

import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;


public class FallingAnimation {
	
	private View star;
	private Rect screen;
	
	public FallingAnimation(View star, Rect screen) {
		this.star = star;
		this.screen = screen;
	}
	
	public void startAnimation() {
		int x = (int) (Math.random() * screen.width());
		int speed = (int) (Math.random() * 5000);
		TranslateAnimation fallingStar = new TranslateAnimation(x, x, 0, screen.bottom);
		fallingStar.setStartOffset(0);
		fallingStar.setDuration(speed);
		fallingStar.setFillAfter(true);
		fallingStar.setAnimationListener(animListener);		
		star.startAnimation(fallingStar);
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
                	startAnimation();
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

