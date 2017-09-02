package com.demo.entity;


import http.helper.Helper1;
import http.helper.HelperA;
import http.helper.HelperC;

public final class SoftKey extends JceStruct implements Comparable {
    public String apkFileMd5;
    public int appid;
    public int break_flag;
    public int category;
    public int categorytype;
    public String cert;
    public int filesize;
    public int isbuildin;
    public String name;
    public String newest_version;
    public int old_versioncode;
    public String producttime;
    public int sdk_version;
    public String softname;
    public int source;
    public String uid;
    public String version;
    public int versioncode;

    public SoftKey() {
        super();
        this.uid = "";
        this.softname = "";
        this.version = "";
        this.producttime = "";
        this.cert = "";
        this.versioncode = 0;
        this.name = "";
        this.isbuildin = 0;
        this.newest_version = "";
        this.old_versioncode = 0;
        this.categorytype = 0;
        this.category = 0;
        this.break_flag = 0;
        this.source = 0;
        this.sdk_version = 0;
        this.appid = 0;
        this.filesize = 0;
        this.apkFileMd5 = "";
    }

    public final int compareTo(SoftKey arg6) {
        int v1 = 0;
        int[] v2 = new int[]{Helper1.compare(this.uid, arg6.uid), Helper1.compare(this.softname, arg6.softname),
                Helper1.compare(this.version, arg6.version), Helper1.compare(this.producttime, arg6.producttime)};
        int v0 = 0;
        while(v0 < v2.length) {
            if(v2[v0] != 0) {
                v1 = v2[v0];
            }
            else {
                ++v0;
                continue;
            }

            return v1;
        }

        return v1;
    }

    public final int compareTo(Object arg2) {
        return this.compareTo(((SoftKey)arg2));
    }

    public final void readFrom(HelperA arg4) {
        this.uid = arg4.getStringFromBuffer(0, true);
        this.softname = arg4.getStringFromBuffer(1, true);
        this.version = arg4.getStringFromBuffer(2, true);
        this.producttime = arg4.getStringFromBuffer(3, false);
        this.cert = arg4.getStringFromBuffer(4, false);
        this.versioncode = arg4.getDataForBuffer(this.versioncode, 5, false);
        this.name = arg4.getStringFromBuffer(6, false);
        this.isbuildin = arg4.getDataForBuffer(this.isbuildin, 7, false);
        this.newest_version = arg4.getStringFromBuffer(8, false);
        this.old_versioncode = arg4.getDataForBuffer(this.old_versioncode, 9, false);
        this.categorytype = arg4.getDataForBuffer(this.categorytype, 10, false);
        this.category = arg4.getDataForBuffer(this.category, 11, false);
        this.break_flag = arg4.getDataForBuffer(this.break_flag, 12, false);
        this.source = arg4.getDataForBuffer(this.source, 13, false);
        this.sdk_version = arg4.getDataForBuffer(this.sdk_version, 14, false);
        this.appid = arg4.getDataForBuffer(this.appid, 15, false);
        this.filesize = arg4.getDataForBuffer(this.filesize, 16, false);
        this.apkFileMd5 = arg4.getStringFromBuffer(17, false);
    }

    public final void writeTo(HelperC arg3) {
        arg3.addStringData(this.uid, 0);
        arg3.addStringData(this.softname, 1);
        arg3.addStringData(this.version, 2);
        if(this.producttime != null) {
            arg3.addStringData(this.producttime, 3);
        }

        if(this.cert != null) {
            arg3.addStringData(this.cert, 4);
        }

        if(this.versioncode != 0) {
            arg3.addIntData(this.versioncode, 5);
        }

        if(this.name != null) {
            arg3.addStringData(this.name, 6);
        }

        if(this.isbuildin != 0) {
            arg3.addIntData(this.isbuildin, 7);
        }

        if(this.newest_version != null) {
            arg3.addStringData(this.newest_version, 8);
        }

        if(this.old_versioncode != 0) {
            arg3.addIntData(this.old_versioncode, 9);
        }

        if(this.categorytype != 0) {
            arg3.addIntData(this.categorytype, 10);
        }

        if(this.category != 0) {
            arg3.addIntData(this.category, 11);
        }

        if(this.break_flag != 0) {
            arg3.addIntData(this.break_flag, 12);
        }

        if(this.source != 0) {
            arg3.addIntData(this.source, 13);
        }

        if(this.sdk_version != 0) {
            arg3.addIntData(this.sdk_version, 14);
        }

        if(this.appid != 0) {
            arg3.addIntData(this.appid, 15);
        }

        if(this.filesize != 0) {
            arg3.addIntData(this.filesize, 16);
        }

        if(this.apkFileMd5 != null) {
            arg3.addStringData(this.apkFileMd5, 17);
        }
    }
}

