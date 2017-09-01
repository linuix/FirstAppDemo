package com.mars;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.demo.App;
import com.demo.process.RootProcess;
import com.demo.utils.Const;
import com.demo.utils.LogUtil;
import com.demo.utils.SpfUtils;
import com.demo.utils.Utils;
import com.mars.root.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/19.
 * 接管root类。主要是在获取到临时root的时候，接管root，不在依赖kingroot组件
 */

public class ReplaceRoot {
    static RootProcess process;
    static Context mContext;
    static ReplaceRoot instance = null;
    private ReplaceRoot(RootProcess arg, Context context) {
        process = arg;
        mContext = context;
    }

    public static ReplaceRoot getInstance(RootProcess arg, Context context) {
        instance = new ReplaceRoot(arg, context);
        if (instance != null) {
            return instance;
        }
        return null;
    }
    /**
     * 启用自己的su文件
     * 稳定root
     */
    private boolean execSuCmd(String cmd) {
        Process mProcess = null;
        boolean flag = false;
        String[] keepRoot = MarsRootCfg.getKeepRoot();
        String mycmd = "";
        if (keepRoot == null)

        {
            LogUtil.e("not found the keepRoot policies ,plz check out it ~~~");
            return flag;
        }
        try
        {
            Log.d("tag---","cmd "+cmd);
            mProcess = Runtime.getRuntime().exec(cmd);
            DataOutputStream outputStream = new DataOutputStream(mProcess.getOutputStream());
            for (int index = 0; index < keepRoot.length; ++index) {
                mycmd = keepRoot[index] + "\n".replace("mydate", date);
                outputStream.writeBytes(mycmd + "\n");
                outputStream.flush();
         }
            outputStream.writeBytes("exit\n");
            Utils.close(outputStream);
            mProcess.waitFor();
            LogUtil.e("接管完成"); //成功标志
            flag = true;
            SpfUtils.set(App.getContext(), Const.ROOT_SUCESS,flag);//设置退出程序标识
        } catch (Throwable v)
        {
            LogUtil.exception("执行自定义su出现异常  ReplaceRoot.class", v);
        }
        return flag;
    }
    /**
     * 接管root
     * 根据临时root身份，提取权限，完成root组件替换
     *
     */
    public boolean takeRoot() {
        boolean flag = false;
        String cmd = "";
        LogUtil.e("take Root begain ~~~~ ");
        String stageRoot[] = MarsRootCfg.stageRoot();//准备接管root方案
        if (stageRoot == null) {
            LogUtil.e("Not found the stageRoot policy !!,check out idt");
            return flag;
        }
        String workDir = getWorkDir();//准备工作目录
        //  boolean isKingRootSu = checkFile(mContext.getResources().getString(R.string.kingrootsu));
        boolean check = checkFile(mContext.getResources().getString(R.string.check));
        //设置时间
        setDate(mContext.getResources().getString(R.string.date));//设置时间戳
        //预留文件更新接口
        //checkFileUpdate()
        String mysucmd = mContext.getResources().getString(R.string.mysucm);//准备调用root命令接口
        //这里传递进来的是具备临时root权限的进程，不必申请root权限进程，直接操作自己的文件即可
        if (!check)
        {//不存在，则开始执行
            //需要执行root
           LogUtil.e("kingroot lib was not exits");
        }
        else
        {
            LogUtil.e("kingroot lib is exits");
        }
        try {
            for (int index = 0; index < stageRoot.length; ++index) {
                cmd = stageRoot[index] + "\n";
                cmd = cmd.replace("myfolder/", workDir).replace("mydate", date);
                process.execut(cmd);
            }
            sleep();//thread sleep 1000ms
            //开始执行自己的文件
            flag = execSuCmd(mysucmd);
        } catch (Throwable v) {
            LogUtil.exception("接管root 出现异常 ，ReplaceRoot.class", v);
        }
        return flag;
    }
    /**
     * 等待一会，避免出现异常
     */
    private static void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 稳定root
     * 这里在完成root之后，在后续的工作中，或清除掉kingroot的root组件，
     * 这里需要处理的是，这部分功能，改如何抽取,因为包名还是kingroot
     */
    public boolean clearKingRoot()
    {
        boolean flag = false;

        return flag;
    }

    /**
     * 准备工作目录
     */
    public String getWorkDir() {
        File workDir = new File(mContext.getFilesDir().getPath() + File.separator + "mars");
        workDir.mkdirs();
        return workDir + File.separator;
    }
    /**
     * 创建工作目录
     * /data/data/pkgName/files/mars
     */
    public void createMyfolder() {
        String cmd = "";
        String workDir = getWorkDir();
        String[] setInstall = MarsRootCfg.getMyfolder();
        if (setInstall == null) {
            LogUtil.e("not found the arrays,plz check out it !!");
            return;
        }
        try {
            Process mProcess = Runtime.getRuntime().exec(MarsRootCfg.sh);
            DataOutputStream out = new DataOutputStream(mProcess.getOutputStream());
            int len = setInstall.length;
            for (int index = 0; index < len; ++index) {
                cmd = setInstall[index];
                cmd = cmd.replace("myfolder/", workDir);
                out.writeBytes(cmd + "\n");
            }
            out.writeBytes("exit\n");
            out.flush();
            Utils.close(out);
            mProcess.waitFor();
            LogUtil.e("touch myfolder/initVariable ok !!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private String date;//时间戳

    /**
     * 读取assets 目录下的mars 文件夹内文件到 /data/data/pkgName/files/mars目录下
     */
    public void prepareFile() {
        AssetManager manager = mContext.getAssets();
        String workDir = getWorkDir();
        try {
            String[] fileName = manager.list("mars");
            if (fileName == null) {
                LogUtil.e("not found file in the assets /mars ");
                return;
            }
            int len = fileName.length;
            for (int index = 0; index < len; ++index)
            {
                String tag = fileName[index];
                InputStream inputStream = manager.open("mars/"+tag); //这里指定的是接管root文件
                FileOutputStream outputStream = new FileOutputStream(workDir + tag);
                Utils.writeFile(inputStream, outputStream);
                Utils.close(inputStream);
                Utils.close(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断指定目录下 的文件是否存在
     */
    private boolean checkFile(String path) {
        return new File(path).exists();
    }

    /**
     * 设置文件修改时间
     */
    private void setDate(String arg) {
        date = new SimpleDateFormat("yyyyMMdd.hhmmss").format(new Date(new File(arg).lastModified()));
    }

}
