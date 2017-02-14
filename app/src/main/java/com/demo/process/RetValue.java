package com.demo.process;

public final class RetValue {  // RetValue是我在demo中重新命名的类f
    public final String cmd;
    public final String stdout;
    public final String err;
    public final Integer ret;

    public RetValue(String arg1, Integer arg2, String arg3, String arg4) {
        super();
        this.cmd = arg1;
        this.ret = arg2;
        this.stdout = arg3;
        this.err = arg4;
    }

    public final boolean a() {
        boolean v0 = this.ret == null || this.ret.intValue() != 0 ? false : true;
        return v0;
    }

    @Override
    public String toString() {
        return "RetValue{" +
                "cmd='" + cmd + '\'' +
                ", stdout='" + stdout + '\'' +
                ", err='" + err + '\'' +
                ", ret=" + ret +
                '}';
    }
}
