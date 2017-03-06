package com.second.letu.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.second.letu.application.MyApplication;

/**
 * 工具类的统一方法类
 */
public class UIUtil {

    //获取Application的变量
    public static Context getContext() {
        return MyApplication.getContext();
    }

    public static Handler getHandler() {
        return MyApplication.getHandler();
    }

    public static int getMainThreadId() {
        return MyApplication.getMainThreadId();
    }

    ///////////////获取资源文件(R文件下的资源获取使用id)/////////////////////
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    public static int getDimen(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    ///////////dip和px/////////////////////

    /**
     * dip(float) * density(float) = px(int)
     * @param dip
     * @return
     */
    public static int dip2px(float dip) {
        //获取设备密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    /////////////加载布局//////////////////////
    public static View getView(int id) {
        return View.inflate(getContext(), id, null);
    }

    /////////////判断是否运行在主线程//////////////////////
    /*public static boolean isRunOnUIThread() {
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }
        return false;
    }*/
    public static boolean isRunOnUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    //////////////运行到主线程/////////////////
    public static void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            //在主线程，直接运行
            r.run();
        } else {
            //如果是子线程，借助handler让其运行在主线程上（post:将Runnable加到消息队列中）
            getHandler().post(r);
        }
    }

    /**
     * 根据id获取color状态选择器(PagerTab使用)
     * @param id
     * @return
     */
    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }
}
