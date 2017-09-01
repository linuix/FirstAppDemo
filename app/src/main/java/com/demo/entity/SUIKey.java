package com.demo.entity;


import http.helper.HelperA;
import http.helper.HelperC;

public final class SUIKey extends JceStruct {
    public String channelid;
    public String guid;
    public String imei;
    public String imsi;
    public String ip;
    public int isbuildin;
    public short lang;
    public String lc;
    public String machineconf;
    public String machineuid;
    public String name;
    public String newguid;
    public String osversion;
    public String phone;
    public String qq;
    public String sdk;
    public String sid;
    public int subplatform;
    public int type;
    public String uuid;
    public String version;

    public SUIKey() {
        super();
        this.lc = "";
        this.name = "";
        this.version = "";
        this.imei = "";
        this.imsi = "";
        this.qq = "";
        this.ip = "";
        this.type = 0;
        this.osversion = "";
        this.machineuid = "";
        this.machineconf = "";
        this.phone = "";
        this.subplatform = 0;
        this.channelid = "";
        this.isbuildin = 0;
        this.uuid = "";
        this.lang = 0;
        this.guid = "";
        this.sdk = "";
        this.sid = "";
        this.newguid = "";
    }

    public final void readFrom(HelperA arg4) {
        this.lc = arg4.a(0, true);
        this.name = arg4.a(1, true);
        this.version = arg4.a(2, true);
        this.imei = arg4.a(3, true);
        this.imsi = arg4.a(4, true);
        this.qq = arg4.a(5, false);
        this.ip = arg4.a(6, false);
        this.type = arg4.getDataForBuffer(this.type, 7, false);
        this.osversion = arg4.a(8, false);
        this.machineuid = arg4.a(9, false);
        this.machineconf = arg4.a(10, false);
        this.phone = arg4.a(11, false);
        this.subplatform = arg4.getDataForBuffer(this.subplatform, 12, false);
        this.channelid = arg4.a(13, false);
        this.isbuildin = arg4.getDataForBuffer(this.isbuildin, 14, false);
        this.uuid = arg4.a(15, false);
        this.lang = arg4.getShort(this.lang, 16, false);
        this.guid = arg4.a(17, false);
        this.sdk = arg4.a(18, false);
        this.sid = arg4.a(19, false);
        this.newguid = arg4.a(20, false);
    }

    public final void writeTo(HelperC arg3) {
        arg3.addStringData(this.lc, 0);
        arg3.addStringData(this.name, 1);
        arg3.addStringData(this.version, 2);
        arg3.addStringData(this.imei, 3);
        arg3.addStringData(this.imsi, 4);
        if(this.qq != null) {
            arg3.addStringData(this.qq, 5);
        }

        if(this.ip != null) {
            arg3.addStringData(this.ip, 6);
        }

        if(this.type != 0) {
            arg3.addIntData(this.type, 7);
        }

        if(this.osversion != null) {
            arg3.addStringData(this.osversion, 8);
        }

        if(this.machineuid != null) {
            arg3.addStringData(this.machineuid, 9);
        }

        if(this.machineconf != null) {
            arg3.addStringData(this.machineconf, 10);
        }

        if(this.phone != null) {
            arg3.addStringData(this.phone, 11);
        }

        if(this.subplatform != 0) {
            arg3.addIntData(this.subplatform, 12);
        }

        if(this.channelid != null) {
            arg3.addStringData(this.channelid, 13);
        }

        if(this.isbuildin != 0) {
            arg3.addIntData(this.isbuildin, 14);
        }

        if(this.uuid != null) {
            arg3.addStringData(this.uuid, 15);
        }

        if(this.lang != 0) {
            arg3.addShortData(this.lang, 16);
        }

        if(this.guid != null) {
            arg3.addStringData(this.guid, 17);
        }

        if(this.sdk != null) {
            arg3.addStringData(this.sdk, 18);
        }

        if(this.sid != null) {
            arg3.addStringData(this.sid, 19);
        }

        if(this.newguid != null) {
            arg3.addStringData(this.newguid, 20);
        }
    }
}

