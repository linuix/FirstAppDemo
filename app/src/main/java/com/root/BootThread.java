package com.root;

import android.content.Context;

import com.demo.handler.MyHandler;
import com.demo.utils.InitConfig;
import com.demo.utils.LogUtil;
import com.kingroot.sdk.root.YunRootExcutor;

/**
 * Created by Administrator on 2016/12/2.
 */

public class BootThread implements  Runnable
{


    private Context mContext;
    private  RootMgr rootMgr;
    public BootThread(Context context,RootMgr mgr)
    {
        mContext=context;
        rootMgr =mgr;
    }

    @Override
    public void run() {
        rootAgain(mContext,rootMgr);

    }
    private static void rootAgain(Context context, RootMgr rootMgr)
    {
        boolean solution;YunRootExcutor excutor = new YunRootExcutor(context, InitConfig.entity);
        int prepare = excutor.prepare(3);
        if (prepare ==0)
        {
            solution = excutor.downloadSolution();
            if (solution)
            {
                LogUtil.e("执行重启后的root");
                rootMgr.execteRoot(3);
            }
        }
        else
        {
            LogUtil.e("不能执行重启后的root，root失败");
            MyHandler.sendBroad(context,"root失败",false);
        }
    }

}

