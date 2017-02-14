package com.demo.entity;

import android.util.Log;

import http.helper.HelperA;
import http.helper.HelperC;


public final class ProductVersion extends JceStruct {
    public int cversion;
    public int hotfix;
    public  int pversion;

    public ProductVersion() {
        super();
        this.pversion = 0;
        this.cversion = 0;
        this.hotfix = 0;
    }

    @Override
    public void readFrom(HelperA arg4) {
        this.pversion = arg4.a(this.pversion, 1, true);
        this.cversion = arg4.a(this.cversion, 2, true);
        this.hotfix = arg4.a(this.hotfix, 3, true);
    }

    @Override
    public void writeTo(HelperC arg3) {
        arg3.a(this.pversion, 1);
        arg3.a(this.cversion, 2);
        arg3.a(this.hotfix, 3);
    }

    public final int compareVersion(ProductVersion arg5) {
        int v0 = 1;
        int v1 = -1;
        if(this.pversion <= arg5.pversion) {
            if(this.pversion == arg5.pversion) {
                if(this.cversion > arg5.cversion) {
                    Log.d("kingroot-sdk", "compareVersion result = " + v0);
                    return v0;
                }
                else if(this.cversion == arg5.cversion) {
                    if(this.hotfix > arg5.hotfix) {
                        Log.d("kingroot-sdk", "compareVersion result = " + v0);
                        return v0;
                    }
                    else if(this.hotfix == arg5.hotfix) {
                        v0 = 0;
                        Log.d("kingroot-sdk", "compareVersion result = " + v0);
                        return v0;
                    }
                }
            }

            v0 = v1;
        }
        return v0;
    }


    @Override
    public String toString() {
        return "ProductVersion{" +
                "cversion=" + cversion +
                ", hotfix=" + hotfix +
                ", pversion=" + pversion +
                '}';
    }
}

