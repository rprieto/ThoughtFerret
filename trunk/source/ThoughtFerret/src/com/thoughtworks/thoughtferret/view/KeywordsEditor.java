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
	
	OnTouchListener dragAndDropListener = new OnTouchListener() 
	{
		WordView draggedWord;
		Point originalDrag;
		
		@Override 
		public boolean onTouch(View v, MotionEvent event) 
		{
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					draggedWord = (WordView) v;
					dragFeedback = new Rect(draggedWord.getLeft(), draggedWord.getTop(), draggedWord.getRight(), draggedWord.getBottom());
					originalDrag = new Point((int)event.getX(), (int)event.getY());
					break;
				case MotionEvent.ACTION_MOVE:
					int dx = originalDrag.x - draggedWord.getLeft();
					int dy = originalDrag.y - draggedWord.getTop();
					dragFeedback.offsetTo((int)event.getX() - dx, (int)event.getY() - dy);
					break;
				case MotionEvent.ACTION_UP:
					int x = (int)event.getX();
			        int y = (int)event.getY();
			        
					draggedWord = null;
					dragFeedback = null;
					break;
			}
			invalidate();
			return true; 
		} 
	};
	
}
