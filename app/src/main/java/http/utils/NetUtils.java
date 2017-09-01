package http.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;

import com.demo.utils.LogUtil;

public final class NetUtils {
    public static byte isConnection(Context context) {
        byte v0_3;
        NetworkInfo networkInfo;
        byte v2 = -1;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo v3 = null;
        try {
            networkInfo = ((ConnectivityManager)connectivityManager).getActiveNetworkInfo();
        }
        catch(Throwable v0_1)
        {
            LogUtil.exception("NetworkUtil.getNetworkType() throw fileSize", v0_1);
            networkInfo = v3;
        }

        if(networkInfo != null)
        {
            if(networkInfo.getState() != NetworkInfo.State.CONNECTING && networkInfo.getState() != NetworkInfo.State.CONNECTED)
            {
                v0_3 = v2;
            }

            if(networkInfo.getType() == 1)
            {
                v0_3 = 0;
                return v0_3;
            }

            if(networkInfo.getType() == 0)
            {
                if(Proxy.getDefaultHost() == null && Proxy.getHost(context) == null)
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

    public static boolean isConnected(Context context) {
        try {
            ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

