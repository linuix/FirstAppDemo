package com.kingroot.sdk.root;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.SystemProperties;
import android.text.TextUtils;

import com.demo.utils.Const;
import com.demo.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/10/15.
 */

public class CommonLog {


    private static CommonLog instanc;
    private File file_actsts;
    private File file_rest;

    public CommonLog(Context context) {
        file_actsts = new File(context.getDir("slog", 0), "actsts");
        file_rest = new File(context.getDir("slog", 0), "rest");
    }

    public static CommonLog getInstanc(Context context) {
        if (instanc == null) {
            instanc = new CommonLog(context);
        }
        return instanc;
    }

    public final void recordExecutInfo(String arg7, int arg8, String arg9, String arg10, Handler arg11, Object[] arg12) {
        char v5 = '|';
        StringBuilder v2 = new StringBuilder();
        v2.append(commonlog_a(1, 154, arg8, arg9, arg10));
        v2.append(v5).append(arg7);
        int v3 = arg12.length;
        int v0;
        for(v0 = 0; v0 < v3; ++v0) {
            Object v4 = arg12[v0];
            v2.append(v5);
            if(v4 != null) {
                v2.append(v4.toString());
            }
        }

        for(v0 = arg12.length + 1; v0 < 10; ++v0) {
            v2.append(v5);
        }

        v2.append(Const.CHANNELID);
        String v0_1 = v2.toString();
        v2.delete(0, v2.length());
//        this.sdk_gt18(200034, v0_1, arg11);
        LogUtil.e("[Trace] " + arg7 + " : " + v0_1);
    }




    /*
    * 初始化的时候会调用这个函数，日志打印
    * */
    public void recordEMID(int arg7, int arg8, String arg9, String arg10, Handler arg11, Object[] arg12) {
        LogUtil.loge("记录日志信息 CommmLog === ");
        String v0_1;
        char v5 = '|';
        StringBuilder v2 = new StringBuilder();
        v2.append(commonlog_a(0, 154, arg8, arg9, arg10));
        int v3 = arg12.length;
        int v0;
        for (v0 = 0; v0 < v3; ++v0) {
            Object v4 = arg12[v0];
            v2.append(v5);
            if (v4 != null) {
                v2.append(common_logb(v4.toString()));
            }
        }

        for (v0 = arg12.length; v0 < 10; ++v0) {
            v2.append(v5);
        }
        v2.append(Const.CHANNELID);//channelId
        String v3_1 = v2.toString();
        StringBuilder v1 = new StringBuilder("EMID : ");
        switch (arg7) {
            case 200011: {
                v0_1 = "EMID_KRSDK_Device_Info_Count";
                break;
            }
            case 200012: {
                v0_1 = "EMID_KRSDK_Download_KU_Count";
                break;
            }
            case 200013: {
                v0_1 = "EMID_KRSDK_Prepare_Begin_Count";
                break;
            }
            case 200014: {
                v0_1 = "EMID_KRSDK_Prepare_End_Count";
                break;
            }
            case 200015: {
                v0_1 = "EMID_KRSDK_Execute_Begin_Count";
                break;
            }
            case 200016: {
                v0_1 = "EMID_KRSDK_Execute_End_Count";
                break;
            }
            case 200017: {
                v0_1 = "EMID_KRSDK_GetShell_Begin_Count";
                break;
            }
            case 200018: {
                v0_1 = "EMID_KRSDK_GetShell_End_Count";
                break;
            }
            case 200019: {
                v0_1 = "EMID_KRSDK_PushSu_Begin_Count";
                break;
            }
            case 200020: {
                v0_1 = "EMID_KRSDK_PushSu_End_Count";
                break;
            }
            case 200021: {
                v0_1 = "EMID_KRSDK_Got_Solutions";
                break;
            }
            case 200022: {
                v0_1 = "EMID_KRSDK_Solution_Prepare_Begin_Count";
                break;
            }
            case 200023: {
                v0_1 = "EMID_KRSDK_Solution_Prepare_End_Count";
                break;
            }
            case 200024: {
                v0_1 = "EMID_KRSDK_Solution_Verify_End_Count";
                break;
            }
            case 200025: {
                v0_1 = "EMID_KRSDK_Solution_Decompress_End_Count";
                break;
            }
            case 200026: {
                v0_1 = "EMID_KRSDK_Solution_Init_Begin_Count";
                break;
            }
            case 200027: {
                v0_1 = "EMID_KRSDK_Solution_Init_End_Count";
                break;
            }
            case 200028: {
                v0_1 = "EMID_KRSDK_Solution_Execute_End_Count";
                break;
            }
            case 200029: {
                v0_1 = "EMID_KRSDK_Solution_Is_Fully_Root_Count";
                break;
            }
            case 200030: {
                v0_1 = "EMID_KRSDK_Remount_System_Count";
                break;
            }
            case 200031: {
                v0_1 = "EMID_KRSDK_Push_SU_Count";
                break;
            }
            case 200032: {
                v0_1 = "EMID_KRSDK_Run_KD_Count";
                break;
            }
            case 200033: {
                v0_1 = "EMID_KRSDK_Fatal_Solution_count";
                break;
            }
            case 200034: {
                v0_1 = "EMID_KRSDK_Trace";
                break;
            }
            case 200035: {
                v0_1 = "EMID_KRSDK_PrepareKu_End_Count";
                break;
            }
            case 200036: {
                v0_1 = "EMID_KRSDK_InstallManager_Begin_Count";
                break;
            }
            case 200037: {
                v0_1 = "EMID_KRSDK_InstallManager_End_Count";
                break;
            }
            case 200038: {
                v0_1 = "EMID_KRSDK_Solution_Is_True_Root_Count";
                break;
            }
            case 200039: {
                v0_1 = "EMID_KRSDK_EXReport_Info";
                break;
            }
            case 200040: {
                v0_1 = "EMID_KRSDK_Solution_PushSu_End_Count";
                break;
            }
            case 200041: {
                v0_1 = "EMID_KRSDK_Solution_RequestSu_End_Count";
                break;
            }
            case 200042: {
                v0_1 = "EMID_KRSDK_Root_Distribute_Begin_Count";
                break;
            }
            case 200043: {
                v0_1 = "EMID_KRSDK_Root_Distribute_End_Count";
                break;
            }
            case 200044: {
                v0_1 = "EMID_KRSDK_Prepare_Switch_End_Count";
                break;
            }
            case 200045: {
                v0_1 = "EMID_KRSDK_Submit_Request_Count";
                break;
            }
            case 200046: {
                v0_1 = "EMID_KRSDK_SDK_Initialize_End_Count";
                break;
            }
            default: {
                v0_1 = "Undefined";
                break;
            }
        }
        LogUtil.loge(v1.append(v0_1).append(", pv = ").append(v3_1).toString());
    }

