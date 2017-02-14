package com.root;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/5.
 */

public class RootHelper {

    public String a;
    public String init;
    public Class[] c;
    public String root;
    public Class[] e;
    public String destroy;
    public Class[] g;
    public String getShell;
    public Class[] i;
    public String executeCommand;
    public Class[] k;
    public String closeShell;
    public Class[] m;
    public String init2;
    public Class[] o;

    public RootHelper() {
        this.a = "krsdk.XSolution";
        this.init = "init";
        this.c = new Class[]{Context.class, String.class, ClassLoader.class};
        this.root = "root";
        this.e = new Class[]{Context.class};
        this.destroy = "destroy";
        this.g = new Class[]{Context.class};
        this.getShell = "getShell";
        this.i = new Class[0];
        this.executeCommand = "executeCommand";
        this.k = new Class[]{Object.class, String.class};
        this.closeShell = "closeShell";
        this.m = new Class[]{Object.class};
        this.init2 = "init2";
        this.o = new Class[]{Context.class, ClassLoader.class, HashMap.class};
    }
}
