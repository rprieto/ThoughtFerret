package com.thoughtworks.thoughtferret.view.home;

import java.util.Arrays;
import java.util.List;

import com.thoughtworks.thoughtferret.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FallingStars {
	 
    List<ImageView> stars;
    ImageView star;
    Rect screen;

    public FallingStars(Context context, ViewGroup layout, Rect screen) {
        star = createStar(context);
        this.screen = screen; 
        layout.addView(star);
    }

    private ImageView createStar(Context context) {
        ImageView image = new ImageView(context);
        image.setBackgroundResource(R.drawable.star);
        return image;
    }

    public void startAnimation() {
		int x = (int) (Math.random() * screen.width());
		TranslateAnimation fallingStar = new TranslateAnimation(x, x, 0, screen.bottom);
		fallingStar.setStartOffset(0);
		fallingStar.setDuration(5000);
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