    private static void getSystemPropertiesInfo() {
        char v4 = '|';
        StringBuilder v1 = new StringBuilder();
        v1.append(commonlog_a(2, 0, 0, "0", ""));
        v1.append(v4);
        v1.append(String.valueOf(common_logb(SystemProperties.get("ro.board.platform") + "")) + ';' + common_logb("" + SystemProperties.get("ro.mtk.hardware")) + ';' + common_logb("" + SystemProperties.get("ro.hardware")));
        v1.append(v4);
        v1.append(String.valueOf(common_logb(SystemProperties.get("ro.miui.ui.version.code"))) + ';' + common_logb("" + SystemProperties.get("ro.miui.ui.version.name")));
        v1.append(v4);
        v1.append(String.valueOf(common_logb(SystemProperties.get("ro.cm.device"))) + ';' + common_logb("" + SystemProperties.get("ro.cm.version")));
        v1.append(v4);
        v1.append(common_logb(SystemProperties.get("ro.build.version.opporom")));
        v1.append(v4);
        v1.append(14);
        v1.append(v4);
        v1.append(154);
        int v0;
        for (v0 = 6; v0 < 10; ++v0) {
            v1.append(v4);
        }
        v1.append("105006");//channelId
        String v0_1 = v1.toString();
        LogUtil.loge("ret = " + v0_1);
    }

    private static String commonlog_a(int arg4, int arg5, int arg6, String arg7, String arg8) {
        char v3 = '|';
        if (arg8 != null) {
            arg8 = common_logb(arg8);
            LogUtil.loge("a8 = " + arg8);
        }
        String v1 = "" + arg4 + v3 + 1 + v3 + common_logb(Build.FINGERPRINT) + v3 + common_logb(getLinxeVersion()) + v3 + Build.BRAND + v3 + Build.MODEL + v3 + Build.VERSION.SDK + v3 + System.currentTimeMillis() + v3 + arg5 + v3 + Const.CHANNELID + v3 + arg6 + v3 + arg7 + v3 + arg8;
        LogUtil.loge("v1===== = " + v1);
        return v1;
    }

    /**
     * 获取到的Linux版本信息
     */
    public static String getLinxeVersion() {
        String v0 = getkernelInfo("/proc/version");
        if (v0 != null) {
            LogUtil.d("linuxVersion = " + v0);
            v0 = v0.trim();
        } else {
            v0 = "";
        }
        return v0;
    }
    public static String getkernelInfo(String arg) {
        LogUtil.loge("读取Linux version ");
        String v0_5;
        int v0_3;
        ByteArrayOutputStream v1 = null;
        BufferedInputStream v3 = null;
        BufferedInputStream v2 = null;
        try {
            v3 = new BufferedInputStream(new FileInputStream(arg));
            v1 = new ByteArrayOutputStream();
            v0_3 = 1024;

            byte[] v0_4 = new byte[v0_3];
            while (true) {
                int v2_1 = v3.read(v0_4);
                if (v2_1 == -1) {
                    break;
                }

                v1.write(v0_4, 0, v2_1);
            }

            v0_5 = new String(v1.toByteArray());

            return v0_5;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (v1 != null) {
                try {
                    v1.close();
                } catch (IOException v1_1) {
                    v1_1.printStackTrace();
                }
            }

            if (v3 != null) {
                try {
                    v3.close();
                } catch (IOException v1_1) {
                    v1_1.printStackTrace();
                }
            }
        }
        return "";
    }

    private static String common_logb(String arg3) {
        return a("\t", "%09", a("\n", "%0A", a("|", "%7C", a(";", "%3B", arg3))));
    }


    private static String a(String arg1, String arg2, String arg3) {
        if (!TextUtils.isEmpty(((CharSequence) arg3))) {
            arg3 = arg3.replace(((CharSequence) arg1), ((CharSequence) arg2));
        }

        return arg3;
    }


}
