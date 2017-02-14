package com.root.impl;

import com.root.dao.IChUtils;
import com.root.dao.ICommUtil;
import com.root.executor.ChattrUtils;
import com.root.executor.CheckMountUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class ChUtilsImpln implements IChUtils {
    private String a;
    private String b;
    private boolean c;


    ChUtilsImpln(String arg1, String arg2, boolean arg3) {
        super();
        this.a = arg1;
        this.b = arg2;
        this.c = arg3;
    }

    public final Object a(ICommUtil arg7, List arg8) {
        if((arg7.a()) && (new File(this.a).exists())) {
            ArrayList v0 = new ArrayList(8);
            ((List)v0).add(CheckMountUtils.a);
            ((List)v0).add(String.format("cat %s > %s", this.a, this.b));
            ((List)v0).add("chown 0.0 " + this.b);
            ((List)v0).add("chmod 0755 " + this.b);
            if(this.c) {
                ((List)v0).add(ChattrUtils.a(this.a, false));
                ((List)v0).add("rm " + this.a);
            }

            ((List)v0).add(CheckMountUtils.b);
            arg7.a(((List)v0));
        }

        return null;
    }
}

