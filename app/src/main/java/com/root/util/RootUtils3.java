package com.root.util;

import android.content.Context;

import com.demo.utils.LogUtil;
import com.root.dao.ICommUtil;
import com.root.dao.IJavaProcessh;
import com.root.executor.CommUtils;

public final class RootUtils3 {
    private static boolean a;

    static {
        RootUtils3.a = false;
    }

    public static boolean a(Context context, IJavaProcessh javaprocessk, String su, String superuser) {
        LogUtil.e("操作自己的数据  mysu="+su+" | \t apk ="+superuser);
        CommUtils v0 = new CommUtils(javaprocessk);
        if(!RootUtils3.a) {
            KuSdkInit.a(((ICommUtil)v0), context, su);
            RootUtils3.a = true;
        }
        KuSdkInit.a(((ICommUtil)v0));//调用这个文件
        return KuSdkInit.a(((ICommUtil)v0), superuser);
    }
}

