package com.root.process;

import com.demo.process.ProcessHelper;
import com.demo.process.RetValue;
import com.demo.utils.LogUtil;
import com.root.util.SelinuxUtils;

/**
 * Created by Administrator on 2016/11/8.
 */

public class SDKLibProcessMgr {
    public static RetValue a(String arg3, String arg4) {
        return SDKLibProcessMgr.a(arg3, new ProcessHelper(arg4, arg4, 120000));
    }
    private static RetValue a(String arg6, ProcessHelper arg7) {
        RetValue v0_3 = null;
        RootProcess2 v0_2 = null;
        RetValue v1 = null;
       try
       {
           v0_2 = new RootProcess2(arg6);

           if( SelinuxUtils.sdk() >= 14) {
               v0_2.execut("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
           }
           v1 = v0_2.execute(arg7);
           v0_2.closeAll();
           v0_3 = v1;
       }
       catch (Exception e)
       {
           if(v0_2 != null) {
               v0_2.closeAll();
               v0_3 = v1;
           }
           v0_3 = v1;
           if(v0_3 == null) {
               v0_3 = new RetValue(arg7.a, Integer.valueOf(2), "", "Run Cmd Exception");
           }

           return v0_3;

       }

        return v0_3;
    }

/**
 *
 * 执行ping命令
 * */
    public static boolean a(String arg3) {
        LogUtil.e("调用进程执行ping 命令");
        RetValue v0 = SDKLibProcessMgr.a("sh", String.valueOf(arg3) + " --ping");
        boolean v0_1 = !v0.a() /*|| !v0.stdout.trim().equals("kinguser_su") ?*/ ?false : true;
        return v0_1;
    }
}
