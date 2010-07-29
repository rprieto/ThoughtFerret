package com.thoughtworks.thoughtferret.view.happywords;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.thoughtworks.thoughtferret.model.tags.MoodTags;
import com.thoughtworks.thoughtferret.model.tags.MoodTagsDao;
import com.thoughtworks.thoughtferret.view.ApplicationBackground;
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

	@Override
    public void onBackPressed() {
		this.finish();
		overridePendingTransition(0, 0);
		return;
    }
	
	class Panel extends View  {
		
		private int minTextSize = 15;
		private int maxTextSize = 45;
		
		private List<Paint> textPaints = new ArrayList<Paint>();

		private MoodTagsDao moodTagsDao;
		private TagCloud tagCloud;
		
	    protected Display display;
		ApplicationBackground appBackground;
		
		public Panel(Context context) {
			super(context, null);
			
			display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			Rect screen = new Rect(0, 0, display.getWidth(), display.getHeight());
			
			moodTagsDao = new MoodTagsDao(context);
			MoodTags moodTags = moodTagsDao.findAll();
			
			tagCloud = new TagCloud(moodTags, screen);
			
	        appBackground = new ApplicationBackground(getResources(), display.getWidth(), display.getHeight(), ApplicationBackground.GradientDirection.HORIZONTAL, true);
	        
			int sizeRange = (maxTextSize - minTextSize);
			int sizeStep = (int) (sizeRange / (float) (TagCloud.SIZE_LEVELS - 1));
			
			for (int i = 0; i < TagCloud.SIZE_LEVELS; ++i) {
				int currentSize = minTextSize + sizeStep * i;
				textPaints.add(new FontPaint(0xFF000000, currentSize, Paint.Align.CENTER));
			}
		}
		 
		@Override
		protected void onDraw(Canvas canvas) {
			appBackground.draw(canvas);
			for (RenderedTag tag : tagCloud.getTags()) {
				//Log.i("TagCloud", String.format("Tag: %s [%d;%d] %d", tag.getText(), tag.getPosition().x, tag.getPosition().y, tag.getSize()));
				Paint paint = textPaints.get(tag.getSize() - 1);
				canvas.drawText(tag.getText(), tag.getPosition().x, tag.getPosition().y, paint);
			}
		}
		
	}
	
}