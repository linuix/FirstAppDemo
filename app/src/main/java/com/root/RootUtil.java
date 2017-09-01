package com.root;

import android.content.Context;

import com.demo.process.RetValue;
import com.demo.process.RootProcess;
import com.demo.utils.Const;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.kingroot.sdk.util.Posix;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import http.demo.SolutionHelpers;

/**
 * Created by Administrator on 2016/11/7.
 * <p>
 * 主要是读取出网络下载的文件，解压出来，提供使用
 */


/**
 * 读取从http中获取的文件，解压出来里边的文件
 **/
public class RootUtil {
    static Context context;
    static String name;//需要读取的文件名称
    File dir;//读取后生成的文件名称
    static int count = 0;
    private static RootUtil instance = null;

    private RootUtil(Context mcontext) {
        context = mcontext;
        dir = context.getDir("solution", 0);
    }

    public static RootUtil getInstanc(Context context, String fileName) {
        if (instance == null) {
            instance = new RootUtil(context);
        }
        name = fileName;
        LogUtil.d("name = " + name + " count =" + count++);
        return instance;
    }

    /**
     * 为读取压缩的文件做准备
     * 这里只是为了读取assets目录下的文件准备。
     */
    private boolean open() {
        boolean flag = false;
        try {
            InputStream inputStream = context.getAssets().open(name);
            File file = new File(dir.getAbsolutePath(), name);
            FileOutputStream stream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            while (true) {
                int len = inputStream.read(buf);
                if (len == -1) {
                    break;
                }
                stream.write(buf);
                stream.flush();
            }
            stream.close();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.d("从assets中读取解决方案 sid = " + name);
        return flag;
    }
    /**
     * 传递进来solutionHelper对象。取出n字段给方法调用
     */
    public static boolean extract(SolutionHelpers helpers, String play) {
        boolean flag = true;
        if (extract(new File(helpers.filePath), play, "777"))
        {
            if (helpers.l == 1)
            {
                File file  = new File(String.valueOf(play)+File.separator+"classes.dex");
                if (!file.exists())
                {
                    LogUtil.e("not found "+file.getAbsolutePath());
                    flag=false;
                }
            }
        }
        return flag;
    }



    /***
     * 读取压缩文件内的root解决方案
     */
    private static boolean extract(File solutionFile, String workdir, String chmod) {
//        File arg9;
        byte[] v6_1 = new byte[1024];
        int v6;
        FileOutputStream v2_2 = null;
        File v5;
        ZipEntry v2_1;
        ZipInputStream v4_1 = null;
        Closeable v3 = null;
        int v2 = 8;
        int v4 = 7007;
        boolean v0 = false;
//        if ( open())
//        {
        File v1 = new File(workdir);
//        arg9 = new File(dir.getAbsolutePath() + File.separator + name);
        LogUtil.loge("path = " + solutionFile.getAbsolutePath());
        if (!v1.exists() && !v1.isDirectory()) {
            v1.mkdirs();
        }

        if (chmod != null) {
            v2 = Posix.chmod(workdir, Integer.parseInt(chmod, v2));
            if (v2 != 0)
            {
                LogUtil.loge(" v4," + String.valueOf(workdir) + " chmod fail rc = " + v2);
                return v0;
            }
        }

        LogUtil.d("chmod ok  play " + workdir + "  retcode = " + v2);
        try {
            v4_1 = new ZipInputStream(new FileInputStream(solutionFile));
            while (true) {
                v2_1 = v4_1.getNextEntry();
                if (v2_1 == null) {
                    Utils.close(((Closeable) v4_1));
                    return true;
                }
                if (!v2_1.isDirectory()) {
                    break;
                }
                new File(v1, v2_1.getName()).mkdir();
                LogUtil.loge("mkDir : " + v1.getAbsolutePath());
            }
            v5 = new File(v1, v2_1.getName());
            v5.delete();
            v2_2 = new FileOutputStream(v5);
            while (true) {
                int v7 = v4_1.read(v6_1);
                if (v7 == -1) {
                    break;
                }
                v2_2.write(v6_1, 0, v7);
                v2_2.flush();
            }
            v2_2.close();
            if (chmod != null) {
                String v2_3 = v5.getAbsolutePath();
                v6 = Posix.chmod(v2_3, Integer.parseInt(chmod, 8));
                if (v6 != 0) {
                    LogUtil.loge("7007," + String.valueOf(v2_3) + " chmod fail rc = " + v6);
                    Utils.close(((Closeable) v4_1));
                    return v0;
                }
            }
            LogUtil.d("extract " + v5.getAbsolutePath());
            //测试
            scanFile(v1.getAbsolutePath());//测试目录下的文件是否存在
            v0 = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.close(v4_1);
            Utils.close(v2_2);
        }
//        }
        return v0;
    }

    /**
     * test
     * <p>
     * 这里读取到的只是soluti下的文件
     */
    private static void scanFile(String arg) {
        LogUtil.e("检索play目录下的文件 test scan files ");
        String path = arg;//dir.getAbsolutePath();
        File files = new File(path);
        if (files.exists() && files.isDirectory()) {
            LogUtil.d("play 文件夹下的文件如下： " + path);
            File[] files1 = files.listFiles();
            for (File file : files1) {
                LogUtil.d("fileName: " + file.getName() + ": path " + file.getAbsolutePath() +": len "+file.length()+ "\n");
                test(file);
            }
        }
    }

    /**
     * 测试使用
     * */
    private static  void test(File file)
    {
        String path ="/mnt/sdcard";
        int count =0;
        if (file.exists() && file.length() >0)
        {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                if (Utils.isSdcardMounted())
                {
                    path = Utils.getSDCardPath();
                }
                File dir = new File(path,"mars");
                dir.mkdirs();
                LogUtil.e("测试地址 ： "+dir.getAbsolutePath() +" ,count ="+ count);
                File out = new File(dir.getAbsolutePath(),file.getName()+count);
                FileOutputStream outputStream = new FileOutputStream(out);
                Utils.writeFile(inputStream,outputStream);
                ++count;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 在执行解决方案之前，先检测相关单位文件是否存在
     */
    public static String getSuInfo() {

        RootProcess rootMgr;
        try {
            rootMgr = new RootProcess("sh");
            rootMgr.execut(Const.EXPORT_PATH);
            RetValue v1_2 = rootMgr.execute("mysu -v", 3000);
            LogUtil.d("RootUtil.getSuInfo mysu -v r.success() = " + v1_2.a() + ", r.mStdOut = " + v1_2.stdout + ", r.mStdErr = " + v1_2.err);
            if (!v1_2.a()) {
                rootMgr.closeAll();
                return "";
            }
            if (v1_2.stdout == null) {
                rootMgr.closeAll();
                return "";
            }
            rootMgr.closeAll();
            return v1_2.stdout.trim();

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.exception("RootUtils getSuInfos ", e);
        }
        return "";
    }


}
