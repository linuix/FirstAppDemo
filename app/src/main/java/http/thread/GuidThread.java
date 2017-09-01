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
        NetConfigration netCfg1 = new NetConfigration((byte) 0);
        NetConfigration netCfg2 = new NetConfigration((byte) 0);
        HttpMgr.init(12, netCfg1, netCfg2);
        netCfg1.setMapData("phonetype",EntityManager.getPhoneType());
        netCfg1.setMapData("userinfo",EntityManager.getUserInfo(context));
        netCfg1.setMapData("suikey",EntityManager.getSuiKey(context));
        netCfg1.setMapData("vecsui",v2);
        LogUtil.e("get guid ...........");
        int ret =HttpMgr.initKrsdkStockConf(context,netCfg1,netCfg2);
        LogUtil.d("guid http  response code ="+ret);



    }
}
