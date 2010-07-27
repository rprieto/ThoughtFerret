package com.thoughtworks.thoughtferret.view.home;

import com.thoughtworks.thoughtferret.MathUtils;

import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;


public class FallingAnimation {
	
	private View star;
	private Rect screen;
	
	private static final int MIN_FALLING_SPEED = 3000;
	private static final int MAX_FALLING_SPEED = 5000;
	private static final int MIN_ROTATION_SPEED = 4000;
	private static final int MAX_ROTATION_SPEED = 9000;
	private static final int MIN_WAITING = 500;
	private static final int MAX_WAITING = 4000;
	
	public FallingAnimation(View star, Rect screen) {
		this.star = star;
		this.screen = screen;
	}
	
	public void startAnimation() {		
		int waitDuration = MathUtils.getRandom(MIN_WAITING, MAX_WAITING);
		
		int x = MathUtils.getRandom(0, screen.width());
		int fallingSpeed = MathUtils.getRandom(MIN_FALLING_SPEED, MAX_FALLING_SPEED);
		TranslateAnimation falling = new TranslateAnimation(x, x, -50, screen.bottom);
		falling.setStartOffset(0);
		falling.setDuration(fallingSpeed);
		falling.setFillAfter(true);
		falling.setStartOffset(waitDuration);
		
		int spinningSpeed = MathUtils.getRandom(MIN_ROTATION_SPEED, MAX_ROTATION_SPEED);
		RotateAnimation spinning = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		spinning.setStartOffset(0);
		spinning.setDuration(spinningSpeed);
		spinning.setFillAfter(true);
		spinning.setStartOffset(waitDuration);
		
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

