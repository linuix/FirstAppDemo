package com.root.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;

import com.demo.utils.Const;
import com.demo.utils.LibSoHelper;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.root.dao.ICommUtil;
import com.root.executor.KuSdkSilentCleaner;
import com.root.executor.RootUtils4;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Administrator on 2016/11/8.
 */

public class KuSdkInit {

    private static KuSdkInitHelper a;

    static {
        KuSdkInit.a = null;
    }
    public static void a(ICommUtil arg4, Context arg5, String su) {
        LogUtil.d("KuSdkInit ");
        if (KuSdkInit.a == null) {
            KuSdkInit.a = new KuSdkInitHelper();
        }
        KuSdkInitHelper.a(arg5, su);
        try {
            KuSdkInit.a(arg4, arg5, "libxy", SDkLib.a().getAbsolutePath(), false);
            KuSdkInit.a(arg4, arg5, "ktools", SDkLib.b().getAbsolutePath(), true);
            int v0_1 = Utils.loadSo(String.valueOf(SDkLib.a().getAbsolutePath()) + File.separator + "libxy.so");
            if (v0_1 != 0) {
                LogUtil.loge("KuSdkInit,  Extract: load KRSDK so fail, ret = " + v0_1);
            }
            if (!SelinuxUtils.a()) {
                return;
            }
            if (KuSdkInit.b(arg4, arg5, String.valueOf(SDkLib.b().getAbsolutePath()) + File.separator
                    + "ktools")) {
                return;
            }
            LogUtil.loge("KuSdkInit , init|copyExecutableFileToDataLibDir...fail");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.exception("kusdkInit exception ", e);
        }
    }

    /**
     * check the daemon process & clean-up the processs
     */
    public static void a(ICommUtil arg1) {
        if (arg1 != null && (KuSdkInit.a())) {
            KuSdkSilentCleaner cleaner = new KuSdkSilentCleaner(arg1);
            cleaner.setTag(Const.mars_tag);//设置tag,采用自己的文件
            cleaner.a();
        }
    }


    /***
     * 读取assets目录下的文件 libxy,ktools
     * <p>
     * ktools is scriptFile in the linux
     */
    private static void a(ICommUtil arg6, Context arg7, String arg8, String arg9, boolean arg10) {
        FileOutputStream v1_1 = null;
        Closeable v2;
        File v5 = null;
        ZipInputStream v4 = null;
        LogUtil.d("KuSdkInit,RootExecutorFactory.extractFileToWorkDir()");
        AssetManager v0 = arg7.getAssets();
        if (LibSoHelper.check(v0, arg8, arg9))
        {
            try
            {
                InputStream v3 = v0.open(arg8);
                File v0_2 = new File(arg9);
                v0_2.mkdirs();
                v4 = new ZipInputStream(v3);
                while (true)
                {
                    ZipEntry v1 = v4.getNextEntry();
                    if (v1 == null)
                    {
                        Utils.close(v1_1);
                        LogUtil.d("extract finished ");
                        if (arg10)
                        {
                            arg6.a("chmod 0764 " + new File(arg9, arg8).getAbsolutePath());
                        }

                        /**
                         *
                         * 读取完成之后，调用进程读取，最后退出这个循环
                         * */
                        break;
                    } else if (v1.isDirectory()) {
                        new File(v0_2, v1.getName()).mkdir();
                        LogUtil.d("FileUtil ,mkDir : " + v0_2.getAbsolutePath());
                        continue;
                    } else {
                        v5 = new File(v0_2, v1.getName());
                        v2 = null;
                        v5.delete();
                        v1_1 = new FileOutputStream(v5);
                        Utils.writeFile(((InputStream) v4), ((OutputStream) v1_1));
                        v1_1.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sdklib文件夹下的文件
     */
    private static boolean b(ICommUtil arg5, Context arg6, String arg7)
    {
        boolean v0 = true;
        String v1 = String.valueOf(Environment.getDataDirectory().getAbsolutePath()) + File.separator+ "data-lib" + File.separator + arg6.getPackageName();
        File v2 = new File(arg7);
        v1 = String.valueOf(v1) + File.separator + v2.getName();
        File v3 = new File(v1);
        try
        {
            //需要校验一下md5的值，这里直接省略
            LogUtil.d(" 需要MD5校验 fileName=" +v3.getName());
            if (!v3.canExecute())
            {
                LogUtil.e("检测文件是否能够被执行  fileName =" +v3.getName());
                LogUtil.d("file can't execut ,start other way " + v3.getName() + " path " + v3.getAbsolutePath());
                Boolean objcet = (Boolean) WakeLockMgr.a(arg5, new ChPropertyUtils(), new Object[]{arg7, v1});
                return objcet.booleanValue();
            }
            return v0;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.exception("kusdkinit chech file iscanExecutable", e);
            return v0;
        }
    }

    /**
     * KuSdkInit installSync
     */
    public static boolean a(ICommUtil commUtil, String mysuperuser) {
        LogUtil.d("KuSdkInit installSync");
        boolean v0 = false;
        if (commUtil == null) {
            LogUtil.loge("KuSdkInit installSync|rootShell is null");
        } else if (KuSdkInit.a()) {
            LogUtil.d("KuSdkInit installSync|kuApkPath..." + mysuperuser);
            if (TextUtils.isEmpty(((CharSequence) mysuperuser))) {
                LogUtil.loge("KuSdkInit installSync|kuApkPath is null");
            } else if (!commUtil.a()) {
                LogUtil.loge("KuSdkInit installSync|no root perm");
            } else if (KuSdkInitHelper.b() == null) {
                LogUtil.d("KuSdkInit installSync|KuSdkConfig.getNewSuPath()...null");
            } else if (!RootUtils4.a(commUtil)) {
                LogUtil.d("KuSdkInit installSync|CheckExecutor.checkSync...fail!");
            } else {
                /*sdk_gt18 v1 = new sdk_gt18(arg5);
                int v2 = v1.sdk_gt18(KuSdkInitHelper.sdk_gt18(), arg6);
                if(v2 != 0) {
                    i.sdk_gt18("KuSdkInit", "installSync|kuInstaller.installKU...install ret:" + v2);
                }
                else {
                    v0 = v1.sdk_gt18();
                }*/
                v0 = true;
                LogUtil.d("启动activity 成功 v0 = "+v0);
            }
        }
        return v0;
    }

    private static boolean a()
    {
        boolean v0 = false;
        if (KuSdkInit.a == null)
        {
            LogUtil.loge("KuSdkInit hasInitOk|sKuSdkConfig...null");
        } else if (KuSdkInitHelper.a() == null)
        {
            LogUtil.loge("KuSdkInit   hasInitOk|KuSdkConfig.getSdkContext()...null");

        } else
        {
            v0 = true;
        }
        LogUtil.d("KuSdkInit   hasInitOk " + v0);
        return v0;
    }


}
