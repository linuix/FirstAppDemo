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
        this.id = arg4.a(0, true);
        this.product = arg4.a(this.product, 1, false);
        this.isbuildin = arg4.a(this.isbuildin, 2, false);
        this.token = arg4.a(3, false);
        if(ChannelInfo.cache_checksoft == null) {
            ChannelInfo.cache_checksoft = new ArrayList();
            ChannelInfo.cache_checksoft.add(new SoftKey());
        }

        this.checksoft = (ArrayList) arg4.a(ChannelInfo.cache_checksoft, 4, false);
    }

    public final void writeTo(HelperC arg3) {
        arg3.a(this.id, 0);
        if(this.product != 0) {
            arg3.a(this.product, 1);
        }

        if(this.isbuildin != 0) {
            arg3.a(this.isbuildin, 2);
        }

        if(this.token != null) {
            arg3.a(this.token, 3);
        }

        if(this.checksoft != null) {
            arg3.a(this.checksoft, 4);
        }
    }
}

