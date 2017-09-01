package com.root;

import android.content.Context;
import android.os.Handler;

import com.demo.entity.Entity;
import com.demo.utils.LogUtil;
import com.demo.utils.SpfUtils;
import com.kingroot.sdk.root.CommonLog;
import com.root.helper.ThreadLocalWeakRef;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import http.demo.SolutionHelpers;

/**
 * Created by Administrator on 2016/11/7.
 */

public class JavaRoot2 extends FooRoot {
    private Process process;
    private String cfg;
    private static boolean i;

    /**
     * 父类的构造函数
     *
     * @param context
     * @param arg
     * @param
     */
    public JavaRoot2(Context context, Entity arg, SolutionHelpers solutionHelpers, Handler handler  /*String sid*/) {
        super(context, arg, solutionHelpers, handler);
        this.i = true;
        this.cfg = String.valueOf(this.play) + "/krcfg.txt";
    }

    public static void setI(JavaRoot2 arg) {
        i = false;
    }

    public int a1(RootLog arg17) {//修改成为test，这个原始的代码
        int tag = 0;
        BufferedReader v13;
        String v9;
//        Utils.clearThreadLocal();
        ThreadLocalWeakRef.createThreadLocal();
        boolean v1 = false;
        int v3 = 0;
        boolean v9_1 = true;
        int v8 = v3;
        StringBuilder v10 = new StringBuilder();
        long v11 = System.nanoTime();
        String v2 = this.entity.a(new String[]{"play", "krmain"});
        File v4 = new File(this.play);
        boolean v2_1;
        try {
            this.process = new ProcessBuilder(new String[]{v2, "-k", this.cfg}).redirectErrorStream(true).directory(v4).start();
            new ExecutThread2(this, process).start();
            SpfUtils.a(mContext, "EMID_KRSDK_EXReport_Info", new String[]{solutionHelpers.sindex, "154", "", RootMgr.msg, "0", "1"});
            arg17.a(" [ onRoot() start sid = " + solutionHelpers.sindex + " ]");
            v9 = String.valueOf(solutionHelpers.sindex) + ".stdout : ";
            v13 = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
            while (true) {
                v2_1 = v13.ready();
                if (v2_1) {
                    LogUtil.d("[ v13.ready() = " + v2_1 + " ] ");
                    break;
                } else if (this.i) {//这个值在process.waitFor()完成之后，就会改变为false,那么不会走这个位置
                    LogUtil.loge("this.url " + this.i);
                    break;
                } else {
                    v8 = v3;
                    v9_1 = v1;
                    LogUtil.loge("else ====== ");
                    //--------
                }

            }
            //-----------
            //if else保留位置
            if (!v2_1) {
                long v4_1 = 500;
                Thread.sleep(v4_1);
            } else {
                String v5 = v13.readLine().trim();
                LogUtil.loge(String.valueOf(v9) + v5);
                arg17.a(v5);
                v2 = RootUtils2.a(v5, "[et] KRS|FT PARAMS:");
                if (v2 != null) {
                    //写本地文件
                }
                v2 = RootUtils2.a(v5, "KRS|STAT|KD:");
                if (v2 != null) {
                    LogUtil.d("真实的KD路径：" + v2);

                }
                String v4_2 = RootUtils2.a(v5, "krerrcode:");
                if (v4_2 != null) {
                    String[] v1_2 = v4_2.split(",");
                    v3 = v1_2 == null || v1_2.length <= 0 || v1_2[0] == null || !"0".equals(v1_2[0].trim()) ? 1 : 0;
                    SpfUtils.markExploitRet(mContext, v3, v4_2);
                    LogUtil.loge("catch ::: errCodes = " + v4_2);
                    if (v3 != 0) {
                        v9_1 = true;
                        v8 = v3;
                        v11 = (System.nanoTime() - v11) / 1000000;
                        arg17.a("onRoot() end sid = " + solutionHelpers.sindex + ", catchResult = " + v9_1 + ", exploitRet = "
                                + v8 + ", childDuingTime = " + v11);
                        LogUtil.d("执行完成1！ catchResult = " + v9_1);
                        String[] v13_1 = SpfUtils.e(mContext, "EMID_KRSDK_EXReport_Info");
                        SpfUtils.removeMarsRootSharedPreferences(mContext, "EMID_KRSDK_EXReport_Info");
                        if (v13_1.length >= 5) {
                            CommonLog commonLog = new CommonLog(mContext);
                            int v2_3 = 200039;
                            v3 = !v9_1 || v8 != 0 ? 1 : 0;
                            commonLog.recordEMID(v2_3, v3, "0", "catchResult=" + v9_1 + ", errCode=" + v8, null, new Object[]{v13_1[0], v13_1[1], v13_1[2], v13_1[3], Long.valueOf(v11), Integer.valueOf(1)});
                        }
                        if (v9_1) {
                            if (v8 == 0) {
                                tag = 0;
                            } else {
                                LogUtil.d("执行完成！");
                                tag = 1;
                            }
                            return tag;
                        } else {
                            LogUtil.loge("Exe fail, EOF");
                            arg17.a("onRoot() not catchResult : ");
                        }
                    }
                }
            }
            ///
            v11 = (System.nanoTime() - v11) / 1000000;
            arg17.a("onRoot() end sid = " + solutionHelpers.sindex + ", catchResult = " + v9_1 + ", exploitRet = "
                    + v8 + ", childDuingTime = " + v11);
            LogUtil.loge("执行完成1！ catchResult = " + v9_1);
            String[] v13_1 = SpfUtils.e(mContext, "EMID_KRSDK_EXReport_Info");
            SpfUtils.removeMarsRootSharedPreferences(mContext, "EMID_KRSDK_EXReport_Info");
            if (v13_1.length >= 5) {
                CommonLog commonLog = new CommonLog(mContext);
                int v2_3 = 200039;
                v3 = !v9_1 || v8 != 0 ? 1 : 0;
                commonLog.recordEMID(v2_3, v3, "0", "catchResult=" + v9_1 + ", errCode=" + v8, null, new Object[]{v13_1[0], v13_1[1], v13_1[2], v13_1[3], Long.valueOf(v11), Integer.valueOf(1)});
            }
            if (v9_1) {
                if (v8 == 0) {
                    tag = 0;
                } else {
                    LogUtil.d("执行完成！");
                    tag = 1;
                }
                return tag;
            } else {
                LogUtil.loge("Exe fail, EOF");
                arg17.a("onRoot() not catchResult : ");
            }
            return tag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

    /**
     * 重写执行的流程
     */
    public int test(RootLog rootLog) {

        ThreadLocalWeakRef.createThreadLocal();

        long time = System.nanoTime();

        String cmd = entity.a(new String[]{"play", "krmain"});

        File v4 = new File(play);
        String v9 = null;
        BufferedReader v13 = null;

        int v8 = 0;
        int v3 = 0;
        int v1_4 = 0;

        StringBuilder v10 = new StringBuilder();
        boolean v9_1 = true;
        boolean v1 = false;
        boolean v2_1 = false;
        try {
            this.process = new ProcessBuilder(new String[]{cmd, "-k", this.cfg}).redirectErrorStream(true).directory(v4).start();
            /**
             * 创建进程失败？？
             * */
            if (process == null) {
                LogUtil.d("process null ");
                return -1;
            }
            ExecutThread2 thread2 = new ExecutThread2(this, process);//这里的线程是读取进程中的信息
            thread2.start();
            LogUtil.d("onRoot() start sid =" + solutionHelpers.sindex);
            v9 = String.valueOf(solutionHelpers.sindex) + ".stdout: ";
            v13 = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (v13 == null) {
                LogUtil.d("nullptr ");
                return 1;
            }
            while (true) {
                v2_1 = v13.ready();
                if (v2_1) {
                    break;
                } else if (i) {
                    break;
                } else {
                    v9_1 = false;
                }
            }
        } catch (Throwable v) {
            LogUtil.exception("javaRoot2 execut Exception ", v);
        }
        if (!v2_1) {
            try {
                Thread.sleep(500);
                LogUtil.d("sleep(500)");
                while (true) {
                    v2_1 = v13.ready();
                    if (v2_1) {
                        break;
                    } else if (i) {
                        break;
                    } else {
                        v9_1 = false;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                LogUtil.exception("javaRoot2 thread interruption", e);
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.exception("javaRoot2 thread IOException", e);
            }
        } else {
            v1_4 = doSomething(rootLog, time, v9, v13, v8, v10, v9_1, v1);
        }
        v1_4 = doSomething(rootLog, time, v9, v13, v8, v10, v9_1, v1);
        return v1_4;
    }

    private int doSomething(RootLog rootLog, long time, String v9, BufferedReader v13, int v8, StringBuilder v10, boolean v9_1, boolean v1) {
        String cmd;
        int v3;
        boolean v2_1;
        int v1_4 = 0;
        try {
            String v5 = v13.readLine().trim();
            LogUtil.d(String.valueOf(v9) + v5);
            rootLog.a(v5);
            cmd = RootUtils2.a(v5, "[et] KRS|FT PARAMS:");
            if (cmd != null) {
                LogUtil.d("EMID_KRSDK_EXReport_Info CommLog.record");
            }

            cmd = RootUtils2.a(v5, "KRS|STAT|KD:");
            if (cmd != null) {
                LogUtil.d("在JavaRoot2 真实的kd path: " + cmd);
                SpfUtils.put(mContext, "REAL_KD_PATH", cmd);
            }
            String v4_2 = RootUtils2.a(v5, "krerrcode:");
            if (v4_2 != null) {
                String[] v1_2 = v4_2.split(",");
                v3 = v1_2 == null || v1_2.length <= 0 || v1_2[0] == null || !"0".equals(v1_2[0].trim()) ? 1 : 0;

                LogUtil.d("记录exploitRet markExploiteRet");
                LogUtil.d("catch ::: errCodes =  + v4_2");
                if (v3 != 0) {
                    v9_1 = true;
                    v8 = 0;
                    time = (System.nanoTime() - time) / 1000000;
                    LogUtil.d("onRoot() end sid = " + solutionHelpers.sindex + ", catchResult = " + v9_1 + ", exploitRet = "
                            + v8 + ", childDuingTime = " + time);
                    LogUtil.d("执行完成！ catchResult = " + v9_1);

                    String[] v13_1 = SpfUtils.e(mContext, "EMID_KRSDK_EXReport_Info");

                    if (v13_1.length >= 5) {
                        LogUtil.d("log need upload ");
                        v3 = !v9_1 || v8 != 0 ? 1 : 0;
                    }
                }
                //test
                v1 = true;
            }

            if (!v1) {
                //循环
                while (true) {
                    v2_1 = v13.ready();
                    if (v2_1) {
                        break;
                    } else if (i) {
                        break;
                    } else {
                        v9_1 = false;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            ThreadLocalWeakRef.a(7011, v10.toString(), e);
            LogUtil.e("onRoot() exception : " + ThreadLocalWeakRef.b());
            v1_4 = 1;
        }

        if (v9_1) {
            if (v8 == 0) {
                v1_4 = 0;
            } else {

                ThreadLocalWeakRef.a(v8, v10.toString());
                LogUtil.d("执行完成！");
                v1_4 = 1;
            }

            return v1_4;//v1_4;
        } else

        {
            ThreadLocalWeakRef.a(7010, "Exe fail, EOF");
            LogUtil.e("onRoot() not catchResult : " + ThreadLocalWeakRef.b());
        }
        return v1_4;
    }

//------------

    /**
     * 开始，初始化完成，之后把krcfg.txt文件写好，然后调用a(x arg)的方法，执行准备好的文件
     */
    @Override
    public boolean beforeRoot() {
        boolean flag = true;
        int v2;
        if (super.beforeRoot()) {
            try {
                if (f) {
                    v2 = 1;
                } else {
                    v2 = 0;
                }
                //这里是生成文件krcfg.txt文件,并没什么作用，不会出现异常信息
                ///data/data/pkgname/filneame
                RootUtils2.a(new File(this.cfg), new String[]{"ver:00001", "chart:" + this.play +
                        File.separator + "krcert.jar", "mydir:" + this.play, "rmode:" + v2, "kddir:" +
                        this.entity.file.getAbsolutePath(), "apkdir:" + this.play});
                LogUtil.d("生成方案配置文件 " + flag);

                return flag;
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.exception("生成方案配置文件出错 root2", e);
            }
        }
        return false;
    }

    public final void c() {
        if (this.process != null) {
            try {
                LogUtil.loge("ExeRootSolution.destroy() start");
                this.i = false;
                LogUtil.loge("ExeRootSolution.destroy() done");
            } catch (Throwable v0) {
                LogUtil.exception("ExeRootSolution.destroy() throw fileSize", (Exception) v0);
            }
        }
    }


    /**
     * 执行写进程操作
     *
     * 主要功能：
     * 1.设置工作目录在/data/data/pkgName/app_krsdk/play
     * 2.执行可执行文件krmain 这个文件，主要释放一些文件，包括kd文件，这linux 内部，把kd文件释放，这个是一个守护进程的文件
     * 3.在找到kd文件的路径之后，退出该操作。然后往后执行，根据kd路径在Linux 内部的路径，启用这个文件，
     * 4.在获取到kd文件的响应之后，表示获取root权限，往后就是执行脚本root.sh和installRecovery.sh脚本，
     *
     */

    public int a(RootLog arg) {
        int v8 = 0;
        int v1_4 = 0;
        boolean v1 = false;
        boolean v9_1 = true;
        BufferedReader br;
        String v9;
        ThreadLocalWeakRef.createThreadLocal();
        int v3 = 0;
        StringBuilder sb = new StringBuilder();
        long time = System.nanoTime();
        String cmd = entity.a(new String[]{"play", "krmain"});
        File play_dir = new File(play);
        try {
            process = new ProcessBuilder(new String[]{cmd, "-k", cfg}).redirectErrorStream(true).directory(play_dir).start();

            new ExecutThread2(this, process).start();//这里的线程是读取进程中的信息

            SpfUtils.a(mContext, "EMID_KRSDK_EXReport_Info", new String[]{this.solutionHelpers.sindex, "154", "", RootMgr.msg, "0", "1"});
            LogUtil.e("onRoot() start sid = " + this.solutionHelpers.sindex);
            v9 = String.valueOf(this.solutionHelpers.sindex) + ".stdout : ";
            br = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
            while (true) {
                boolean v2_1 = br.ready();
                if (v2_1)
                {
                    //写
                    try {
                        String v5 = br.readLine().trim();
                        LogUtil.e(String.valueOf(v9) + v5);
//                        LogUtil.fileSize("读取到的信息 ： " + v5);
                        cmd = RootUtils2.a(v5, "[et] KRS|FT PARAMS:");
                        if (cmd != null)
                        {
                            SpfUtils.a(this.mContext, "EMID_KRSDK_EXReport_Info", new String[]{this.solutionHelpers.sindex, "154", cmd, new StringBuilder(String.valueOf(RootMgr.msg)).toString(), "0", "1"});
                        }
                        cmd = RootUtils2.a(v5, "KRS|STAT|KD:");
                        if (cmd != null)
                        {
                            LogUtil.e("真实的KD路径：" + cmd);
                            SpfUtils.removeMarsRootSharedPreferences(this.mContext, "REAL_KD_PATH", cmd);
                        }
                        String v4_2 = RootUtils2.a(v5, "krerrcode:");
                        if (v4_2 != null) {

                            String[] v1_2 = v4_2.split(",");
                            v3 = v1_2 == null || v1_2.length <= 0 || v1_2[0] == null || !"0".equals(v1_2[0].trim()) ? 1 : 0;
                            SpfUtils.markExploitRet(this.mContext, v3, v4_2);
                            LogUtil.e("catch ::: errCodes = " + v4_2);

                            //写文件，记录下信息，然后让网络上传日志信息

                            //recordFile
                            //上报日志信息

                            //   HttpMgr.reportRootResult(mContext,handler);
                            LogUtil.e("上报日志信息 ，1111111111");
                            if (v3 != 0) {
                                v9_1 = true;
                                v8 = v3;
                                ///------105start-------
                                try {

                                    time = (System.nanoTime() - time) / 1000000;

                                    LogUtil.e("onRoot() end sid = " + this.solutionHelpers.sindex + ", catchResult = " + v9_1 + ", exploitRet = " + v8 + ", childDuingTime = " + time);

                                    LogUtil.e("执行完成1！ catchResult = " + v9_1);

                                    String[] v13_1 = SpfUtils.e(this.mContext, "EMID_KRSDK_EXReport_Info");

                                    SpfUtils.removeMarsRootSharedPreferences(this.mContext, "EMID_KRSDK_EXReport_Info");
                                    if (v13_1.length >= 5) {
                                        LogUtil.e("上报日志信息，2222222222222");
                                    }

                                } catch (Throwable v1_1) {
                                    v1_1.printStackTrace();
                                    ThreadLocalWeakRef.a(7011, sb.toString(), v1_1);
                                    LogUtil.e("onRoot() exception : " + ThreadLocalWeakRef.b());
                                    v1_4 = 1;
                                }

                                if (v9_1) {
                                    if (v8 == 0) {
                                        v1_4 = 0;
                                    } else {
                                        ThreadLocalWeakRef.a(v8, sb.toString());
                                        LogUtil.e("执行完成！");
                                        v1_4 = 1;
                                    }
                                    LogUtil.e("return * ret ="+v1_4);
                                    return v1_4;
                                } else {
                                    ThreadLocalWeakRef.a(7010, "Exe fail, EOF");
                                    LogUtil.e("onRoot() not catchResult : " + ThreadLocalWeakRef.b());
                                }
                                ///-------105 end --------
                            }
                            v1 = true;
                        }
                        //跳转到308
                        if (!v1) {
                            continue;
                        }
                    } catch (Throwable v) {
                        LogUtil.exception("执行root sdk_gt18(X arg)，写的时候出现异常", v);
                    }
                } else if (i)
                {
                    //sleep
                    try {
                        Thread.sleep(500);
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        continue;
                    }
                } else {
                    //finished
                    v8 = v3;
                    v9_1 = v1;
                    break;
                }
            }

        } catch (IOException v) {
            LogUtil.exception("javaRoot2 execotion", v);
        }
        //执行完成函数
        ///-------------------执行完成----------
        LogUtil.e("quit the while ,and ");
        try {
            time = (System.nanoTime() - time) / 1000000;
            LogUtil.e("onRoot() end sid = " + this.solutionHelpers.sindex + ", catchResult = " + v9_1 + ", exploitRet = " + v8 + ", childDuingTime = " + time);
            LogUtil.e("执行完成1！ catchResult = " + v9_1);
            String[] v13_1 = SpfUtils.e(this.mContext, "EMID_KRSDK_EXReport_Info");

            SpfUtils.removeMarsRootSharedPreferences(this.mContext, "EMID_KRSDK_EXReport_Info");
            if (v13_1.length >= 5) {
                LogUtil.e("上报日志信息，2222222222222");
            }
        } catch (Throwable v1_1) {
            v1_1.printStackTrace();
            ThreadLocalWeakRef.a(7011, sb.toString(), v1_1);
            LogUtil.e("onRoot() exception : " + ThreadLocalWeakRef.b());
            v1_4 = 1;
        }
        if (v9_1) {
            if (v8 == 0) {
                v1_4 = 0;
            } else {
                ThreadLocalWeakRef.a(v8, sb.toString());
                LogUtil.e("执行完成！");
                v1_4 = 1;
            }
            LogUtil.e("return ** ret ="+v1_4);
            return v1_4;
        } else {
            ThreadLocalWeakRef.a(7010, "Exe fail, EOF");
            LogUtil.e("onRoot() not catchResult : " + ThreadLocalWeakRef.b());
        }
        LogUtil.e("return ***** ret ="+v1_4);
        return v1_4;

    }


}
