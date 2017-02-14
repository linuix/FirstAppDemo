package com.root.util;

import android.content.Context;

import com.demo.utils.InitConfig;
import com.demo.utils.LogUtil;
import com.root.dao.IJavaProcessh;

import java.io.File;

/**
 * Created by Administrator on 2016/11/8.
 *
 * 主要是管理静默安装的应用
 */

public class ApkInstallUtil {

    public static int a() {
        ExeRunner3 v1 = new ExeRunner3(new File(InitConfig.entity.file, "su_check").getAbsolutePath());
        v1.run();
        int v0 = v1.a();
       LogUtil.d("rootCode = " + v0);
        LogUtil.d("verifyR, rcode = " + v0);
        return v0;
    }
    public static boolean a(IJavaProcessh arg2) {
        boolean v0 = arg2 == null || arg2.a() != 4 || !arg2.b() ? false : true;
        return v0;
    }
}
