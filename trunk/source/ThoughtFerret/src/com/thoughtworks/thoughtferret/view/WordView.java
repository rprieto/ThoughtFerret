package com.thoughtworks.thoughtferret.view;

import com.thoughtworks.thoughtferret.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WordView extends LinearLayout {
	
	public WordView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.wordview, this);
	}

	public void setText(String text) {
		TextView word = (TextView) findViewById(R.id.wordText);
		word.setText(text);
	}
	
}
