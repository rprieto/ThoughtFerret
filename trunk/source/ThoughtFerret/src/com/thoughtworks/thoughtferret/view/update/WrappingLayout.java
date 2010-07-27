package com.thoughtworks.thoughtferret.view.update;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

class WrappingLayout extends ViewGroup  {

	private int rowHeight;
	private int horizontalSpacing = 10;
	private int verticalSpacing = 10;

	public WrappingLayout(Context context) {
		super(context, null);
	}
	
	public WrappingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		
		mesureChildren(width, height);
		int totalHeight = calculateTotalHeight(width);
		
		setMeasuredDimension(width, totalHeight);
    }
	
	private void mesureChildren(int maxWidth, int maxHeight) {
		for (int i = 0; i < getChildCount(); i++) {
			int widthSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST);
			int heightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
			getChildAt(i).measure(widthSpec, heightSpec);
			rowHeight = Math.max(rowHeight, getChildAt(i).getMeasuredHeight());
		}
	}

	private int calculateTotalHeight(int maxWidth) {
		Point currentPos = new Point(0, 0);
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
            calculateChildPosition(child, currentPos, maxWidth);
            currentPos.offset(child.getMeasuredWidth() + horizontalSpacing, 0);
		}
		return currentPos.y + rowHeight;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		Point currentPos = new Point(0, 0);
		for (int i = 0; i < getChildCount(); ++i) {
			View child = getChildAt(i);			
			calculateChildPosition(child, currentPos, right - left);
			layoutChild(child, currentPos);
            currentPos.offset(child.getMeasuredWidth() + horizontalSpacing, 0);
		}
	}
	
	private void calculateChildPosition(View child, Point suggested, int maxWidth) {
		if (suggested.x + child.getMeasuredWidth() > maxWidth) {
			suggested.x = 0;
			suggested.y += rowHeight + verticalSpacing;
		}
	}
	
	private void layoutChild(View child, Point topLeft) {
		int childWidth = child.getMeasuredWidth();
		int childHeight = child.getMeasuredHeight();
		child.layout(topLeft.x, topLeft.y, topLeft.x + childWidth, topLeft.y + childHeight);
	}
	
}
