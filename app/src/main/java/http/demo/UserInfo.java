package http.demo;


import com.demo.entity.JceStruct;
import com.demo.entity.ProductVersion;

import http.helper.HelperA;
import http.helper.HelperC;

public final class UserInfo extends JceStruct {
    static ProductVersion cache_version;
    public String channelid;
    public String ip;
    public int isbuildin;
    public short lang;
    public double latitude;
    public String lc;
    public double longitude;
    public String newguid;
    public String phone;
    public int product;
    public String qq;
    public int sdkversion;
    public String ua;
    public String uuid;
    public String imei;
    public String imsi;
    public  int ct;
    public String guid;
    public  int isroot;
    public  int buildno;

    public ProductVersion version;
    public UserInfo() {
        super();
        this.imei = "";
        this.qq = "";
        this.phone = "";
        this.ip = "";
        this.lc = "";
        this.channelid = "";
        this.ua = "";
        this.ct = 0;
        this.product = 0;
        this.version = null;
        this.guid = "";
        this.imsi = "";
        this.isbuildin = 0;
        this.isroot = 0;
        this.sdkversion = 0;
        this.buildno = 0;
        this.uuid = "";
        this.lang = 0;
        this.longitude = 0;
        this.latitude = 0;
        this.newguid = "";
    }

    @Override
    public void readFrom(HelperA arg5) {
        this.imei = arg5.a(0, true);
        this.qq = arg5.a(1, false);
        this.phone = arg5.a(2, false);
        this.ip = arg5.a(3, false);
        this.lc = arg5.a(4, false);
        this.channelid = arg5.a(5, false);
        this.ua = arg5.a(6, false);
        this.ct = arg5.a(this.ct, 7, false);
        this.product = arg5.a(this.product, 8, false);
        if(UserInfo.cache_version == null) {
            UserInfo.cache_version = new ProductVersion();
        }

        this.version = (ProductVersion) arg5.a(UserInfo.cache_version, 9, false);
        this.guid = arg5.a(10, false);
        this.imsi = arg5.a(11, false);
        this.isbuildin = arg5.a(this.isbuildin, 12, false);
        this.isroot = arg5.a(this.isroot, 13, false);
        this.sdkversion = arg5.a(this.sdkversion, 14, false);
        this.buildno = arg5.a(this.buildno, 15, false);
        this.uuid = arg5.a(16, false);
        this.lang = arg5.a(this.lang, 17, false);
        this.longitude = arg5.a(this.longitude, 18, false);
        this.latitude = arg5.a(this.latitude, 19, false);
        this.newguid = arg5.a(20, false);
    }

    @Override
    public void writeTo(HelperC arg6) {

        double v3 = 0;
        arg6.a(this.imei, 0);
        if(this.qq != null) {
            arg6.a(this.qq, 1);
        }

        if(this.phone != null) {
            arg6.a(this.phone, 2);
        }

        if(this.ip != null) {
            arg6.a(this.ip, 3);
        }

        if(this.lc != null) {
            arg6.a(this.lc, 4);
        }

        if(this.channelid != null) {
            arg6.a(this.channelid, 5);
        }

        if(this.ua != null) {
            arg6.a(this.ua, 6);
        }

        if(this.ct != 0) {
            arg6.a(this.ct, 7);
        }

        if(this.product != 0) {
            arg6.a(this.product, 8);
        }

        if(this.version != null) {
            arg6.a(this.version, 9);
        }

        if(this.guid != null) {
            arg6.a(this.guid, 10);
        }

        if(this.imsi != null) {
            arg6.a(this.imsi, 11);
        }

        if(this.isbuildin != 0) {
            arg6.a(this.isbuildin, 12);
        }

        if(this.isroot != 0) {
            arg6.a(this.isroot, 13);
        }

        if(this.sdkversion != 0) {
            arg6.a(this.sdkversion, 14);
        }

        if(this.buildno != 0) {
            arg6.a(this.buildno, 15);
        }

        if(this.uuid != null) {
            arg6.a(this.uuid, 16);
        }

        if(this.lang != 0) {
            arg6.a(this.lang, 17);
        }

        if(this.longitude != v3) {
            arg6.a(this.longitude, 18);
        }

        if(this.latitude != v3) {
            arg6.a(this.latitude, 19);
        }

        if(this.newguid != null) {
            arg6.a(this.newguid, 20);
        }

    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "channelid='" + channelid + '\'' +
                ", ip='" + ip + '\'' +
                ", isbuildin=" + isbuildin +
                ", lang=" + lang +
                ", latitude=" + latitude +
                ", lc='" + lc + '\'' +
                ", longitude=" + longitude +
                ", newguid='" + newguid + '\'' +
                ", phone='" + phone + '\'' +
                ", product=" + product +
                ", qq='" + qq + '\'' +
                ", sdkversion=" + sdkversion +
                ", ua='" + ua + '\'' +
                ", uuid='" + uuid + '\'' +
                ", imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", ct=" + ct +
                ", guid='" + guid + '\'' +
                ", isroot=" + isroot +
                ", buildno=" + buildno +
                ", version=" + version +
                '}';
    }
}

