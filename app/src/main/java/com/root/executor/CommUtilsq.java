package com.root.executor;

import android.os.Build;

import com.demo.process.RetValue;
import com.demo.utils.LogUtil;
import com.root.dao.AbsCommUtila;
import com.root.dao.ICommUtil;
import com.root.process.SDKLibProcessMgr;
import com.root.util.KuSdkInitHelper;
import com.root.util.SelinuxUtils;
import com.root.util.WakeLockMgr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *  更新记录，
 *  这里把su 替换成为了mysu文件，在外部调用的时候，会读取这个文件，同时执行这个文件
 *
 * **/
public final class CommUtilsq extends AbsCommUtila {
    private boolean a;
    private boolean b;
    private boolean c;

    public CommUtilsq() {
        super();
        this.a = false;
        this.b = false;
        this.c = false;
    }


    private static void a(ICommUtil arg7, boolean arg8, boolean arg9) {
        int v6 = 2;
        ArrayList v0 = new ArrayList(8);
        ((List)v0).add(CheckMountUtils.a);
        ((List)v0).add(ChattrUtils.a("/system/bin/sutemp", false));
        ((List)v0).add(ChattrUtils.a("/system/xbin/sutemp", false));
        ((List)v0).add("rm /system/bin/sutemp");
        ((List)v0).add("rm /system/xbin/sutemp");
        if(arg8) {
            ((List)v0).add(ChattrUtils.a("/system/bin/mysu", false));
            ((List)v0).add("rm /system/bin/mysu");
            ((List)v0).add(String.format("ln -s %s %s", "/system/xbin/mysu", "/system/bin/mysu"));
            ((List)v0).add(ChattrUtils.a("/system/bin/mysu", true));
        }

        if(arg9) {
            ((List)v0).add(ChattrUtils.a("/system/xbin/mysu", false));
            ((List)v0).add("rm /system/xbin/mysu");
            ((List)v0).add(String.format("ln -s %s %s", "/system/bin/mysu", "/system/xbin/mysu"));
            ((List)v0).add(ChattrUtils.a("/system/xbin/mysu", true));
        }

        ((List)v0).add(CheckMountUtils.b);
        arg7.a(((List)v0));
    }

    static boolean a(ICommUtil arg4, String arg5) {
        boolean v0 = true;
        if(arg4.a()) {
            boolean v2 = CommUtils2.a(arg5, "/system/xbin/mysu") == 0 ? SDKLibProcessMgr.a("/system/xbin/mysu")
                     : false;
            if(v2) {
                CommUtilsq.a(arg4, true, false);
                return v0;
            }

            if(CommUtilsq.a(arg4, arg5, "/system/xbin/sutemp")) {
                v2 = CommUtilsq.a(arg4, arg5, "/system/xbin/mysu");
            }

            if(v2) {
                CommUtilsq.a(arg4, true, false);
                return v0;
            }

            v2 = CommUtils2.a(arg5, "/system/bin/mysu") == 0 ? SDKLibProcessMgr.a("/system/bin/mysu") :
                    false;
            if(v2) {
                CommUtilsq.a(arg4, false, true);
                return v0;
            }

            if(CommUtilsq.a(arg4, arg5, "/system/bin/sutemp")) {
                v2 = CommUtilsq.a(arg4, arg5, "/system/bin/mysu");
            }

            if(!v2) {
                v0 = false;
            }

            CommUtilsq.a(arg4, false, true);
        }
        else {
            v0 = false;
        }

        return v0;
    }

    private static boolean a(ICommUtil arg2, String arg3, String arg4) {
        int v0 = CommUtilsq.b();
        if((CommUtilsq.a(arg2, arg3, arg4, v0)) && v0 == 3565) {
            long v0_1 = 2000;
            try {
                Thread.sleep(v0_1);
            }
            catch(InterruptedException v0_2) {
                v0_2.printStackTrace();
            }

            if(new File(arg4).exists()) {
                return SDKLibProcessMgr.a(arg4);
            }

            CommUtilsq.a(arg2, arg3, arg4, 493);
        }
        return false;

    }

    private static boolean a(ICommUtil arg6, String arg7, String arg8, int arg9) {
        LogUtil.d("这处理的数据对象是谁？？ arg7="+arg7+"\targ8= "+arg8+"\t arg9="+arg9);
        boolean v2 = false;
        ArrayList v0 = new ArrayList(6);
        ((List)v0).add(CheckMountUtils.a);
        ((List)v0).add(ChattrUtils.a(arg8, false));
        ((List)v0).add("rm " + arg8);
        ((List)v0).add(String.format("cat %s > %s", arg7, arg8));
        ((List)v0).add("chown 0.0 " + arg8);
        ((List)v0).add(String.format("chmod 0%o %s", Integer.valueOf(arg9), arg8));
        ((List)v0).add(ChattrUtils.a(arg8, true));
        ((List)v0).add(CheckMountUtils.b);
        List v4 = arg6.a(((List)v0));
        if(v4 != null && v4.size() == ((List)v0).size()) {
            int v1;
            for(v1 = 0; v1 < v4.size(); ++v1) {
                if(!((RetValue)v4.get(v1)).isSuccess()) {
                    String v0_1 = ((RetValue)v4.get(v1)).stdout;
                    if(!v0_1.startsWith("rm ") && !v0_1.startsWith("mount")) {
                        if(v0_1.startsWith(ChattrUtils.a())) {
                            continue;
                        }
                        return v2;
                    }
                }
            }
            v2 = true;
        }

        return v2;
    }

