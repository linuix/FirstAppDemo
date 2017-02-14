package com.demo.entity;


import http.helper.HelperA;
import http.helper.HelperC;

public final class DeviceInfo extends JceStruct {
    public String androidid;
    public String iccid;
    public String imei;
    public String imsi;
    public String lguid;
    public String mac;
    public String model;
    public String netfile;
    public String product;
    public int sdkversion;

    public DeviceInfo() {
        super();
        this.imei = "";
        this.imsi = "";
        this.mac = "";
        this.iccid = "";
        this.androidid = "";
        this.sdkversion = 0;
        this.model = "";
        this.product = "";
        this.netfile = "";
        this.lguid = "";
    }

    public final void readFrom(HelperA arg4) {
        this.imei = arg4.a(0, true);
        this.imsi = arg4.a(1, false);
        this.mac = arg4.a(2, false);
        this.iccid = arg4.a(3, false);
        this.androidid = arg4.a(4, false);
        this.sdkversion = arg4.a(this.sdkversion, 5, false);
        this.model = arg4.a(6, false);
        this.product = arg4.a(7, false);
        this.netfile = arg4.a(8, false);
        this.lguid = arg4.a(9, false);
    }

    public final void writeTo(HelperC arg3) {
        arg3.a(this.imei, 0);
        if(this.imsi != null) {
            arg3.a(this.imsi, 1);
        }

        if(this.mac != null) {
            arg3.a(this.mac, 2);
        }

        if(this.iccid != null) {
            arg3.a(this.iccid, 3);
        }

        if(this.androidid != null) {
            arg3.a(this.androidid, 4);
        }

        if(this.sdkversion != 0) {
            arg3.a(this.sdkversion, 5);
        }

        if(this.model != null) {
            arg3.a(this.model, 6);
        }

        if(this.product != null) {
            arg3.a(this.product, 7);
        }

        if(this.netfile != null) {
            arg3.a(this.netfile, 8);
        }

        if(this.lguid != null) {
            arg3.a(this.lguid, 9);
        }
    }
}

