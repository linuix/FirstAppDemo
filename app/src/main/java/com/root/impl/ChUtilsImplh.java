package com.root.impl;

import com.root.dao.IChUtils;
import com.root.dao.ICommUtil;
import com.root.executor.ChattrUtils;
import com.root.executor.CheckMountUtils;
import com.root.executor.ExecutorHelper;

import java.util.ArrayList;
import java.util.List;

public  final class ChUtilsImplh implements IChUtils {
   public   ChUtilsImplh() {
        super();
    }

    public final Object a(ICommUtil arg11, List arg12) {
        int v9 = 2;
        int v8 = -1;
        Object v0 = arg12.get(0);
        Object v1 = arg12.get(1);
        if(arg11.a()) {
            ArrayList v2 = new ArrayList();
            ((List)v2).add(CheckMountUtils.a);
            ((List)v2).add(ChattrUtils.a(((String)v0), false));
            if(((ExecutorHelper)v1).a != v8 && ((ExecutorHelper)v1).b != v8) {
                Object[] v4 = new Object[3];
                v4[0] = Integer.valueOf(((ExecutorHelper)v1).a);
                v4[1] = Integer.valueOf(((ExecutorHelper)v1).b);
                v4[v9] = v0;
                ((List)v2).add(String.format("chown %d.%d %s", v4));
            }

            if(((ExecutorHelper)v1).c != v8) {
                ((List)v2).add(String.format("chmod 0%o %s", Integer.valueOf(((ExecutorHelper)v1).c), 
                        v0));
            }

            if(((ExecutorHelper)v1).e != null) {
                ((List)v2).add(String.format("chcon %s %s", ((ExecutorHelper)v1).e, v0));
            }

            ((List)v2).add(ChattrUtils.a(((String)v0), true));
            ((List)v2).add(CheckMountUtils.b);
            arg11.a(((List)v2));
        }

        return null;
    }
}

