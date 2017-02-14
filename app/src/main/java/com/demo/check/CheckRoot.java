package com.demo.check;

import com.demo.entity.Entity;
import com.demo.process.RootProcess;
import com.demo.utils.LogUtil;

import java.io.File;

/**
 * Created by Administrator on 2016/9/29.
 * 这里是开始的时候在application中调用
 * 开始执行这个文件，尝试获取root
 *com.kingroot.sdk.root.u
 * 这个类中得到相关代码
 */

public class CheckRoot {


    /*
    *执行检测su文件
    * */
    public static int excuteSuAndSh() {
        Entity entity = new Entity();
        LogUtil.d("check su and sh ");
        //检测su_check文件
        CheckRootThread checkRootThread = new CheckRootThread(new File(entity.file, "su_check").getAbsolutePath());
        checkRootThread.run();
        int ret = checkRootThread.getRet();
        LogUtil.d("rootCode = " + ret);
        LogUtil.d("verifyR, rcode = " + ret);
        //执行su文件
        excuteSu(15000,1);

        return ret;
    }
/*
*尝试链接su文件。
* 执行su文件，命令
* */
    private  static void excuteSu(int arg1,int arg2){
        RootProcess rootProcess = new RootProcess("sh");
        rootProcess.execut("export PATH=/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin:$PATH");
        com.demo.process.RetValue retValue = rootProcess.execut("su");
        LogUtil.d("sh su ret : " + retValue.ret + ", stdout : " + retValue.stdout + ", stderr : " + retValue.err);
        retValue = rootProcess.execute("id",(long)arg2);
        if (retValue.stdout.contains("uid==0(root)") && retValue.ret.intValue() ==0){
            LogUtil.loge("has get root  ================^^^^^^^==========");
            LogUtil.loge(("VirtualTerminal runCommand ret : " + retValue.ret + ", stdout : " + retValue.stdout + ", stderr : " + retValue.err));
        }
        else {
            LogUtil.loge("phone has not root ,click to root");
        }
    }

}
