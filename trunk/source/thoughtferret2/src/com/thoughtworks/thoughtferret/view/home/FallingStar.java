package com.thoughtworks.thoughtferret.view.home;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.view.Screen;


public class FallingStar {
	
	private static final int MIN_FALLING_SPEED = 4000;
	private static final int MAX_FALLING_SPEED = 8000;
	private static final int MIN_INITIAL_WAIT = 0;
	private static final int MAX_INITIAL_WAIT = 6000;
	private static final int MAX_X_OFFSET = 25;
	
	private View star;
	private Screen screen;
	private boolean initialFall = true;
	private int x;
	
	public FallingStar(View star, Screen screen, int x) {
		this.star = star;
		this.screen = screen;
		this.x = x;
	}
	
	public void startAnimation() {	
		int transitionTime = 0;
		if (initialFall) {
			transitionTime = MathUtils.getRandom(MIN_INITIAL_WAIT, MAX_INITIAL_WAIT);
			initialFall = false;
		}
		
		int xOffset = -MAX_X_OFFSET + MathUtils.getRandom(0, MAX_X_OFFSET * 2);
		
		int fallingSpeed = MathUtils.getRandom(MIN_FALLING_SPEED, MAX_FALLING_SPEED);
		TranslateAnimation falling = new TranslateAnimation(x + xOffset, x + xOffset, screen.getRect().top - 30, screen.getRect().bottom);
		falling.setStartOffset(0);
		falling.setDuration(fallingSpeed);
		falling.setFillAfter(true);
		falling.setStartOffset(transitionTime);
		
		int spin = (Math.random() > 0.5) ? 360 : -360;
		RotateAnimation spinning = new RotateAnimation(0, spin, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		spinning.setStartOffset(0);
		spinning.setDuration(fallingSpeed);
		spinning.setFillAfter(true);
		spinning.setStartOffset(transitionTime);
		
		AnimationSet set = new AnimationSet(true);
		set.addAnimation(spinning);
		set.addAnimation(falling);
		set.setAnimationListener(animListener);
		star.startAnimation(set);
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

