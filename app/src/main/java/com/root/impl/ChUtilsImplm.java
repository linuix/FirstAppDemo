package com.root.impl;

import com.root.dao.IChUtils;
import com.root.dao.ICommUtil;
import com.root.executor.ChattrUtils;
import com.root.executor.CheckMountUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class ChUtilsImplm implements IChUtils {
    private String a;
    private String b;
    ChUtilsImplm(String arg1, String arg2) {
        super();
        this.a = arg1;
        this.b = arg2;
    }

    public final Object a(ICommUtil arg9, List arg10) {
        int v7 = 2;
        if(arg9.a()) {
            ArrayList v0 = new ArrayList(8);
            ((List)v0).add(CheckMountUtils.a);
            ((List)v0).add(ChattrUtils.a(this.a, false));
            if(new File(this.a).exists()) {
                ((List)v0).add(String.format("cat %s > %s", this.a, String.valueOf(this.a) + "-ku.bak"));
                ((List)v0).add("rm " + this.a);
            }

            ((List)v0).add(String.format("cat %s > %s", this.b, this.a));
            ((List)v0).add("chown 0.0 " + this.a);
            ((List)v0).add("chmod 0755 " + this.a);
            ((List)v0).add("chcon u:object_r:system_file:s0 " + this.a);
            ((List)v0).add("rm " + this.b);
            ((List)v0).add(ChattrUtils.a(this.a, true));
            ((List)v0).add(CheckMountUtils.b);
            arg9.a(((List)v0));
        }

        return null;
    }
}

