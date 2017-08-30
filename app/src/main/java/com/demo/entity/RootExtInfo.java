package com.demo.entity;


import http.helper.HelperA;
import http.helper.HelperC;

public final class RootExtInfo extends JceStruct {
    public int canRoot;
    public int succRate;
    public int succUsers;
    public int useTime;

    public RootExtInfo() {
        super();
        this.canRoot = 0;
        this.useTime = 0;
        this.succUsers = 0;
        this.succRate = 0;
    }

    public final void readFrom(HelperA arg4) {
        this.canRoot = arg4.a(this.canRoot, 0, false);
        this.useTime = arg4.a(this.useTime, 1, false);
        this.succUsers = arg4.a(this.succUsers, 2, false);
        this.succRate = arg4.a(this.succRate, 3, false);
    }

    public final void writeTo(HelperC arg3) {
        if(this.canRoot != 0) {
            arg3.addIntData(this.canRoot, 0);
        }

        if(this.useTime != 0) {
            arg3.addIntData(this.useTime, 1);
        }

        if(this.succUsers != 0) {
            arg3.addIntData(this.succUsers, 2);
        }

        if(this.succRate != 0) {
            arg3.addIntData(this.succRate, 3);
        }
    }
}

