package com.thoughtworks.thoughtferret.view.paints;

import android.graphics.Paint;
import android.graphics.Shader;

public class FillPaint extends Paint {

	public FillPaint(int color) {
		setStyle(Paint.Style.FILL);
		setAntiAlias(true);
		setStrokeWidth(1f);
		setStrokeCap(Cap.BUTT);
		setColor(color);
	}
	
	public FillPaint(int color, Shader shader) {
		this(color);
		setShader(shader);
	}
	
}
