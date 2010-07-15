package com.thoughtworks.thoughtferret.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.thoughtworks.thoughtferret.presenter.HappyWordsPresenter;
import com.thoughtworks.thoughtferret.presenter.HappyWordsPresenter.Word;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.FontPaint;

public class HappyWords extends Activity {
	
private Panel panel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		panel = new Panel(this);
		setContentView(panel);
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
	    Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	    window.addFlags(WindowManager.LayoutParams.FLAG_DITHER);
	}

	class Panel extends Scroll  {
	
		private int backgroundColor = 0xFFDDDDDD;
		private int happyColor = 0x9900FF00;
		private int sadColor = 0x99FF3300;
		
		private int minTextSize = 15;
		private int maxTextSize = 45;
		
		private List<Paint> textPaints = new ArrayList<Paint>();
		private Paint backgroundGradientPaint;
		
		private HappyWordsPresenter presenter;
		
		public Panel(Context context) {
			super(context, null);
			
			presenter = new HappyWordsPresenter(super.display.getWidth(), super.display.getHeight());
			setFullSize(presenter.getGraphRect());

			int sizeRange = (maxTextSize - minTextSize);
			int sizeStep = (int) (sizeRange / (float) (presenter.getSizeLevels() - 1));
			
			for (int i = 0; i < presenter.getSizeLevels(); ++i) {
				int currentSize = minTextSize + sizeStep * i;
				textPaints.add(new FontPaint(0xFF000000, currentSize, Paint.Align.CENTER));
			}
			
			Shader gradient = new LinearGradient(0, 0, 700, 0, sadColor, happyColor, Shader.TileMode.CLAMP);
			backgroundGradientPaint = new FillPaint(backgroundColor, gradient);
		}
		 
		@Override
		protected void drawFullCanvas(Canvas canvas, Rect visibleRect) {
			canvas.drawColor(backgroundColor);
			canvas.drawRect(getFullSize(), backgroundGradientPaint);

			for (Word word : presenter.getWords()) {
				canvas.drawText(word.text, word.position.x, word.position.y, textPaints.get(word.weight));
			}
				  
			super.drawFullCanvas(canvas, visibleRect);
		}
		
	}
	
}