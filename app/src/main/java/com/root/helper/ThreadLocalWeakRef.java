package com.root.helper;


import com.demo.utils.LogUtil;

public final class ThreadLocalWeakRef {
    public String a;
    public String b;
    public Throwable c;
    private static ThreadLocal d;

    static {
        ThreadLocalWeakRef.d = new ThreadLocal();
    }

    public ThreadLocalWeakRef() {
        super();
        this.a = "";
        this.b = "";
    }

    public static void a(int arg1, String arg2)
    {
        ThreadLocalWeakRef.a(arg1, arg2, null);
    }

    public static void a(int arg3, String arg4, Throwable arg5)
    {
        ThreadLocalWeakRef v0_1 = null;
        String v1 = new StringBuilder(String.valueOf(arg3)).toString();
        if(arg5 == null) {
            LogUtil.loge("[" + v1 + "]" + arg4);
        }
        else {
           LogUtil.exception("[" + v1 + "]" + arg4, arg5);
        }
        Object v0 = ThreadLocalWeakRef.d.get();
        v0_1 = new ThreadLocalWeakRef();
        if(v0 == null) {
            LogUtil.d("threadlocal v0 =null ");
            ThreadLocalWeakRef.d.set(v0_1);
        }
        v0_1.a = v1;
        v0_1.b = arg4;
        v0_1.c = arg5;
    }

    public static String a() {
        Object v0 = ThreadLocalWeakRef.d.get();
        String v0_1 = v0 == null ? "0" : ((ThreadLocalWeakRef)v0).a;
        return v0_1;
    }
    public static String a(Throwable arg3) {
        String v0;
        if(arg3 != null) {
            v0 = arg3.toString();
            Throwable v1 = arg3.getCause();
            if(v1 != null) {
                v0 = String.valueOf(v0) + "_cause_" + v1.toString();
                v1 = v1.getCause();
                if(v1 != null) {
                    v0 = String.valueOf(v0) + "_cause_" + v1.toString();
                }
            }
        }

        else {
            v0 = "";
        }
        return v0;
    }

    public static String b() {
        Object v0 = ThreadLocalWeakRef.d.get();
        String v0_1 = v0 == null ? "" : ((ThreadLocalWeakRef)v0).b;
        StringBuilder v1 = new StringBuilder(String.valueOf(v0_1));
        v0 = ThreadLocalWeakRef.d.get();
        Throwable v0_2 = v0 == null ? null : ((ThreadLocalWeakRef)v0).c;
        return v1.append(ThreadLocalWeakRef.a(v0_2)).toString();
    }

    public static void c() {
        if(ThreadLocalWeakRef.d.get() != null) {
            ThreadLocalWeakRef.d.remove();
        }
    }
}

