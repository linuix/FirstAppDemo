package com.kingroot.sdk.util;

import android.content.Context;

public final class Cryptor {
    /**
     * 加密
     * @param arg0
     * @param arg1
     * @return
     */
    public static native byte[] x(Context arg0, byte[] arg1) ;

    public static native byte[] x2(Context arg0, byte[] arg1) ;

    /**
     * 解密数据
     * @param arg0
     * @param arg1
     * @return
     */
    public static native byte[] y(Context arg0, byte[] arg1) ;

    /**
     * 解密
     * @param arg0
     * @return
     */
    public static final native byte[] z(byte[] arg0) ;
}

