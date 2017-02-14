package com.kingroot.sdk.util;

import android.content.Context;

public final class Cryptor {
    public static native byte[] x(Context arg0, byte[] arg1) ;

    public static native byte[] x2(Context arg0, byte[] arg1) ;

    public static native byte[] y(Context arg0, byte[] arg1) ;

    public static final native byte[] z(byte[] arg0) ;
}

