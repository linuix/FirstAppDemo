package com.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/11/7.
 * 记录root过程的信息
 *
 */
public class SpfUtils {

    public static SharedPreferences getPref(Context context)
    {
        return context.getSharedPreferences("marsroot",0);
    }


    public static void markSdkStart(Context arg4, String arg5, int arg6, boolean arg7) {
        SharedPreferences.Editor v0 = getPref(arg4).edit();
        v0.putString("x_sdk_old_root_status", arg5);
        v0.putLong("x_sdk_start_nano_time", System.nanoTime());
        v0.putInt("x_sdk_solution_count", arg6);
        v0.putBoolean("x_sdk_su_mode", arg7);
        v0.commit();

    }

    public static void markLastNanoTime(Context arg4) {
        SharedPreferences.Editor v0 =getPref(arg4).edit();
        v0.putLong("x_last_nano_time", System.nanoTime());
        v0.commit();
    }

    public static void putMarsRootSharedPreferences(Context context, String key, String value) {
        context.getSharedPreferences("marsroot", 0).edit().putString(key, value).commit();
    }


    public static void mark(Context context, String arg5, int arg6, int arg7) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putString("x_sid", arg5);
        editor.putInt("x_sindex", arg6);
        editor.putInt("x_stype", arg7);
        editor.putLong("x_start_time", System.currentTimeMillis());
        editor.putLong("x_start_nano_time", System.nanoTime());
        editor.putLong("x_last_nano_time", System.nanoTime());
        editor.putInt("x_exploit_ret", 7053);
        editor.putString("x_exploit_errcodes", "7053");
        editor.commit();
    }

    public static void markExploitRet(Context context, int retValue, String errcodesValue) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putInt("x_exploit_ret", retValue);
        editor.putString("x_exploit_errcodes", errcodesValue);
        editor.commit();
    }


//--------------------------------------------------------------------------//
    public static void putMarsRootShareAndSeparator(Context context, String key, String[] data) {
        int i = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences("marsroot", 0);
        String v2 = "__separator__";
        StringBuilder stringBuilder = new StringBuilder();
        int v4 = data.length;
        while(i < v4) {
            stringBuilder.append(data[i]);
            stringBuilder.append(v2);
            ++i;
        }
        if(stringBuilder.length() > 0) {
            stringBuilder.delete(stringBuilder.length() - v2.length(), stringBuilder.length());
        }
        sharedPreferences.edit().putString(key, stringBuilder.toString()).commit();
    }
       public static void removeMarsRootSharedPreferences(Context arg2, String arg3) {
        arg2.getSharedPreferences("marsroot", 0).edit().remove(arg3).commit();
    }

    public static void put(Context arg2, String arg3, String value) {
        arg2.getSharedPreferences("marsroot", 0).edit().putString(arg3,value).commit();
    }

    public static void a(Context arg2, String arg3, int arg4) {
        arg2.getSharedPreferences("marsroot", 0).edit().putInt(arg3, arg4).commit();
    }

    public static void a(Context arg2, String arg3, long arg4) {
        arg2.getSharedPreferences("marsroot", 0).edit().putLong(arg3, arg4).commit();
    }

    public static String getMarsrootSharePreferences(Context context, String key) {
        return context.getSharedPreferences("marsroot", 0).getString(key, null);
    }

    public static int getIntFromMarsrootSharePreferences(Context arg2, String key) {
        return arg2.getSharedPreferences("marsroot", 0).getInt(key, 0);
    }

    public static long d(Context arg3, String arg4) {
        return arg3.getSharedPreferences("marsroot", 0).getLong(arg4, 0);
    }

    public static String[] getMarsRootShareAndRemoveSeparator(Context context, String key) {
        String separator = "__separator__";
        String value = context.getSharedPreferences("marsroot", 0).getString(key, null);
        String[] result = value != null ? value.split(separator) : new String[0];
        return result;
    }

    /**
     * 保存初始化标志
     * */
   public static void set(Context context, String key, boolean value)
   {
       context.getSharedPreferences("marsroot", 0).edit().putBoolean(key,value).commit();
   }

    /***
     *读取初始化状态
     */
    public static boolean get(Context context, String key)
    {
        return context.getSharedPreferences("marsroot", 0).getBoolean(key, false);
    }
}

