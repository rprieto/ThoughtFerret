package com.thoughtworks.thoughtferret.view;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.view.MoodGraph.Panel;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.FontPaint;

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
		
		private Paint backgroundGradientPaint;
		
		private Paint textPaintSmall;
		private Paint textPaintMedium;
		private Paint textPaintLarge;
		
		public Panel(Context context) {
			super(context, null);
			setFullSize(new Rect(0, 0, 700, 420));
			
			textPaintSmall = new FontPaint(0xFF000000, 20, Paint.Align.CENTER);
			textPaintMedium = new FontPaint(0xFF000000, 26, Paint.Align.CENTER);
			textPaintLarge = new FontPaint(0xFF000000, 32, Paint.Align.CENTER);
			
			Shader gradient = new LinearGradient(0, 0, 700, 0, sadColor, happyColor, Shader.TileMode.CLAMP);
			backgroundGradientPaint = new FillPaint(backgroundColor, 1f, gradient);
		}
		 
		 @Override
		 protected void drawFullCanvas(Canvas canvas, Rect visibleRect) {
			 canvas.drawColor(backgroundColor);
			 
			 canvas.drawRect(getFullSize(), backgroundGradientPaint);
			 canvas.drawText("Politics", 50, 100, textPaintLarge);
			 canvas.drawText("Scrum", 100, 150, textPaintSmall);
			 canvas.drawText("Debugging", 230, 60, textPaintMedium);
			 canvas.drawText("Testing", 400, 110, textPaintMedium);
			 canvas.drawText("Pairing", 420, 300, textPaintLarge);
			 canvas.drawText("Coaching", 510, 70, textPaintSmall);
			 canvas.drawText("The big bang theory", 530, 160, textPaintLarge);
			 
			 
			 super.drawFullCanvas(canvas, visibleRect);
		 }
		
	}
	
}