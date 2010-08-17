package com.thoughtworks.thoughtferret.view.update;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

class ViewDragDropListener implements OnTouchListener
{
	
	private View draggedView;
	private Rect originalRect;
	private Point originalDrag;
	private Rect dragFeedback;
	
	private OnDropListener onDropListener;
	
	public void setOnDropListener(OnDropListener listener) {
		onDropListener = listener;
	}
	
	public View getDraggedView() {
		return draggedView;
	}
	
	public Rect getDragFeedback() {
		return dragFeedback;
	}
	
	@Override 
	public boolean onTouch(View view, MotionEvent event) 
	{
		int x = (int)event.getX();
		int y = (int)event.getY();
		
		switch (event.getAction()) {
		
			case MotionEvent.ACTION_DOWN:
				draggedView = view;
				originalRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
				dragFeedback = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
				originalDrag = new Point(x, y);			
				//Log.w("View", "New drag = " + originalRect.left + " , " + originalRect.top + " , " + originalRect.right + " , " + originalRect.bottom);
				break;
				
			case MotionEvent.ACTION_MOVE:
				int dx = originalDrag.x;
				int dy = originalDrag.y;
				if (originalRect.contains(view.getLeft() + x, view.getTop() + y) == false) {
					//Log.w("View", "Move at " + x + " , " + y);
					dragFeedback.offsetTo(view.getLeft() + x - dx, view.getTop() + y - dy);
				}
				break;
				
			case MotionEvent.ACTION_UP:
				//Log.w("View", "Original rect = " + originalRect.left + " , " + originalRect.top + " , " + originalRect.right + " , " + originalRect.bottom);
				if (originalRect.contains(view.getLeft() + x, view.getTop() + y) == false) {
					//Log.w("View", "Sending drop at " + x + " , " + y);
					if (onDropListener != null) {
						onDropListener.onDrop(draggedView, view.getLeft() + x, view.getTop() + y);						
					}
				}
				draggedView = null;
				dragFeedback = null;
				break;
		}
		
		//invalidate();
		return true; 
	}
}
