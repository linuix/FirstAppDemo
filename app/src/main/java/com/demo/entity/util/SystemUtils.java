package com.demo.entity.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemProperties;

import com.demo.entity.PhoneHelper;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.kingroot.sdk.root.CommonLog;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/10/18.
 */

public class SystemUtils {
    private static long availableBytes = -1;
    public static long getAvailable() {
        return getAvailableBytes("/system");
    }
    /**
     * 获取availableBytes
     */
    private static long getAvailableBytes(String arg5) {
        long v0_2;
        try {
            StatFs v0_1 = new StatFs(arg5);
            if (Build.VERSION.SDK_INT > 18) {
                v0_2 = v0_1.getAvailableBytes();
                return v0_2;
            }
            v0_2 = (((long) v0_1.getAvailableBlocks())) * (((long) v0_1.getBlockSize()));
        } catch (Throwable v0) {
            v0.printStackTrace();
            v0_2 = -1;
        }
        return v0_2;
    }
    /**
     * 获取手机的外部存储和手机内部存储信息
     **/
    public static String getPhoneExternalInfo(Context arg8) {
        long v5 = 0;
        String v0 = new String();
        String[] v1 = getCpuInfo(arg8);
        StringBuilder v0_1 = new StringBuilder(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(v0) + "MODEL " + v1[0] + ";") + "ANDROID " + v1[1] + ";") + "CPU " + v1[2] + ";") + "CPUFreq " + getCpuFrequency() + ";") + "CPUNum " + Runtime.getRuntime().availableProcessors() + ";") + "resolution " + v1[3] + ";") + "ram " + getMeminfo() + ";")).append("rom ");
        StatFs v2 = new StatFs(Environment.getDataDirectory().getPath());
        String v1_1 = v0_1.append((((long) v2.getBlockCount())) * (((long) v2.getBlockSize()))).append(";").toString();
        PhoneHelper v2_1 = new PhoneHelper();
        if (Utils.isSdcardMounted())
        {
            File v0_2 = Environment.getExternalStorageDirectory();
            try
            {
                StatFs v3 = new StatFs(v0_2.getPath());
                long v4 = ((long) v3.getBlockSize());
                v2_1.a = (((long) v3.getAvailableBlocks())) * v4;
                v2_1.b = (((long) v3.getBlockCount())) * v4;
            }
            catch (Exception v0_3)
            {
                v0_3.printStackTrace();
            }
        } else {
            v2_1.a = v5;
            v2_1.b = v5;
        }
        return String.valueOf(String.valueOf(String.valueOf(String.valueOf(v1_1) + "sdcard " + v2_1.b + ";") + "simNum 1;") + "baseband " + SystemProperties.get("gsm.version.baseband", "") + ";") + "inversion " + Build.DISPLAY + ";";
    }


    public  static String getPrevSuVersion()
    {
        Process v2 = null;
        LogUtil.e("getPrevSuVersion -- ");
        String v1 = "";
        String v0 = "/system/bin/su";
        if (!new File(v0).exists())
        {
            v0 ="/system/xbin/su";
            if (!new File(v0).exists())
            {
                LogUtil.e("Not found su file! ");
            }
        }
        try {
            v2 = new ProcessBuilder(new String[0]).command(new String[]{v0, "-v"}).redirectErrorStream(true).start();
            if (v2 == null)
            {
                return "";
            }
            InputStream v0_4 = v2.getInputStream();
            ByteArrayOutputStream v3 = new ByteArrayOutputStream();
            byte[] v4 = new byte[1024];
            int v5 =-1;

            while(true)
            {
                v5 = v0_4.read(v4);
                if (v5 <=0)
                {
                    break;
                }
                v3.write(v4);
            }

            v4 = v3.toByteArray();
            v0_4.close();
            v3.close();
            v2.destroy();
            v0 = new String(v4,"UTF-8");
            v1 = v0.trim();
            return v1;

        }catch (Exception e)
        {
            LogUtil.exception("getPreSuVersion exceptin ",e);
            if (v2 != null)
            {
                v2.destroy();
                v2= null;
            }
        }
        return  v1;
    }



    /**
     * 读取cpu的信息
     */
    private static String[] getCpuInfo(Context arg4) {
        String v0 = "";
        String[] v2 = new String[4];
        v2[0] = Build.MODEL;
        v2[1] = Build.VERSION.RELEASE;
        String v1 = "/proc/cpuinfo";
        try {
            v0 = CommonLog.getkernelInfo(v1).split("\\n")[0];
        } catch (Exception v1_1) {
            v1_1.printStackTrace();
        }

        v2[2] = v0;
        v2[3] = String.valueOf(Integer.toString(PhoneInfoUtil.widthPixels(arg4))) + "*" + Integer.toString(PhoneInfoUtil.heightPixels(arg4));
        return v2;
    }

    /**
     * 获取cpu的频率
     */
    private static String getCpuFrequency() {
        StringBuilder v0 = new StringBuilder();
        int v1 = 2;
        try {
            String[] v1_1 = new String[v1];
            v1_1[0] = "/system/bin/cat";
            v1_1[1] = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
            InputStream v1_2 = new ProcessBuilder(v1_1).start().getInputStream();
            byte[] v2 = new byte[24];
            while (v1_2.read(v2) != -1) {
                v0.append(new String(v2));
            }

            v1_2.close();
        } catch (IOException v0_1) {
            v0_1.printStackTrace();
            v0 = new StringBuilder("N/A");
        }

        return v0.toString().trim();
    }

    /**
     * 读取内存信息
     **/
    private static long getMeminfo()
    {
        DataInputStream v1 = null;
        if (availableBytes != -1)
        {
            long v0_6 = availableBytes > 0 ? availableBytes : 1;
            return v0_6;
        }
        File v0 = new File("/proc/meminfo");
        DataInputStream v2 = null;
        if (!v0.exists())
        {
            long v0_6 = availableBytes > 0 ? availableBytes : 1;
            return v0_6;
        }
        try
        {
            v1 = new DataInputStream(new FileInputStream(v0));
            String v0_5 = v1.readLine();
            if (v0_5 == null) {
                throw new IOException("/proc/meminfo is empty!");
            }
            availableBytes = Long.parseLong(v0_5.trim().split("[\\s]+")[1]);
            v1.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return availableBytes;
    }

    /**
     * 获取/data目录下的数据信息
     **/
    public static long getSanBoxData() {  // 获取沙箱的数据
        return getAvailableBytes("/data");
    }
}
