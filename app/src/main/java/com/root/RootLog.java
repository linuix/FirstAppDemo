package com.root;

import android.os.Build;

import com.demo.utils.Const;
import com.demo.utils.InitConfig;
import com.demo.utils.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/7.
 */

public class RootLog {
    public static String a;
    public static volatile boolean b;
    private BufferedWriter c;
    private SimpleDateFormat d;
    private File e;

    static {
        a = "";
        b = false;
    }

    public RootLog() {
        super();
        this.d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(Const.CHANNELID.equals(Const.CHANNELID)) {
            RootLog.b = true;
            File v0 = new File(InitConfig.entity.file, "selog");
            if(!v0.exists()) {
                v0.mkdir();
            }

            File v2 = new File(v0, "kr_154_" + System.currentTimeMillis() + "_" + Build.BRAND + "_" +
                    Build.MODEL + ".log");
            try {
                this.c = new BufferedWriter(new FileWriter(v2));
//                this.sdk_gt18(sdk_gt18);
                this.e = v2;
            }
            catch(IOException v0_1) {
//                e.sdk_gt18(this.chart);
                Utils.close(c);
                this.c = null;
            }

        }
    }


    public final void a(String arg4, String arg5) {
        if((Const.CHANNELID.equals(Const.CHANNELID)) && this.c != null) {
            try {
                this.c.append(this.d.format(new Date())).append(" ").append("[" + arg4 + "]").append(
                        " ").append(((CharSequence)arg5)).append("\n");
                this.c.flush();
            }
            catch(Exception v0) {
                Utils.close(this.c);
                this.c = null;
            }
        }
    }

    public final void a(String arg3) {
        if(Const.CHANNELID.equals(Const.CHANNELID)) {
            this.a("d", arg3);
        }
    }

}
