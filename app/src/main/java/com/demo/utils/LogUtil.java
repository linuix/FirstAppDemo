package com.demo.utils;

import android.util.Log;

public class LogUtil {
    private static final String TAG = "LOL";
    public static boolean debug = true;
    public static boolean test = true;


    public static void loge(String msg) {
        if (debug) {
            Log.e(TAG, " [==> " + msg + " <==]");
        }
    }

    public static void w(String msg) {
        if (debug) {
            Log.e(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (debug) {
            Log.d(TAG, " [--> " + msg + " <--]");
        }
    }

    public static void i(String msg) {
        if (debug) {
            Log.i(TAG, " [--> " + msg + " <--]");
        }
    }

    public static void e(String msg)
    {
        if (test)
        {
            Log.e(TAG,"[   "+msg+ "  ]");
        }
    }

    public static void exception(String msg, Throwable e) {
        if (debug) {
            LogUtil.loge(msg +  " == " + Log.getStackTraceString(e));
        }
    }

}