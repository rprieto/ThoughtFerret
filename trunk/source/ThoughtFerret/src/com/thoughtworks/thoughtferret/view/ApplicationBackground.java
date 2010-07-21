package com.thoughtworks.thoughtferret.view;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;

public class ApplicationBackground {

	public enum GradientDirection {
		HORIZONTAL,
		VERTICAL
	}
	
	private final int happyColor = 0x5500FF00;
	private final int sadColor = 0xCCFF3300;
	
	private final Paint nopPaint;
	
	private final Paint fadeOverlayPaint;
    private final Paint backgroundPaint;
    private final Paint backgroundGradientPaint;
    private final Bitmap backgroundBitmap;
	
    private Bitmap cachedBitmap;
    
	public ApplicationBackground(Resources resources, int width, int height, GradientDirection direction, boolean fadeOverlay) {
    	backgroundBitmap = BitmapFactory.decodeResource(resources, R.drawable.homebackground);
        backgroundPaint = new FillPaint(0xAA000000);
		
        Shader gradient = getGradientShader(width, height, direction);
 		backgroundGradientPaint = new FillPaint(0xFFDDDDDD, gradient);
        
		nopPaint = new Paint();
		fadeOverlayPaint = new FillPaint(0x44DDDDDD);
		
		cachedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas fullCanvas = new Canvas(cachedBitmap);
		
		prepare(fullCanvas, width, height, fadeOverlay);
	}
	
	private Shader getGradientShader(int width, int height, GradientDirection direction) {
		Point start, end;
		if (direction == GradientDirection.HORIZONTAL) {
			start = new Point(0, 0);
			end = new Point(width, 0);
		} else {
			start = new Point(0, height);
			end = new Point(0, 0);
		}
		return new LinearGradient(start.x, start.y, end.x, end.y, sadColor, happyColor, Shader.TileMode.CLAMP);
	}
	
	private void prepare(Canvas canvas, int width, int height, boolean fadeOverlay) {
    	Rect target = new Rect(0, 0, width, height);
    	canvas.drawBitmap(backgroundBitmap, null, target, backgroundPaint);
    	canvas.drawRect(0, 0, width, height, backgroundGradientPaint);
    	if (fadeOverlay) {
    		canvas.drawRect(target, fadeOverlayPaint);
    	}
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(cachedBitmap, 0, 0, nopPaint);
	}
	
	public Paint getFadeOverlayPaint() {
		return fadeOverlayPaint;
	}
	
	public Bitmap getBitmap() {
		return cachedBitmap;
	}
	
}
