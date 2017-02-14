package com.kinguser.sdk.util;

import android.content.Context;

import com.demo.utils.LibSoHelper;
import com.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/9/28.
 */

public class Encode {
    private static boolean a;

    static {
//        System.loadLibrary("xy");
        Encode.a = false;
    }
    public static byte[] a(Context arg1, byte[] arg2) {
        if(!Encode.a) {
           //判断load.libxy.so文件
            loadSo();//這個文件实在data/data/com.demo/sdklib下的libxy.so文件
        }
        LogUtil.d("native libxy.so load finished ~^_^~");
        return Encode.y(arg1, arg2);
    }
    
    private static void loadSo(){
        LogUtil.d(Encode.class.getName()+"load so begin -_-||");
        LibSoHelper.loadSo();
    }

    public static native byte[] y(Context arg0, byte[] arg1) ;
}
