package com.thoughtworks.thoughtferret;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public abstract class ScrollablePanel extends View {

	Bitmap bufferBitmap;
    Canvas bufferCanvas;
	
    float lastTouchX;
    int scrollPosX;
    
    int sizeX;
    int sizeY;
    
	public ScrollablePanel(Context context, int sizeX, int sizeY) {
        super(context);
        
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        
		bufferBitmap = Bitmap.createBitmap(sizeX, sizeY, Bitmap.Config.ARGB_8888);
	    bufferCanvas = new Canvas(bufferBitmap);
	} 
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	final int action = event.getAction();
        switch (action) {
	        case MotionEvent.ACTION_DOWN: {
	            lastTouchX = event.getX();
	            break;
	        }			            
	        case MotionEvent.ACTION_MOVE: {
	            scrollPosX += lastTouchX - event.getX();
	            scrollPosX = MathUtils.clamp(scrollPosX, 0, sizeX - getWidth());	            
	            lastTouchX = event.getX();
	            invalidate();
	            break;
	        }
        }
        
        return true;
    }
	
    @Override
    public void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	
    	drawFullCanvas(bufferCanvas);
    	
    	Rect source = new Rect(scrollPosX, 0, scrollPosX + getWidth(), getHeight());
    	Rect dest = new Rect(0, 0, getWidth(), getHeight());
    	canvas.drawBitmap(bufferBitmap, source, dest, null);		    	
    }
    
    protected abstract void drawFullCanvas(Canvas canvas);
	
}
