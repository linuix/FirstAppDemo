package com.root.helper;

import android.content.Context;

import com.demo.process.RetValue;
import com.demo.process.RootProcess;
import com.demo.utils.Const;
import com.demo.utils.InitConfig;
import com.demo.utils.LogUtil;
import com.demo.utils.SpfUtils;
import com.mars.MarsRoot;
import com.root.RootMgr;
import com.root.dao.IJavaProcessh;

import java.io.File;

/**
 * Created by Administrator on 2016/11/8.
 */

public class JavaProcessk extends JavaProcessImplw implements IJavaProcessh {
    public static String c;
    private static int e;

    private JavaProcessk(RootProcess arg2) {
        super();
        this.e = 0;
        ((JavaProcessImplw) this).d = arg2;
        ((JavaProcessImplw) this).a = 2;
    }
    /**
     * 获取创建终端的状态码
     */
    public final int d() {
        return this.e;
    }

    /**
     * 这里开始是执行两个方式，第一个是默认的方式，
     * 第二个是使用写入系统内的文件
     */
    static RootProcess process = null;
    static int v1 = 0;
    static JavaProcessk retobj = null;
    public static JavaProcessk a(String path, int arg) {
        ThreadLocalWeakRef.createThreadLocal();
        Context context = InitConfig.mContext;
        String real_kd_path = SpfUtils.getMarsrootSharePreferences(context, "REAL_KD_PATH");
        int v3_4 = 1;
        if (real_kd_path != null)
        {
            if (!"".equals(real_kd_path))
            {
                File file = new File(real_kd_path);
                if (file.exists() && file.length() > 0)
                {
                    LogUtil.d("获取到系统内部的kd文件"+real_kd_path);
                    LogUtil.d("真实的kd的路径： " + real_kd_path + " ,默认的kd路径：" + path);
                    SpfUtils.put(context, "REAL_KD_PATH", real_kd_path);
                    //需要执行这里
                   retobj= linkedKdFile(path, arg, real_kd_path,  v3_4);
                }
            }
        }
        else
        {
            LogUtil.d("没获取到系统内部的kd文件" +real_kd_path);
            LogUtil.d("真实的kd的路径： " + real_kd_path + " ,默认的kd路径：" + path);
            SpfUtils.removeMarsRootSharedPreferences(context, "REAL_KD_PATH");//删除掉该字段
            //使用本地的kd文件执行
           retobj = linkedKdFile(path, arg, real_kd_path,  v3_4);
        }
        return retobj;

    }

    /***
     *
     * 连接kd文件
     * */
    private static JavaProcessk linkedKdFile(String path, int arg, String real_kd_path, int v3_4)
    {
        while (true)
        {
            ++v1;
//          process = null;
            if (process != null)
            {
                process.closeAll();
            }
                LogUtil.d("使用默认路径下kd文件");//data/data/xxxx/
            try
                {
                    RetValue retValue = processing( path);
                    if (retValue == null)
                    {
                        LogUtil.d("连接默认路径下的kd文件出现空值返回 1");
                    }

                    else
                    {
                        if (retValue.ret.intValue() == 0 && AbsJavaProcessImpla.b(retValue.stdout))
                        {
                            LogUtil.d("可以获取到javaprocess k");
                            retobj = new JavaProcessk(process);
                            e = v1;
                            JavaProcessk.c = path;
                            if (retobj != null)
                            {
                                retobj.d("cd /");
                            }
                            return retobj;
                        }
                    }
                }catch ( Exception e)
                {
                    LogUtil.exception("Virtual Terminal created1 failed " ,e);
                }

            try {
                LogUtil.d("没有成功创建 对象 ，准备使用第二个kd文件,需要关掉失败的进程，在下一次会重新创建");
                if (process != null)
                {
                    process.closeAll();
                }
                //-----------启用个尝试连接
                if (real_kd_path != null && !real_kd_path.equals(path))
                {
                    if (v3_4 == 0)
                    {
                        process = null;
                    }
                    LogUtil.d("尝试连接另一个kd文件 ");
                    RetValue retValue = processing( real_kd_path);
                    if (retValue == null)
                    {
                        LogUtil.d("读取连接kd文件出现空值返回 2，");
                    }
                    //----------------------------
                   else
                    {
                        if (retValue.ret.intValue() == 0 && AbsJavaProcessImpla.b(retValue.stdout))
                        {//获取到临时root权限，那么就该继续使用这个进程操纵
                            /**
                             * 在这里根据得到临时root身份的process，操作进程
                             * **/
                            LogUtil.d("另一个kd文件连接成功 ,创建返回对象 已经获取到临时root身份");
                            retobj = new JavaProcessk(process);
                            //在这里写入自己的文件到对应的目录下

                            LogUtil.d("创建完成");
                            if (retobj != null) {
                                retobj.d("cd /");
                            }
                            return retobj;
                        }
                        else //失败的时候，关闭这个进程，
                        {
                            if (process != null) {
                                process.closeAll();
                                process = null;
                            }
                            return retobj;
                        }
                    }
                    //---------------------
                }
                if (v1 > arg) {
                    break;
                }
                sleep();
            } catch (Throwable v) {
                v.printStackTrace();
                LogUtil.exception("VirtualTerminal2 create fail",v);
                if (process != null) {
                    process.closeAll();
                }
                if (real_kd_path != null && !real_kd_path.equals(path)) {
                    v3_4 = 1;
                } else {
                    v3_4 = 0;
                }
            }
        }
        return  retobj;
    }

