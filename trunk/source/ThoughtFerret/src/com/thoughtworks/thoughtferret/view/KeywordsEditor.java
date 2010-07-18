package com.thoughtworks.thoughtferret.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.thoughtworks.thoughtferret.presenter.KeywordsEditorPresenter;

public class KeywordsEditor extends LinearLayout implements OnWordDeletionListener {
	
	WrappingLayout wrappingLayout;
	
	KeywordsEditorPresenter presenter;
	
	public KeywordsEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		presenter = new KeywordsEditorPresenter();
		wrappingLayout = new WrappingLayout(context);
		addView(wrappingLayout);
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
            wrappingLayout.addView(wordView);    
          }
		wrappingLayout.invalidate();
	}

	@Override
	public void onWordDeletion(WordView wordView) {
		presenter.deleteKeyword(wordView.getText());
        updateView();
	}
	
}
