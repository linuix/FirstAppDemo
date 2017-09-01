package com.demo.handler;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.demo.entity.Entity;
import com.demo.utils.Const;
import com.demo.utils.InitConfig;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.kingroot.sdk.root.YunRootExcutor;
import com.root.RootMgr;
import com.root.TestRootThread;
import com.root.helper.JavaProcessk;

import junit.framework.Test;

import java.io.CharArrayReader;
import java.io.File;

import http.demo.HttpMgr;

/**
 * Created by Administrator on 2016/11/9.
 */

public class MyHandler extends Handler {

    private static Context mContext;
    private Entity mEntity;
    private RootMgr rootMgr;
    private Handler handler;
    private YunRootExcutor rootExcutor;
    private TestRootThread testRootThread;

    public MyHandler(Context context, Entity entity, Looper looper) {
        super(looper);
        mContext = context;
        mEntity = entity;
        rootMgr = new RootMgr(mContext, mEntity, this);
        rootExcutor = InitConfig.getExecutor();
        testRootThread = new TestRootThread(rootMgr, rootExcutor);
        handler = this;
    }
    @Override
    public void handleMessage(Message msg) {
        LogUtil.d("init handler");
        switch (msg.what) {
            case Const.INIT_SOURCE:
                post(new InitThread());
                break;
            case Const.FIND_FILE:
                scanFile("play", 0);
                scanFile("play", 1);
                break;
            case Const.HTTP_TEST:
                //-----------------------修改后----------------------
                LogUtil.e("download finished ");
                String text  ="这个http的测试功能集成在了execute功能中，可以直接点击executeBtn";
                sendBroad(mContext,text,false);
                /**
                 * 修改：
                 * 把下载并且执行放在一起，避免多次执行root过程
                 * */
                //-----------------------------原始测试使用的样子-------------------------------------

//                HttpMgr httpManagers = new HttpMgr(mContext);
//                //获取解决方案
//                httpManagers.getSolutions(mContext);
                /*int prepare = rootExcutor.prepare(3);
                if (prepare ==0)
                {
                    LogUtil.fileSize("可以请求解决方案 !!1 ");
                    boolean flag = rootExcutor.downloadSolution();
                    if (flag)
                    {
                        LogUtil.fileSize("download finished ");
                        String absolutePath = mEntity.file.getAbsolutePath()+ File.separator+"jars";
                        File file = new File(absolutePath);
                        if (file.exists() && file.isDirectory()&& file.length()>0)
                        {
                            File[] files = file.listFiles();
                            for (File tm: files)
                            {
                                LogUtil.fileSize("fileName ="+tm.getName()+", filePath ="+tm.getAbsolutePath() +" ,len ="+tm.length());
                            }
                        }
                    }
                }*/
                break;
            case Const.ROOT_TEST:
                //添加检测是否root
                executeRoot();
                break;
            case Const.GET_TMP_SHELL:
                LogUtil.d("root成功");
                testRootThread.stop();//停止线程
                String ret = (String) msg.obj;
                sendBroad(mContext,"已经获取到root "+ret,true);
                break;
            case Const.INIT_OK:
                sendBroad(mContext,"初始化完成,点击获取root",false);
                break;
            default:
                LogUtil.d("等着其他需求功能");
                break;
        }
    }

    private void scanFile() {
        if (mEntity == null)
        {
            return;
        }
        String absolutePath = mEntity.file.getAbsolutePath() + File.separator + "jars";
        File file = new File(absolutePath);
        if (file.exists() && file.isDirectory() && file.length() > 0) {
            File[] files = file.listFiles();
            for (File tm : files) {
                LogUtil.e("fileName =" + tm.getName() + ", filePath =" + tm.getAbsolutePath() + " ,len =" + tm.length());
            }
        }
    }

    public static void sendBroad( Context context,String msg,boolean flag) {
        Intent intent = new Intent(Const.UP_DATE_UI);
        intent.putExtra("root",msg);
        intent.putExtra("flag",flag);
        context.sendBroadcast(intent);

    }

    /**
     * 执行root
     * ret ==0,表示成功
     * ret != 0 ，表示不成功
     *
     */
    private void executeRoot() {
        if (TestRootThread.getFlag() != 0) {
            boolean ret = InitConfig.initSdk();
            if (!Utils.checkNetService(mContext))
            {
                sendBroad(mContext,"网络异常",false);
                return;
            }
            if (ret) {
                int prepare = rootExcutor.prepare(3); //网络请求解决方案
                if (prepare == 0) {
                    scanFile();//为了读取一下系统下的文件
                    post(testRootThread);
                }
                else {
                    LogUtil.e("初始化出现异常，未能获取到网络解决方案~~~");
                }
            }
        } else {
            LogUtil.e("不再执行root功能");
        }
    }
    /**
     * 测试使用，主要是在未root的情况下，检测执行文件夹下的文件，
     *
     */
    private void scanFile(String name, int type) {
        if (mEntity == null)
        {
            return;
        }
        String path = mEntity.file.getAbsolutePath();
        String path2 = "";
        if (type == 1) {
            path = mContext.getDir("solution", 0).getAbsolutePath();
            LogUtil.d("path = " + path2);
        }
        File files = new File(path + File.separator + name);
        if (files.exists() && files.isDirectory()) {
            LogUtil.d("play 文件夹下的文件如下： " + path);
            File[] files1 = files.listFiles();
            for (File file : files1) {
                LogUtil.d("fileName: " + file.getName() + ": path " + file.getAbsolutePath() + "\n");
            }
        }
    }

    /**
     * 初始化线程
     */
    class InitThread implements Runnable
    {
        @Override
        public void run()
        {
            int ret = InitConfig.init();
            if (ret == 0)
            {
                if (!Utils.checkNetService(mContext))
                {
                   sendBroad(mContext,"网络异常",false);
                    return;
                }
                LogUtil.e("****----iniittt------*****");
                //请求网络,获取数据返回
                JavaProcessk.a(mEntity.file + File.separator + "kd", 0);
                //记录一些属性字段
                HttpMgr.initKrsdkStockConf(mContext, handler);
                HttpMgr.reportRootResult(mContext, handler);
                LogUtil.d("检查sdcard下是否有kr-stock-conf文件可以用");
            }
        }
    }
}
