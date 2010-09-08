package com.thoughtworks.thoughtferret.view.update;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.thoughtworks.thoughtferret.model.tags.MoodTags;
import com.thoughtworks.thoughtferret.model.tags.MoodTagsBuilder;
import com.thoughtworks.thoughtferret.view.paints.FontPaint;
import com.thoughtworks.thoughtferret.view.paints.LinePaint;

public class KeywordsEditor extends LinearLayout implements OnWordDeletionListener, OnDropListener {
	
	WrappingLayout wrappingLayout;
	MoodTagsBuilder moodTagsBuilder;
	ViewDragDropListener dragDropListener;
	
	public KeywordsEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		moodTagsBuilder = new MoodTagsBuilder();
		wrappingLayout = new WrappingLayout(context);
		dragDropListener = new ViewDragDropListener();
		dragDropListener.setOnDropListener(this);
		addView(wrappingLayout);
		setWillNotDraw(false);
	}

	public void addKeywords(List<String> words) {
		moodTagsBuilder.addKeywords(words);
		updateView();
	}
	
	public void updateView() {
		wrappingLayout.removeAllViews();
		for (String word : moodTagsBuilder.getKeywords()) {
            WordView wordView = new WordView(getContext(), null);
            wordView.setText(word);
            wordView.setOnWordDeletionListener(this);
            wordView.setOnTouchListener(wordListener);
            wrappingLayout.addView(wordView);    
          }
		wrappingLayout.invalidate();
	}

	public MoodTags getMoodTags(int rating) {
		return moodTagsBuilder.build(rating);
	}
	
	@Override
	public void onWordDeletion(WordView wordView) {
		moodTagsBuilder.deleteKeyword(wordView.getText());
        updateView();
	}

	@Override
	public void onDrop(View draggedView, int x, int y) {
		for (int i = 0; i < wrappingLayout.getChildCount(); ++i) {
			WordView wordView = (WordView) wrappingLayout.getChildAt(i);
			Rect wordRect = new Rect(wordView.getLeft(), wordView.getTop(), wordView.getRight(), wordView.getBottom());
			if (wordRect.contains(x, y)) {
				moodTagsBuilder.merge(((WordView)draggedView).getText(), wordView.getText());
				break;
			}
		}
		updateView();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (moodTagsBuilder.getKeywords().isEmpty()) {
			FontPaint textPaint = new FontPaint(0xFF000000, 24, Align.CENTER);
			canvas.drawText("...and don't forget to add a few tags!", getWidth() / 2, getHeight() / 2, textPaint);
		}
		if (dragDropListener.getDraggedView() != null) {
			RectF feedback = new RectF(dragDropListener.getDragFeedback());
			feedback.offset(getPaddingLeft(), getPaddingTop());
			canvas.drawRoundRect(feedback, 15f, 15f, new LinePaint(0xFF666666, 2f));
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
