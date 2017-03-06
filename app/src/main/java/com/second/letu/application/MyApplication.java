package com.second.letu.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 自定义的Applcaition(获取Context，Handler，mainThreadId)
 */

public class MyApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mainThreadId = android.os.Process.myTid();
    }


    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}















