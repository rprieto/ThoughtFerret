package com.thoughtworks.thoughtferret.view.home;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thoughtworks.thoughtferret.R;

public class FallingStars {
	 
    List<ImageView> stars;
    Rect screen;

    public FallingStars(Context context, ViewGroup layout, Rect screen) {
    	this.screen = screen; 
        stars = Arrays.asList(createStar(context), createStar(context), createStar(context));
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
    		new FallingAnimation(star, screen).startAnimation();
		}
    }
    
    
    

}

