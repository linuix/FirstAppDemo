package com.root.util;

import com.demo.process.RetValue;
import com.demo.process.RootProcess;
import com.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/11/8.
 */

public class ExeRunner3 extends Thread {
    private String a;
    private int b;

    public ExeRunner3(String arg2) {
        super();
        this.b = -1;
        this.a = arg2;
    }

    public final int a() {
        return this.b;
    }

    public final void run() {
        Throwable v1_1;
        Throwable v4;
        RootProcess v0_1 = null;
        LogUtil.d("[ExeRunner]3 " + this.a);
        RootProcess v1 = null;
        try {
            v0_1 = new RootProcess("sh");
        } catch (Throwable v0) {
            if (v0_1 != null) {
                v0_1.closeAll();
            }
            if (v0_1 == null) {
                LogUtil.loge("[ExeRunner] ret = " + this.b);
            }
        }
        try {
            v0_1.execut("export PATH=/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin:$PATH");
            RetValue v1_2 = v0_1.execute(this.a, 15000);
            LogUtil.d("getKDRootShell() ret = " + v1_2.ret + ", stdout = " + v1_2.stdout + ", stderr = " +
                    v1_2.err);
            this.b = v1_2.ret.intValue();
            v0_1.closeAll();
        }catch (Throwable v)
        {
            v.printStackTrace();
            if (v0_1 != null)
            {
                v0_1.closeAll();
            }
            LogUtil.exception("executRunner3 exception ",v);
        }

    }
}
