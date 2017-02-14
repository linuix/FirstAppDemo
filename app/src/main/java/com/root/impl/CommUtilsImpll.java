package com.root.impl;

import android.os.Build;

import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.root.dao.AbsCommUtila;
import com.root.dao.ICommUtil;
import com.root.executor.CommUtils2;
import com.root.executor.ExecutorHelper;
import com.root.executor.InstallRecovery;
import com.root.util.KuSdkInitHelper;
import com.root.util.WakeLockMgr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/8.
 */

public class CommUtilsImpll extends AbsCommUtila {

    private boolean a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private boolean g;
    private static byte[] h;

    static {
        byte[] v0 = new byte[16];
        v0[0] = 127;
        v0[1] = 69;
        v0[2] = 76;
        v0[3] = 70;
        v0[4] = 1;
        v0[5] = 1;
        v0[6] = 1;
        CommUtilsImpll.h = v0;
    }

    public CommUtilsImpll() {
        super();
        this.a = false;
        this.b = false;
        this.c = false;
        this.d = false;
        this.e = false;
        this.f = false;
        this.g = false;
    }

    private static void a(ICommUtil arg2, String arg3, String arg4) {
        WakeLockMgr.a(arg2, new ChUtilsImplm(arg4, arg3), new Object[0]);
    }


    private static void a(ICommUtil arg2, String arg3, String arg4, boolean arg5) {
        WakeLockMgr.a(arg2, new ChUtilsImpln(arg3, arg4, arg5), new Object[0]);
    }



    private static boolean a(ICommUtil arg5,String arg6)
    {
        boolean v0 = false;
        StringBuilder v1 = new StringBuilder();
        File v2 = new File("/system/bin/ddexe");
        File v3 = new File("/system/bin/ddexe_real");

        if((v2.exists()) || (v3.exists())) {
            v1.append("#!/system/bin/sh\n");
            v1.append(String.valueOf(arg6) + "\n\n");
            v1.append("/system/bin/ddexe_real\n");
            try {
                String v2_1 = KuSdkInitHelper.a().getFilesDir() + File.separator + "sh.tmp";
                a(v1.toString().getBytes(), v2_1);
                CommUtilsImpll.a(arg5, v2_1, "/system/bin/ddexe");
                v0 = true;
            }
            catch(Exception v1_1) {
                LogUtil.exception(null, ((Throwable)v1_1));
            }
        }

        return v0;
    }