    public final boolean a(ICommUtil arg7) {
        int v5 = -2;
        boolean v2 = true;
        String v0 = KuSdkInitHelper.b();
        this.b = true;
        this.c = false;
        this.a = false;
        int v3 = CommUtils2.a(v0, "/system/bin/mysu");
        int v0_1 = CommUtils2.a(v0, "/system/xbin/mysu");
        if(v3 != 0 || v0_1 != v5) {
            if(v3 == v5 && v0_1 == 0) {
                LogUtil.e("准备操作mysu 文件");
                this.b = false;
                if(!this.b) {
                    ExecutorHelper v0_2 = new ExecutorHelper();
                    v0_2.a = 0;
                    v0_2.b = 0;
                    v0_2.c = CommUtilsq.b();
                    v0_2.e = "u:object_r:system_file:s0";
                    ArrayList v3_1 = new ArrayList();
                    v3_1.add("/system/bin/mysu");
                    v3_1.add("/system/xbin/mysu");
                    this.a = CommUtils2.a(arg7, ((List)v3_1), v0_2);
                }

                boolean v0_3 = (new File("/sbin/mysu").exists()) || (new File("/vendor/bin/mysu").exists()) || (
                        new File("/system/sbin/mysu").exists()) ? true : false;
                this.c = v0_3;
                if((this.b) || (this.c) || (this.a)) {
                    v2 = false;
                }

                return v2;
            }

            if(v3 != 0) {
                if(!this.b) {
                    ExecutorHelper v0_2 = new ExecutorHelper();
                    v0_2.a = 0;
                    v0_2.b = 0;
                    v0_2.c = CommUtilsq.b();
                    v0_2.e = "u:object_r:system_file:s0";
                    ArrayList v3_1 = new ArrayList();
                    v3_1.add("/system/bin/mysu");
                    v3_1.add("/system/xbin/mysu");
                    this.a = CommUtils2.a(arg7, ((List)v3_1), v0_2);
                }

                boolean v0_3 = (new File("/sbin/mysu").exists()) || (new File("/vendor/bin/mysu").exists()) || (
                        new File("/system/sbin/mysu").exists()) ? true : false;
                this.c = v0_3;
                if((this.b) || (this.c) || (this.a)) {
                    v2 = false;
                }

                return v2;
            }

            if(v0_1 != 0) {
                if(!this.b) {
                    ExecutorHelper v0_2 = new ExecutorHelper();
                    v0_2.a = 0;
                    v0_2.b = 0;
                    v0_2.c = CommUtilsq.b();
                    v0_2.e = "u:object_r:system_file:s0";
                    ArrayList v3_1 = new ArrayList();
                    v3_1.add("/system/bin/mysu");
                    v3_1.add("/system/xbin/mysu");
                    this.a = CommUtils2.a(arg7, ((List)v3_1), v0_2);
                }

                boolean v0_3 = (new File("/sbin/mysu").exists()) || (new File("/vendor/bin/mysu").exists()) || (
                        new File("/system/sbin/mysu").exists()) ? true : false;
                this.c = v0_3;
                if((this.b) || (this.c) || (this.a)) {
                    v2 = false;
                }
                return v2;
            }
            this.b = false;
        }
        else {
            LogUtil.e("准备操作mysu 文件出现失败！！");
            this.b = false;
        }

        return   v2;

    }

    private static int b() {
        int v0 = 493;
        int v1 = 3565;
        if(!Build.MODEL.equals("Coolpad 8720L") && !SelinuxUtils.a()) {
            v0 = v1;
        }
        return v0;
    }

    private static boolean b(ICommUtil arg3, String arg4) {
        boolean v0_1 = false;
        Class v1 = CommUtilsq.class;
        try {
            v0_1 = ((Boolean)WakeLockMgr.a(arg3, new ChUtilss(arg4), new Object[0])).booleanValue();
        }
        catch(Throwable v0) {
        }

        return v0_1;
    }

    public final boolean b(ICommUtil arg6) {
        boolean v0 = true;
        if(this.b) {
            LogUtil.d("check_su_files repair su...cat,chown,chmod,chcon");
            v0 = CommUtilsq.b(arg6, KuSdkInitHelper.b());
            LogUtil.d("check_su_files repair su..." + v0);
        }
        else if(this.a) {
            ExecutorHelper v1 = new ExecutorHelper();
            v1.a = 0;
            v1.b = 0;
            v1.c = CommUtilsq.b();
            v1.e = "u:object_r:system_file:s0";
            ArrayList v2 = new ArrayList();
            v2.add("/system/bin/mysu");
            v2.add("/system/xbin/mysu");
            CommUtils2.b(arg6, ((List)v2), v1);
        }

        if((v0) && (this.c)) {
            LogUtil.d("check_su_files clear other su");
            WakeLockMgr.a(arg6, new ChUtilsr(this), new Object[0]);
        }
        return v0;
    }
}

