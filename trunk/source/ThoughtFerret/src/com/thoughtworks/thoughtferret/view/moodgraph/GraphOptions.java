package com.thoughtworks.thoughtferret.view.moodgraph;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.google.common.collect.ImmutableBiMap;
import com.thoughtworks.thoughtferret.MathUtils;
import com.thoughtworks.thoughtferret.R;

public class GraphOptions extends LinearLayout implements OnClickListener {

	private static final int MONTH = 30;
	private static final int YEAR = 365;
	
	public static final ImmutableBiMap<Integer, ChartType> TYPE_CONVERTER = 
		new ImmutableBiMap.Builder<Integer, ChartType>().
			put(R.id.barChart, ChartType.BAR).
			put(R.id.lineChart, ChartType.LINE).
			build();
	
	private RadioGroup chartTypes;
	private SeekBar slider;
	private Button okButton; 
	
	private OnClickListener validateListener;
	
	public GraphOptions(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.graphoptions, this);
		chartTypes = (RadioGroup) findViewById(R.id.chartTypes);
		slider = (SeekBar) findViewById(R.id.scaleSlider);
		okButton = (Button) findViewById(R.id.ok);
		okButton.setOnClickListener(this);
	}
	
	public ChartType getChartType() {
		int selected = chartTypes.getCheckedRadioButtonId();
		return TYPE_CONVERTER.get(selected);
	}
	
	public void setChartType(ChartType type) {
		int selected = TYPE_CONVERTER.inverse().get(type);
		chartTypes.check(selected);
	}
	
	public int getDaysToShowOnScreen() {
		int days = MathUtils.project(0, slider.getMax(), MONTH, YEAR, slider.getProgress());
		return days;
	}
	
	public void setDaysToShowOnScreen(int days) {
		int progress = MathUtils.project(MONTH, YEAR, 0, slider.getMax(), days);
		slider.setProgress(progress);
	}

	public void setValidateListener(OnClickListener onClickListener) {
		validateListener = onClickListener;
	}

	@Override
	public void onClick(View v) {
		if (validateListener != null) {
			validateListener.onClick(this);
		}
	}
	
}
