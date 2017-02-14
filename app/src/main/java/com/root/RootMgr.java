package com.root;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.demo.entity.Entity;
import com.demo.utils.Const;
import com.demo.utils.LogUtil;
import com.demo.utils.SpfUtils;
import com.demo.utils.Utils;
import com.kingroot.sdk.root.CommonLog;
import com.mars.MarsRoot;
import com.root.helper.AbsJavaProcessImpla;
import com.root.dao.IJavaProcessa;
import com.root.dao.IJavaProcessh;
import com.root.helper.AbsJavaProcessImpla;
import com.root.helper.JavaProcess;
import com.root.helper.JavaProcessk;
import com.root.helper.ThreadLocalWeakRef;
import com.root.util.ApkInstallUtil;
import com.root.util.RootUtils3;

import java.io.File;
import java.lang.ref.WeakReference;

import http.demo.SolutionHelpers;


/**
 * Created by Administrator on 2016/11/7.
 */

public class RootMgr {


    public static Context mConext;
    public static String msg;
    protected static Entity entity;
    public static boolean control;
    private static CommonLog commlog;
    private static long g;
    private static Handler handler;

    static {
        control = false;
    }

    private CommExecutThread executThread;

    //方案执行列表  = 798_691_800_795_796_ 第一次
    // 方案执行列表 = 800_798_691_795_796_ 第二次

    public String[] sids = new String[]{"798", "691", "800", "795", "796"};

    //    public String[] test = new String[]{"t1", "t2", "t3", "t4", "t5"};
    public RootMgr(Context context, Entity mentity, Handler mHanlder) {
        mConext = context;
        entity = mentity;
        handler = mHanlder;
        commlog = new CommonLog(mConext);
    }

