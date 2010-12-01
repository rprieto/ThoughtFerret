package com.thoughtworks.thoughtferret.view.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.view.Screen;

public class FallingStars {
	 
	private static final int NB_STARS = 7;
	private static final int PADDING = 50;
	
    List<FallingStar> stars;
 
    public FallingStars(Context context, ViewGroup layout) {
    	Screen screen = new Screen(context); 
    	stars = new ArrayList<FallingStar>();
    	for (int i = 0; i < NB_STARS; ++i) {
			ImageView image = new ImageView(context);
			image.setBackgroundResource(R.drawable.star);
			layout.addView(image);
			stars.add(createStar(image, screen, i));
    	}
    }

    public FallingStar createStar(View image, Screen screen, int index) {
		int x = MathUtils.project(0, NB_STARS - 1, PADDING, screen.width() - PADDING, index);
		return new FallingStar(image, screen, x);
    }
    
    public void startAnimation() {
    	for (FallingStar star : stars) {
    		star.startAnimation();
		}
    }
    
}

