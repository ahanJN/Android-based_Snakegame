package com.fcmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/28.
 * Create database
 */
public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBManager(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS UserInfo"
                + "(id integer primary key,username VARCHAR,pwd VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Ranking"
                + "(id integer primary key,userName VARCHAR,Fraction VARCHAR,Ranking VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Music"
                + "(id integer primary key,musicName VARCHAR,music VARCHAR)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("drop table if exists UserInfo");
            db.execSQL("drop table if exists Ranking");
            db.execSQL("drop table if exists Music");
            onCreate(db);
        }

    }
}
