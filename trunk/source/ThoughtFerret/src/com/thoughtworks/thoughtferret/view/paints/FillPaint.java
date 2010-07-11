package com.thoughtworks.thoughtferret.view.paints;

import android.graphics.Paint;
import android.graphics.Shader;

public class FillPaint extends Paint {

	public FillPaint(int color, float width) {
		setStyle(Paint.Style.FILL);
		setAntiAlias(true);
		setStrokeWidth(width);
		setStrokeCap(Cap.BUTT);
		setColor(color);
	}
	
	public FillPaint(int color, float width, Shader shader) {
		this(color, width);
		setShader(shader);
	}
	
}
