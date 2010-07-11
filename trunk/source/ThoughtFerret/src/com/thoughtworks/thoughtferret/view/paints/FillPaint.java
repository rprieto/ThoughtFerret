package com.thoughtworks.thoughtferret.view.paints;

import android.graphics.Paint;

public class FillPaint extends Paint {

	public FillPaint(int color, float width) {
		setStyle(Paint.Style.STROKE);
		setAntiAlias(true);
		setStrokeWidth(width);
		setStrokeCap(Cap.BUTT);
		setColor(color);
	}
	
}
