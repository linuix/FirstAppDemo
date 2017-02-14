package com.root.helper;

import com.demo.process.RetValue;
import com.demo.process.RootProcess;
import com.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/11/8.
 *
 *
 */

public class JavaProcessImplw extends AbsJavaProcessImpla
{

    protected RootProcess d;
    public JavaProcessImplw()
    {
        super();
    }
    public  final  JavaSolutionHelpers a(String arg)
    {
        return a(arg,120000);
    }
    public final JavaSolutionHelpers a(String arg5, long arg6)
    {
        JavaSolutionHelpers v2 = new JavaSolutionHelpers();
        try
        {
            RetValue v0_1 = this.d.execute(arg5, arg6);
            v2.a = v0_1.ret.intValue();
            if (v0_1.ret.intValue() == 0)
            {
                v2.b = String.valueOf(v0_1.ret) + v0_1.err;
                if (v2.a == 0)
                {
                    LogUtil.d("cmd: " + arg5 + ", ret: " + v2.a + ", stdout = " + v2.b);
                }
                else {
                    LogUtil.d("cmd: " + arg5 + ", ret: " + v2.a + ", stdout = " + v2.b);
                }
                return v2;
            }
            v2.b = String.valueOf(v0_1.ret) + v0_1.err;
        }
        catch (Throwable v0) {
            Throwable v1 = v0;
            LogUtil.exception("KD(SU)RootShell.executeCommand:" + arg5, v1);
            v2.a = -1;
            String v0_2 = v1.toString();
            v1 = v1.getCause();
            if (v1 != null) {
                v0_2 = String.valueOf(v0_2) + "_cause_" + v1.toString();
            }
            v2.b = v0_2;
        }
        return v2;
    }

    @Override
    public void c() {
        if (this.d != null) {
            this.d.closeAll();
        }
    }

    @Override
    public String d(String arg5) {
        String v0 = null;
        ThreadLocalWeakRef.c();
        try {
            v0 = this.d.execut(arg5).stdout;
        } catch (Throwable v1) {
            LogUtil.exception("KD(SU)RootShell.executeCommand:" + arg5, v1);
            ThreadLocalWeakRef.a(7016, "kd(su)shell throw exception", v1);
        }
        LogUtil.d("cmd: " + arg5 + ", ret: " + v0);
        return v0;
    }
}
