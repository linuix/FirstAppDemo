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

    private static String url;
    private static Helpers[] helperses;

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
    public static int reportRootResult(Context context, NetConfigration netCfg1, NetConfigration netCfg2) {
        return HttpMgr.getDataFromServer(context, netCfg1, netCfg2, true);
    }

    /**
     * 外部调用接口,在初始化kr-stock-conf的时候，会调用这个接口
     */
    public static int initKrsdkStockConf(Context context, NetConfigration arg2, NetConfigration arg3) {
        return getDataFromServer(context, arg2, arg3, false);
    }
    static {
        HttpMgr.url = UrlTest.url;
        HttpMgr.helperses = new Helpers[]{new Helpers(0, "info|getUpdatesV2"), new Helpers(1, "conf|getConfigV2"),
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
        NetConfigration netCfg1 = new NetConfigration((byte) 0);
        NetConfigration netCfg2 = new NetConfigration((byte) 0);
        NetConfigration.setTag(Const.GET_SOLUTIONS);//设置解析标志
        //  EntityManager.setFromSolution(true);//设置请求solution标识，
        UserInfo userInfo = EntityManager.getUserInfo(context);
        LogUtil.d("userinfo =" + userInfo.toString());
        GetKingRootSolutionReq getKingRootSolutionReq = EntityManager.getKingRootSolutionReq(); //Manager.chart();
        LogUtil.d("getkingrootsolution = " + getKingRootSolutionReq.toString());
        LogUtil.e("WupSession.getSolutions()上报设备信息deviceInfoXml : " + getKingRootSolutionReq.deviceInfoXml);
        HttpMgr.init(15, netCfg1, netCfg2);
        netCfg1.setMapData("userinfo", userInfo);
        netCfg1.setMapData("req", getKingRootSolutionReq);
        netCfg1.setMapData("phonetype", EntityManager.getPhoneType());
        LogUtil.e("phoneType = " + EntityManager.getPhoneType().toString());
        //准备网络请求
        int v0_1 = HttpMgr.getDataFromServer(context, netCfg1, netCfg2, true);
        if (v0_1 == 0) {
            GetKingRootSolutionResp getKingRootSolutionResp = (GetKingRootSolutionResp) netCfg2.getDataFromMap("resp", new GetKingRootSolutionResp());
            LogUtil.e("vvvv " + getKingRootSolutionResp);
            if (getKingRootSolutionResp != null)
            {
                //有没有找到sessionId，那么这里就不管
//            EntityManager.sdk_gt18(((GetKingRootSolutionResp)getKingRootSolutionResp).sessionId);
                try
                {
                    LogUtil.e("((GetKingRootSolutionResp)getKingRootSolutionResp).solutionsXmls " +  getKingRootSolutionResp.solutionsXmls);
                    managers.respSolutionHelpers = XmlFileSolute.getSolutionHelpers(getKingRootSolutionResp.solutionsXmls);
                    managers.pcRootInfo = getKingRootSolutionResp.pcRootInfo;
                    managers.mobileRootInfo = getKingRootSolutionResp.mobileRootInfo;
                    managers.a = 0;//写死掉这个值，网络加载还是没有，后续可再次修改这个程序
                } catch (Exception e)
                {
                    e.printStackTrace();
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
            AtomicReference atomicReference = new AtomicReference();
            NetConfigration req = new NetConfigration((byte) 0);
            NetConfigration resp = new NetConfigration((byte) 0);
            HttpMgr.init(13, req, resp);
            req.setMapData("phonetype", EntityManager.getPhoneType());
            req.setMapData("userinfo", EntityManager.getUserInfo(context));
            req.setMapData("deviceinfo", deviceInfo);
            int ret = getDataFromServer(context, req, resp, true);
            if (ret == 0)
            {
                v0_2 = resp.getDataFromMap("guidinfo", new GUIDInfo());
                if (v0_2 != null) {
                    atomicReference.set(v0_2);
                }
                v2_1 = 0;
            }
            if (v2_1 == 0) {
                v0_2 = atomicReference.get();
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
    private static int getDataFromServer(Context context, NetConfigration netCfg1, NetConfigration netCfg2, boolean arg9) {
        LogUtil.e(" 每次调用的状态标识，是否启用kr-stock-conf " + arg9);
        int flagCode = -1;
        AtomicReference atomicReference;
        HttpUtils v1_1;
        HttpUtils httpUtils;
        byte[] dataBuffer;
        int v0_5 = -1;
        int v1 = -6000;
        if (!arg9) {
            HttpMgr.c(context);//添加guid属性到kr-stock-conf文件
        }
        HttpUtils v2 = null;
        dataBuffer = Cryptor.x(context, netCfg1.getHelperCBuffer());//
        LogUtil.d("cryptor.x = " + new String(dataBuffer));
        httpUtils = HttpUtils.getInstance(context, HttpMgr.url);
        if (httpUtils == null)
        {
            LogUtil.e("没有获取到网络的对象!!");
            return  -1;
        }

        httpUtils.setRequestType("POST");
        httpUtils.setParameter(dataBuffer);
        //发送请求，获取请求的反馈
        LogUtil.d("waiting for http request !!" + url);
        int ret = httpUtils.execute();
        LogUtil.d("WupSession.reponseCode = " + httpUtils.getRespCode() + ", contentLength = " + httpUtils.getContentLength() + ", contentType = " + httpUtils.getHead_Content_Type());
        LogUtil.d("responseCode = " + ret);
        atomicReference = new AtomicReference();
        flagCode = httpUtils.getData(atomicReference);
        if (flagCode == 0)
        {
            byte[] data = (byte[]) atomicReference.get();
            LogUtil.d("response data = " + new String(data));
            if (data == null) {
                v0_5 = flagCode;
            } else if (data.length > 0)
            {
                dataBuffer = Cryptor.y(context, data);

                if (dataBuffer != null)
                {
                    LogUtil.e("cryptor y ok !!! " + new String(dataBuffer));
                    netCfg2.setData(dataBuffer);
                    v0_5 = flagCode;
                } else {
                    LogUtil.e("WupSession.Cryptor.y(..)出错了！");
                    v0_5 = v1;
                }
            } else {
                v0_5 = flagCode;
            }
        }
        if (httpUtils != null) {
            httpUtils.closeHttp();
        }
        return v0_5;
    }
    /**
     * 配置网络请求参数
     */
    public static void init(int arg1, NetConfigration arg2, NetConfigration arg3) {
        arg2.setChart("UTF-8");
        arg2.setRequestId(arg1);
        arg2.setServantName(HttpMgr.helperses[arg1].argFirst);
        arg2.setFuncName(HttpMgr.helperses[arg1].argSecond);
        arg3.setChart("UTF-8");
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
        LogUtil.e("WupSession.downloadSolutionFile()下载方案Jar, sid = " + solutionHelpers.sindex);
        File file = new File(solutionHelpers.filePath);
        LogUtil.e("下载保存的文件路径是： "+solutionHelpers.filePath);
        httpUtils = HttpUtils.getInstance(context, solutionHelpers.url);
        if (httpUtils == null) {
            LogUtil.e("网络下载Jar异常，没有获取到http对象");
            return flag;
        }
        httpUtils.setRequestType("GET");
        httpUtils.execute();//启动网络请求
        LogUtil.d("下载jar网络响应 状态 responseCode : "+httpUtils.getRespCode());
        inputStream = httpUtils.getInputStream();
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
