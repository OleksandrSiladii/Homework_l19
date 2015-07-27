package com.example.simantik.homework_l19;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sasha on 23.07.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notificationsDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notification";
    public static final String UID = "_id";
    public static final String MESSAGE = "message";
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    public static final String TICKER_TEXT = "tickerText";
    public static final String VIBRATE = "vibrate";
    public static final String SOUND = "sound";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + TABLE_NAME    + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MESSAGE       + " TEXT,"
            + TITLE         + " TEXT,"
            + SUBTITLE      + " TEXT,"
            + TICKER_TEXT   + " TEXT,"
            + VIBRATE       + " TEXT,"
            + SOUND         + " TEXT);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}


