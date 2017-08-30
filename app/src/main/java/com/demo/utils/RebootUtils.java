package com.demo.utils;

import android.content.Context;
import android.os.HandlerThread;

import com.demo.MainActivity;
import com.demo.handler.MyHandler;
import com.demo.solution.RecordRootSolution;
import com.root.BootThread;
import com.root.RootMgr;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import http.helper.ParseDataHelper;

/**
 * Created by Administrator on 2016/12/2.
 */

public class RebootUtils {
    public static void init(Context context) {

        boolean solution = false;
        //执行监听到重启之后的行为
        InitConfig.readKRSDK_RES(context);//entity
        boolean flag = InitConfig.initSdk();
        if (flag)
        {
            InitConfig.init();
            if (!SpfUtils.get(context,Const.ROOT_SUCESS))
            {
                InitConfig.getHandler().sendEmptyMessage(Const.ROOT_TEST);
            }
        }

//        inisdk();//handler.http
//        MyHandler handler = new MyHandler(context, InitConfig.entity, thread.getLooper());//krsdk .dir  xmls play ,jars
//        MainActivity.setHandler(handler);
//        RootMgr rootMgr = new RootMgr(context, InitConfig.entity, handler);
//        File list = new File(InitConfig.entity.file + File.separator + "xmls");
//        if (!list.isFile() &&! list.isDirectory()) {
//            SpfUtils.set(context,"initsdk",false);//设置初始化开关，可以点击重新获取解决方案
//            return;
//        }
//        File[] files = list.listFiles();
//        for (File file : files) {
//            readXmls(file);
//        }
//        ArrayList list1 = ParseDataHelper.getmList();
//        if (list == null || list.length() < 0) {
//            LogUtil.e("本地方案没有，不执行root");
//            return;
//        }
//        setList(list1);
//        if (!Utils.checkNetService(context))
//        {
//            MyHandler.sendBroad(context,"网络连接异常",false);
//            return;
//        }
//        handler.post(new BootThread(context, rootMgr));
    }
    private static void setList(ArrayList list1) {
        RecordRootSolution.setReboot(1);//设置重启标志位
        RecordRootSolution.setSolutionList(list1);//设置字符列表
    }
    /***
     * 读取配置文件
     **/
    private static void readXmls(File file) {
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] buf = new byte[1024];
        int len = -1;
        if (!file.exists() || file.length() < 0) {
            return;
        }
        try {
            fileInputStream = new FileInputStream(file);
            byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                len = fileInputStream.read(buf);
                if (len == -1) {
                    break;
                }
                byteArrayOutputStream.write(buf, 0, len);
            }
            byte[] tmp = byteArrayOutputStream.toByteArray();
            LogUtil.e("重启手机读取配置 ："+new String(tmp,"UTF-8"));
            ParseDataHelper.setTag(1);//设置重启读取
            ParseDataHelper.getSolution(tmp);//设置数据，重新执行
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Utils.close(fileInputStream);
            Utils.close(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            Utils.close(fileInputStream);
            Utils.close(byteArrayOutputStream);
        }
    }
}
