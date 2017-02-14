package com.demo.entity;


import http.helper.HelperA;
import http.helper.HelperC;

public final class PhoneType extends JceStruct {
    public int phonetype;
    public int subplatform;

    public PhoneType() {
        super();
        this.phonetype = 0;
        this.subplatform = 0;
    }

    public final void readFrom(HelperA arg4) {
        this.phonetype = arg4.a(this.phonetype, 0, true);
        this.subplatform = arg4.a(this.subplatform, 1, false);
    }

    public final void writeTo(HelperC arg3) {
        arg3.a(this.phonetype, 0);
        if(this.subplatform != 0) {
            arg3.a(this.subplatform, 1);
        }
    }

    @Override
    public String toString() {
        return "PhoneType{" +
                "phonetype=" + phonetype +
                ", subplatform=" + subplatform +
                '}';
    }
}

