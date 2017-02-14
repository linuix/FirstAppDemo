package com.mars;

import android.content.Context;

import com.demo.App;
import com.mars.root.R;

/**
 * Created by Administrator on 2016/12/16.
 */

public class MarsRootCfg {


    public static String bin = "/system/bin/";
    public static String xbin = "/system/xbin/";
    public static String lib = "system/lib/";
    public static String etc = "/system/etc/";
    public  static  String chown="chown 0.0 ";
    public  static  String chmod="chmod 4755 ";
    public  static  String sh="/system/bin/sh";

    /**
     * 读取手机架构 arm ,mips ,x86 ,armv7等
     */
    public static void getArchiv() {

    }
    /**
     * 读取配置信息 ，这里的配置是接管root使用
     */
    public static String[] stageRoot() {
        return App.getContext().getResources().getStringArray(R.array.stageRoot);
    }

    /**
     * 固定root使用配置信息。在接管完成root之后，会把root环境配置完成，形成稳定root环境
     */
    public static String[] getKeepRoot() {
        return App.getContext().getResources().getStringArray(R.array.keepRoot);
    }


    /**
     *
     * 开始接管之前，需要执行的是创建文件夹
     * */
    public  static String[] getMyfolder()
    {
        return App.getContext().getResources().getStringArray(R.array.setInstall);
    }

}




