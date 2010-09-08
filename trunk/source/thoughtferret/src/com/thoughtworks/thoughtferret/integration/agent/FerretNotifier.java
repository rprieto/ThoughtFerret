package com.thoughtworks.thoughtferret.integration.agent;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.thoughtworks.thoughtferret.R;
import com.thoughtworks.thoughtferret.activities.MoodUpdate;

public class FerretNotifier {

	private static final int FERRET_NOTIFICATION = 1;
	
	public void show(Context context) {
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		int icon = R.drawable.icon;
		CharSequence tickerText = "ThoughtFerret";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_ALL;
		
		RemoteViews contentView = new RemoteViews("com.thoughtworks.thoughtferret", R.layout.notification);
		contentView.setImageViewResource(R.id.notificationImage, R.drawable.icon);
		contentView.setTextViewText(R.id.notificationHeader, context.getString(R.string.notificationHeader));
		contentView.setTextViewText(R.id.notificationGreeting, context.getString(R.string.notificationGreeting));
		notification.contentView = contentView;
		
		Intent notificationIntent = new Intent(context, MoodUpdate.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.contentIntent = contentIntent;

		notificationManager.notify(FERRET_NOTIFICATION, notification);
	}
	
}
