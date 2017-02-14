package com.root.util;

import android.content.Context;

import com.root.process.SDKLibProcessMgr;

import java.io.File;

public final class SDkLib {
    public static File a() {
        return SDkLib.a(SDkLib.c(), "sdklib");
    }

    private static File a(File arg4, String arg5) {
        File v0 = new File(arg4, arg5);
        if(!v0.exists()) {
            Context v1 = KuSdkInitHelper.a();
            File v2 = v1.getDir(String.valueOf(arg5) + "temp", 0);
            if((v2.exists()) && !v2.renameTo(v0)) {
                v2.delete();
            }

            v0 = new File(arg4, arg5);
            if(!v0.exists()) {
                v0.mkdirs();
                v0 = new File(arg4, arg5);
                if(!v0.exists()) {
                    v1.getFilesDir();
                    v0.mkdirs();
                    v0 = new File(arg4, arg5);
                }
            }

            SDKLibProcessMgr.a("sh", "chmod 0771 " + v0.getAbsolutePath());
        }

        return v0;
    }

    public static File b()
    {
        return SDkLib.a(SDkLib.c(), "sdkexec");
    }

    private static File c() {
        return new File(KuSdkInitHelper.a().getApplicationInfo().dataDir);
    }
}