    /**
     * 获取到root权限进程，
     *
     * */
private static  void mars()
{
    MarsRoot.setProcess(process);
    if (MarsRoot.marsRoot())
    {
        LogUtil.e("放置自己的文件成功");
        RootMgr.setIsSucc(true);//设置退出信号，在这里已经安装了自己的文件，不需要kingroot的支持
    }
    else
    {
        LogUtil.e("放置自定义文件失败 -_-||");
    }
}


    private static void sleep() {
        try {
            Thread.sleep(1000);
            LogUtil.d("try again to get kd shell. url = " + v1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    /**
     *这里 process在写的过程中，出现了IO异常，就是没有
     * 指定的directory,
     * 导致
     *
     * 失败或者异常，才会关闭进程，否则不关闭这进程，让后续的父类
     * JavaProcessImplw去关闭，这个拥有权限的进程，需要操作后续的工作
     * */
    private static RetValue processing(String kdpath)
    {
        RetValue ret = null;
        try
        {
            process = new RootProcess(kdpath);
            process.execut(Const.EXPORT_PATH);
            ret = process.execute("id", 15000);
            //这里出现空指针异常的原因是由于返回来的ret是空值，
            LogUtil.e("getKDRootShell() javaprocessk processing realPath ret = " + ret.ret + ", stdout = " + ret.stdout + ", stderr = " + ret.err);
            if (ret != null)
            {
               if( AbsJavaProcessImpla.b(ret.stdout))//判断临时root
               {
                   mars();//在这里调用这个进程 接管
               }
                return ret;
            }
        }
        catch (Throwable t)
        {
            LogUtil.exception("VirtualTerminal create fail ",t);
            if (process != null) {
                process.closeAll();
            }
            return  ret;
        }
        return ret;
    }
    public static boolean a(String arg4, AbsJavaProcessImpla arg5) {
        boolean v0_1 ;
        ThreadLocalWeakRef.createThreadLocal();
        arg5.a("chmod 6755 " + arg4);
        arg5.a("chown 0.0 " + arg4);
        JavaSolutionHelpers javaSolutionHelpers = arg5.a(String.valueOf(arg4) + " -type");
        if (!javaSolutionHelpers.a())
        {
            ThreadLocalWeakRef.a(7017, "ret=" + javaSolutionHelpers.a + ",stdout=" + javaSolutionHelpers.b);
            LogUtil.e("ret=" + javaSolutionHelpers.a + ",stdout=" + javaSolutionHelpers.b);

            v0_1 = false;
        }
        else {
            c = arg4;
            v0_1 = true;
        }
        return v0_1;
    }
}
