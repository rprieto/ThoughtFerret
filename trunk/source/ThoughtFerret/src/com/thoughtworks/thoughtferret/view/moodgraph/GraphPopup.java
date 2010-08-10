package com.thoughtworks.thoughtferret.view.moodgraph;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.R;

public class GraphPopup extends LinearLayout {

	private static final int MONTH = 30;
	private static final int YEAR = 365;
	
	private SeekBar slider;
	
	public GraphPopup(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.graphpopup, this);
		slider = (SeekBar) findViewById(R.id.scaleSlider);
	}
	
	public ChartType getChartType() {
		return ChartType.BAR;
	}
	
	public void setChartType(ChartType type) {
		
	}
	
	public int getDaysToShowOnScreen() {
		int days = MathUtils.project(0, slider.getMax(), MONTH, YEAR, slider.getProgress());
		return days;
	}
	
	public void setDaysToShowOnScreen(int days) {
		int progress = MathUtils.project(MONTH, YEAR, 0, slider.getMax(), days);
		slider.setProgress(progress);
	}
	
}
