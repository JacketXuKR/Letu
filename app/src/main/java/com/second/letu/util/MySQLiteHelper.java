package com.second.letu.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jacket_Xu on 2017/4/20.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String CREATE_OUT_DATA = "create table outdata ("
            + "id integer primary key autoincrement,"
            + "startPoi text,"
            + "endPoi text)";
    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_OUT_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
