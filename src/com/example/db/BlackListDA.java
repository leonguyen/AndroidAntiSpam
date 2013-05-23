package com.example.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class BlackListDA {
	// Helper
	private SQLiteOpenHelper sqlHelper;
 
	// Table name
	private static final String TABLE_NAME = "blacklist";
 
	// Table Columns names
	public static final String KEY_ID = "id";
	public static final String KEY_NUM = "number";
 
	public BlackListDA(SQLiteOpenHelper helper) {
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
	public void add(BlackList obj) {
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
 
		ContentValues values = new ContentValues();
		values.put(KEY_NUM, obj.getNumber());
 
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}
 
	public BlackList get(int id) {
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
 
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NUM },
				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null,
				null, null);
		if (cursor != null)
			cursor.moveToFirst();
 
		BlackList obj = new BlackList(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1));
		return obj;
	}

	public BlackList getNum(String num) {
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
 
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NUM },
				KEY_NUM + "=?", new String[] { String.valueOf(num) }, null, null,
				null, null);
		if (cursor != null)
			cursor.moveToFirst();
 
		BlackList obj = new BlackList(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1));
		return obj;
	}
 
	public List<BlackList> getAll() {
		List<BlackList> list = new ArrayList<BlackList>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME;
 
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
 
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				BlackList obj = new BlackList();
				obj.setID(Integer.parseInt(cursor.getString(0)));
				obj.setNumber(cursor.getString(1));
				// Adding user to list
				list.add(obj);
			} while (cursor.moveToNext());
		}
 
		return list;
	}
	
	public List<Map> getAllByMap() {
		List<Map> list = new ArrayList<Map>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME;
 
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
 
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Map map = new HashMap();
				map.put(KEY_ID, cursor.getString(0));
		        map.put(KEY_NUM, cursor.getString(1));
				list.add(map);
			} while (cursor.moveToNext());
		}
 
		return list;
	}
	
	public int update(BlackList obj) {
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
 
		ContentValues values = new ContentValues();
		values.put(KEY_NUM, obj.getNumber());
 
		// updating row
		return db.update(TABLE_NAME, values, KEY_ID + " = ?",
				new String[] { String.valueOf(obj.getID()) });
	}
 
	public void delete(BlackList obj) {
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID + " = ?",
				new String[] { String.valueOf(obj.getID()) });
		db.close();
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
