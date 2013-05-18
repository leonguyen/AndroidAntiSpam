package com.example.antispam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	// Database
	private static final String DB_NAME = "antispam.db";
	private static final int DB_VERSION = 1;

	private CallDA cda = null;
	private BlackListDA blda = null;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	public CallDA getCallDA() {
		if (cda == null)
			cda = new CallDA(this);
		return cda;
	}

	public BlackListDA getBlackListDA() {
		if (blda == null)
			blda = new BlackListDA(this);
		return blda;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		cda = new CallDA(this);
		cda.onCreate(db);
		blda = new BlackListDA(this);
		blda.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		cda.onUpgrade(db, oldVersion, newVersion);
		blda.onUpgrade(db, oldVersion, newVersion);
	}
}
