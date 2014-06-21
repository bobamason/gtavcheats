package com.bobamason.gtavcheats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter {

	private DatabaseAdapter.SQLHelper helper;

	private Context ctx;

	public DatabaseAdapter(Context context) {
		ctx = context;
		helper = new SQLHelper(ctx);
	}

	public boolean saved(String cheatCode) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query(SQLHelper.TABLE_NAME,
				new String[] { SQLHelper.UID }, SQLHelper.COLUMN_CHEAT + " = '"
						+ cheatCode + "'", null, null, null, null);
		boolean b = c.moveToFirst();
		c.close();
		return b;
	}

	public void insert(String title, String cheatCode, boolean isPS3) {
		SQLiteDatabase db = helper.getWritableDatabase();
		if (!saved(cheatCode)) {
			ContentValues vals = new ContentValues();
			vals.put(SQLHelper.COLUMN_TITLE, title);
			vals.put(SQLHelper.COLUMN_CHEAT, cheatCode);
			vals.put(SQLHelper.COLUMN_CONSOLE, isPS3 ? 1 : 0);
			db.insert(SQLHelper.TABLE_NAME, null, vals);
		}
		db.close();
	}

	public void deleteEntry(String cheatCode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(SQLHelper.TABLE_NAME, SQLHelper.COLUMN_CHEAT + " = '"
				+ cheatCode + "'", null);
		db.close();
	}

	public Cursor getAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.query(SQLHelper.TABLE_NAME, null, null, null, null,
					null, SQLHelper.COLUMN_TITLE + " ASC");
			Log.e("Cursor", "cursor returned");
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e("Cursor", e.toString());
		}
		return cursor;
	}

	static class SQLHelper extends SQLiteOpenHelper {

		private static final int DB_VERSION = 1;

		private static final String DB_NAME = "database1.db";

		private static final String TABLE_NAME = "TABLE1";

		public static final String UID = "_id";

		public static final String COLUMN_CONSOLE = "category";

		public static final String COLUMN_TITLE = "name";

		public static final String COLUMN_CHEAT = "cheatcode";

		// private Context context;

		private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_NAME
				+ " ("
				+ UID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_TITLE
				+ " VARCHAR(255), "
				+ COLUMN_CONSOLE
				+ " INTEGER, "
				+ COLUMN_CHEAT + " VARCHAR(255));";

		public SQLHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			// this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				Log.e("Create Table", "created");
				db.execSQL(CREATE_TABLE);
				// Message.makeToast(context, this.toString() +
				// ".onCreate TABLE CREATED");

			} catch (SQLException e) {
				Log.e("Create Table", e.toString());
				e.printStackTrace();
				// Message.makeToast(context, e.toString());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
				// Message.makeToast(context, this.toString() +
				// ".onUpgrade TABLE UPGRADED");
				this.onCreate(db);
			} catch (SQLException e) {
				e.printStackTrace();
				// Message.makeToast(context, e.toString());
			}
		}
	}
}
