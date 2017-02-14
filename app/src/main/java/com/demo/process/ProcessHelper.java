package com.demo.process;

public final class ProcessHelper {
    public final String a;
    public final String b;
    public final long c;

    public ProcessHelper(String arg1, String arg2, long arg3) {
        super();
        this.a = arg1;
        this.b = arg2;
        this.c = arg3;
    }

    @Override
    public String toString() {
        return "ProcessHelper{" +
                "sdk_gt18='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c=" + c +
                '}';
    }
}

