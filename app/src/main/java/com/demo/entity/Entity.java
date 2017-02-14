package com.demo.entity;

import java.io.File;

public class Entity {

    public File file;//init /data/data/pkgname/app_krsdk/play
    public String krsdk_res;
    public String libkrsdk_so;
    public boolean f;
    public String g;
    public long i;
    public boolean d;
    public boolean e;
    public  boolean c;

    public Entity() {
        krsdk_res = "krsdk.res";
        libkrsdk_so = "libkrsdk.1.0154.so";
        g = "0";
        i = 120000;
        f = false;
        d= false;
        e =false;
        c = false;
    }

    public final String a(String[] arg4) {
        String v1 = this.file.getAbsolutePath();
        int v0;
        for (v0 = 0; v0 < arg4.length; ++v0) {
            v1 = String.valueOf(v1) + File.separator + arg4[v0];
        }

        return v1;
    }
}
