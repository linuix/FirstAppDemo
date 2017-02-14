package com.root.impl;

import com.root.dao.IChUtils;
import com.root.dao.ICommUtil;
import com.root.executor.ChattrUtils;

import java.util.ArrayList;
import java.util.List;

public final class ChUtilImplk implements IChUtils {
  public  ChUtilImplk() {
        super();
    }

    public final Object a(ICommUtil arg6, List arg7) {
        if(arg6.a()) {
            ArrayList v0 = new ArrayList(5);
            ((List)v0).add(ChattrUtils.a("/data/system/tmp_init.rc", false));
            ((List)v0).add("rm /data/system/tmp_init.rc");
            ((List)v0).add(String.format("cat %s > %s", "/init.rc", "/data/system/tmp_init.rc"));
            ((List)v0).add("chmod 0755 /data/system/tmp_init.rc");
            arg6.a(((List)v0));
        }

        return null;
    }
}

