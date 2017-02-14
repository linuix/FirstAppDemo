package com.root.util;

import android.os.Build;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/11/8.
 */

public class SelinuxUtils
{
    private static Boolean a;
    private static Object b;

    static {
        SelinuxUtils.a = null;
        SelinuxUtils.b = new Object();
    }

    /**
     *
     * check the linux version was Selinux?
     * **/
    public static boolean a() {
        Boolean v0_3 = null;
        FileInputStream v3;
        Boolean v1;
        boolean v0 = false;
        Object v2 = SelinuxUtils.b;
        try {
            if (SelinuxUtils.a != null) {
                return SelinuxUtils.a.booleanValue();
            }

            v1 = null;
            if (sdk() >= 17 && (new File("/sys/fs/selinux/enforce").exists()))
            {
                try {
                    v3 = new FileInputStream("/sys/fs/selinux/enforce");
                    if (((InputStream) v3).read() == 49) {
                        v0 = true;
                    }

                    v0_3 = Boolean.valueOf(v0);
                    ((InputStream) v3).close();

                    if (v0_3 == null) {
                        v0_3 = Boolean.valueOf(false);
                        SelinuxUtils.a = v0_3;
                    }

                } catch (Exception v0_2) {
                    v0_3 = v1;
                    if (v0_3 == null) {
                        v0_3 = Boolean.valueOf(false);
                        SelinuxUtils.a = v0_3;
                    }
                }

                v0_3 = v1;
            }
        }
        catch(Throwable v0_1) {
        }
        return v0_3;
    }

/**
 *
 * read sdkversion from os
 * */
    public static int sdk()
    {
        return new Integer(Build.VERSION.SDK).intValue();
    }

}
