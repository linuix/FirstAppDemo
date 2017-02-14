package com.root.helper;

public final class LinuxProperties {

    public  int PID;
    public  int PPID;
    public  int UID;
    public String NAME;
    public LinuxProperties() {
        super();
        this.PID = 0;
        this.PPID = 0;
        this.NAME = null;
        this.UID = 0;
    }

    public LinuxProperties(int arg1, int arg2, String arg3, int arg4) {
        super();
        this.PID = arg1;
        this.PPID = arg2;
        this.NAME = arg3;
        this.UID = arg4;
    }

    public final String toString() {
        return "PID=" + this.PID + " PPID=" + this.PPID + " NAME=" + this.NAME + " UID=" + this.UID;
    }
}

