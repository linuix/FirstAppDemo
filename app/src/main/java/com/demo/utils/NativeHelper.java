package com.demo.utils;

import android.content.Context;

import com.kinguser.sdk.util.Encode;


/**
 * Created by Administrator on 2016/9/28.
 */

public class NativeHelper {
    public static byte[] a(Context arg2, byte[] arg3) {
        byte[] v0 = null;
        if(arg3 != null && arg3.length != 0) {
            try {
                v0 = Encode.a(arg2, arg3);
            }
            catch(Throwable v1) {
                v1.printStackTrace();
//                Encode.cmd();
                LogUtil.loge("load so exception = "+v1.getCause()+" , "+v1.getMessage().toString());
                try {
                    v0 = Encode.a(arg2, arg3);
                }
                catch(Throwable v) {
                    v.printStackTrace();
                    LogUtil.loge("load so exception = "+v1.getCause()+" , "+v1.getMessage().toString());
                }
            }
        }

        return v0;
    }
}
