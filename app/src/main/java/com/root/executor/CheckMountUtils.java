package com.root.executor;

import android.os.Build;

import com.root.util.SelinuxUtils;

import java.util.Locale;

public final class CheckMountUtils {
    public static String c;
    public static String d;
    public  static String b;
    public  static String a;

    static {
        int sdk_14 = 14;
        int sdk_18 = 18;
        String v0 = SelinuxUtils.sdk() >= sdk_18 ? "mount -o remount -w /system" : "mount -o remount,rw /system /system";
        CheckMountUtils.a = v0;
        if(SelinuxUtils.sdk() >= sdk_14 && (Build.MODEL.toLowerCase(Locale.ENGLISH).contains("htc"))) {
            v0 = "echo";
        }
        else if(SelinuxUtils.sdk() >= sdk_18) {
            v0 = "mount -o remount -r /system";
        }
        else {
            v0 = "mount -o remount,ro /system /system";
        }
        CheckMountUtils.b = v0;
        v0 = SelinuxUtils.sdk() >= sdk_18 ? "mount -o remount -w /" : "mount -o remount,rw / /";
        CheckMountUtils.c = v0;
        if(SelinuxUtils.sdk() >= sdk_14 && (Build.MODEL.toLowerCase(Locale.ENGLISH).contains("htc"))) {
            v0 = "echo";
        }
        else if(SelinuxUtils.sdk() >= sdk_18) {
            v0 = "mount -o remount -r /";
        }
        else {
            v0 = "mount -o remount,ro / /";
        }
        CheckMountUtils.d = v0;
    }
}

