package com.thoughtworks.thoughtferret.view.update;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtworks.thoughtferret.R;

public class WordView extends LinearLayout implements OnClickListener {
	
	TextView wordText;
	ImageView wordDelete;
	
	OnWordDeletionListener deletionListener;
	
	public WordView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.wordview, this);
		wordText = (TextView) findViewById(R.id.wordText);
		wordDelete = (ImageView) findViewById(R.id.wordDelete);
		
		wordDelete.setOnClickListener(this);
	}

	public void setText(String text) {
		wordText.setText(text);
	}

	public String getText() {
		return (String) wordText.getText();
	}
	
	public void setOnWordDeletionListener(OnWordDeletionListener listener) {
		deletionListener = listener;
	}

	@Override
	public void onClick(View v) {
		if (deletionListener != null) {
			deletionListener.onWordDeletion(this);
		}
	}
	
}
