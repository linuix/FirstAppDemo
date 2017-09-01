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
        this.phonetype = arg4.getDataForBuffer(this.phonetype, 0, true);
        this.subplatform = arg4.getDataForBuffer(this.subplatform, 1, false);
    }

    public final void writeTo(HelperC helperC) {
        helperC.addIntData(this.phonetype, 0);
        if(this.subplatform != 0) {
            helperC.addIntData(this.subplatform, 1);
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

