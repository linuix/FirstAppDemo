package com.root;

import android.content.Context;
import android.os.Handler;

import com.demo.entity.Entity;
import com.demo.process.ProcessManager;
import com.demo.process.RootProcess;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.mars.MarsApkUtils;
import com.root.dao.IJavaProcessh;
import com.root.helper.AbsJavaProcessImpla;
import com.root.helper.JavaProcessk;
import com.root.helper.JavaSolutionHelpers;
import com.root.helper.ThreadLocalWeakRef;

import java.io.File;

import http.demo.SolutionHelpers;

/**
 * Created by Administrator on 2016/11/5.
 */

public abstract class FooRoot {

    protected Context mContext;
    public String play;
    protected Entity entity;
    protected Handler handler;
    protected boolean f;
    protected SolutionHelpers solutionHelpers;

    /**
     * 父类的构造函数
     */

    public FooRoot(Context context, Entity arg, SolutionHelpers helpers, Handler mHandler  /*String sid*/)
    {
        mContext = context;
        entity = arg;
        solutionHelpers =helpers;
        handler = mHandler;
        play = arg.getPathByName(new String[]{"play"});
        f = true;
    }

    /**
     * 首先把app_krsdk/play的文件输入到进程中去
     */
    public boolean beforeRoot()
    {
        LogUtil.d("beforeRoo()");
        boolean flag = false;
        //这里首先要确保play文件夹下没有文件，
        boolean ret = Utils.clearPlay(play);
        LogUtil.loge("[ clear ok =?  " + ret + " ]");
        new File(this.play).mkdirs();
//        String v2 = this.entity.sdk_gt18(new String[]{"superuser.apk"});//MySuperuser.apk . 测试使用的代码
        String kinguser = String.valueOf(this.play) + "/Kinguser.apk";
//        String v4 = this.entity.sdk_gt18(new String[]{"superuser.apk"});//superuser.apk
        String superuserApk = String.valueOf(this.play) + "/Superuser.apk";

        /*******************增加自定义的su superuser.apk文件 ****************/
        String myApk = this.entity.getPathByName(new String[]{"superuser.apk"});//superuser.apk

        String v9 = String.valueOf(this.play) + "/superuser.apk";
        String mysu = this.entity.getPathByName(new String[]{"su"});//superuser.apk
        String v11 = String.valueOf(this.play) + "/su";

        /*******************************************************************/
        test(superuserApk);
//        String v6 = this.entity.sdk_gt18(new String[]{"su"});//su
        String su = String.valueOf(this.play) + "/su";//su
        test(su);
        RootProcess process = null;
        try {
            process = new RootProcess("sh");
            process.execut("export PATH=/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin:$PATH");
            process.execut("cat " + myApk + " > " + kinguser);//修改掉使用v8 替换掉v2的文件
            process.execut("cat " + mysu + " > " + su);
            process.execut("cat " + myApk + " > " + superuserApk);
            /*********************增加自定义的su superuser.apk文件******************/
            process.execut("cat " + myApk + " > " + v9);
            process.execut("cat " + mysu + " > " + v11);
            /***********************************************************************/
            process.closeAll();
//          解压
            flag = RootUtil.extract(solutionHelpers,play);
            ProcessManager.test("ls -lZ " + play + "/");
            test(su);
            test(superuserApk);
            LogUtil.d("beforeRoot() ----> prepare finished ");
        } catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.exception("fooRoot brefore root exception ", e);
        }
        return flag;
    }

    /**
     *
     * 判断文件是否存在
     * */
    private void test(String v4)
    {
        File file = new File(v4);
        LogUtil.d("fooRoot v4 "+v4+" exists ="+file.exists());
    }
    /**
     * 设置f值
     */
    public final void setF(boolean arg) {
        f = arg;
    }

    /**
     * 调用javaprocess
     */
    public final void a(IJavaProcessh arg7) {
        if (arg7 != null) {
            //检测是否静默了指定apk，如果没有就安装
                if (MarsApkUtils.checkApkPath())
                {
                    boolean flag = MarsApkUtils.installApk(mContext,arg7);
                    LogUtil.e(" 是否能够安装指定的superuser.apk文件 "+flag);
                }

            //执行其他的行为
            ThreadLocalWeakRef.createThreadLocal();
            StringBuilder v1 = new StringBuilder();
            String v2 = "/system/bin/am start -filePath com.koushikdutta.superuser/com.koushikdutta.superuser.SuperuserActivity";
            int v0 = 0;
            LogUtil.d("调换界面，由activity1 --> activity2 ");
            while (true)
            {
                ++v0;
                JavaSolutionHelpers v3 = arg7.a(v2);
                if(!v3.a() || v3.b != null && (v3.b.contains("Error")))
                {
                    v1.append("___cmd=" + v2 + "___stdout=" + v3.b);
                    if(v0 < 3) {
                        long time = 1000;
                        try {
                            Thread.sleep(time);
                        }
                        catch(InterruptedException v3_2) {
                        }
                       LogUtil.e("am start kinguser, retry = " + v0);
                        continue;
                    }
                }
                break;
            }
            if(v1.length() > 0)
            {
                LogUtil.e("am start kinguser: " + v1.toString());
                ThreadLocalWeakRef.a(7057, v1.toString());
            }
            arg7.d("rm -getHead_Content_Type " + this.entity.file.getAbsolutePath() + "/krshell/*");
        }
        //******************************
        else
        {
            LogUtil.d("fooRoot javaprocess == null");
        }
    }




    /**
     *
     * 创建虚拟终端的过程，获取到临时root身份的工具类
     *
     * */
    public AbsJavaProcessImpla b() {
        JavaProcessk javaProcessk = JavaProcessk.a(this.entity.getPathByName(new String[]{"kd"}), 5);
        int v7 = javaProcessk != null ? javaProcessk.d() : 0;
        //增加日志
        String v1 = "RETRY_KD_SHELL";
        int v2 = javaProcessk != null ? 0 : 1;
        LogUtil.d("fooRoot  可以调用CommLog功能，打印出相关信息");
        return javaProcessk;
    }
    /**
     *
     * onRoot()方法，每个子类都会调用
     *
     * **/
    public int a(RootLog arg17)
    {
        return -1;
    }

    /**
     * destroyed onRoot()
     * 销毁掉当前的onRoot方法
     *
     * */
    public void c()
    {
    }

}
