package http.thread;

import android.content.Context;

import com.demo.entity.EntityManager;
import com.demo.utils.LogUtil;

import java.util.ArrayList;

import http.demo.HttpMgr;
import http.helper.NetConfigration;

/**
 * Created by Administrator on 2016/11/15.
 */

public class GuidThread implements Runnable {

    private Context context;
    public  GuidThread(Context mcontext)
    {
        context =mcontext;
    }
    @Override
    public void run() {
        ArrayList v2 =new ArrayList();
        NetConfigration v3 = new NetConfigration((byte) 0);
        NetConfigration v4 = new NetConfigration((byte) 0);
        HttpMgr.init(12, v3, v4);
        v3.setMapData("phonetype",EntityManager.getPhoneType());
        v3.setMapData("userinfo",EntityManager.getUserInfo(context));
        v3.setMapData("suikey",EntityManager.getSuiKey(context));
        v3.setMapData("vecsui",v2);
        LogUtil.e("get guid ...........");
        int ret =HttpMgr.initKrsdkStockConf(context,v3,v4);
        LogUtil.d("guid http  response code ="+ret);



    }
}
