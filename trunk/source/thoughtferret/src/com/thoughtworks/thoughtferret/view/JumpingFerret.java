package com.thoughtworks.thoughtferret.view;

import com.thoughtworks.thoughtferret.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

public class JumpingFerret extends VideoView {

	public JumpingFerret(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		Uri path = Uri.parse("android.resource://com.thoughtworks.thoughtferret/" + R.raw.ferret);
        
		setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				Log.i("JumpingFerret", "Duration = " + getDuration());
				requestFocus();
				start();
			}
		});
        
		setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				start();
			}
		});
		
	    setVideoURI(path);
	}

}
