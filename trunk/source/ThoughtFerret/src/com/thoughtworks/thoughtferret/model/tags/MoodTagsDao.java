package com.thoughtworks.thoughtferret.model.tags;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
		for (MoodTag tag : moodTags.getValues()) {
			MoodTag existing = find(tag, database);
			if (existing != null) {
				update(existing.add(tag), database);
			} else {
				addNew(tag, database);
			}
		}
		database.close();
	}
	
	public MoodTags findAll() {
		List<MoodTag> moodTags = new ArrayList<MoodTag>();
		SQLiteDatabase database = databaseHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select text, count, ratingSum from MoodTag", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			MoodTag tag = new MoodTag(cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
			moodTags.add(tag);
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
		return new MoodTags(moodTags);
	}
	
	private MoodTag find(MoodTag moodTag, SQLiteDatabase database) {
		MoodTag result;
		String query = String.format("select text, count, ratingSum from MoodTag where text = '%s'", moodTag.getText());
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			result = new MoodTag(cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
		}
		else {
			result = null;
		}
		cursor.close();
		return result;
	}
	
	private void addNew(MoodTag moodTag, SQLiteDatabase database) {
		ContentValues values = new ContentValues();
		values.put("text", moodTag.getText());
		values.put("count", moodTag.getCount());
		values.put("ratingSum", moodTag.getRatingSum());
		database.insert("MoodTag", "text", values);
	}
	
	private void update(MoodTag moodTag, SQLiteDatabase database) {
//		ContentValues values = new ContentValues();
//		values.put("text", moodTag.getText());
//		values.put("count", moodTag.getCount());
//		values.put("ratingSum", moodTag.getRatingSum());
//		database.update("MoodTag", values, "text=?", new String[]{ "'" + moodTag.getText() + "'"});
		String query1 = String.format("update MoodTag set count=%d where text='%s'", moodTag.getCount(), moodTag.getText());
		String query2 = String.format("update MoodTag set ratingSum=%d where text='%s'", moodTag.getRatingSum(), moodTag.getText());
		database.execSQL(query1);
		database.execSQL(query2);
	}
	
}
