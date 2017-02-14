package com.root.executor;

import com.root.dao.IChUtils;
import com.root.dao.ICommUtil;

import java.util.List;

public final class ChUtilss implements IChUtils {
    private String a;
    ChUtilss(String arg1) {
        super();
        this.a = arg1;
    }

    public final Object a(ICommUtil arg2, List arg3) {
        return Boolean.valueOf(CommUtilsq.a(arg2, this.a));
    }
}

