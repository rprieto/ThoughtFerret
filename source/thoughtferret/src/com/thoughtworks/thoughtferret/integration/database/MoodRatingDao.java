package com.thoughtworks.thoughtferret.integration.database;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.thoughtferret.model.map.Coordinates;
import com.thoughtworks.thoughtferret.model.ratings.MoodRating;
import com.thoughtworks.thoughtferret.model.ratings.MoodRatings;

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
	
	public void persist(MoodRating rating) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		insertIntoDatabase(rating, database);
		database.close();
	}

	public void persist(MoodRatings ratings) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		try{
			database.beginTransaction();
			for (MoodRating moodRating : ratings.getValues()) {
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
		values.put("loggedDate", moodRating.getLoggedDate().toDateTime().getMillis());
		values.put("rating", moodRating.getRating());
		values.put("latitude", moodRating.getCoordinates().getLatitude());
		values.put("longitude", moodRating.getCoordinates().getLongitude());
		database.insert("MoodRating", "loggedDate", values);
	}
	
	public MoodRatings findAll() {
		List<MoodRating> ratings = new ArrayList<MoodRating>();
		SQLiteDatabase database = databaseHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select loggedDate, rating, latitude, longitude from MoodRating", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Coordinates coords = new Coordinates(cursor.getDouble(2), cursor.getDouble(3));
			ratings.add(new MoodRating(cursor.getLong(0), cursor.getInt(1), coords));
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
		return new MoodRatings(ratings);
	}
	
}
