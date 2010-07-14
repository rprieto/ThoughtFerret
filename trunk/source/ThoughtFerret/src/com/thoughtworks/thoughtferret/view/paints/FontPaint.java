package com.thoughtworks.thoughtferret.view.paints;

import android.graphics.Paint;

public class FontPaint extends Paint {

	public FontPaint(int color, int fontSize, Paint.Align alignment) {
		setStyle(Paint.Style.FILL);
		setAntiAlias(true);
		setStrokeWidth(1f);
		setStrokeCap(Cap.BUTT);
		setColor(color);
		setTextSize(fontSize);
		setTextAlign(alignment);
	}
	
}
