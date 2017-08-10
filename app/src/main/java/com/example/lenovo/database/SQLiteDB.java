package com.example.lenovo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lenovo on 09-08-2017.
 */

public class SQLiteDB {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "basePrice";
    public static final String KEY_CUSINE = "takeawayPrice";
    public static final String KEY_RATE = "maxQuantity";
    public static final String KEY_STALLNO = "cusine";

    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "SQLiteDB";

    private static final String TABLE_NAME = "sample";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = "create table if not exists sample (id text primary key, name text,basePrice text,takeawayPrice text,maxQuantity text,cusine text);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public SQLiteDB(Context ctx) {

        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public void insert(ContentValues values) {
        db.insert(TABLE_NAME, null, values);

        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME, null);
        while (cursor.moveToNext()){
            System.out.println("Data: " + cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
            onCreate(db);
        }
    }

    //---open SQLite DB---
    public SQLiteDB open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---close SQLite DB---
    public void close() {
        DBHelper.close();
    }

    //---Delete All Data from table in SQLite DB---
    public void deleteAll() {
        db.delete(TABLE_NAME, null, null);
    }

    //---Get All Contacts from table in SQLite DB---
    public Cursor getAllData() {
        return db.query(TABLE_NAME, new String[]{KEY_NAME},
                null, null, null, null, null);
    }


}
