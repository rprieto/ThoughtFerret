package com.thoughtworks.thoughtferret.view.paints;

import android.graphics.DashPathEffect;

public class DottedEffect extends DashPathEffect {

	public DottedEffect() {
		super(new float[] { 3, 3 }, 0);
	}
	
}
