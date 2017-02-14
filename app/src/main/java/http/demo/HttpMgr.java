package http.demo;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.demo.entity.DeviceInfo;
import com.demo.entity.EntityManager;
import com.demo.entity.GUIDInfo;
import com.demo.entity.GetKingRootSolutionReq;
import com.demo.entity.GetKingRootSolutionResp;
import com.demo.solution.SolutionManager;
import com.demo.utils.Const;
import com.demo.utils.FileUtils;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.kingroot.sdk.util.Cryptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import http.helper.NetConfigration;
import http.thread.GuidThread;
import http.thread.RootReportThread;
import http.utils.HttpUtils;
import http.utils.XmlFileSolute;

/**
 * Created by Administrator on 2016/11/3.
 */

public class HttpMgr {

    private static String a;
    private static Helpers[] b;

    private static Context mContext;

    public HttpMgr(Context context)
    {
        mContext = context;
    }

    /**
     * kr-stock-conf 线程
     */
    public static void initKrsdkStockConf(Context context, Handler handler) {
        handler.post(new GuidThread(context));

    }

    public static void reportRootResult(Context context, Handler handler) {
        handler.post(new RootReportThread(context));
    }

    /**
     * 上报root结果的是时候，调用这个接口
     */
    public static int reportRootResult(Context arg1, NetConfigration arg2, NetConfigration arg3) {
        return HttpMgr.a(arg1, arg2, arg3, true);
    }

    /**
     * 外部调用接口,在初始化kr-stock-conf的时候，会调用这个接口
     */
    public static int initKrsdkStockConf(Context context, NetConfigration arg2, NetConfigration arg3) {
        return a(context, arg2, arg3, false);
    }

    static {
        HttpMgr.a = UrlTest.b;
        HttpMgr.b = new Helpers[]{new Helpers(0, "info|getUpdatesV2"), new Helpers(1, "conf|getConfigV2"),
                new Helpers(2, "kinguser|getSoftStatus"), new Helpers(3, "kinguser|reportSoftStatus"),
                new Helpers(4, "info|reportSoftList"), new Helpers(5, "kinguser|reportMsg"), new Helpers(
                6, "tipsmain|getMainTips"), new Helpers(7, "softupdate|getSoftUpdateTips"), new Helpers(
                8, "conf|getConfigTips"), new Helpers(9, "cmdactivity|getActivityTips"), new Helpers(
                10, "info|reportTipsRes"), new Helpers(11, "info|reportChannelInfo"), new Helpers(12,
                "report|reportSoftUsageInfo"), new Helpers(13, "info|getGuid"), new Helpers(14, "kinguserreport|reportKUStatus"),
                new Helpers(15, "kingrootsolution|getSolutions"), new Helpers(16, "kingrootreport|reportKingRootResult"),
                new Helpers(17, "KingUserIssue|getKingUserUrl"), new Helpers(18, "checkCanTmpRoot|checkCanTmpRoot")};
    }

