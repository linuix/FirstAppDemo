package com.root.helper;


import com.demo.utils.LogUtil;

public final class ThreadLocalWeakRef {
    public String a;
    public String b;
    public Throwable c;
    private static ThreadLocal threadLocal;

    static {
        ThreadLocalWeakRef.threadLocal = new ThreadLocal();
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
        ThreadLocalWeakRef threadLocalWeakRef = null;
        String stringBuilder = new StringBuilder(String.valueOf(arg3)).toString();
        if(arg5 == null) {
            LogUtil.loge("[" + stringBuilder + "]" + arg4);
        }
        else {
           LogUtil.exception("[" + stringBuilder + "]" + arg4, arg5);
        }
        Object obj = ThreadLocalWeakRef.threadLocal.get();
        threadLocalWeakRef = new ThreadLocalWeakRef();
        if(obj == null) {
            LogUtil.d("threadlocal obj =null ");
            ThreadLocalWeakRef.threadLocal.set(threadLocalWeakRef);
        }
        threadLocalWeakRef.a = stringBuilder;
        threadLocalWeakRef.b = arg4;
        threadLocalWeakRef.c = arg5;
    }

    public static String a() {
        Object v0 = ThreadLocalWeakRef.threadLocal.get();
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
        Object v0 = ThreadLocalWeakRef.threadLocal.get();
        String v0_1 = v0 == null ? "" : ((ThreadLocalWeakRef)v0).b;
        StringBuilder v1 = new StringBuilder(String.valueOf(v0_1));
        v0 = ThreadLocalWeakRef.threadLocal.get();
        Throwable v0_2 = v0 == null ? null : ((ThreadLocalWeakRef)v0).c;
        return v1.append(ThreadLocalWeakRef.a(v0_2)).toString();
    }

    public static void createThreadLocal() {
        if(ThreadLocalWeakRef.threadLocal.get() != null) {
            ThreadLocalWeakRef.threadLocal.remove();
        }
    }
}

