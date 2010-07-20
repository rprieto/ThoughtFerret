package com.thoughtworks.thoughtferret.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;

public class GraphPaints {

	private final int happyColor = 0x5500FF00;
	private final int sadColor = 0xCCFF3300;
	
	private final Paint fadeOverlayPaint;
    private final Paint backgroundPaint;
    private final Paint backgroundGradientPaint;
    private final Bitmap backgroundBitmap;
	
    public GraphPaints(Resources resources, Point gradientStart, Point gradientEnd) {
    	backgroundBitmap = BitmapFactory.decodeResource(resources, R.drawable.homebackground);
        backgroundPaint = new FillPaint(0xAA000000);
		
		Shader gradient = new LinearGradient(gradientStart.x, gradientStart.y, gradientEnd.x, gradientEnd.y, sadColor, happyColor, Shader.TileMode.CLAMP);
		backgroundGradientPaint = new FillPaint(0xFFDDDDDD, gradient);
        
		fadeOverlayPaint = new FillPaint(0x44DDDDDD);
    }
    
    public void drawBackground(Canvas canvas, Rect fullSize, Rect visibleRect) {
		canvas.save();
    	canvas.translate(-visibleRect.left, -visibleRect.top);
    	Rect atZero = new Rect(0, 0, visibleRect.width(), visibleRect.height());
    	canvas.drawBitmap(backgroundBitmap, null, atZero, backgroundPaint);
    	canvas.restore();
    	canvas.drawRect(fullSize, backgroundGradientPaint);
	}

	public Paint getFadeOverlayPaint() {
		return fadeOverlayPaint;
	}
    
}
