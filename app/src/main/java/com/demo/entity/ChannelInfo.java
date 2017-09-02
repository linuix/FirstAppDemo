package com.demo.entity;


import java.util.ArrayList;

import http.helper.HelperA;
import http.helper.HelperC;

public final class ChannelInfo extends JceStruct {
    static ArrayList cache_checksoft;
    public ArrayList checksoft;
    public int product;
    public String token;
    public String id;
    public  int isbuildin;

    public ChannelInfo() {
        super();
        this.id = "";
        this.product = 0;
        this.isbuildin = 0;
        this.token = "";
        this.checksoft = null;
    }

    public final void readFrom(HelperA arg4) {
        this.id = arg4.getStringFromBuffer(0, true);
        this.product = arg4.getDataForBuffer(this.product, 1, false);
        this.isbuildin = arg4.getDataForBuffer(this.isbuildin, 2, false);
        this.token = arg4.getStringFromBuffer(3, false);
        if(ChannelInfo.cache_checksoft == null) {
            ChannelInfo.cache_checksoft = new ArrayList();
            ChannelInfo.cache_checksoft.add(new SoftKey());
        }

        this.checksoft = (ArrayList) arg4.a(ChannelInfo.cache_checksoft, 4, false);
    }

    public final void writeTo(HelperC arg3) {
        arg3.addStringData(this.id, 0);
        if(this.product != 0) {
            arg3.addIntData(this.product, 1);
        }

        if(this.isbuildin != 0) {
            arg3.addIntData(this.isbuildin, 2);
        }

        if(this.token != null) {
            arg3.addStringData(this.token, 3);
        }

        if(this.checksoft != null) {
            arg3.addListData(this.checksoft, 4);
        }
    }
}

