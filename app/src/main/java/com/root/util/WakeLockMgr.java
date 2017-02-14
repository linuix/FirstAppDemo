package com.root.util;

import android.content.Context;
import android.os.PowerManager;

import com.demo.utils.LogUtil;
import com.root.dao.IChUtils;
import com.root.dao.ICommUtil;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/11/8.
 */

public class WakeLockMgr {
    private static int a;
    private static PowerManager.WakeLock b;
    static {
        WakeLockMgr.a = 0;
        WakeLockMgr.b = null;
    }
    public WakeLockMgr() {
        super();
    }
    private static void a() {

        if(WakeLockMgr.a > 0) {
            --WakeLockMgr.a;
        }
        LogUtil.d("wl_mgr WakeLockMgr|release, count:" + WakeLockMgr.a);
        if(WakeLockMgr.a <= 0) {
            PowerManager.WakeLock v0_2 = null;
            WakeLockMgr.b = v0_2;
        }
        if(WakeLockMgr.b == null) {
            PowerManager.WakeLock v0_2 = null;
            WakeLockMgr.b = v0_2;
        }
        if(!WakeLockMgr.b.isHeld()) {
            PowerManager.WakeLock v0_2 = null;
            WakeLockMgr.b = v0_2;
        }
        WakeLockMgr.b.release();
    }

    public static Object a(ICommUtil arg4, IChUtils arg5, Object[] arg6) {
        Object v0_4 = null;
        ++WakeLockMgr.a;
        LogUtil.d("wl_mgr WakeLockMgr|lock, count:" + WakeLockMgr.a);
        if(WakeLockMgr.a > 1) {
            v0_4 = arg5.a(arg4, Arrays.asList(arg6)); // <=>ChUtilsd.sdk_gt18(arg4, Arrays.asList(arg6))
        }
        Context v0_1 = KuSdkInitHelper.a();
        PowerManager systemService = (PowerManager) v0_1.getSystemService(Context.POWER_SERVICE);
        WakeLockMgr.b =  systemService.newWakeLock(536870913, "wk_mgr");
        if(WakeLockMgr.b != null) {
            if(WakeLockMgr.b == null) {
                v0_4 = arg5.a(arg4, Arrays.asList(arg6));
            }
            if(WakeLockMgr.b.isHeld()) {
                v0_4 = arg5.a(arg4, Arrays.asList(arg6));
            }
            WakeLockMgr.b.acquire();
            LogUtil.d("wl_mgr WakeLockMgr|acquire lock");
        }
        WakeLockMgr.a();
        return v0_4;

    }
}
