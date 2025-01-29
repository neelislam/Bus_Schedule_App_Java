package com.example.busscheduleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BusSchedule.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "bus_schedule";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ROUTE = "route";
    private static final String COLUMN_BUS_NAME = "bus_name";
    private static final String COLUMN_BUS_NUMBER = "bus_number";
    private static final String COLUMN_TIME = "time";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ROUTE + " TEXT, " +
                COLUMN_BUS_NAME + " TEXT, " +
                COLUMN_BUS_NUMBER + " TEXT, " +
                COLUMN_TIME + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String route, String busName, String busNumber, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROUTE, route);
        values.put(COLUMN_BUS_NAME, busName);
        values.put(COLUMN_BUS_NUMBER, busNumber);
        values.put(COLUMN_TIME, time);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateData(String id, String route, String busName, String busNumber, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROUTE, route);
        values.put(COLUMN_BUS_NAME, busName);
        values.put(COLUMN_BUS_NUMBER, busNumber);
        values.put(COLUMN_TIME, time);

        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
        return rowsDeleted > 0;
    }
}