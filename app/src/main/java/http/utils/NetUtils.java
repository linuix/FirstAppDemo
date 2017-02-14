package http.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;

import com.demo.utils.LogUtil;

public final class NetUtils {
    public static byte a(Context arg5) {
        byte v0_3;
        NetworkInfo v0_2;
        byte v2 = -1;
        ConnectivityManager v0 = (ConnectivityManager) arg5.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo v3 = null;
        try {
            v0_2 = ((ConnectivityManager)v0).getActiveNetworkInfo();
        }
        catch(Throwable v0_1)
        {
            LogUtil.exception("NetworkUtil.getNetworkType() throw e", v0_1);
            v0_2 = v3;
        }

        if(v0_2 != null)
        {
            if(v0_2.getState() != NetworkInfo.State.CONNECTING && v0_2.getState() != NetworkInfo.State.CONNECTED)
            {
                v0_3 = v2;
            }

            if(v0_2.getType() == 1)
            {
                v0_3 = 0;
                return v0_3;
            }

            if(v0_2.getType() == 0)
            {
                if(Proxy.getDefaultHost() == null && Proxy.getHost(arg5) == null)
                {
                    return 1;
                }

                return 2;
            }

            v0_3 = v2;
        }
        else {
            v0_3 = v2;
        }

        return v0_3;
    }

    public static boolean b(Context arg2) {
        try {
            ConnectivityManager systemService = (ConnectivityManager) arg2.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo v0_1= systemService.getActiveNetworkInfo();
            if(v0_1 != null) {
                if(!v0_1.isConnected()) {
                    return false;
                }
                return true;
            }

            return false;
        }
        catch(Throwable v0) {
            v0.printStackTrace();
            boolean v0_2 = false;
            return v0_2;
        }

    }
}

