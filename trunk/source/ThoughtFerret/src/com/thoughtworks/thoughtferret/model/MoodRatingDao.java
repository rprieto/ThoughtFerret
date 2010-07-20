package com.thoughtworks.thoughtferret.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MoodRatingDao {

	private final DatabaseHelper databaseHelper;
	
	public MoodRatingDao(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	
	public void persist(MoodRating moodRating) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("loggedDate", moodRating.getLoggedDate().getMillis());
		values.put("rating", moodRating.getRating());
		database.insert("MoodRating", "loggedDate", values);
		database.close();
	}

	public List<MoodRating> findAll() {
		List<MoodRating> ratings = new ArrayList<MoodRating>();
		SQLiteDatabase database = databaseHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select loggedDate, rating from MoodRating", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ratings.add(new MoodRating(cursor.getLong(0), cursor.getInt(1)));
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
		return ratings;
	}
	
}
