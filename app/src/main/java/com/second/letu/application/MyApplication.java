package com.second.letu.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import cn.sharesdk.framework.ShareSDK;

/**
 * 自定义的Applcaition(获取Context，Handler，mainThreadId)
 */

public class MyApplication extends Application {

    private static Context mContext;//全局上下文
    private static Handler mHandler;//全局Handler
    private static int mainThreadId;//主线程id

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mainThreadId = android.os.Process.myTid();
        ShareSDK.initSDK(mContext);
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















