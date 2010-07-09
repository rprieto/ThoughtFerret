package com.thoughtworks.thoughtferret;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Scroller;

public class Scroll extends View implements OnGestureListener {

	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1500;

	private float mX;
	private float mY;
	private Scroller mScroller;
	private Paint mPaint;
	private Paint mBorderPaint;
	private Paint mTextPaint;
	private GestureDetector mGestureDetector;
	private float mScale;
	
	private float flingSpeed = 0.75f;
	
	public Scroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		
		mPaint = new Paint();
		LinearGradient shader = new LinearGradient(0, 0, WIDTH, HEIGHT, Color.GREEN, Color.BLUE, TileMode.CLAMP);
		mPaint.setShader(shader);
		mPaint.setAntiAlias(true);
		mBorderPaint = new Paint();
		mBorderPaint.setColor(Color.WHITE);
		mBorderPaint.setStrokeWidth(20);
		mBorderPaint.setStyle(Style.STROKE);
		mTextPaint = new Paint();
		mTextPaint.setTextSize(18);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mTextPaint.setStrokeWidth(2);
		
		mGestureDetector = new GestureDetector(this);
		mGestureDetector.setIsLongpressEnabled(false);
		mScale = 1;
	
		mTextPaint.setColor(0xff000000);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		
		if (mScroller.computeScrollOffset()) {
			mX = mScroller.getCurrX();
			mY = mScroller.getCurrY();
			invalidate();
		}
		
		canvas.scale(mScale, mScale);
		float dx = mX - (getWidth() / mScale) * (mX / WIDTH);
		float dy = mY - (getHeight() / mScale) * (mY / HEIGHT);
		canvas.translate(dx, dy);
		
		canvas.drawRect(0, 0, WIDTH, HEIGHT, mPaint);
		canvas.drawRect(0, 0, WIDTH, HEIGHT, mBorderPaint);
		
		canvas.restore();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		mX -= distanceX / mScale;
		mY -= distanceY / mScale;
		mX = Math.max(-WIDTH, Math.min(0, mX));
		mY = Math.max(-HEIGHT, Math.min(0, mY));
		invalidate();
		return true;
	}
	
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		velocityX *= flingSpeed;
		velocityY *= flingSpeed;
		
		mScroller.fling((int) mX, (int) mY, (int) velocityX, (int) velocityY, -WIDTH, 0, -HEIGHT, 0);
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
