package com.mars;

import android.content.Context;

import com.demo.utils.PkgAddBroadcastReciver;
import com.demo.process.RetValue;
import com.demo.process.RootProcess;
import com.demo.utils.Const;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.root.dao.IJavaProcessh;
import com.root.helper.JavaSolutionHelpers;

import java.io.File;

/**
 * Created by Administrator on 2016/11/29.
 */

public class MarsApkUtils {

    /**
     * 这里检测一下是否存在可以安装的app文件
     */
    public static boolean checkApkPath() {
        LogUtil.e(" 检查apk路径是否存在,如果不存在，则表示没有安装，那么会都用installApk方法");
        boolean flag = false;
        RootProcess process = null;
        try {
            process = new RootProcess("sh");
            process.execut(Const.EXPORT_PATH);
            RetValue value = process.execute("pm path com.koushikdutta.superuser", 10000);
            LogUtil.e("cmd: pm path com.koushikdutta.superuser, ret = " + value.ret + ", stdout = " + value.stdout + ", stderr = " + value.err);
            if (value.a()) {
                process.closeAll();
            }
            if (value.stdout == null) {
                process.closeAll();
            }
            if (!value.stdout.contains("package:")) {
                process.closeAll();
            } else {
                flag = true;
            }
            process.closeAll();
        } catch (Throwable v) {
            LogUtil.exception("检测安装的apk路径出现异常", v);
            if (process != null) {
                process.closeAll();
            }
        }
        LogUtil.e("check SuperuserApk Package Installed = " + flag);
        return flag;
    }

    /**
     * 安装superuser.apk文件
     **/
    public static boolean installApk(Context context, IJavaProcessh javaProcessk) {
        LogUtil.e("install apk file ");
        boolean flag = false;
        String path;
        String cmd;
        JavaSolutionHelpers helpers = null;
        if (Utils.getSdk() >= 21) {
            path = "/system/app/Mars/superuser.apk";//这里可以更改为自己的名称apk文件
            cmd = "pm instatll -r -type " + path;
        } else {
            if (Utils.getSdk() >= 17 || Utils.getSdk() <= 20) {
                path = "/system/app/superuser.apk";
                cmd = "pm install -r -type " + path;
            }
            path = "/system/app/superuser.apk";
            cmd = "pm install -r " + path;
        }
        if (new File(path).exists()) {
            try {
                PkgAddBroadcastReciver.registerPermission(context);
                helpers = javaProcessk.a(cmd);
            } catch (Throwable v) {
                LogUtil.exception("注册动态添加apk权限出现异常，在MarsApkUtils内部", v);
                PkgAddBroadcastReciver.unRegisterPermission(context);
            }

            PkgAddBroadcastReciver.unRegisterPermission(context);
            LogUtil.e("cmd: " + cmd + ", ret = " + helpers.a + ", stdout = " + helpers.b);
            if (helpers != null && helpers.b != null && (helpers.b.contains("Success")))
            {
                flag = true;
                return flag;//在这里没有检测到sucess字段，但是，已经安装成功
            }
        }

        else{
            LogUtil.e("Not found apk file in the " + path + " , failed ||||||||||");

        }
        return flag;
    }
}
