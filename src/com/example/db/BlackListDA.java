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
	public static final String KEY_TYPE = "type";
 
	public BlackListDA(SQLiteOpenHelper helper) {
		sqlHelper = helper;
	}
 
	public static void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NUM + " TEXT," + KEY_TYPE + " INTEGER" + ")";
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
	public boolean checkNum(BlackList obj){
		BlackList bl = getNum(obj.getNumber());
		return bl!=null ? true : false;
	}
	
	public boolean add(BlackList obj) {
		Boolean b = checkNum(obj);
		if(!b){
			SQLiteDatabase db = sqlHelper.getWritableDatabase();
	 
			ContentValues values = new ContentValues();
			values.put(KEY_NUM, obj.getNumber());
			values.put(KEY_TYPE, obj.getType());
	 
			// Inserting Row
			db.insert(TABLE_NAME, null, values);
			db.close(); // Closing database connection
		}
		return b;
	}
 
	public BlackList get(int id) {
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
 
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NUM, KEY_TYPE },
				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null,
				null, null);
		BlackList obj = null;
		if (cursor != null)
			if(cursor.moveToFirst()) 
				obj = new BlackList(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1),Integer.parseInt(cursor.getString(2)));
		return obj;
	}

	public BlackList getNum(String num) {
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
 
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NUM, KEY_TYPE },
				KEY_NUM + "=?", new String[] { String.valueOf(num) }, null, null,
				null, null);
		BlackList obj = null;
		if (cursor != null)
			if(cursor.moveToFirst()) 
			obj = new BlackList(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1),Integer.parseInt(cursor.getString(2)));
		
		return obj;
	}

	public BlackList getNum(String num, int type) {
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
 
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NUM, KEY_TYPE },
				KEY_NUM + "=? AND " + KEY_TYPE + "=?", new String[] { String.valueOf(num), String.valueOf(type) }, null, null,
				null, null);
		BlackList obj = null;
		if (cursor != null)
			if(cursor.moveToFirst()) 
			obj = new BlackList(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1),Integer.parseInt(cursor.getString(2)));
		
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
				obj.setType(Integer.parseInt(cursor.getString(2)));
				// Adding user to list
				list.add(obj);
			} while (cursor.moveToNext());
		}
 
		return list;
	}
	
	public List<Map> getAllByMap(int type) {
		List<Map> list = new ArrayList<Map>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME;
		selectQuery += " WHERE " + KEY_TYPE + " = " + type;
		
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
 
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Map map = new HashMap();
				map.put(KEY_ID, cursor.getString(0));
		        map.put(KEY_NUM, cursor.getString(1));
		        map.put(KEY_TYPE, cursor.getString(2));
				list.add(map);
			} while (cursor.moveToNext());
		}
 
		return list;
	}
	
	public int update(BlackList obj) {
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
 
		ContentValues values = new ContentValues();
		values.put(KEY_NUM, obj.getNumber());
		values.put(KEY_TYPE, obj.getType());
 
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
		if(cursor.moveToFirst()){
			cursor.close();
			return cursor.getCount();
		} else
			return 0;
	}
}
