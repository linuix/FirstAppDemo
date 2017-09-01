package com.demo.entity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;

import com.demo.entity.util.PhoneInfoUtil;
import com.demo.entity.util.SystemUtils;
import com.demo.utils.FileUtils;
import com.demo.utils.LogUtil;
import com.demo.utils.SpfUtils;

import java.util.ArrayList;

import http.demo.UrlTest;
import http.demo.UserInfo;
import http.utils.NetUtils;
import http.utils.XmlFileSolute;

/**
 * Created by Administrator on 2016/10/18.
 * <p>
 * 管理需要用到的实体类型
 * 在application的初始化时赋初值
 */

public class EntityManager {
    public static ChannelInfo channelId;
    private static GetKingRootSolutionReq getKingRootSolutionReq;
    private static DeviceInfo deviceInfo;
    private static SUIKey suiKey;
    private static PhoneType phoneType;
    private static UserInfo userInfo;
    private static long i =0;
    private static ArrayList arrayList;
    private static ReportKingRootResultReq reportKingRootResultReq;
    public static String a;

    /**
     * 初始化使用的参数信息
     * <p>
     * abandent it
     **/
    public static void test(int arg11, Context arg12) {
        LogUtil.loge("EntityManager init  ");
        String v0;
        int v10 = 39;
        int v9 = 3;
        int v8 = 2;
        UserInfo tmpUserInfo = new UserInfo();
        tmpUserInfo.lc = "AA12CC01775BC76A";
        tmpUserInfo.buildno = 154;
        String[] v5 = new String[v9];
        v5[0] = "ro.mediatek.platform";
        v5[1] = "ro.build.hidden_ver";
        v5[v8] = "ro.product.model";
        int v6 = v5.length;
        int v3 = 0;
        while (true) {
            if (v3 < v6) {
                v0 = SystemProperties.get(v5[v3]);
                ++v3;
                if (TextUtils.isEmpty(((CharSequence) v0))) {
                    LogUtil.e("empty ---");
                    continue;
                }
            } else {
                break;
            }
            LogUtil.d("entitymgr v0 =" + v0);
            tmpUserInfo.ua = v0;
            tmpUserInfo.product = v10;
            tmpUserInfo.sdkversion = Build.VERSION.SDK_INT;
            String[] v3_1 = UrlTest.init().trim().split("[\\.]");
            ProductVersion v5_1 = new ProductVersion();
            int v0_1 = v3_1.length > 0 ? Integer.parseInt(v3_1[0]) : 1;
            v5_1.pversion = v0_1;
            v0_1 = v3_1.length >= v8 ? Integer.parseInt(v3_1[1]) : 0;
            v5_1.cversion = v0_1;
            v0_1 = v3_1.length >= v9 ? Integer.parseInt(v3_1[v8]) : 0;
            v5_1.hotfix = v0_1;
            tmpUserInfo.version = v5_1;
            EntityManager.userInfo = tmpUserInfo;

//----phonetype
            PhoneType v0_2 = new PhoneType();
            v0_2.phonetype = v8;
            v0_2.subplatform = 201;
            EntityManager.phoneType = v0_2;

            //-----getKingrootSolutionReq

            GetKingRootSolutionReq v0_6 = new GetKingRootSolutionReq();
            v0_6.deviceInfoXml = XmlFileSolute.getDeviceXmlInfo();
            v0_6.phoneType = EntityManager.phoneType;
            v0_6.callerProduct = arg11;
            EntityManager.getKingRootSolutionReq = v0_6;

        }
        v0 = Build.MODEL;
        LogUtil.d("EntityMgr finally v0 = " + v0);
        //-----------------
        /*for (v3 =0;v3<v6;v3++){
            v0 = SystemProperties.get(v5[v3]);
            if (TextUtils.isEmpty(((CharSequence) v0))) {
                ++v3;
                continue;
            }
            LogUtil.type("entitymanger v3 ="+v3);
            v0 = Build.MODEL;
            tmpUserInfo.ua = v0;
            tmpUserInfo.product = v10;
            tmpUserInfo.sdkversion = Build.VERSION.SDK_INT;
            userInfo=tmpUserInfo;
            String[] v3_1 = UrlTest.sdk_gt18().trim().split("[\\.]");
            PhoneType v0_2 = new PhoneType();
            v0_2.phonetype = v8;
            v0_2.subplatform = 201;
            EntityManager.phoneType = v0_2;
            ChannelInfo v0_3 = new ChannelInfo();
            v0_3.product = v10;
            v0_3.isbuildin = 0;
            PackageManager v1_1 = arg12.getPackageManager();
            try {
                //GET_UNINSTALLED_PACKAGES = 8192 ==0x00002000
                ApplicationInfo v1_4 = v1_1.getApplicationInfo(*//*"com.kingroot.RushRoot"*//*arg12.getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);

                if (v1_4 == null)
                {
                    LogUtil.loge("entitymanager appinfo ==nulll");
                    channelId = v0_3;
                    SUIKey v0_4 = new SUIKey();
                    v0_4.lc = "AA12CC01775BC76A";
                    v0_4.name = "EP_KingRoot_SDK";
                    v0_4.version = UrlTest.sdk_gt18().trim();
                    v0_4.type = v8;
                    v0_4.osversion = Build.VERSION.SDK;
                    v0_4.machineuid = Build.MODEL;
                    v0_4.machineconf = "screen=" + PhoneInfoUtil.widthPixels(arg12) + "*" + PhoneInfoUtil.heightPixels(arg12);
                    v0_4.subplatform = 0;
                    v0_4.isbuildin = 0;
                    EntityManager.suiKey = v0_4;
                    DeviceInfo v0_5 = new DeviceInfo();
                    v0_5.androidid = "android_id";
                    v0_5.sdkversion = new Integer(Build.VERSION.SDK).intValue();
                    v0_5.model = Build.MODEL;
                    v0_5.product = Build.PRODUCT;
                    v0_5.netfile = SystemUtils.getPhoneExternalInfo(arg12);
                    v0_5.lguid = "V2;99000479400232;24:4B:81:2D:FC:64";//stdout.stdout();
                    EntityManager.deviceInfo = v0_5;
                    GetKingRootSolutionReq v0_6 = new GetKingRootSolutionReq();
                    v0_6.deviceInfoXml = XmlFileSolute.getDeviceXmlInfo();//PhoneInfoUtil.getDeviceXmlInfo();
                    v0_6.phoneType = EntityManager.phoneType;
                    v0_6.callerProduct = arg11;
                    EntityManager.getKingRootSolutionReq = v0_6;
                    EntityManager.reportKingRootResultReq = new ReportKingRootResultReq();
                    EntityManager.arrayList = new ArrayList();
                    v0 = SPF.getMarsrootSharePreferences(arg12, "session_id");
                    if (v0 != null) {
                        try {
                            EntityManager.url = Long.parseLong(v0);
                            LogUtil.type("local sessionId = " + EntityManager.url);
                            return;
                        } catch (Exception v0_7) {
                            EntityManager.url = 0;
                        }
                    }
                }
                if ((v1_4.flags & 1) == 0) {//判断是否是系统级别的应用
                    SUIKey v0_4 = new SUIKey();
                    EntityManager.suiKey = v0_4;
                    DeviceInfo v0_5 = new DeviceInfo();
                    v0_5.androidid = "android_id";
                    v0_5.sdkversion = new Integer(Build.VERSION.SDK).intValue();
                    v0_5.model = Build.MODEL;
                    v0_5.product = Build.PRODUCT;
                    v0_5.netfile = SystemUtils.getPhoneExternalInfo(arg12);
                    v0_5.lguid = "V2;99000479400232;24:4B:81:2D:FC:64";//
                    EntityManager.deviceInfo = v0_5;
                    GetKingRootSolutionReq v0_6 = new GetKingRootSolutionReq();
                    v0_6.deviceInfoXml = XmlFileSolute.getDeviceXmlInfo();
                    v0_6.phoneType = EntityManager.phoneType;
                    v0_6.callerProduct = arg11;
                    EntityManager.getKingRootSolutionReq = v0_6;

                    EntityManager.reportKingRootResultReq = new ReportKingRootResultReq();
                    EntityManager.arrayList = new ArrayList();
                    v0 = SPF.getMarsrootSharePreferences(arg12, "session_id");

                    if (v0 != null) {
                        try {
                            EntityManager.url = Long.parseLong(v0);
                            LogUtil.type("local sessionId = " + EntityManager.url);
                            return;
                        } catch (Exception v0_7)
                        {
                            EntityManager.url = 0;
                        }
                    }
                }
                v0_3.isbuildin = 1;
            } catch (Exception fileSize) {
                fileSize.printStackTrace();
            }
        }*/
    }

