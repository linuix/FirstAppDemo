package com.mars;

import android.content.Context;
import com.demo.App;
import com.demo.entity.Entity;
import com.demo.process.RetValue;
import com.demo.process.RootProcess;
import com.demo.utils.Const;
import com.demo.utils.InitConfig;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/11/28.
 * <p>
 * 安装自己的su和superuser.apk文件
 */
public class MarsRoot {
    private static Entity entity;
    private static RootProcess process = null;
    static ReplaceRoot replaceRoot;
    static Context mContext;
    static String fileName = "";

    /**
     * 根据javaprocessk 在连接的
     */
    public static void setProcess(RootProcess arg) {
        process = arg;
    }

    public static void setEntity(Entity arg) {
        entity = arg;
    }

    public static boolean marsRoot() {
        boolean flag = false;
        mContext = App.getContext();
        check();
        replaceRoot = ReplaceRoot.getInstance(process, mContext);

        if (!checkMount()) {//修改/system属性，调用mount命令
            return flag;
        }
        if (installSu(Const.SU_NAME, entity.a(new String[]{Const.SU_NAME}))) {
            //改变system/app下的权限，安装superuser.apk文件
            try {
                fileName = "ssu";
                replaceRoot.createMyfolder();//创建工作目录
                LogUtil.e("开始接管root ---  ---  ");

                if (takeKingRoot())

                {
                    Process mProcess = Runtime.getRuntime().exec("su");
                    InputStream stdout = mProcess.getInputStream();
                    printf(stdout);
                    InputStream err = mProcess.getErrorStream();
                    printf(err);
                    LogUtil.e("测试完成接管root  ");
                }

                else {
                    LogUtil.e("接管失败--- == ");
                }
                flag = installApk();
            } catch (Throwable v) {
                LogUtil.exception("marsRoot ------ ", v);
            }
        }
                LogUtil.e("测试成功？？ + " + flag);
        return flag;
    }
      /**
     * 测试执行完成的接管root功能
     */
    private static void printf(InputStream inputStream) {
        int len = -1;
        byte[] buf = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            while (true) {
                len = inputStream.read(buf);
                if (len == -1) {
                    break;
                }
                outputStream.write(buf, 0, len);
            }
            String ret = new String(buf);
            LogUtil.e("marsRoot ret == " + ret.trim());
        } catch (Throwable v) {
            LogUtil.exception("tetstss ", v);
        }
    }
    /**
     * 接管root
     *
     * 首先是准备文件到指定目录 调用isPrepareFile()
     * 最后，接管root 调用 ReplaceRoot.takeRoot()函数
     * 如下：
     *
     */
    private static boolean takeKingRoot() {
        boolean flag = false;
        if (isPrepareFile())//准备接管文件
        {
            LogUtil.e("文件准备完成，");

            flag= replaceRoot.takeRoot();
        }
        return flag;
    }
    private static void check() {
        boolean flag = false;
        if (process == null)
        {
            LogUtil.e("没有可以临时root的进程来操作，返回状态码： " + flag);
            return;
        }
        if (entity == null) {
            LogUtil.e("没有找到文件管理类的实例，entity,是否没有给定在参数，在RootMrg中调用");
            return;
        }
    }
    /**
     *
     * 准别接管文件
     * */
    private static boolean isPrepareFile()
    {
        boolean flag = false;
        replaceRoot.prepareFile();//准备文件

        String su = replaceRoot.getWorkDir() + "su";//xbin
        String busybox = replaceRoot.getWorkDir() + "busybox";//bin
        String supolicy = replaceRoot.getWorkDir()  + "supolicy";//xbin
        String installed_su = replaceRoot.getWorkDir() + ".installed_su_daemon";//etc
        String install_sh = replaceRoot.getWorkDir() + "install-recovery.sh";//etc
        String ddexe = entity.file + File.separator + "ddexe";//bin
        String libsupol_so = replaceRoot.getWorkDir() + "libsupol.so";//lib

        if (installBusybox(MarsRootCfg.xbin, busybox, "busybox") && installSu("su", su)) {

            installSu("supolicy", supolicy);
            installBusybox(MarsRootCfg.bin, su, "su");
            installBusybox(MarsRootCfg.etc, installed_su, ".installed_su_daemon");
            installBusybox(MarsRootCfg.etc, install_sh, "install-recovery.sh");
            installBusybox(MarsRootCfg.bin, ddexe, "ddexe");
            installBusybox(MarsRootCfg.lib, libsupol_so, "libsupol.so");

            flag = true;
        }
        LogUtil.e("prepare the files by ReplaceRoot ,ok " + flag);
        return flag;
    }
    /**
     * 执行mount 命令，修改system的属性
     */
    private static boolean checkMount() {
        boolean flag = false;
        String cmd = "mount -o remount ,rw /system";//更改system的属性
        try {
            if (process == null) {
                return flag;
            }
            RetValue value = process.execut(cmd);
            if (value.ret == 0) {
                File file = new File("/system/bin/au");
                if (file.exists()) {
                    process.execut("rm -f " + "/system/bin/au");
                }
                if (new File("/system/xbin/su").exists()) {
                    process.execut("rm -f " + " /systtem/xbin/su");
                }
                File su = new File("/system/bin/su");
                if (su.exists()) {
                    process.execut("rm -f " + "/system/bin/su");
                }

                if (new File("/system/app/MySuperuser.apk").exists()) {
                    process.execut("rm -f " + "/system/app/MySuperuser.apk");
                }
                flag = true;
            }
        } catch (Throwable v) {
            LogUtil.exception("MarsRoot mount exeception ", v);
        }
        LogUtil.e("更改system 目录的属性 成功 " + flag);
        return flag;
    }

    /**
     * 这里，只需要把文件拷贝到/system/app目录下，不需要别的作用
     */
    private static boolean installApk() {
        boolean flag = false;
        if (process == null) {
            return flag;
        }
        File apk = new File("/system/app/MySuperuser.apk");
        try {
            String src = entity.a(new String[]{"MySuperuser.apk"});
            String dst = apk.getAbsolutePath();
            String cmd = MarsRootCfg.chown;
            String extract = MarsRootCfg.chmod;
            flag = install(src, dst, cmd, extract, apk);
        } catch (Throwable e) {
            LogUtil.exception("安装自定义apk出现异常信息", e);
        }
        return flag;
    }

    private static boolean installBusybox(String installPath, String src, String name) {
        boolean flag = false;
        String path = installPath;
        if (process == null) {
            return flag;
        }
        File bin = new File(path + name);
        try {
//            String src = entity.jarname(new String[]{name});
            String dst = bin.getAbsolutePath();
            String cmd = MarsRootCfg.chmod;
            String extract = MarsRootCfg.chmod;//修改指令，4755 增加s为，让文件，可以执行
            flag = install(src, dst, cmd, extract, bin);
            LogUtil.e("写入文件是否成功 " + name + " ：" + flag);
        } catch (Throwable e) {
            LogUtil.exception("myroot exception install au", e);
        }
        return flag;
    }

    private static boolean installSu(String name, String src) {
        boolean flag = false;
        String path = MarsRootCfg.bin;
        if (process == null) {
            return flag;
        }
        if (Utils.getSdk() >= 19) {
            path = MarsRootCfg.xbin;
        }
        File bin = new File(path + name);
        try {
            String dst = bin.getAbsolutePath();
            String cmd = MarsRootCfg.chown;
            String extract = MarsRootCfg.chmod;//修改指令，4755 增加s为，让文件，可以执行
            flag = install(src, dst, cmd, extract, bin);
            LogUtil.e("写入文件是否成功 + " + name + " ：" + flag);
        } catch (Throwable e) {
            LogUtil.exception("myroot exception install au", e);
        }
        return flag;
    }

    /**
     * src 表示要输入的文件路径
     * dst 表示要写入的路径
     * cmd 表示要更改文件的名称
     * <p>
     * 把文件准备到对应的目录下，然后执行修改文件属性命令 如果是su文件，那么，就修改该文件成为ｒｏｏｔ用户
     * 如果是ｓｕｐｅｒｕｅｒ，那么就把它放置到／ｓｙｓｔｅｍ／ｂｉｎ目录下，等待后续的安装
     **/
    private static boolean install(String src, String dst, String cmd, String extract, File file) {
        boolean flag = true;
        RetValue value = null;
        try {
            LogUtil.d("cmdmysu: " + "cat " + src + " > " + dst);
            process.execut("cat " + src + " > " + dst);//写入文件 copy file
            if (cmd != null) {
                LogUtil.d("cmd2: " + cmd + dst);
                process.execut(cmd + dst);//修改文件夹的权限 chown 0:0
            }
            if (extract != null) {
                LogUtil.d("cmd3: " + extract + dst);
                process.execut(extract + dst); //chmod 4755 /0755
            }
            if (value != null) {
                LogUtil.e("MarsRoot 调用自己的反馈回来的流  ret =" + value.ret + ", stdout: " + value.stdout + " ,err: " + value.err);
            } else {
                LogUtil.e(" MarsRoot调用自己的 文件出现异常 ");
            }
        } catch (Throwable v) {
            LogUtil.exception("安装自己的文件出错，", v);
            flag = false;
        }
        return flag;
    }
}
