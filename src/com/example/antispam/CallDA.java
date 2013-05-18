package com.example.antispam;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class CallDA {
	// Helper
	private SQLiteOpenHelper sqlHelper;
 
	// Table name
	private static final String TABLE_NAME = "call";
 
	// Table Columns names
	public static final String KEY_ID = "id";
	public static final String KEY_NUM = "number";
 
	public CallDA(SQLiteOpenHelper helper) {
		sqlHelper = helper;
	}
 
	public static void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NUM + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		Log.d(TABLE_NAME, "DB created!");
	}
 
	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		// Create tables again
		onCreate(db);
		Log.d(TABLE_NAME, "DB upgraded!");
	}
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */ 
	public void add(Call obj) {
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
 
		ContentValues values = new ContentValues();
		values.put(KEY_NUM, obj.getNumber());
 
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}
 
	public Call get(int id) {
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
 
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NUM },
				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null,
				null, null);
		if (cursor != null)
			cursor.moveToFirst();
 
		Call obj = new Call(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1));
		return obj;
	}
 
	public List<Call> getAll() {
		List<Call> list = new ArrayList<Call>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME;
 
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
 
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Call obj = new Call();
				obj.setID(Integer.parseInt(cursor.getString(0)));
				obj.setNumber(cursor.getString(1));
				// Adding user to list
				list.add(obj);
			} while (cursor.moveToNext());
		}
 
		return list;
	}
 
	public int update(Call obj) {
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
 
		ContentValues values = new ContentValues();
		values.put(KEY_NUM, obj.getNumber());
 
		// updating row
		return db.update(TABLE_NAME, values, KEY_ID + " = ?",
				new String[] { String.valueOf(obj.getID()) });
	}
 
	public void delete(Call obj) {
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID + " = ?",
				new String[] { String.valueOf(obj.getID()) });
		db.close();
	}

	public void deleteAll() {
		SQLiteDatabase db= sqlHelper.getWritableDatabase();
	    db.delete(TABLE_NAME, null, null);
	}
 
	public int getCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
 
		// return count
		return cursor.getCount();
	}
}
