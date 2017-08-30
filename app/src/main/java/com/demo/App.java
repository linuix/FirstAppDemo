package com.demo;

import android.app.Application;
import android.content.Context;

import com.demo.utils.FileUtils;
import com.demo.utils.InitConfig;
import com.demo.utils.LogUtil;
import com.demo.utils.ManagerInfo;

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this.getApplicationContext();

        //读取系统的属性信息
        String ret = FileUtils.getChannelId(mContext);//105006
        LogUtil.w("***channelId = " + ret);
        //mgrInfo
        ManagerInfo.init(mContext);//info
        InitConfig.readKRSDK_RES(App.getContext());  //准备krsdk文件目录
        LogUtil.w("ready ===============");
        InitConfig.initSdk();//初始化assetes目录下的文件
        //CheckRoot.excuteSuAndSh();
    }

    public static Context getContext() {
        return mContext;
    }

}
