package com.kingroot.common.utils.encode;

import android.content.Context;

public final class Encode {
    static {
        System.loadLibrary("xy");
    }

    public static native byte[] x(Context arg0, byte[] arg1) ;

    public static native byte[] y(Context arg0, byte[] arg1);
}