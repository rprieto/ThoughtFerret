package com.thoughtworks.thoughtferret.view;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.thoughtworks.thoughtferret.R;

public class DemoHacks extends Activity {

	private static final int FERRET_NOTIFICATION = 1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demohacks);
	}
	
	public void triggerFerretClick(View view) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		
		int icon = R.drawable.icon;
		CharSequence tickerText = "Hello";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		//notification.defaults |= Notification.DEFAULT_VIBRATE;
		
		RemoteViews contentView = new RemoteViews("com.thoughtworks.thoughtferret", R.layout.notification);
		contentView.setImageViewResource(R.id.notificationImage, R.drawable.icon);
		contentView.setTextViewText(R.id.notificationHeader, getString(R.string.notificationHeader));
		contentView.setTextViewText(R.id.notificationGreeting, getString(R.string.notificationGreeting));
		notification.contentView = contentView;
		
		Intent notificationIntent = new Intent(this, MoodUpdate.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.contentIntent = contentIntent;

		mNotificationManager.notify(FERRET_NOTIFICATION, notification);
	}
	
	public void populateDatabaseClick(View view) {
		
	}
	
}
