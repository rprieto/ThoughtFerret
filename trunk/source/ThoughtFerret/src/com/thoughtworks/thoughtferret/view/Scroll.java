package com.thoughtworks.thoughtferret.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Scroller;

public class Scroll extends View implements OnGestureListener {

	private int fullWidth = 1000;
	private int fullHeight = 1500;

	private float mX;
	private float mY;
	
	private Scroller mScroller;
	private GestureDetector mGestureDetector;
	
	private float flingSpeed = 0.75f;
	
    protected Display display;
    
    private Paint fpsPaint;
    private Paint borderPaint;
	
	public Scroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		
		mGestureDetector = new GestureDetector(this);
		mGestureDetector.setIsLongpressEnabled(false);
	
		display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		
        fpsPaint = new Paint() {{
			setStyle(Paint.Style.STROKE);
			setAntiAlias(true);
			setStrokeWidth(1.0f);
			setStrokeCap(Cap.BUTT);
			setColor(0xFFFFFFFF);
		}};
		
        borderPaint = new Paint() {{
			setStyle(Paint.Style.STROKE);
			setAntiAlias(true);
			setStrokeWidth(3.0f);
			setStrokeCap(Cap.SQUARE);
			setColor(0xFF0000);
		}};
	}
	
	public void setFullSize(int width, int height) {
		fullWidth = width;
		fullHeight = height;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		
		if (mScroller.computeScrollOffset()) {
			mX = mScroller.getCurrX();
			mY = mScroller.getCurrY();
			invalidate();
		}
		
		float dx = mX - getWidth() * (mX / fullWidth);
		float dy = mY - getHeight() * (mY / fullHeight);
		canvas.translate(dx, dy);

		Rect visibleRect = new Rect((int)mX, (int)mY, (int)mX + getWidth(), (int)mY + getHeight());
		drawFullCanvas(canvas, visibleRect);
		
		canvas.restore();
	}
	
	protected void drawFullCanvas(Canvas canvas, Rect visibleRect) {
		canvas.drawRect(0, 0, fullWidth, fullHeight, borderPaint);
    	String fps = String.format("%d fps", (int)display.getRefreshRate());
    	canvas.drawText(fps.toString(), visibleRect.left + 20, visibleRect.top + 20, fpsPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		mX -= distanceX;
		mY += distanceY;
		mX = Math.max(-fullWidth, Math.min(0, mX));
		mY = Math.max(-fullHeight, Math.min(0, mY));
		invalidate();
		return true;
	}
	
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		velocityX *= flingSpeed;
		velocityY *= flingSpeed;
		
		mScroller.fling((int) mX, (int) mY, (int) velocityX, (int) velocityY, -fullWidth, 0, -fullHeight, 0);
		invalidate();
		return true;
	}

	public void onLongPress(MotionEvent e) {
	}

	public void onShowPress(MotionEvent e) {
	}
	
	public boolean onDown(MotionEvent e) {
		return true;
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

}
