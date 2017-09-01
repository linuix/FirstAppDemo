package com.demo.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;

import com.demo.App;
import com.demo.entity.ChannelInfo;
import com.demo.entity.DeviceInfo;
import com.demo.entity.PhoneType;
import com.demo.entity.SUIKey;
import com.demo.entity.util.PhoneInfoUtil;
import com.demo.entity.util.SystemUtils;

import http.demo.UserInfo;

/**
 * Created by Administrator on 2016/10/19.
 *
 * 这里的channelId，直接写死了，105006，冲assets目录下读取的channelId 15001可能是备用的渠道id
 */

public class ManagerInfo {
    private static UserInfo userInfor;
    private static PhoneType phoneType;
    private static ChannelInfo channelInfo;
    private static SUIKey suikey;
    private static DeviceInfo deviceInfo;

    private static String getVerionName() {
        PackageManager packageManager = App.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(App.getContext().getPackageName(), 0);
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void init(Context context) {
        String v0;
        int v10 = 14;
        int v9 = 3;
        int v8 = 2;
        UserInfo userInfo = new UserInfo();
        userInfo.lc = "761FF612569141E9";
        userInfo.channelid = Const.CHANNELID;//FileUtils.getChannelId(App.getContext());
        userInfo.buildno = 249;
        String[] v5 = new String[v9];
        v5[0] = "ro.mediatek.platform";
        v5[1] = "ro.build.hidden_ver";
        v5[v8] = "ro.product.model";
        int v3 ;
        for (v3 =0;v3<v5.length;v3++){
            v0 = SystemProperties.get(v5[v3]);
            if (TextUtils.isEmpty(v0)) {
                ++v3;
                continue;
            }

            v0 = Build.MODEL;
            userInfo.ua = v0;
            userInfo.product = v10;
            userInfo.sdkversion = Build.VERSION.SDK_INT;
            String[] v3_1 = getVerionName().trim().split("[\\.]");
//            userInfo.version = new ProductVersion();
//            ProductVersion v5_1 = userInfo.version;
            int v0_1 = v3_1.length > 0 ? Integer.parseInt(v3_1[0]) : 1;
//            v5_1.pversion = v0_1;
//            v5_1 = userInfo.version;
            v0_1 = v3_1.length >= v8 ? Integer.parseInt(v3_1[1]) : 0;
//            v5_1.cversion = v0_1;
//            ProductVersion v1 = userInfo.version;
            v0_1 = v3_1.length >= v9 ? Integer.parseInt(v3_1[v8]) : 0;
//            v1.hotfix = v0_1;
            ManagerInfo.userInfor = userInfo;
            PhoneType v0_2 = new PhoneType();
            v0_2.phonetype = v8;
            v0_2.subplatform = 201;
            ManagerInfo.phoneType = v0_2;
            ChannelInfo channelInfo = new ChannelInfo();
            channelInfo.id = FileUtils.getChannelId(App.getContext());
            channelInfo.product = v10;
            channelInfo.isbuildin = 0;
            PackageManager packageManager = context.getPackageManager();
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
                if (applicationInfo == null) {
                    ManagerInfo.channelInfo = channelInfo;
                    SUIKey v0_4 = new SUIKey();
                    v0_4.lc = "761FF612569141E9";
                    v0_4.name = "EP_KingRoot";
                    v0_4.version = getVerionName().trim();
                    v0_4.type = v8;
                    v0_4.osversion = Build.VERSION.SDK;
                    v0_4.machineuid = (Build.MODEL);
                    v0_4.machineconf = "screen=" + PhoneInfoUtil.widthPixels(context) + "*" + PhoneInfoUtil.heightPixels(context);
                    v0_4.subplatform = 0;
                    v0_4.channelid = Const.CHANNELID;//FileUtils.getChannelId(App.getContext());
                    v0_4.isbuildin = 0;
                    ManagerInfo.suikey = v0_4;
                    DeviceInfo v0_5 = new DeviceInfo();
                    v0_5.androidid = "android_id";
                    v0_5.sdkversion = new Integer(Build.VERSION.SDK).intValue();
                    v0_5.model = Build.MODEL;
                    v0_5.product = (Build.PRODUCT);
                    v0_5.netfile = (SystemUtils.getPhoneExternalInfo(context));
                    v0_5.lguid = "V2;99000479400232;24:4B:81:2D:FC:64";  // 假设这里为空
                    ManagerInfo.deviceInfo = v0_5;
                }
                if ((applicationInfo.flags & 1) == 0) {
                    ManagerInfo.channelInfo = channelInfo;
                    SUIKey suiKey = new SUIKey();
                    suiKey.lc = "761FF612569141E9";
                    suiKey.name = "EP_KingRoot";
                    suiKey.version = getVerionName().trim();
                    suiKey.type = v8;
                    suiKey.osversion = Build.VERSION.SDK;
                    suiKey.machineuid = (Build.MODEL);
                    suiKey.machineconf = "screen=" + PhoneInfoUtil.widthPixels(context) + "*" + PhoneInfoUtil.heightPixels(context);
                    suiKey.subplatform = 0;
                    suiKey.channelid = Const.CHANNELID;//FileUtils.getChannelId(App.getContext());
                    suiKey.isbuildin = 0;
                    ManagerInfo.suikey = suiKey;
                    DeviceInfo deviceInfo = new DeviceInfo();
                    deviceInfo.androidid = "android_id";
                    deviceInfo.sdkversion = new Integer(Build.VERSION.SDK).intValue();
                    deviceInfo.model = Build.MODEL;
                    deviceInfo.product = (Build.PRODUCT);
                    deviceInfo.netfile = (SystemUtils.getPhoneExternalInfo(context));
                    deviceInfo.lguid = "V2;99000479400232;24:4B:81:2D:FC:64";  // 假设这里为空
                    ManagerInfo.deviceInfo = deviceInfo;
                }
                channelInfo.isbuildin = 1;
                v3++;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public static ChannelInfo b() {
        return ManagerInfo.channelInfo;
    }

    public static UserInfo getUserInfo(Context arg3) {
        int v0_1;
        UserInfo v2;
        v2 = ManagerInfo.userInfor;
        v2.imei = PhoneInfoUtil.getIMEI(App.getContext());
        v2.imsi = PhoneInfoUtil.getIMSI(App.getContext());
        if(Utils.checkNet(arg3) == 0) {
            v0_1 = 2;
        }
        else {
            v0_1 = 1;
        }
        v2.netFlag = v0_1;
        v2.guid = "V2;99000479400232;24:4B:81:2D:FC:64";
        return v2;

    }

    public static SUIKey c(Context arg3) {
        SUIKey v0_1 =null;
        Class v1 = ManagerInfo.class;
        try {
            v0_1 = ManagerInfo.suikey;
            v0_1.guid = "";
            v0_1.imei = PhoneInfoUtil.getIMEI(App.getContext());
            v0_1.imsi = PhoneInfoUtil.getIMSI(App.getContext());
        }
        catch(Throwable v0) {
        }

        return v0_1;
    }
    public static DeviceInfo d(Context arg3) {
        DeviceInfo v0_1 =null;
        Class v1 = ManagerInfo.class;
        try {
            v0_1 = ManagerInfo.deviceInfo;
            v0_1.imei = PhoneInfoUtil.getIMEI(App.getContext());
            v0_1.imsi = PhoneInfoUtil.getIMSI(App.getContext());
            v0_1.mac =PhoneInfoUtil.getMac(App.getContext());
            v0_1.iccid = PhoneInfoUtil.getICCID(App.getContext());
        }
        catch(Throwable v0) {
        }

        return v0_1;
    }

}
