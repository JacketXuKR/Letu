package com.second.letu.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteUtil {
    private static MySQLiteHelper mMySQLiteHelper = new MySQLiteHelper(UIUtil.getContext(), "outData.db", null, 1);
    private SQLiteDatabase db;
    private static final SQLiteUtil mSQLiteUtil = new SQLiteUtil();
    private SQLiteUtil() {
    }

    public static SQLiteUtil getInstance() {
        return mSQLiteUtil;
    }

    public boolean insertData(String start, String end) {
        if (db == null) {
            db = mMySQLiteHelper.getWritableDatabase();
        }
        Cursor outdata1 = db.query("outdata", new String[]{"id"}, "startPoi = ? AND endPoi = ?", new String[]{start, end}, null, null, null);
        if(outdata1 != null) {
            if(!outdata1.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put("startPoi", start);
                values.put("endPoi", end);
                long outdata = db.insert("outdata", null, values);
                return outdata != -1 ? true : false;
            }
        }
        return false;
    }

    public ArrayList<HashMap<String, String>> searchData() {
        if (db == null) {
            db = mMySQLiteHelper.getWritableDatabase();
        }
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> item = null;
        Cursor cursor = db.query("outdata", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String startPoi = cursor.getString(cursor.getColumnIndex("startPoi"));
                String endPoi = cursor.getString(cursor.getColumnIndex("endPoi"));
                item = new HashMap<String, String>();
                item.put("startPoi", startPoi);
                item.put("endPoi", endPoi);
                data.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

}