    public static void a(long arg0) {
        EntityManager.i = arg0;
    }


    public static void setChannelId(String arg) {
        UserInfo v0 = EntityManager.userInfo;
        v0.channelid = arg;
        a = arg;

    }

    private static boolean fromSolution=false;

    public static void setFromSolution(boolean fromSolution) {
        EntityManager.fromSolution = fromSolution;
    }

    public static UserInfo getUserInfo(Context context) {
        LogUtil.loge("getUserInfo " + context);
        int connectFlag;
        UserInfo userInfo;
        userInfo = EntityManager.userInfo;
        userInfo.imei = PhoneInfoUtil.getIMEI(context);
        userInfo.imsi = PhoneInfoUtil.getIMSI(context);
        if (NetUtils.isConnection(context) == 0) {
            connectFlag = 2;
        } else {
            connectFlag = 1;
        }
        userInfo.netFlag = connectFlag;
        userInfo.guid = FileUtils.getGuid();//读取网络下发数据的放回值
//        if (fromSolution)
//        {
//            userInfo.guid="V2;99000479400232;24:4B:81:2D:FC:64";
//        }
        LogUtil.loge("getUserInfo ----" + EntityManager.userInfo);
        return userInfo;
    }

    public static GetKingRootSolutionReq getKingRootSolutionReq() {

        LogUtil.loge("getRootSolutionReq  -->" + getKingRootSolutionReq);
        return EntityManager.getKingRootSolutionReq;
    }

