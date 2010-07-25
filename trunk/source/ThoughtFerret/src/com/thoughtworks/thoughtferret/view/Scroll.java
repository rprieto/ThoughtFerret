package com.thoughtworks.thoughtferret.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Scroller;

import com.thoughtworks.thoughtferret.MathUtils;

public class Scroll extends View implements OnGestureListener {

	private Rect fullSize = new Rect(); 
	private Point currentScroll = new Point();
	
	private Scroller mScroller;
	private GestureDetector gestureDetector;
	
	private float flingSpeed = 0.75f;
	
    protected Display display;

	public Scroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		
		gestureDetector = new GestureDetector(this);
		gestureDetector.setIsLongpressEnabled(true);
	
		display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	}

	public Rect getFullSize() {
		return fullSize;
	}
	
	public void setFullSize(Rect fullSize) {
		this.fullSize = fullSize;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		
		if (mScroller.computeScrollOffset()) {
			currentScroll.x = mScroller.getCurrX();
			currentScroll.y = mScroller.getCurrY();
			constraintScrolling();
			invalidate();
		}
		
		float dx = currentScroll.x - getWidth() * (currentScroll.x / fullSize.width());
		float dy = currentScroll.y - getHeight() * (currentScroll.y / fullSize.height());
		canvas.translate(dx, dy);

		Rect visibleRect = MathUtils.getRect(currentScroll, getWidth(), getHeight());
		drawFullCanvas(canvas, visibleRect);
		
		canvas.restore();
	}
	
	protected void drawFullCanvas(Canvas canvas, Rect visibleRect) {
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		currentScroll.x -= distanceX;
		currentScroll.y -= distanceY;
		constraintScrolling();
		invalidate();
		return true;
	}
	
	private void constraintScrolling() {
		currentScroll.x = MathUtils.clamp(currentScroll.x, -fullSize.width() + display.getWidth(), 0);
		currentScroll.y = MathUtils.clamp(currentScroll.y, -fullSize.height() + display.getHeight(), 0);		
	}
	
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		velocityX *= flingSpeed;
		velocityY *= flingSpeed;
		
		mScroller.fling(currentScroll.x, currentScroll.y, (int) velocityX, (int) velocityY, -fullSize.width(), 0, -fullSize.height(), 0);
		invalidate();
		return true;
	}

	public void onLongPress(MotionEvent e) {
		onContextMenu();
	}

	public void onShowPress(MotionEvent e) {
	}
	
	public boolean onDown(MotionEvent e) {
		mScroller.forceFinished(true);
		return true;
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return true;
	}

	protected void onContextMenu() {
	}
	
}
