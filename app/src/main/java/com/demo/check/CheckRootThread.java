package com.demo.check;

import com.demo.process.RootProcess;
import com.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/9/29.
 */

public class CheckRootThread implements Runnable {

    private String cmd;
    private int ret;

    public CheckRootThread(String arg){
        ret =-1;
        cmd = arg;
    }
public int getRet(){
    return ret;
}
    @Override
    public void run() {

        RootProcess process=new RootProcess("sh");
        process.execut("export PATH=/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin:$PATH");
        com.demo.process.RetValue retValue = process.execute(cmd,15000);
        LogUtil.d("root check shell() ret = "+retValue.ret + ", stdout = " + retValue.stdout + ", stderr = " + retValue.err);
        ret = retValue.ret.intValue();
        process.closeAll();
    }
}
