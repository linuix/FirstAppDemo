package com.root.executor;

import com.demo.utils.LogUtil;
import com.root.dao.ICommUtil;
import com.root.dao.ICommoUtil2;
import com.root.helper.ExecutHelper;
import com.root.impl.CommUtilsImple;
import com.root.impl.CommUtilsImpll;
import com.root.util.KuSdkInitHelper;
import com.root.util.SelinuxUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 * <p>
 * 重要的工具类。
 * 包括了守护root过程种的各种异常信息
 */

public class RootUtils4 {
    private static Object a;

    static {
        RootUtils4.a = new Object();
    }

    public static boolean a(ICommUtil arg12) {
        LogUtil.d("rootutils4 start");
        boolean v0_1 = true;
        boolean v1_1;
        Iterator v2;
        Object v9 = RootUtils4.a;
        try {
            ArrayList v10 = new ArrayList();
            ((List) v10).add(new CommUtilsImpll());
            ((List) v10).add(new CommUtilc(new ExecutHelper(493, 14, false, "u:object_r:system_file:s0",
                    KuSdkInitHelper.b(), "/system/xbin/ku.sud")));
            ((List) v10).add(new CommUtilsImple());
            ((List) v10).add(new CommUtilc(new ExecutHelper(493, -1, false, "u:object_r:system_file:s0",
                    "/system/bin/sh", "/system/bin/rt.sh")));
            ((List) v10).add(new CommUtilc(new ExecutHelper(493, 20, true, "u:object_r:zygote_exec:s0",
                    KuSdkInitHelper.b(), "/system/xbin/kugote")));
            ((List) v10).add(new CommUtilsq());
            ArrayList v11 = new ArrayList();
            String v5 = KuSdkInitHelper.b();//这里获取到的是mysu文件地址
            if (v5 != null) {
                if (!SelinuxUtils.a()) {
                    ((List) v11).add(new CommUtilc(new ExecutHelper(3565, -1, false, "u:object_r:system_file:s0",
                            v5, "/system/usr/iku/isu")));
                }

                ((List) v11).add(new CommUtilc(new ExecutHelper(3565, -1, false, "u:object_r:system_file:s0",
                        v5, "/system/bin/.usr/.ku")));
            }
            ((List) v10).addAll(((Collection) v11));
            v2 = ((List) v10).iterator();
            int v1;
            for (v1 = 0; v2.hasNext(); v1 = 1) {
                if (((ICommoUtil2) v2.next()).c(arg12)) {
                    continue;
                }
            }

            if (v1 != 0) {

                v2 = ((List) v10).iterator();
                v1_1 = true;

                do {
                    if (v2.hasNext()) {
                        Object v0_2 = v2.next();
                        if (!((ICommoUtil2) v0_2).a()) {
                            continue;
                        }

                        if (!((ICommoUtil2) v0_2).d(arg12)) {
                            break;
                        } else {
                            continue;
                        }
                    } else {
                        v0_1 = v1_1;
                    }
                    return v0_1;
                } while (true);
            } else {
                return v0_1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.exception("RootUtils4 exception ", e);
        }
        return v0_1;
    }
}
