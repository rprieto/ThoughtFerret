package com.thoughtworks.thoughtferret.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.LinearLayout;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;

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
	
    private Screen screen;
    private Bitmap cachedBitmap;
    
	public ApplicationBackground(Context context, GradientDirection direction, boolean fadeOverlay) {
		
		screen = new Screen(context);

		Resources resources = context.getResources();
    	backgroundBitmap = BitmapFactory.decodeResource(resources, R.drawable.homebackground);
        backgroundPaint = new FillPaint(0xAA000000);
		
        Shader gradient = getGradientShader(direction);
 		backgroundGradientPaint = new FillPaint(0xFFDDDDDD, gradient);
        
		nopPaint = new Paint();
		fadeOverlayPaint = new FillPaint(0x44DDDDDD);
		
		cachedBitmap = Bitmap.createBitmap(screen.width(), screen.height(), Bitmap.Config.ARGB_8888);
		Canvas fullCanvas = new Canvas(cachedBitmap);
		
		prepare(fullCanvas, fadeOverlay);
	}
	
	public void setBackground(View view) {
        BitmapDrawable drawable = new BitmapDrawable(getBitmap());        
        view.setBackgroundDrawable(drawable);
	}
	
	private Shader getGradientShader(GradientDirection direction) {
		Point start, end;
		if (direction == GradientDirection.HORIZONTAL) {
			start = new Point(0, 0);
			end = new Point(screen.width(), 0);
		} else {
			start = new Point(0, screen.height());
			end = new Point(0, 0);
		}
		return new LinearGradient(start.x, start.y, end.x, end.y, sadColor, happyColor, Shader.TileMode.CLAMP);
	}
	
	private void prepare(Canvas canvas, boolean fadeOverlay) {
    	canvas.drawBitmap(backgroundBitmap, null, screen.getRect(), backgroundPaint);
    	canvas.drawRect(0, 0, screen.width(), screen.height(), backgroundGradientPaint);
    	if (fadeOverlay) {
    		canvas.drawRect(screen.getRect(), fadeOverlayPaint);
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
