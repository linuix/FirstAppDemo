package com.root.executor;

import android.os.Environment;

import com.demo.utils.LogUtil;
import com.root.util.KuSdkInitHelper;
import com.root.util.SDkLib;
import com.root.util.SelinuxUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/11/8.
 */

public class KToolsUtils {
    public static String a() {
        LogUtil.e("KtoolsUtils --- .>>");
        String v0;
        if (SelinuxUtils.a()) {
            File v1 = new File(String.valueOf(String.valueOf(Environment.getDataDirectory().getAbsolutePath())
                    + File.separator + "data-lib" + File.separator + KuSdkInitHelper.a().getPackageName())
                    + File.separator + "ktools");
            if (v1.exists()) {
                v0 = v1.getAbsolutePath();
            } else {
                v0 = String.valueOf(SDkLib.b().getAbsolutePath()) + File.separator + "ktools";
                if (!new File(v0).exists()) {
                    LogUtil.d("ktools KToolsMgr|getPublicFilePath|NO KTOOLS FOUND!");
                    v0 = "echo";
                }
            }
        } else {
            v0 = String.valueOf(SDkLib.b().getAbsolutePath()) + File.separator + "ktools";
            if (!new File(v0).exists()) {
                LogUtil.d("ktools KToolsMgr|getPublicFilePath|NO KTOOLS FOUND!");
                v0 = "echo";
            }
        }
        return v0;

    }
}
