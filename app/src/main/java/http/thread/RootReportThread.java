package http.thread;

import android.content.Context;

import com.demo.entity.EntityManager;
import com.demo.entity.ReportKingRootResultReq;
import com.demo.utils.LogUtil;

import java.util.ArrayList;

import http.demo.HttpMgr;
import http.demo.UserInfo;
import http.helper.NetConfigration;

/**
 * Created by Administrator on 2016/11/15.
 */

public class RootReportThread implements Runnable {

    private Context mContext;
    public  RootReportThread(Context context)
    {
        mContext = context;

    }
    @Override
    public void run() {

        ArrayList v2 = new ArrayList();

        NetConfigration v3 = new NetConfigration((byte) 0);

        NetConfigration v4 =new NetConfigration((byte) 0);

        UserInfo userInfo  = EntityManager.getUserInfo(mContext);

        EntityManager.getDeviceInfo(mContext);

        ReportKingRootResultReq reportKingRootResultReq = EntityManager.getReportRootResult();

        reportKingRootResultReq.kingRootResults = v2;

        HttpMgr.b(16,v3,v4);

        v3.a("userinfo",userInfo);

        v3.a("req",reportKingRootResultReq);

        LogUtil.e("report root result ------- ");

        int ret = HttpMgr.reportRootResult(mContext,v3,v4);

        LogUtil.e("root result 请求结果："+ret);


    }
}
