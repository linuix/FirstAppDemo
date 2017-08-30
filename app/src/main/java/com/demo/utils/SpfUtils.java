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

    public static void removeMarsRootSharedPreferences(Context arg2, String arg3, String arg4) {
        arg2.getSharedPreferences("marsroot", 0).edit().putString(arg3, arg4).commit();
    }


    public static void mark(Context arg4, String arg5, int arg6, int arg7) {
        SharedPreferences.Editor v0 = getPref(arg4).edit();
        v0.putString("x_sid", arg5);
        v0.putInt("x_sindex", arg6);
        v0.putInt("x_stype", arg7);
        v0.putLong("x_start_time", System.currentTimeMillis());
        v0.putLong("x_start_nano_time", System.nanoTime());
        v0.putLong("x_last_nano_time", System.nanoTime());
        v0.putInt("x_exploit_ret", 7053);
        v0.putString("x_exploit_errcodes", "7053");
        v0.commit();
    }

    public static void markExploitRet(Context arg2, int arg3, String arg4) {
        SharedPreferences.Editor v0 = getPref(arg2).edit();
        v0.putInt("x_exploit_ret", arg3);
        v0.putString("x_exploit_errcodes", arg4);
        v0.commit();
    }


//--------------------------------------------------------------------------//
    public static void a(Context arg6, String arg7, String[] arg8) {
        int v0 = 0;
        SharedPreferences v1 = arg6.getSharedPreferences("marsroot", 0);
        String v2 = "__separator__";
        StringBuilder v3 = new StringBuilder();
        int v4 = arg8.length;
        while(v0 < v4) {
            v3.append(arg8[v0]);
            v3.append(v2);
            ++v0;
        }

        if(v3.length() > 0) {
            v3.delete(v3.length() - v2.length(), v3.length());
        }

        v1.edit().putString(arg7, v3.toString()).commit();
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

    public static int c(Context arg2, String arg3) {
        return arg2.getSharedPreferences("marsroot", 0).getInt(arg3, 0);
    }

    public static long d(Context arg3, String arg4) {
        return arg3.getSharedPreferences("marsroot", 0).getLong(arg4, 0);
    }

    public static String[] e(Context arg4, String arg5) {
        String v1 = "__separator__";
        String v0 = arg4.getSharedPreferences("marsroot", 0).getString(arg5, null);
        String[] v0_1 = v0 != null ? v0.split(v1) : new String[0];
        return v0_1;
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

