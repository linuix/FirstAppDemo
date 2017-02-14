package com.root.util;

import android.content.Context;

public final class KuSdkInitHelper {
    public static boolean a;
    private static Context b;
    private static String c;

    static {
        KuSdkInitHelper.a = false;
        KuSdkInitHelper.c = null;
    }

    public KuSdkInitHelper() {
        super();
    }

    public static Context a() {
        return KuSdkInitHelper.b;
    }

    public static void a(Context arg1, String arg2) {
        KuSdkInitHelper.b = arg1;
        KuSdkInitHelper.a = false;
        KuSdkInitHelper.c = arg2;
    }

    public static String b() {
        return KuSdkInitHelper.c;
    }
}