    public static PhoneType getPhoneType() {
        return phoneType;
    }
    /**
     * 网络请求解决方案的初始化对象方法
     * <p>
     * 修改使用这个方式
     *
     *
     * 读取各种信息并将信息放入<code>EntityManager<code/>中
     */

    public static void init(int arg11, Context context) {
        String info;
        int v10 = 39;
        int v9 = 3;
        int v8 = 2;
        UserInfo userInfo = new UserInfo();
        userInfo.lc = "AA12CC01775BC76A";
        userInfo.buildno = 154;
        String[] build_prop_keys = new String[v9];
        build_prop_keys[0] = "ro.mediatek.platform";
        build_prop_keys[1] = "ro.build.hidden_ver";
        build_prop_keys[v8] = "ro.product.model";    //手机型号信息
        int v6 = build_prop_keys.length;
        int v3 = 0;
        while(true) {
            if (v3 < v6) {
                info = SystemProperties.get(build_prop_keys[v3]);
                ++v3;
                if (TextUtils.isEmpty(info)) {
                    continue;
                }
            }
            else {
                break;
            }
            userInfo.ua = info;
            userInfo.product = v10;
            userInfo.sdkversion = Build.VERSION.SDK_INT;
            String[] versionStrs = UrlTest.init().trim().split("[\\.]");
            ProductVersion productVersion = new ProductVersion();
            int v0_1 = versionStrs.length > 0 ? Integer.parseInt(versionStrs[0]) : 1;
            productVersion.pversion = v0_1;
            v0_1 = versionStrs.length >= v8 ? Integer.parseInt(versionStrs[1]) : 0;
            productVersion.cversion = v0_1;
            v0_1 = versionStrs.length >= v9 ? Integer.parseInt(versionStrs[v8]) : 0;
            productVersion.hotfix = v0_1;
            userInfo.version = productVersion;

            EntityManager.userInfo = userInfo;
            PhoneType phoneType = new PhoneType();
            phoneType.phonetype = v8;
            phoneType.subplatform = 201;
            EntityManager.phoneType = phoneType;
            ChannelInfo channelInfo = new ChannelInfo();
            channelInfo.product = v10;
            channelInfo.isbuildin = 0;
            PackageManager packageManager = context.getPackageManager();
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
                if (applicationInfo == null)
                {

                    EntityManager.channelId = channelInfo;
                    SUIKey v0_4 = new SUIKey();
                    v0_4.lc = "AA12CC01775BC76A";

                    v0_4.name = "EP_KingRoot_SDK";
                    v0_4.version = UrlTest.init().trim();
                    v0_4.type = v8;

                    v0_4.osversion = Build.VERSION.SDK;
                    v0_4.machineuid = Build.MODEL;

                    v0_4.machineconf = "screen=" + PhoneInfoUtil.widthPixels(context) + "*" + PhoneInfoUtil.heightPixels(context);
                    v0_4.subplatform = 0;
                    v0_4.isbuildin = 0;

                    EntityManager.suiKey = v0_4;
                    DeviceInfo v0_5 = new DeviceInfo();

                    v0_5.androidid = "android_id";
                    v0_5.sdkversion = new Integer(Build.VERSION.SDK).intValue();

                    v0_5.model = Build.MODEL;
                    v0_5.product = Build.PRODUCT;

                    v0_5.netfile = SystemUtils.getPhoneExternalInfo(context);
                    v0_5.lguid = FileUtils.getLguid();//读取属性文件w.md5 ///高版本的时候出现异常信息，导致后续不能执行
                    EntityManager.deviceInfo = v0_5;

                    GetKingRootSolutionReq v0_6 = new GetKingRootSolutionReq();

                    v0_6.deviceInfoXml = XmlFileSolute.getDeviceXmlInfo();

                    v0_6.phoneType = EntityManager.phoneType;
                    v0_6.callerProduct = arg11;

                    EntityManager.getKingRootSolutionReq = v0_6;
                    LogUtil.e("getkrsolution obi " + v0_6);

                    EntityManager.reportKingRootResultReq = new ReportKingRootResultReq();
                    EntityManager.arrayList = new ArrayList();
                    info = SpfUtils.getMarsrootSharePreferences(context, "session_id");

                    if (info != null)
                    {
                        try {
                            EntityManager.i = Long.parseLong(info);
                            LogUtil.w("local sessionId = " + EntityManager.i);
                            return;
                        } catch (Exception v0_7) {
                            EntityManager.i = 0;
                        }
                    }
                    LogUtil.w("no local sessionId 1");
                }

                if ((applicationInfo.flags & 1) == 0)
                {
                    EntityManager.channelId = channelInfo;
                    SUIKey suiKey = new SUIKey();
                    suiKey.lc = "AA12CC01775BC76A";
                    suiKey.name = "EP_KingRoot_SDK";
                    suiKey.version = UrlTest.init().trim();
                    suiKey.type = v8;
                    suiKey.osversion = Build.VERSION.SDK;
                    suiKey.machineuid = Build.MODEL;
                    suiKey.machineconf = "screen=" + PhoneInfoUtil.widthPixels(context) + "*" + PhoneInfoUtil.heightPixels(context);
                    suiKey.subplatform = 0;
                    suiKey.isbuildin = 0;
                    EntityManager.suiKey = suiKey;
                    DeviceInfo deviceInfo = new DeviceInfo();
                    deviceInfo.androidid = "android_id";
                    deviceInfo.sdkversion = new Integer(Build.VERSION.SDK).intValue();
                    deviceInfo.model = Build.MODEL;
                    deviceInfo.product = Build.PRODUCT;
                    deviceInfo.netfile = SystemUtils.getPhoneExternalInfo(context);
                    deviceInfo.lguid = FileUtils.getLguid();//读取属性文件w.go

                    EntityManager.deviceInfo = deviceInfo;
                    GetKingRootSolutionReq getKingRootSolutionReq = new GetKingRootSolutionReq();
                    getKingRootSolutionReq.deviceInfoXml = XmlFileSolute.getDeviceXmlInfo();
                    getKingRootSolutionReq.phoneType = EntityManager.phoneType;
                    getKingRootSolutionReq.callerProduct = arg11;
                    EntityManager.getKingRootSolutionReq = getKingRootSolutionReq;
                    EntityManager.reportKingRootResultReq = new ReportKingRootResultReq();

                    EntityManager.arrayList = new ArrayList();
                    info = SpfUtils.getMarsrootSharePreferences(context, "session_id");
                    if (info != null) {
                        try {
                            EntityManager.i = Long.parseLong(info);
                            LogUtil.w("local sessionId = " + EntityManager.i);
                            return;
                        } catch (Exception v0_7) {
                            EntityManager.i = 0;
                        }
                    }
                    LogUtil.w("no local sessionId  2");
                }
                channelInfo.isbuildin = 1;
            } catch (Exception e) {
                LogUtil.exception("entitymgr exception", e);
            }
        }
        info = Build.MODEL;
    }

    public  static SUIKey getSuiKey(Context context)
    {
        SUIKey suiKey = EntityManager.suiKey;
        suiKey.guid = FileUtils.getLguid();
        suiKey.imei = FileUtils.getGuid();
        suiKey.imsi = PhoneInfoUtil.getIMSI(context);
        return  suiKey;

    }


    public  static DeviceInfo getDeviceInfo(Context context)
    {
        DeviceInfo ret = deviceInfo;
        ret.imei = PhoneInfoUtil.getIMEI(context);
        ret.imsi = PhoneInfoUtil.getIMSI(context);
        ret.mac = PhoneInfoUtil.getMac(context);
        ret.iccid = PhoneInfoUtil.getICCID(context);
        return  ret;
    }

    public  static ReportKingRootResultReq getReportRootResult()
    {
        ReportKingRootResultReq reportKingRootResultReq = EntityManager.reportKingRootResultReq;
        if (reportKingRootResultReq == null)
        {
            reportKingRootResultReq= new ReportKingRootResultReq();
        }
        reportKingRootResultReq.sessionId = EntityManager.i;
        reportKingRootResultReq.kingRootResults = arrayList;
        reportKingRootResultReq.mac = deviceInfo.mac;
        reportKingRootResultReq.prevSuVersion = SystemUtils.getPrevSuVersion();
        return reportKingRootResultReq;
    }

}



