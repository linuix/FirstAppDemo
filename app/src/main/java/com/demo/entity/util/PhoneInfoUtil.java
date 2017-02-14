package com.demo.entity.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Administrator on 2016/10/18.
 * 手获取手机参数工具
 */

public class PhoneInfoUtil {
    public static String getIMEI(Context context) {
        TelephonyManager telmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telmgr.getDeviceId();
        if (deviceId != null) {
            return deviceId;
        }
        return "";
    }
    public static String getIMSI(Context context) {
        TelephonyManager telmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String subscriberId = telmgr.getSubscriberId();
        if (subscriberId != null) {
            return subscriberId;
        }
        return "";
    }
    public static String getMac(Context arg3) {
        String v0_2;
        try {
            WifiManager v0_1 = (WifiManager) arg3.getSystemService(Context.WIFI_SERVICE);
            WifiInfo connectionInfo = v0_1.getConnectionInfo();
            if (connectionInfo == null) {
                v0_2 = null;
                return v0_2;
            }
            v0_2 = connectionInfo.getMacAddress();
        } catch (Exception v0) {
            Log.e("PhoneInfoUtil", "skipping getMacAddress()", ((Throwable) v0));
            v0_2 = "00:00:00:00:00:01";
        }

        return v0_2;
    }
    public static String getICCID(Context context) {
        String v0_1;
        try {
            TelephonyManager telmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            v0_1 = telmgr.getDeviceId();
        } catch (Exception v0) {
            Log.e("PhoneInfoUtil", "skipping getSimSerialNumber()", ((Throwable) v0));
            v0_1 = "0000001";
        }

        return v0_1;
    }

    public static int widthPixels(Context arg1) {
        return arg1.getResources().getDisplayMetrics().widthPixels;
    }

    public static int heightPixels(Context arg1) {
        return arg1.getResources().getDisplayMetrics().heightPixels;
    }


    //-----------------------------------------------------------------------


}
