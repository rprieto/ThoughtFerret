package com.thoughtworks.thoughtferret.activities;



import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.thoughtworks.thoughtferret.R;

public class AnimationTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
    }

    private static class SampleView extends View {

        private Movie mMovie;
        private long mMovieStart;

        public SampleView(Context context) {
            super(context);
            setFocusable(true);
            java.io.InputStream is = context.getResources().openRawResource(R.drawable.jumpingferret);
            mMovie = Movie.decodeStream(is);
        }

        @Override protected void onDraw(Canvas canvas) {
            canvas.drawColor(0xFFCCCCCC);            

            Paint p = new Paint();
            p.setAntiAlias(true);

            long now = android.os.SystemClock.uptimeMillis();
            if (mMovieStart == 0) {
                mMovieStart = now;
            }
            if (mMovie != null) {
                int dur = mMovie.duration();
                if (dur == 0) {
                    dur = 1000;
                }
                int relTime = (int)((now - mMovieStart) % dur);
                mMovie.setTime(relTime);
                mMovie.draw(canvas, getWidth() - mMovie.width(), getHeight() - mMovie.height());
                invalidate();
            }
        }
    }
}
