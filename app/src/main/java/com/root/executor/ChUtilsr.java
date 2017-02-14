package com.root.executor;

import com.root.dao.IChUtils;
import com.root.dao.ICommUtil;

import java.util.ArrayList;
import java.util.List;

public final class ChUtilsr implements IChUtils {
    private CommUtilsq a;

    ChUtilsr(CommUtilsq arg1) {
        super();
        this.a = arg1;
    }

    public final Object a(ICommUtil arg4, List arg5) {
        if(arg4.a()) {
            ArrayList v0 = new ArrayList(7);
            ((List)v0).add(CheckMountUtils.a);
            ((List)v0).add(CheckMountUtils.c);
            ((List)v0).add(ChattrUtils.a("/sbin/su", false));
            ((List)v0).add("rm /sbin/su");
            ((List)v0).add(ChattrUtils.a("/vendor/bin/su", false));
            ((List)v0).add("rm /vendor/bin/su");
            ((List)v0).add(ChattrUtils.a("/system/sbin/su", false));
            ((List)v0).add("rm /system/sbin/su");
            ((List)v0).add(CheckMountUtils.b);
            ((List)v0).add(CheckMountUtils.d);
            arg4.a(((List)v0));
        }

        return null;
    }
}

