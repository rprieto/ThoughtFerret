package com.thoughtworks.thoughtferret;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;

public class MoodGraph extends Activity {

	Paint linePaint;
	Paint lineBorderPaint;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        linePaint = new Paint() {{
	        	  setStyle(Paint.Style.STROKE);
	        	  setAntiAlias(true);
	        	  setStrokeWidth(1.5f);
	        	  setColor(0xFF097286);
	        	}};

	        lineBorderPaint = new Paint() {{
	        	  setStyle(Paint.Style.STROKE);
	        	  setAntiAlias(true);
	        	  setStrokeWidth(3.0f);
	        	  setStrokeCap(Cap.ROUND);
	        	  setColor(0xFF054763);
	        	}};
	        	
	        setContentView(new Panel(this));	
	 }
	
	 class Panel extends View {
		    public Panel(Context context) {
		        super(context);
		    }
		 
		    @Override
		    public void onDraw(Canvas canvas) {
		    	
	        	Path p = new Path();
	        	Point mid = new Point();
	        	
	        	Point start = new Point(10,10);
	        	Point end = new Point(30,30);
	        	mid.set((start.x + end.x) / 2, (start.y + end.y) / 2);

	        	p.reset();
	        	p.moveTo(start.x, start.y);
	        	p.quadTo((start.x + mid.x) / 2, start.y, mid.x, mid.y);
	        	p.quadTo((mid.x + end.x) / 2, end.y, end.x, end.y);

	        	canvas.drawPath(p, lineBorderPaint);
	        	canvas.drawPath(p, linePaint);
	        	
		    }
		}
	 
}
