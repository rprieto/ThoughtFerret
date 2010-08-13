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
import com.thoughtworks.thoughtferret.view.moodgraph.charts.ChartType;

public class OptionsPopup extends LinearLayout implements OnClickListener {
	
	public static final ImmutableBiMap<Integer, ChartType> TYPE_CONVERTER = 
		new ImmutableBiMap.Builder<Integer, ChartType>().
			put(R.id.barChart, ChartType.BAR).
			put(R.id.lineChart, ChartType.LINE).
			build();
	
	private RadioGroup chartTypes;
	private SeekBar slider;
	private Button okButton; 
	
	private OnClickListener validateListener;
	
	public OptionsPopup(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.optionspopup, this);
		chartTypes = (RadioGroup) findViewById(R.id.chartTypes);
		slider = (SeekBar) findViewById(R.id.scaleSlider);
		okButton = (Button) findViewById(R.id.ok);
		okButton.setOnClickListener(this);
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

	public void setOptions(ChartOptions chartOptions) {
		int selected = TYPE_CONVERTER.inverse().get(chartOptions.getType());
		int progress = MathUtils.project(ChartOptions.MONTH, ChartOptions.YEAR, 0, slider.getMax(), chartOptions.getDaysOnScreen());
		chartTypes.check(selected);
		slider.setProgress(progress);
	}
	
	public ChartOptions getOptions() {
		int selected = chartTypes.getCheckedRadioButtonId();
		ChartType chartType = TYPE_CONVERTER.get(selected);
		int days = MathUtils.project(0, slider.getMax(), ChartOptions.MONTH, ChartOptions.YEAR, slider.getProgress());
		return new ChartOptions(chartType, days);
	}
	
}
