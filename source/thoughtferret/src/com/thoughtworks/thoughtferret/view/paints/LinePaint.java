package com.thoughtworks.thoughtferret.view.paints;

import android.graphics.Paint;

public class LinePaint extends Paint {

	public LinePaint(int color, float width) {
		setStyle(Paint.Style.STROKE);
		setAntiAlias(true);
		setStrokeWidth(width);
		setStrokeCap(Cap.BUTT);
		setColor(color);
	}
	
}
