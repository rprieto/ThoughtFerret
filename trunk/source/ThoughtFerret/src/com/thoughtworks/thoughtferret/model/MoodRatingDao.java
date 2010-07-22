package com.thoughtworks.thoughtferret.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MoodRatingDao {

	private final DatabaseHelper databaseHelper;
	
	public MoodRatingDao(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	
	public void deleteAll() {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		database.execSQL("delete from MoodRating");
		database.close();
	}
	
	public void persist(MoodRating moodRating) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		insertIntoDatabase(moodRating, database);
		database.close();
	}

	public void persist(List<MoodRating> ratings) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		try{
			database.beginTransaction();
			for (MoodRating moodRating : ratings) {
				insertIntoDatabase(moodRating, database);
			}
			database.setTransactionSuccessful();
		} catch (SQLException e) {
		} finally {
			database.endTransaction();
			database.close();
		}
	}
	
	private void insertIntoDatabase(MoodRating moodRating, SQLiteDatabase database) {
		ContentValues values = new ContentValues();
		values.put("loggedDate", moodRating.getLoggedDate().getMillis());
		values.put("rating", moodRating.getRating());
		database.insert("MoodRating", "loggedDate", values);
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