    /**
     * 获取solutions的关键点
     * 点击root按钮，会调用这个接口获取解决方案
     */
    public static SolutionManager getSolutions(Context context) {
        LogUtil.d("getSolution ");
        SolutionManager managers = new SolutionManager();
        NetConfigration v0 = new NetConfigration((byte) 0);
        NetConfigration v2 = new NetConfigration((byte) 0);
        NetConfigration.setTag(Const.GET_SOLUTIONS);//设置解析标志
        //  EntityManager.setFromSolution(true);//设置请求solution标识，
        UserInfo v3 = EntityManager.getUserInfo(context);
        LogUtil.d("userinfo =" + v3.toString());
        GetKingRootSolutionReq v4 = EntityManager.getKingRootSolutionReq(); //Manager.c();
        LogUtil.d("getkingrootsolution = " + v4.toString());
        LogUtil.e("WupSession.getSolutions()上报设备信息deviceInfoXml : " + v4.deviceInfoXml);
        HttpMgr.b(15, v0, v2);
        v0.a("userinfo", v3);
        v0.a("req", v4);
        v0.a("phonetype", EntityManager.getPhoneType());
        LogUtil.e("phoneType = " + EntityManager.getPhoneType().toString());
        //准备网络请求
        int v0_1 = HttpMgr.a(context, v0, v2, true);

        if (v0_1 == 0) {
            GetKingRootSolutionResp v0_3 = (GetKingRootSolutionResp) v2.b("resp", new GetKingRootSolutionResp());
            LogUtil.e("vvvv " + v0_3);
            if (v0_3 != null)
            {
                //有没有找到sessionId，那么这里就不管
//            EntityManager.sdk_gt18(((GetKingRootSolutionResp)v0_3).sessionId);
                try
                {
                    LogUtil.e("((GetKingRootSolutionResp)v0_3).solutionsXmls " + ((GetKingRootSolutionResp) v0_3).solutionsXmls);
                    managers.respSolutionHelpers = XmlFileSolute.getSolutionHelpers(((GetKingRootSolutionResp) v0_3).solutionsXmls);
                    managers.pcRootInfo = v0_3.pcRootInfo;
                    managers.mobileRootInfo = v0_3.mobileRootInfo;
                    managers.a = 0;//写死掉这个值，网络加载还是没有，后续可再次修改这个程序
                } catch (Exception v2_1)
                {
                    v2_1.printStackTrace();
                }
            }
        }
        else if (v0_1== -1)
        {
            //网络异常
            LogUtil.e("网络异常，不能请求网络");
            return  null;
        }
        return managers;
    }
    /**
     * 初始化kr-stock-conf文件
     * 初始化工作，准备guid，从网上加载，需要调用这个函数
     */
    private static int c(Context context) {
        LogUtil.d("prepare kr-stock-conf");
        NetConfigration.setTag(Const.GET_GUID);
        String v0_3;
        Object v0_2;
        int v2_1 = 0;
        String tag = FileUtils.getLguid();
        LogUtil.e("guid ="+tag);
        if (TextUtils.isEmpty(FileUtils.getLguid())) {
            DeviceInfo deviceInfo = EntityManager.getDeviceInfo(context);
            AtomicReference v4 = new AtomicReference();
            NetConfigration req = new NetConfigration((byte) 0);
            NetConfigration resp = new NetConfigration((byte) 0);
            HttpMgr.b(13, req, resp);
            req.a("phonetype", EntityManager.getPhoneType());
            req.a("userinfo", EntityManager.getUserInfo(context));
            req.a("deviceinfo", deviceInfo);
            int ret = a(context, req, resp, true);
            if (ret == 0)
            {
                v0_2 = resp.b("guidinfo", new GUIDInfo());
                if (v0_2 != null) {
                    v4.set(v0_2);
                }
                v2_1 = 0;
            }
            if (v2_1 == 0) {
                v0_2 = v4.get();
                if (v0_2 != null) {
                    v0_3 = ((GUIDInfo) v0_2).guid;
                    LogUtil.e("get guid =" + v0_3);
                    if (TextUtils.isEmpty(v0_3)) {
                        return -1;
                    }
                    LogUtil.d("from net guid  =" + v0_3);
                    FileUtils.setKrsdkconfData(v0_3);
                    return v2_1;
                }
            }
        }

        else {
            //网路出现异常
            LogUtil.e("网络异常");
            v2_1 =-1;

        }

        LogUtil.d("prepare kr-stock-conf end " + v2_1);
        return v2_1;
    }
    /***
     * 请求网络,主要函数
     */
    private static int a(Context arg6, NetConfigration arg7, NetConfigration arg8, boolean arg9) {
        LogUtil.e(" 每次调用的状态标识，是否启用kr-stock-conf " + arg9);
        int v2_1 = -1;
        AtomicReference v0_4;
        HttpUtils v1_1;
        HttpUtils v3;
        byte[] v0_3;
        int v0_5 = -1;
        int v1 = -6000;
        if (!arg9) {
            HttpMgr.c(arg6);//添加guid属性到kr-stock-conf文件
        }
        HttpUtils v2 = null;
        v0_3 = Cryptor.x(arg6, arg7.a());//
        LogUtil.d("cryptor.x = " + new String(v0_3));
        v3 = HttpUtils.a(arg6, HttpMgr.a);
        if (v3 == null)
        {
            LogUtil.e("没有获取到网络的对象!!");
            return  -1;
        }

        v3.a("POST");
        v3.a(v0_3);
        //发送请求，获取请求的反馈
        LogUtil.d("waiting for http request !!" + a);
        int ret = v3.a();
        LogUtil.d("WupSession.reponseCode = " + v3.c() + ", contentLength = " + v3.e() + ", contentType = " + v3.f());
        LogUtil.d("responseCode = " + ret);
        v0_4 = new AtomicReference();
        v2_1 = v3.a(v0_4);
        if (v2_1 == 0)
        {
            byte[] v0_6 = (byte[]) v0_4.get();
            LogUtil.d("response data = " + new String(v0_6));
            if (v0_6 == null) {
                v0_5 = v2_1;
            } else if (v0_6.length > 0)
            {
                v0_3 = Cryptor.y(arg6, v0_6);

                if (v0_3 != null)
                {
                    LogUtil.e("cryptor y ok !!! " + new String(v0_3));
                    arg8.a(v0_3);//解密反馈回来的数据包
                    v0_5 = v2_1;
                } else {
                    LogUtil.e("WupSession.Cryptor.y(..)出错了！");
                    v0_5 = v1;
                }
            } else {
                v0_5 = v2_1;
            }
        }
        if (v3 != null) {
            v3.closeHttp();
        }
        return v0_5;
    }
    /**
     * 配置网络请求参数
     */
    public static void b(int arg1, NetConfigration arg2, NetConfigration arg3) {
        arg2.a("UTF-8");
        arg2.a(arg1);
        arg2.b(HttpMgr.b[arg1].b);
        arg2.c(HttpMgr.b[arg1].c);
        arg3.a("UTF-8");
    }
    /**
     * 下载解决方案外部接口
     */
    public static boolean downloadSolutionJars(Context context, SolutionHelpers solutionHelpers)
    {
            if (FileUtils.checkJars(solutionHelpers))
            {
                LogUtil.e("存在本地文件，不用下载");
                return true;
            }
        return download(context, solutionHelpers);
    }
    /**
    * 下载解决方案内部调用
    * */
    private static boolean download(Context context, SolutionHelpers solutionHelpers) {
        boolean flag = false;
        int len = -1;
        FileOutputStream fileOutputStream = null;
        byte[] buf = new byte[1024];
        InputStream inputStream;
        HttpUtils httpUtils;
        LogUtil.e("WupSession.downloadSolutionFile()下载方案Jar, sid = " + solutionHelpers.b);
        File file = new File(solutionHelpers.n);
        LogUtil.e("下载保存的文件路径是： "+solutionHelpers.n);
        httpUtils = HttpUtils.a(context, solutionHelpers.i);
        if (httpUtils == null) {
            LogUtil.e("网络下载Jar异常，没有获取到http对象");
            return flag;
        }
        httpUtils.a("GET");
        httpUtils.a();//启动网络请求
        LogUtil.d("下载jar网络响应 状态 responseCode : "+httpUtils.c());
        inputStream = httpUtils.b();
        if (inputStream == null) {
            LogUtil.e("网络下载jar异常，不能获取到InputStream");
            return flag;
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            while (true) {
                len = inputStream.read(buf);
                if (len != -1) {
                    fileOutputStream.write(buf, 0, len);
                    fileOutputStream.flush();
                } else {
                    break;
                }
            }
            fileOutputStream.close();
            if (httpUtils != null)
            {
                httpUtils.closeHttp();
            }
            flag = true;
            LogUtil.e("文件下载完成 ！！！ fileName =" +file.getName()+" ,filePath ="+file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if(httpUtils != null)
            {
                httpUtils.closeHttp();
            }
            if (fileOutputStream != null)
            {
                Utils.close(fileOutputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(httpUtils != null)
            {
                httpUtils.closeHttp();
            }
            if (fileOutputStream != null)
            {
                Utils.close(fileOutputStream);
            }
        }
        return flag;
    }







}
