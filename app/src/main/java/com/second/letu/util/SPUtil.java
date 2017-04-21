package com.second.letu.util;

/**
 * Created by Jacket_Xu on 2017/3/11.
 */

import android.content.Context;
import android.content.SharedPreferences;
public class SPUtil {
    private static SharedPreferences sp;
    private static Context context = UIUtil.getContext();
    /**
     * 存储boolean类型的值
     * @param key 输入的变量名
     * @param value true/false
     */
    public static void putBoolean(String key, boolean value) {
        //如果没有创建过SharedPreferences,则进入判断,存储文件名称为config
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取存储的boolean值,没有返回默认值
     * @param key 输入的变量名
     * @param defValue 默认值:true/false
     * @return 存储的boolean值
     */
    public static boolean getBoolean(String key, boolean defValue) {
        //如果没有创建过SharedPreferences,则进入判断,存储文件名称为config
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 存储int类型的值
     * @param key 输入的变量名
     * @param value String
     */
    public static void putString(String key, String value) {
        //如果没有创建过SharedPreferences,则进入判断,存储文件名称为config
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取存储的String值,没有返回默认值
     * @param key 输入的变量名
     * @param defValue 默认值:true/false
     * @return 存储的String值
     */
    public static String getString(String key, String defValue) {
        //如果没有创建过SharedPreferences,则进入判断,存储文件名称为config
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString (key, defValue);
    }

    /**
     * 存储int类型的值
     * @param key 输入的变量名
     * @param value int
     */
    public static void putInt(String key, int value) {
        //如果没有创建过SharedPreferences,则进入判断,存储文件名称为config
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }
    /**
     * 获取存储的String值,没有返回默认值
     * @param key 输入的变量名
     * @param defValue 默认值:true/false
     * @return 存储的String值
     */
    public static int getInt(String key, int defValue) {
        //如果没有创建过SharedPreferences,则进入判断,存储文件名称为config
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    public static void putFloat(String key,float value) {
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putFloat(key,value).commit();
    }

    public static float getFloat(String key, float defValue) {
        //如果没有创建过SharedPreferences,则进入判断,存储文件名称为config
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getFloat(key, defValue);
    }

    public static void putLong(String key,long value) {
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key,value).commit();
    }

    public static long getLong(String key, long defValue) {
        //如果没有创建过SharedPreferences,则进入判断,存储文件名称为config
        if(sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getLong(key, defValue);
    }

}
