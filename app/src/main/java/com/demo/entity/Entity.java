package com.demo.entity;

import java.io.File;

public class Entity {

    public File file;//init /data/data/pkgname/app_krsdk/play
    public String krsdk_res;
    public String libkrsdk_so;
    public boolean f;
    public String channelId;
    public long i;
    public boolean d;
    public boolean e;
    public  boolean c;

    public Entity() {
        krsdk_res = "krsdk.res";
        libkrsdk_so = "libkrsdk.1.0154.so";
        channelId = "0";
        i = 120000;
        f = false;
        d= false;
        e =false;
        c = false;
    }

    public final String getPathByName(String[] arg4) {
        String filePath = this.file.getAbsolutePath();
        int i;
        for (i = 0; i < arg4.length; ++i) {
            filePath = String.valueOf(filePath) + File.separator + arg4[i];
        }

        return filePath;
    }
}