    private static boolean a(String arg9) {
        FileInputStream v2;
        boolean v1 = true;
        String v3 = null;
        boolean v0 = false;
        int v6 = 1023;
        int v4_1 = 0;
        File v4 = new File(arg9);
        if(!v4.exists()) {
            return v0;
        }
        byte[] v5 = new byte[1024];
        try {
            v2 = new FileInputStream(v4);
            if(((InputStream)v2).read(v5, 0, v6) != -1) {
                if(new String(v5).contains("#!/system/bin/sh")) {
                    v0 = true;
                    ((InputStream)v2).close();
                    return v0;
                }
                else {
                    while(true) {
                        if(v4_1 < CommUtilsImpll.h.length) {
                            if(CommUtilsImpll.h[v4_1] != v5[v4_1]) {
                                break;
                            }
                            ++v4_1;
                            continue;
                        }
                        else {
                            v1 = false;
                        }
                    }
                    if(v4_1 < 4)
                    {
                        if(v4_1 == CommUtilsImpll.h.length)
                        {
                            ((InputStream)v2).close();
                            return v0;
                        }
                        v0 = v1;
                        ((InputStream)v2).close();
                        return v0;
                    }
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return  v0;
    }


    private static boolean a(String arg8, String arg9) {
        String v0_1;
        String v2 = null;
        boolean v1 = false;
        if(!new File(arg8).exists() && !new File("/system/bin/ddexe_real").exists()) {
            return true;
        }

        if(!new File("/system/bin/ddexereal").exists() && (CommUtilsImpll.a(arg8))) {
            File v3 = new File(arg8);
            if(!v3.exists()) {
                return v1;
            }

            try {
                v0_1 = new String(Utils.a(v3.getAbsolutePath()));
            }
            catch(Exception v0) {
                LogUtil.exception(v2, ((Throwable)v0));
                v0_1 = v2;
            }

            if(v0_1 == null) {
                return v1;
            }

            if(v0_1.length() <= 0) {
                return v1;
            }

            String[] v5 = v0_1.split("\n");
            if(v5.length <= 0) {
                return v1;
            }

            int v0_2 = 0;
            int v2_1 = 0;
            boolean v3_1 = false;
            while(v0_2 < v5.length) {
                if(v5[v0_2].contains("/system/xbin/ku.sud")) {
                    if(v5[v0_2].trim().equals(arg9)) {
                        v3_1 = true;
                    }

                    ++v2_1;
                }

                ++v0_2;
            }

            if(v2_1 != 1 && (v3_1)) {
                return v1;
            }

            v1 = v3_1;
        }

        return v1;
    }

    private static boolean a(String arg8, String arg9, String arg10) {
        String v0_1;
        boolean v1 = false;
        String v2 = null;
        File v3 = new File(arg8);
        if(v3.exists()) {
            try {
                v0_1 = new String(Utils.a(v3.getAbsolutePath()));
            }
            catch(Exception v0) {
                v0.printStackTrace();
                v0_1 = v2;
            }

            if(v0_1 == null) {
                return v1;
            }

            if(v0_1.length() <= 0) {
                return v1;
            }

            String[] v5 = v0_1.split("\n");
            if(v5.length <= 0) {
                return v1;
            }

            int v0_2 = 0;
            int v2_1 = 0;
            boolean v3_1 = false;
            while(v0_2 < v5.length) {
                String v6 = v5[v0_2];
                if(v6.contains(((CharSequence)arg9))) {
                    if(v6.trim().equals(arg10)) {
                        v3_1 = true;
                    }

                    ++v2_1;
                }

                ++v0_2;
            }

            if(v2_1 != 1 && (v3_1)) {
                return v1;
            }

            v1 = v3_1;
        }

        return v1;
    }


    @Override
    public boolean a(ICommUtil arg9) {
        ExecutorHelper v0_1;
        int v7 = 493;
        boolean v2 = true;
        this.a = false;
        this.b = false;
        this.c = false;
        this.d = false;
        String v3 = "/system/xbin/ku.sud -d";
        String v4 = "/system/xbin/ku.sud -d &";
        boolean v0 = CommUtilsImpll.a("/system/bin/ddexe", v4) ? false : true;
        this.a = v0;
        if (!this.a) {
            v0_1 = new ExecutorHelper();
            v0_1.a = 0;
            v0_1.b = 0;
            v0_1.c = v7;
            v0_1.e = "u:object_r:system_file:s0";
            v0 = CommUtils2.a(arg9, "/system/bin/ddexe", v0_1) != 0 ? true : false;
            this.c = v0;
        }
        String v5 = InstallRecovery.a(arg9);
        this.e = false;
        this.f = false;
        this.g = false;
        this.e = CommUtilsImpll.a(v5, v3, v4);
        this.f = CommUtilsImpll.a(v5, "/system/etc/install-recovery-2.sh", "/system/etc/install-recovery-2.sh");
        this.g = CommUtilsImpll.a("/system/etc/install-recovery-2.sh", v3, v4);
        if (this.e) {
            if ((this.f) && (this.g)) {
                v0 = true;
            }

            v0 = false;
        } else {
            if ((this.f) && (this.g) && !Build.MODEL.equals("SM-N9002")) {
                v0 = false;
                this.b = v0;
                if (!this.b) {
                    v0_1 = new ExecutorHelper();
                    v0_1.a = 0;
                    v0_1.b = 0;
                    v0_1.c = v7;
                    v0_1.e = "u:object_r:system_file:s0";
                    ArrayList v3_1 = new ArrayList();
                    v3_1.add(v5);
                    v3_1.add("/system/etc/install-recovery-2.sh");
                    this.d = CommUtils2.a(arg9, ((List) v3_1), v0_1);
                }

                if ((this.a) || (this.b) || (this.c) || (this.d)) {
                    v2 = false;
                }

                return v2;
            }
        }
        return false;
    }



    private static int b(ICommUtil arg8, String arg9, String arg10) {

        StringBuilder v0_2 =null;
        String v1 = null;
        int v2 = -1;
        int v0_3 = 0;
        StringBuilder v3 = new StringBuilder();
        File v0 = new File(arg9);

        if(v0.exists()) {
            try {
                v0_2 = new StringBuilder(new String(Utils.a(v0.getAbsolutePath())));
                v3.append("#!/system/bin/sh");
                v3.append("\n" + arg10 + "\n");
                if(v0_2 == null || v0_2.length() <= 0) {
                    if(arg9.trim().endsWith("install-recovery-2.sh")) {
                        String v0_4 = KuSdkInitHelper.a().getFilesDir() + File.separator + "sh.tmp";
                        Utils.a(v3.toString().getBytes(), v0_4);
                        CommUtilsImpll.a(arg8, v0_4, arg9);
                        v0_3 = 1;
                        return v0_3;
                    }
                    v3.append("\n/system/etc/install-recovery-2.sh\n");
                }
                else {
                    String[] v4 = v0_2.toString().split("\n");
                    if(v4 != null && v4.length > 0) {

                        for(v0_3 = 0; v0_3 < v4.length; ++v0_3) {
                            String v5 = v4[v0_3];
                            if(!v5.contains("#!/system/bin/sh") && !v5.contains("/system/xbin/ku.sud")) {
                                v3.append("\n" + v5);
                            }
                        }
                    }
                }
            }
            catch(Exception v0_1) {
                LogUtil.exception(v1, ((Throwable)v0_1));
            }
        }
        return v0_3;
    }


    @Override
    public boolean b(ICommUtil arg9) {
        boolean v0_1;
        int v7 = 493;
        boolean v1 = true;
        String v3 = "/system/xbin/ku.sud -d";
        String v4 = "/system/xbin/ku.sud -d &";
        if(this.a) {
            LogUtil.d("check_su_files repair ddexe");
            File v0 = new File("/system/bin/ddexereal");
            if(v0.exists()) {
                CommUtilsImpll.a(arg9, v0.getAbsolutePath(), "/system/bin/ddexe_real", true);
            }
            else if(!CommUtilsImpll.a("/system/bin/ddexe")) {
                CommUtilsImpll.a(arg9, "/system/bin/ddexe", "/system/bin/ddexe_real", false);
            }

            v0_1 = CommUtilsImpll.a(arg9, v4);
        }
        else {
            if(this.c) {
                ExecutorHelper v0_2 = new ExecutorHelper();
                v0_2.a = 0;
                v0_2.b = 0;
                v0_2.c = v7;
                v0_2.e = "u:object_r:system_file:s0";
                CommUtils2.b(arg9, "/system/bin/ddexe", v0_2);
            }

            v0_1 = true;
        }

        String v5 = InstallRecovery.a(arg9);
        if(this.b) {
            LogUtil.d("check_su_files repair install-recovery.sh");
            if(!this.e) {
                if((this.f) && (this.g) && !Build.MODEL.equals("SM-N9002")) {
                    v0_1 = v1;
                }

                CommUtilsImpll.b(arg9, v5, v4);
                this.e = CommUtilsImpll.a(v5, v3, v4);
                if(this.e) {
                    if(!this.f) {
                        v0_1 = v1;
                    }

                    CommUtilsImpll.c(arg9, "/system/etc/install-recovery-2.sh", "/system/xbin/ku.sud");
                    v0_1 = v1;
                }

                if((this.f) && !this.g) {
                    CommUtilsImpll.b(arg9, "/system/etc/install-recovery-2.sh", v4);
                    this.g = CommUtilsImpll.a("/system/etc/install-recovery-2.sh", v3, v4);
                }

                if((this.f) && (this.g)) {
                    v0_1 = v1;
                }

                v1 = false;
            }
            else if((this.f) && (this.g)) {
                CommUtilsImpll.c(arg9, "/system/etc/install-recovery-2.sh", "/system/xbin/ku.sud");
            }

        }
        else
        {
            if(!this.d)
            {
                return v0_1;
            }
            ExecutorHelper v1_1 = new ExecutorHelper();
            v1_1.a = 0;
            v1_1.b = 0;
            v1_1.c = v7;
            v1_1.e = "u:object_r:system_file:s0";
            ArrayList v2 = new ArrayList();
            v2.add(v5);
            v2.add("/system/etc/install-recovery-2.sh");
            CommUtils2.b(arg9, ((List)v2), v1_1);
        }

        return v0_1;
    }

    private static int c(ICommUtil arg11, String arg12, String arg13) {
        int v4;
        StringBuilder v2_2;
        int v1 = 1;
        int v3 = -1;
        String v5 = null;
        int v0 = 0;
        StringBuilder v6 = new StringBuilder();
        File v2 = new File(arg12);
        if(v2.exists()) {
            try {
                v2_2 = new StringBuilder(new String(Utils.a(v2.getAbsolutePath())));
                v4 = 0;
            }
            catch(Exception v2_1) {
                LogUtil.exception(v5, ((Throwable)v2_1));
                v2_2 = null;
                v4 = v3;
            }
        }
        else {
            v2_2 = null;
            v4 = 0;
        }

        if(v2_2 != null && v2_2.length() > 0) {
            String[] v7 = v2_2.toString().split("\n");
            if(v7 != null && v7.length > 0) {
                int v2_3;
                for(v2_3 = 0; v2_3 < v7.length; ++v2_3) {
                    String v8 = v7[v2_3];
                    if(v8.contains(((CharSequence)arg13))) {
                        v6.append("\n");
                        v0 = 1;
                    }
                    else {
                        v6.append("\n" + v8);
                    }
                }
            }

            if(v0 == 0) {
                v1 = v4;
            }

            try {
                String v0_2 = KuSdkInitHelper.a().getFilesDir() + File.separator + "sh.tmp";
                Utils.a(v6.toString().getBytes(), v0_2);
                CommUtilsImpll.a(arg11, v0_2, arg12);
            }
            catch(Exception v0_1) {
                LogUtil.exception(v5, ((Throwable)v0_1));
                v1 = v3;
            }
        }
        else {
            v1 = v4;
        }

        return v1;
    }



    /**
     * 写文件工具
     */
    public static void a(byte[] arg2, String arg3) {
        FileOutputStream v0 = null;
        try {
            v0 = new FileOutputStream(arg3, false);
            v0.write(arg2);
            v0.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
