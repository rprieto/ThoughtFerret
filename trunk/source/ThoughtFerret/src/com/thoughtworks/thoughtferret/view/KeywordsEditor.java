package com.thoughtworks.thoughtferret.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.thoughtworks.thoughtferret.presenter.KeywordsEditorPresenter;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.LinePaint;

public class KeywordsEditor extends LinearLayout implements OnWordDeletionListener {
	
	WrappingLayout wrappingLayout;
	KeywordsEditorPresenter presenter;
	Rect dragFeedback;
	
	public KeywordsEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		presenter = new KeywordsEditorPresenter();
		wrappingLayout = new WrappingLayout(context);
		addView(wrappingLayout);
		setWillNotDraw(false);
	}

	public void addKeywords(List<String> words) {
		presenter.addKeywords(words);
		updateView();
	}
	
	public void updateView() {
		wrappingLayout.removeAllViews();
		for (String word : presenter.getKeywords()) {
            WordView wordView = new WordView(getContext(), null);
            wordView.setText(word);
            wordView.setOnWordDeletionListener(this);
            wordView.setOnTouchListener(dragAndDropListener);
            wrappingLayout.addView(wordView);    
          }
		wrappingLayout.invalidate();
	}

	@Override
	public void onWordDeletion(WordView wordView) {
		presenter.deleteKeyword(wordView.getText());
        updateView();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (dragFeedback != null) {
			canvas.drawRect(dragFeedback, new LinePaint(0xFF00FF00, 2f));
		}
	}
	
	private void processDrop(View draggedWord, int x, int y) {
		for (int i = 0; i < wrappingLayout.getChildCount(); ++i) {
			WordView wordView = (WordView) wrappingLayout.getChildAt(i);
			Rect wordRect = new Rect(wordView.getLeft(), wordView.getTop(), wordView.getRight(), wordView.getBottom());
			if (wordRect.contains(x, y)) {
				presenter.merge(((WordView)draggedWord).getText(), wordView.getText());
				break;
			}
		}
		updateView();
	} 
	
	OnTouchListener dragAndDropListener = new OnTouchListener() 
	{
		View draggedView;
		Rect originalRect;
		Point originalDrag;
		
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
					Log.w("View", "New drag = " + originalRect.left + " , " + originalRect.top + " , " + originalRect.right + " , " + originalRect.bottom);
					break;
				case MotionEvent.ACTION_MOVE:
					int dx = originalDrag.x; // - originalRect.left;
					int dy = originalDrag.y; // - originalRect.top;
					if (originalRect.contains(view.getLeft() + x, view.getTop() + y) == false) {
						Log.w("View", "Move at " + x + " , " + y);
						dragFeedback.offsetTo(view.getLeft() + x - dx, view.getTop() + y - dy);
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.w("View", "Original rect = " + originalRect.left + " , " + originalRect.top + " , " + originalRect.right + " , " + originalRect.bottom);
					if (originalRect.contains(view.getLeft() + x, view.getTop() + y) == false) {
						Log.w("View", "Sending drop at " + x + " , " + y);
						processDrop(draggedView, view.getLeft() + x, view.getTop() + y);
					}
					draggedView = null;
					dragFeedback = null;
					break;
			}
			invalidate();
			return true; 
		}
	};
	
}
