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
    private BufferedWriter bufferedWriter;
    private SimpleDateFormat simpleDateFormat;
    private File e;

    static {
        a = "";
        b = false;
    }

    public RootLog() {
        super();
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(Const.CHANNELID.equals(Const.CHANNELID)) {
            RootLog.b = true;
            File logFileDir = new File(InitConfig.entity.file, "selog");
            if(!logFileDir.exists()) {
                logFileDir.mkdir();
            }

            File logFile = new File(logFileDir, "kr_154_" + System.currentTimeMillis() + "_" + Build.BRAND + "_" +
                    Build.MODEL + ".log");
            try {
                this.bufferedWriter = new BufferedWriter(new FileWriter(logFile));
//                this.sdk_gt18(sdk_gt18);
                this.e = logFile;
            }
            catch(IOException v0_1) {
//                fileSize.sdk_gt18(this.chart);
                Utils.close(bufferedWriter);
                this.bufferedWriter = null;
            }

        }
    }


    public final void record(String arg4, String arg5) {
        if((Const.CHANNELID.equals(Const.CHANNELID)) && this.bufferedWriter != null) {
            try {
                this.bufferedWriter.append(this.simpleDateFormat.format(new Date())).append(" ").append("[" + arg4 + "]").append(" ").append(arg5).append("\n");
                this.bufferedWriter.flush();
            }
            catch(Exception v0) {
                Utils.close(this.bufferedWriter);
                this.bufferedWriter = null;
            }
        }
    }

    public final void record(String arg3) {
        if(Const.CHANNELID.equals(Const.CHANNELID)) {
            this.record("type", arg3);
        }
    }

}
