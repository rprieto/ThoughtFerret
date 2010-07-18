package com.thoughtworks.thoughtferret.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class KeywordsEditor extends LinearLayout implements OnWordDeletionListener {
	
	WrappingLayout wrappingLayout;
	
	List<String> keywords;
	
	public KeywordsEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		keywords = new ArrayList<String>();
		wrappingLayout = new WrappingLayout(context);
		addView(wrappingLayout);
	}

	public void addKeywords(List<String> words) {
		keywords.addAll(words);
		updateView();
	}
	
	public void updateView() {
		wrappingLayout.removeAllViews();
		for (String word : keywords) {
            WordView wordView = new WordView(getContext(), null);
            wordView.setText(word);
            wordView.setOnWordDeletionListener(this);
            wrappingLayout.addView(wordView);    
          }
		wrappingLayout.invalidate();
	}

	@Override
	public void onWordDeletion(WordView wordView) {
		String keyword = wordView.getText();
		Iterator<String> iter = keywords.iterator();
        while (iter.hasNext()) {
           if (iter.next() == keyword) {
              iter.remove();
           }
        }
        updateView();
	}
	
}