    public static boolean startKDFile(IJavaProcessa arg10) {
        LogUtil.d("startKDFILE -- ");
        boolean v0 = true;
        int v2 = 1;
        try {
            long v4 = System.currentTimeMillis();
            if (v4 - g <= 0) {
                v2 = 0;
            } else if (v4 - g < 3000) {
                v2 = 1;
            } else {
                v2 = 0;
            }
            if (arg10 == null || v2 != 0) {
                v0 = false;
            } else {
                String v4_1 = entity.file + File.separator + "kd";
                JavaProcessk v2_1 = JavaProcessk.a(v4_1, 3);
                if (v2_1 == null) {
                    arg10.d("chmod 6755 " + v4_1);
                    arg10.d("chown 0.0 " + v4_1);
                    LogUtil.d("start kd : ");
                    arg10.d(String.valueOf(v4_1) + " -d");
                    JavaProcessk.c = v4_1;
                    v2_1 = JavaProcessk.a(v4_1, 3);
                }
                if (v2_1 != null) {
                    ((IJavaProcessh) v2_1).c();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.exception("start kd exception ", e);
        }
        return v0;
    }

    private static SolutionHelpers[] helperses;

    /**
     * http添加解决方案到执行函数接口
     *
     */
    public static void setSolutionHelpers(SolutionHelpers[] solutionHelpers)
    {
        helperses = solutionHelpers;
    }


    private  static boolean isSucc=false;
    public  static  void setIsSucc(boolean arg)
    {
        isSucc = arg;
    }

    /**
     * execute root
     *
     * 根据反馈回去的resultCode ==0?0:1
     * 0:suc
     * 1:failed
     */
    //----------------------------备份---------------------
    public int execteRoot(int arg) {//arg=3
        LogUtil.d("Execute Root ~~~ 参数是：" + arg);
        int v2 = 200027;
        int v4 = -1;
        AbsJavaProcessImpla v5_1;
        int v6_1;
        int v1_1;
        IJavaProcessh v6_3 = null;
        boolean v2_2;
        int v16;
        int v15;
        JavaProcess v17;
        boolean v10_1;
        int v19 = 0;
        JavaProcess v4_1;
        int resultCode = -1;
        AbsJavaProcessImpla v4_2;
        JavaProcess v9 = null;
        AbsJavaProcessImpla v8 = null;
        JavaProcess v6;
        boolean v14 = (arg & 1) == 1 ? true : false;
        msg = RootUtil.getSuInfo();//检查su文件
        if (msg == null)
        {
            msg = "";
        }
        System.nanoTime();
        int v10 = 1;
        //test
        int len = sids.length;//修改了这里，
        //execute
        if (helperses == null) {
            LogUtil.e("没有可以执行的解决方案，请检查网络或者确认是否初始化了");
            return resultCode;
        }
        int length = helperses.length;
        SpfUtils.markSdkStart(mConext, msg, len, v14);
        LogUtil.d("记录文件生成 "+"\t ,solution length ="+len);
        int indx = 0;
        while (indx < length)
        {
            LogUtil.e("index ="+indx);
            if (indx == length)
            {
                LogUtil.e("执行到了最后一个 ");
            }
            /**
             * 读取每一个执行文件
             在这个方法体中，去读取每一个文件，执行每一个文件
             这里采用的是数组的方式，执行每一个文件，
             在选择执行的时候，需要有一个状态标识，选择不同的XSolution类
             flag:
             1.2:XSolution ===>对应的是vrtoot.jar 这是一个classes.dex文件。对应着loadCLasss就可以使用反射的方式（之前的oppo手机，采用的是这个方案）
             3.4: 根据生成的配置文件，然后启动执行 (这里的三星的手机，采用这个方案) 在这个解决方案中的kcert.jar是一个classes.dex文件
             5.：这里是安装对应的osc.apk文件，同样来自的是网络适配获取，对应着读取就可安装, 然后执行相关的方法(目前还没有执行这个方案的时候出现出这个情况)
             *
             *  */
            //test使用
//            String sid = sids[indx];//测试只是用一个文件的情况下
            //执行使用
            SolutionHelpers solutionHelpers = helperses[indx];
            //soltionHelpers.n = data/data/com.demo/app_krsdk/jars/sid
            LogUtil.e("解决方案的执行路径：" + solutionHelpers.n);
            ///
            LogUtil.d("执行的方案: sid =" + solutionHelpers.b);
            if (indx + 1 == len) {
                LogUtil.d("next_execte_solution_id indx = len 最后一个 " + indx);
                SpfUtils.a(this.mConext, "next_execute_solution_id");
            } else {
                LogUtil.d("index =" + indx + " " + "next_execte_solution_id " + helperses[indx + 1].b);
                //记录下一个要执行的解决方案
                SpfUtils.a(this.mConext, "next_execute_solution_id", helperses[indx + 1].b);
            }
            //获取Root实例类工具执行root,记得加上标志位,同时使用weekReference使用
            //switchCracker的int tag =solutionHelpers.l interface_type 的值
            FooRoot cracker = switchCracker(mConext, entity, solutionHelpers, handler);
            if (cracker == null)
            {
                LogUtil.loge("没有找到cracker使用");
                return resultCode;
            } else {
                //设置标志位i
                cracker.a(v14);
                LogUtil.d("init solution idx = " + indx + ", sid = " + solutionHelpers.b + " 给父类的标志位是：" + v14);
                boolean flag = cracker.beforeRoot();//准备文件
                MarsRoot.setEntity(entity);
                if (flag)
                {//开始执行
                    LogUtil.d(" 准备文件ok ");
                    commlog.recordExecutInfo("KRSDK_Solution_Execute_Begin", 0, "", "", handler, new Object[]{solutionHelpers.b});
                    RootLog rootLog = new RootLog();
                    startRootThread(mConext, solutionHelpers.b, new WeakReference(cracker));
                    //记录执行文件
                    SpfUtils.mark(mConext, solutionHelpers.b, indx, solutionHelpers.d);//记录执行的
                    //这里的内部方法应该需要在此调试 ，
                    resultCode = cracker.a(rootLog);//启动执行,把kd文件释放到linux内，返回kd文件的路径。供给下一步调用cracker.b()就是执行这个文件
                    LogUtil.d("执行获取临时root的 process resultcode[ " + resultCode + " ]");
                }
                stopThread();//
                if (resultCode == 0)
                {
                    LogUtil.d("resultCode == 0");
                    AbsJavaProcessImpla v18 = cracker.b();//获取解决方案的对象.调用javaprocessk 执行连接kd文件，生成javaprocessk 对象，关键函数1,任然是连接kd文件
                    if (v18 != null)
                    {
                        LogUtil.d("AbsJavaProcessImpla!= null");
                        v18.c(solutionHelpers.b);
                        v1_1 = 1;
                        v2 = 0;
                        LogUtil.d("方案临时Root成功：sid = " + solutionHelpers.b);


                    } else {
                        LogUtil.d("AbsJavaProcessImpla == null");
                        v2 = 1;
                        v1_1 = 0;
                    }
                    if (v14) {
                        LogUtil.d("v14 =true");

                        v4 = ApkInstallUtil.a();//调用第三个executorRunner
                        v6 = JavaProcess.a(20000, 10);//测试执行su文件能否成功~~~
                        if (v6 != null) {
                            ((AbsJavaProcessImpla) v6).c(solutionHelpers.b);
                            v15 = 1;
                            v16 = 0;
                        } else {
                            v15 = v1_1;
                            v16 = v2;
                        }
                        boolean v1_3 = ApkInstallUtil.a(v6);//读取相关的状态变化

                        if (v18 != null) {
                            if (v4 == 0 && (v1_3)) {
                                v10_1 = v1_3;
                                v17 = v6;
                                v6_1 = 0;
                            }
                            LogUtil.d("ready to read RootUtils3 ");
                            //安装su 和superuser.apk
                            /**
                             *
                             * 还是安装的是文件是kingroot的su 和apk
                             * 这里选择自己的
                             * mysu
                             * MySuperuser.apk文件
                             * **/
                            if (RootUtils3.a(
                                    this.mConext,
                                    v18,
                                    String.valueOf(this.entity.file.getAbsolutePath()) + "/mysu",
                                    String.valueOf(this.entity.file.getAbsolutePath()) + "/MySuperuser.apk"))
                            {
                                LogUtil.d("read RootUtils3 ok");
                                v1_1 = ApkInstallUtil.a();
                                v4_1 = JavaProcess.a(15000, 1);
                                v2_2 = ApkInstallUtil.a(((IJavaProcessh) v4_1));
                            }
                            else
                            {
                                LogUtil.d("read RootUtils3 failed");
                                v2_2 = v1_3;
                                v1_1 = v4;
                                v4_1 = v6;
                            }
                            v6_1 = 1;
                            v10_1 = v2_2;
                            v17 = v4_1;
                            v4 = v1_1;
                        } else {
                            v10_1 = v1_3;
                            v17 = v6;
                            v6_1 = 0;
                        }
                        v1_1 = v16;
                        v2 = v4;
                        v4_1 = v17;
                    }//v14
                    else {
                        LogUtil.d("v14 =false");
                        v15 = v1_1;
                        v1_1 = v2;
                        v2 = v4;
                        v4_1 = v9;
                    }
                    if (v15 != 0)
                    {
                        LogUtil.d("记录成功方案，移除下一个待执行方案标记");

                        SpfUtils.a(this.mConext, "solution_success_id", solutionHelpers.b);
                        SpfUtils.a(this.mConext, "next_execute_solution_id");
                        v6 = v4_1;
                        v4_2 = v18;
                        LogUtil.d("sid = " + solutionHelpers.b + ", exploitRet = " + v19 + ", rootCode = "+ v2 + ", tmpShell = " + v4_2 + ", suShell = " + v6);
                    }
                    v6 = v4_1;
                    v4_2 = v18;
                    /*****************************/
                    cracker.a(v18);//这里开始安装了apk文件
                    if (isSucc)
                    {
                        LogUtil.e("已经安装好自己的su superuser.apk文件，不需要其他的辅助功能");
                        resultCode =0;
                        Message msg = new Message();
                        msg.obj="已经安装了自己的文件，可以尝试调用mysu文件获取root权限";
                        msg.what= Const.GET_TMP_SHELL;
                        handler.sendMessage(msg);
                        SpfUtils.set(mConext,"succ",isSucc);
                        break;
                    }

                    /**************************/
                    if (v1_1 != 0)
                    {
                        LogUtil.d("v1_1 != 0");
                        v1_1 = v19;
                        v8 = v4_2;
                        v9 = v6;
                        ++indx;
                        v10 = v1_1;
                    }

                    v5_1 = v4_2;
                    if (v5_1 != null && (arg & 2) == 2) {
                        startKDFile(((IJavaProcessa) v5_1));
                        LogUtil.d("startKDFile ");
                        if (checkTmpShell())
                        {
                            LogUtil.e("已获取到root权限，退出循环");
                            handler.sendEmptyMessage(Const.GET_TMP_SHELL);
                        }
                        break;
                    }

                    if (v19 != 0) {
                        v6_3 = null;
                    } else if (v6 == null) {
                        AbsJavaProcessImpla v6_2 = v5_1;
                    }
                } else {
                    LogUtil.d("读取进程中的返回状态值 不是 0： " + resultCode);
                    LogUtil.e("出现失败的exploit " + solutionHelpers.b);
                    Utils.recordFailedExploit(mConext, solutionHelpers.b);
                    v2 = v4;
                    v6 = v9;
                    v4_2 = v8;
                    ++indx;
                }
            }
        }
        return  resultCode;
    }


    /***************************************************/
    /**
     * 在这里检测是否获取到临时root权限。
     * 如果获取到了临时的root权限，那么，就开始执行相关的root功能
     * 完善root功能
     */
    private boolean checkTmpShell() {
        return AbsJavaProcessImpla.b(JavaProcess.flag);

    }

    /**
     * 开始执行root记录信息，
     * 可能被打断的情况，在这里的线程记录
     */
    private void startRootThread(Context context/*, Handler handler*/, String sid, WeakReference weakReference) {
        long v2 = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        LogUtil.d("bootTime = " + v2);
        SpfUtils.a(context, "executing_sid_time", String.valueOf(sid) + "\t" + v2);
        control = true;
        stopThread();
        CommExecutThread tmp = new CommExecutThread(context/*, handler*/, sid, weakReference);
        executThread = tmp;
        tmp.start();
    }

    private void stopThread() {
        if (executThread != null) {
            executThread.interrupt();
            executThread = null;
        }
    }

    /**
     * 选择不同的cracker
     * solutionHelpers.l =intertype 字段，是一个int值
     */
    private FooRoot switchCracker(Context context, Entity entity, SolutionHelpers solutionHelpers, Handler handler /*String sid, int flag*/) {
        FooRoot fooRoot = null;
        ThreadLocalWeakRef.c();
        switch (solutionHelpers.l) {
            case 1:
                fooRoot = new JavaRoot(context, entity, solutionHelpers, handler/*sid*/);
                break;
            case 3:
            case 4:
                fooRoot = new JavaRoot2(context, entity, solutionHelpers, handler /*sid*/);
                break;
            case 5:
                fooRoot = new JavaRoot3(context, entity, solutionHelpers, handler /*sid*/);
                break;
            default:
                LogUtil.loge("没有更多的cracker");
                ThreadLocalWeakRef.a(7003, "interface_type=" + solutionHelpers.l);
                break;
        }
        LogUtil.d("getCracker =" + fooRoot);
        return fooRoot;
    }

}
