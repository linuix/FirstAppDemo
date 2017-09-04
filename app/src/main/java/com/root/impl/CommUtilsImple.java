package com.root.impl;


import com.demo.process.RetValue;
import com.demo.utils.LogUtil;
import com.root.dao.AbsCommUtila;
import com.root.dao.ICommUtil;
import com.root.util.SelinuxUtils;

public final class CommUtilsImple extends AbsCommUtila {
    public CommUtilsImple() {
        super();
    }

    public final boolean a(ICommUtil arg4) {
        boolean v0 = true;
        if(SelinuxUtils.sdk() >= 14) {
            RetValue v1 = arg4.a("ku.sud --ping");
            if((v1.isSuccess()) && (v1.stdout.trim().equals("kinguser_su"))) {
                return v0;
            }

            v0 = false;
        }

        return v0;
    }

    public final boolean b(ICommUtil arg3) {
        LogUtil.d("check_su_files start daemon");
        if(arg3.a()) {
            arg3.a("/system/xbin/ku.sud -type");
        }
        return true;
    }
}

