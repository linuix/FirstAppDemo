package com.root;

import android.content.Context;

import com.demo.utils.InitConfig;
import com.kingroot.sdk.root.YunRootExcutor;

/**
 * Created by Administrator on 2016/11/11.
 *
 * 执行root线程
 */
public class TestRootThread implements Runnable
{
    private RootMgr mgr;
    private  static  int flag =-1;
    private  YunRootExcutor excutor;
    private static boolean isRunning = true;
    private Context mContext;
    public TestRootThread(RootMgr rootMgr, YunRootExcutor yunRootExcutor)
    {
        mgr = rootMgr;
        excutor=yunRootExcutor;
        mContext = InitConfig.mContext;
    }
    @Override
    public void run()
    {
        if (isRunning)
        {
            boolean tag = excutor.downloadSolution();//下载解决方案
            if (mgr != null && tag)
            {
               flag= mgr.execteRoot(3);//执行解决方案
            }
        }
    }

     /**
      * 获取判断的返回值
      * */
    public static  int getFlag()
    {
        return flag;
    }
    /**
     * 停止线程
     * */
    public static void stop()
    {
        isRunning =false;
    }
}
