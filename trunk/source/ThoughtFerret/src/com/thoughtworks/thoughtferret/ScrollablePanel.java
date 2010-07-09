package com.thoughtworks.thoughtferret;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public abstract class ScrollablePanel extends View  {
	
	private Bitmap bufferBitmap;
	private Canvas bufferCanvas;
	
    private float lastTouchX;
    private int scrollPosX;
    
    private int sizeX;
    private int sizeY;
    
    private Display display;
    private Paint fpsPaint;
    
	public ScrollablePanel(Context context) {
        super(context);
        
        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        fpsPaint = new Paint() {{
			setStyle(Paint.Style.STROKE);
			setAntiAlias(true);
			setStrokeWidth(1.0f);
			setStrokeCap(Cap.BUTT);
			setColor(0xFFFFFFFF);
		}};
	} 
	
	protected void init(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        
        if (bufferBitmap == null)
        {
        	recycleResources();
        	bufferBitmap = Bitmap.createBitmap(sizeX, sizeY, Bitmap.Config.ARGB_8888);
        	bufferCanvas = new Canvas(bufferBitmap);	
        }        
	}
	
	protected void recycleResources() {
		if (bufferBitmap != null)
        {
			bufferBitmap.recycle();
			bufferBitmap = null;
        }
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
    	
    	String fps = String.format("%d fps", (int)display.getRefreshRate());
    	canvas.drawText(fps.toString(), 10, 20, fpsPaint);
    }
    
    protected abstract void drawFullCanvas(Canvas canvas);
	
}
