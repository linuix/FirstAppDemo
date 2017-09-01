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
    public  int netFlag;
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
        this.netFlag = 0;
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
        this.netFlag = arg5.getDataForBuffer(this.netFlag, 7, false);
        this.product = arg5.getDataForBuffer(this.product, 8, false);
        if(UserInfo.cache_version == null) {
            UserInfo.cache_version = new ProductVersion();
        }

        this.version = (ProductVersion) arg5.a(UserInfo.cache_version, 9, false);
        this.guid = arg5.a(10, false);
        this.imsi = arg5.a(11, false);
        this.isbuildin = arg5.getDataForBuffer(this.isbuildin, 12, false);
        this.isroot = arg5.getDataForBuffer(this.isroot, 13, false);
        this.sdkversion = arg5.getDataForBuffer(this.sdkversion, 14, false);
        this.buildno = arg5.getDataForBuffer(this.buildno, 15, false);
        this.uuid = arg5.a(16, false);
        this.lang = arg5.getShort(this.lang, 17, false);
        this.longitude = arg5.a(this.longitude, 18, false);
        this.latitude = arg5.a(this.latitude, 19, false);
        this.newguid = arg5.a(20, false);
    }

    @Override
    public void writeTo(HelperC helperC) {

        double v3 = 0;
        helperC.addStringData(this.imei, 0);
        if(this.qq != null) {
            helperC.addStringData(this.qq, 1);
        }

        if(this.phone != null) {
            helperC.addStringData(this.phone, 2);
        }

        if(this.ip != null) {
            helperC.addStringData(this.ip, 3);
        }

        if(this.lc != null) {
            helperC.addStringData(this.lc, 4);
        }

        if(this.channelid != null) {
            helperC.addStringData(this.channelid, 5);
        }

        if(this.ua != null) {
            helperC.addStringData(this.ua, 6);
        }

        if(this.netFlag != 0) {
            helperC.addIntData(this.netFlag, 7);
        }

        if(this.product != 0) {
            helperC.addIntData(this.product, 8);
        }

        if(this.version != null) {
            helperC.addJceStructData(this.version, 9);
        }

        if(this.guid != null) {
            helperC.addStringData(this.guid, 10);
        }

        if(this.imsi != null) {
            helperC.addStringData(this.imsi, 11);
        }

        if(this.isbuildin != 0) {
            helperC.addIntData(this.isbuildin, 12);
        }

        if(this.isroot != 0) {
            helperC.addIntData(this.isroot, 13);
        }

        if(this.sdkversion != 0) {
            helperC.addIntData(this.sdkversion, 14);
        }

        if(this.buildno != 0) {
            helperC.addIntData(this.buildno, 15);
        }

        if(this.uuid != null) {
            helperC.addStringData(this.uuid, 16);
        }

        if(this.lang != 0) {
            helperC.addShortData(this.lang, 17);
        }

        if(this.longitude != v3) {
            helperC.addDoubleData(this.longitude, 18);
        }

        if(this.latitude != v3) {
            helperC.addDoubleData(this.latitude, 19);
        }

        if(this.newguid != null) {
            helperC.addStringData(this.newguid, 20);
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
                ", netFlag=" + netFlag +
                ", guid='" + guid + '\'' +
                ", isroot=" + isroot +
                ", buildno=" + buildno +
                ", version=" + version +
                '}';
    }
}

