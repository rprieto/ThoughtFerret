package com.thoughtworks.thoughtferret.model.tags;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.thoughtworks.thoughtferret.model.DatabaseHelper;

public class MoodTagsDao {

	private final DatabaseHelper databaseHelper;
	
	public MoodTagsDao(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	
	public void deleteAll() {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		database.execSQL("delete from MoodTag");
		database.close();
	}
	
	public void persist(MoodTags moodTags) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		//insertIntoDatabase(tag, database);
		database.close();
	}
	
//	private void insertIntoDatabase(MoodTag moodTag, SQLiteDatabase database) {
//		ContentValues values = new ContentValues();
//		values.put("text", moodTag.getText());
//		values.put("count", moodTag.getCount());
//		values.put("ratingSum", moodTag.getRatingSum());
//		database.insert("MoodTag", "text", values);
//	}
	
}
