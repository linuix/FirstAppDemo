package com.demo.process;

import android.content.Context;

import com.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/10/28.
 */

public class ProcessManager {

    //测试方式，写命令，写文件，写整个文件夹三种方式

//    private HandlerThread thread;
    private static ProcessManager instance;
//    private MyHandler handler;

    private ProcessManager(Context context) {
//        thread = new HandlerThread("processThread");
//        thread.start();//启动自己的线程
//        handler = new MyHandler(this, thread.getLooper());
//        handler.sendEmptyMessage(90001);

    }

    public static ProcessManager getInstance(Context context) {
        instance = new ProcessManager(context);
        return instance;
    }

    /**
     * 测试命令行是否可用
     * @param arg 命令
     * @return
     */
    public static int test(String arg) {
        RootProcess processs = new RootProcess("sh");
        processs.execut("export PATH=/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin:$PATH");
        RetValue execut = processs.execut(arg);
        if (execut != null) {
            LogUtil.loge(arg+":\n ret = " + execut.ret + ", stdout = " + execut.stdout + ", stderr = " + execut.err);
        }
        processs.closeAll();
        return 0;
    }
}
