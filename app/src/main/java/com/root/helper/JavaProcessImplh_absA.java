package com.root.helper;

import com.demo.utils.LogUtil;
import com.root.RootHelper;

/**
 * Created by Administrator on 2016/11/21.
 */

public class JavaProcessImplh_absA extends AbsJavaProcessImpla {

    private Class c;
    private Object d;
    private Object e;
    private RootHelper f;
    public JavaProcessImplh_absA(Class arg2, Object arg3, Object arg4, RootHelper arg5) {
        super();
        this.c = arg2;
        this.d = arg3;
        this.e = arg4;
        this.f = arg5;
        ((AbsJavaProcessImpla)this).a = 1;
    }

    @Override
    public void c() {
        try {
            this.c.getMethod(f.closeShell, this.f.m).invoke(this.d, this.e);
        }
        catch(Throwable v0) {
            LogUtil.exception("调用JavaShell的close()方法异常!", v0);
        }
    }
    /**
     *
     * 父类调用执行静默安装，这里是被方法a(String arg) 调用
     *
     * **/
    @Override
    public String d(String arg1) {


        String v0_2 =null;
        ThreadLocalWeakRef.createThreadLocal();
        String v1 = null;
        try
        {

            Object v0_1 = this.c.getMethod(this.f.closeShell, this.f.k).invoke(this.d, this.e, arg1);

        }catch (Throwable v0)
        {
            LogUtil.exception("调用JavaShell的executeCommand()方法异常!", v0);
            ThreadLocalWeakRef.a(7016, "jshell throw exception", v0);
            v0_2 = v1;
        }
        try {
            v1 = null;
        }catch (Throwable v)
        {
            LogUtil.exception("调用JavaShell的executeCommand()方法异常!", v);
            ThreadLocalWeakRef.a(7016, "jshell throw exception", v);
            v0_2 = v1;
        }
        LogUtil.e("cmd: " + arg1 + ", ret: " + v0_2);
        return v0_2;
    }
}
