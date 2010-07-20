package com.thoughtworks.thoughtferret.view;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.view.paints.FillPaint;
import com.thoughtworks.thoughtferret.view.paints.FontPaint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

public class Home extends Activity {

	private Panel panel;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        //panel = new Panel(this);
		//setContentView(panel);
	}
	
	public void updateClick(View view) {
		startActivity(new Intent(this, MoodUpdate.class));
	}

	public void graphClick(View view) {
		startActivity(new Intent(this, MoodGraph.class));
	}
	
	public void wordsClick(View view) {
		startActivity(new Intent(this, HappyWords.class));
	}
	
	public void preferencesClick(View view) {
		startActivity(new Intent(this, EditPreferences.class));
	}
	
	public void demoHacksClick(View view) {
		startActivity(new Intent(this, DemoHacks.class));
	}
	

	class Panel extends Scroll  {
		
		GraphPaints graphPaints;
		
		private final Bitmap ferretBitmap;
		private final Paint ferretPaint;
		private Paint textPaint;
	    
	    public Panel(Context context) {
	        super(context, null);
	        
			Rect fullSize = new Rect(0, 0, super.display.getWidth(), super.display.getHeight());
	        setFullSize(fullSize);
			
	        Point gradientStart = new Point(0, 0);
	        Point gradientEnd = new Point(super.display.getWidth(), 0);
	        graphPaints = new GraphPaints(getResources(), gradientStart, gradientEnd);
	        
	        ferretBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homebackground);
	        ferretPaint = new FillPaint(0xAA000000);
	        
	        textPaint = new FontPaint(0xFF000000, 22, Paint.Align.CENTER);
	    }
	    
	    @Override
	    protected void drawFullCanvas(Canvas canvas, Rect visibleRect) {
	    	drawBackground(canvas, visibleRect);	
	    	drawFerret(canvas, visibleRect);
	    	drawMenu(canvas);
        	super.drawFullCanvas(canvas, visibleRect);
	    }

		private void drawBackground(Canvas canvas, Rect visibleRect) {
			graphPaints.drawBackground(canvas, super.getFullSize(), visibleRect);
			canvas.drawRect(visibleRect, graphPaints.getFadeOverlayPaint());
		}

		private void drawFerret(Canvas canvas, Rect visibleRect) {
			Rect target = new Rect(0, 0, ferretBitmap.getWidth(), ferretBitmap.getHeight());
			canvas.drawBitmap(ferretBitmap, null, target, ferretPaint);
		}
		
		private void drawMenu(Canvas canvas) {
			canvas.drawText("Happy words", 100, 100, textPaint);
			canvas.drawText("Mood graph", 100, 200, textPaint);
			canvas.drawText("Preferences", 100, 300, textPaint);
		}
		
	}
	
}
