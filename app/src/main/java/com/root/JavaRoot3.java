package com.root;

import android.content.Context;
import android.os.Handler;

import com.demo.utils.MyContextWrapper;
import com.demo.entity.Entity;
import com.demo.process.RootProcess;
import com.demo.utils.Const;
import com.demo.utils.InitConfig;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;

import java.io.File;
import java.util.HashMap;
import dalvik.system.DexClassLoader;
import http.demo.SolutionHelpers;

/**
 * Created by Administrator on 2016/11/7.
 */

public class JavaRoot3 extends FooRoot {
    /**
     * 父类的构造函数
     *
     * @param context
     * @param arg
     * @param
     */
    public JavaRoot3(Context context, Entity arg, SolutionHelpers solutionHelpers, Handler handler /*String sid*/) {
        super(context, arg, solutionHelpers,handler);
    }

    public int a(RootLog arg) {
        LogUtil.e("javaroot 3 getShell()");
        int v0_3;
        String v0 = String.valueOf(this.play) + "/osc.apk";
        String v2 = String.valueOf(this.play) + "/lib/armeabi";
        MyContextWrapper contextWrapper = new MyContextWrapper(mContext, v0);
        DexClassLoader v4 = new DexClassLoader(v0, this.play, v2, this.mContext.getClassLoader());
        try {
            Class v0_2 = ((ClassLoader) v4).loadClass("krsdk.OscSolution");
            v0_3 = ((Integer) v0_2.getMethod("root", Context.class, HashMap.class).invoke(v0_2.newInstance(), contextWrapper, null)).intValue();
        } catch (Exception v0_1) {
            v0_1.printStackTrace();
            v0_3 = 1;
        }
        LogUtil.e("osc_ret = " + v0_3);
        return v0_3;
    }
    @Override
    public boolean beforeRoot() {
        super.beforeRoot();
        boolean flag = false;
        File file = null;
        String v3 = String.valueOf(this.play) + "/osc.apk";
        RootProcess process = null;
        try {
            process = new RootProcess("sh");
            process.execut(Const.EXPORT_PATH);
            process.execut("cat " + solutionHelpers.n + " > " + v3);
            process.closeAll();
        } catch (Throwable v0) {
            LogUtil.exception("复制KU异常  - - ", v0);
            if (process != null) {
                process.closeAll();
            }
            return flag;
        }
        try {
            file = new File(String.valueOf(mContext.getCacheDir().getAbsolutePath()) + "/kr");
            Utils.clearPlay(file.getAbsolutePath());
            file.mkdirs();
            process = new RootProcess("sh");
            process.execut(Const.EXPORT_PATH);
            process.execut("cat " + entity.file.getAbsolutePath() + "/superuser.apk > " + file.getAbsolutePath() + "/Kinguser.apk");
            process.closeAll();
            InitConfig.zipFile(file, play);
            flag = true;
            LogUtil.e("javaroot 3 beforeRoot ok " + flag);
        } catch (Throwable v) {
            LogUtil.exception("复制KU异常 -- ", v);
            if (process != null) {
                process.closeAll();
            }
            return flag;
        }
        return flag;
    }
}
