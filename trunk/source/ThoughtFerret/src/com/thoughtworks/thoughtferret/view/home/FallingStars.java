package com.thoughtworks.thoughtferret.view.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.view.Screen;

public class FallingStars {
	 
	private static final int NB_STARS = 6;
	
    List<ImageView> stars;
    Screen screen;

    public FallingStars(Context context, ViewGroup layout) {
    	screen = new Screen(context); 
    	
    	stars = new ArrayList<ImageView>();
    	for (int i = 0; i < NB_STARS; ++i) {
    		stars.add(createStar(context));
    	}

    	for (ImageView star : stars) {			
        	layout.addView(star);
		}
    }

    private ImageView createStar(Context context) {
        ImageView image = new ImageView(context);
        image.setBackgroundResource(R.drawable.star);
        return image;
    }

    public void startAnimation() {
    	for (ImageView star : stars) {
    		new FallingAnimation(star, screen.getRect()).startAnimation();
		}
    }
    
}

