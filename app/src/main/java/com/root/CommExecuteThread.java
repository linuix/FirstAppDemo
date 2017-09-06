package com.root;

import android.content.Context;

import com.demo.utils.InitConfig;
import com.demo.utils.LogUtil;
import com.demo.utils.SpfUtils;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/11/7.
 */

public class CommExecuteThread extends Thread {
    private Context mContext;
//    private Handler handler;
    private WeakReference weakReference;
    private String sid;

    public CommExecuteThread(Context contextg/*, Handler arg2*/, String arg3, WeakReference arg4) {
        mContext = contextg;
//        handler = arg2;
        sid = arg3;
        weakReference = arg4;
    }
    @Override
    public void run()
    {
        long v3 = InitConfig.entity.i / 2000;
        long v0;
        for(v0 = 0; v0 < v3; ++v0) {
           LogUtil.loge("打点：" + v0);
            long v5 = 2000;
            try {
                Thread.sleep(v5);
            } catch (InterruptedException e) {
                e.printStackTrace();
                LogUtil.loge("被打断");
                SpfUtils.markLastNanoTime(mContext);
                return;
            }
        }
        if (weakReference != null)
        {
            FooRoot fooRoot = (FooRoot) weakReference.get();
            if (fooRoot!= null)
            {
                LogUtil.loge("solution Timeout");
                fooRoot.c();
            }
        }
        LogUtil.loge("Solution Timeout, no solution");
    }
}
