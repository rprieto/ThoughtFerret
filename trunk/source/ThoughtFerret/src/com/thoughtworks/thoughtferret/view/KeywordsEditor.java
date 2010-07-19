package com.thoughtworks.thoughtferret.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.thoughtworks.thoughtferret.presenter.KeywordsEditorPresenter;
import com.thoughtworks.thoughtferret.view.paints.LinePaint;

public class KeywordsEditor extends LinearLayout implements OnWordDeletionListener, OnDropListener {
	
	WrappingLayout wrappingLayout;
	KeywordsEditorPresenter presenter;
	ViewDragDropListener dragDropListener;
	
	public KeywordsEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		presenter = new KeywordsEditorPresenter();
		wrappingLayout = new WrappingLayout(context);
		dragDropListener = new ViewDragDropListener();
		dragDropListener.setOnDropListener(this);
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
            wordView.setOnTouchListener(wordListener);
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
	public void onDrop(View draggedView, int x, int y) {
		for (int i = 0; i < wrappingLayout.getChildCount(); ++i) {
			WordView wordView = (WordView) wrappingLayout.getChildAt(i);
			Rect wordRect = new Rect(wordView.getLeft(), wordView.getTop(), wordView.getRight(), wordView.getBottom());
			if (wordRect.contains(x, y)) {
				presenter.merge(((WordView)draggedView).getText(), wordView.getText());
				break;
			}
		}
		updateView();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (dragDropListener.getDraggedView() != null) {
			canvas.drawRect(dragDropListener.getDragFeedback(), new LinePaint(0xFF00FF00, 2f));
			//canvas.drawLine(startX, startY, stopX, stopY, paint)
		}
	}
	
	OnTouchListener wordListener = new OnTouchListener() 
	{
		@Override 
		public boolean onTouch(View view, MotionEvent event) 
		{
			boolean processed = dragDropListener.onTouch(view, event);
			if (processed) {
				invalidate();
			}
			return processed;
		}
	};
	
}
