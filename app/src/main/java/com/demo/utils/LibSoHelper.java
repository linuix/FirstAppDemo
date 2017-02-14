package com.demo.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.demo.App;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Administrator on 2016/9/28.
 */
/*
* 读取assets目录下的lixy,ktool两个文件
* */
public class LibSoHelper {

    /*
    * 从assets下得到执行文件流，并且解压写入文件，
    * */
    private static void writeFile(Context context, String name, String filePath) {
        FileOutputStream fos = null;
        File file = null;
        ZipInputStream zipInpu = null;
        Closeable v2;
        AssetManager assetMgr = context.getAssets();
        if (check(assetMgr, name, filePath)) {
            try {
                InputStream input = assetMgr.open(name);
                File tmpFile = new File(filePath);
                tmpFile.mkdirs();
                zipInpu = new ZipInputStream(input);
                while (true) {
                    ZipEntry v1 = zipInpu.getNextEntry();
                    if (v1 == null) {
                        Utils.close(((Closeable) input));
                        LogUtil.d("KuSdkInit" + "Extract: finished.");
                        break;
                    } else if (v1.isDirectory()) {
                        new File(tmpFile, v1.getName()).mkdir();
                        LogUtil.d("FileUtil" + "mkDir : " + tmpFile.getAbsolutePath());
                        continue;
                    } else {
                        LogUtil.d("fiel  ====  path = " + tmpFile + " - " + "name =" + v1.getName());
                        file = new File(tmpFile, v1.getName());
                        if (!file.exists()) {
                            fos = new FileOutputStream(file);
                            Utils.writeFile(((InputStream) zipInpu), ((OutputStream) fos));
                            fos.flush();
                            LogUtil.d("WWWWWWWWWWW");
                            v2 = null;
//                            break;
                            continue;//继续下一个文件
                        } else {
                            LogUtil.d(file.getAbsolutePath() + file.getName() + " has exists!!!,not changed!! ");
                            continue;
                        }
                    }
                   /* file.delete();
                    fos = new FileOutputStream(file);
                    HttpArgUtils.writeFile(((InputStream) zipInpu), ((OutputStream) fos));
                    fos.flush();
                    LogUtil.d("WWWWWWWWWWW");*/
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Utils.close(fos);
                Utils.close(zipInpu);
                LogUtil.d("close stream finish ============== >>>> ");
            }

        } else {
            LogUtil.loge("there was not found file where assets directory");
            return;
        }
    }

    /*
    * load libxy.so
    * */
    public static void loadSo() {
        writeFile(App.getContext(), "libxy", sdklib().getAbsolutePath());
        LogUtil.d("write libxy.so");
        writeFile(App.getContext(), "ktools", sdklib().getAbsolutePath());
        int ret = loadSo(sdklib().getAbsolutePath() + File.separator + "libxy.so");

        if (ret == 0) {
            LogUtil.d("load libxy.so succ");
        }
    }

    /*
    * load libxy.so
    * */
    private static int loadSo(String path) {
        File v0 = new File(path);
        LogUtil.d("soossososoossoos  == " + path + " \r" + v0.getAbsolutePath() + "-" + v0.getName());
       if (v0.exists() && v0.isFile()){
           LogUtil.d("file is exists "+v0.getName());
           try {
               do {
                   System.load(path);
                   int v0_1 = 0;
                   return v0_1;
               } while (true);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
        return -1;
    }

    /*
    * 文件工具
    * */
    public static File sdklib() {
        return a(c(), "sdklib");
    }
    /*
    * 文件工具
    * */
    private static File c() {
        return new File(App.getContext().getApplicationInfo().dataDir);
    }

    /*
    文件工具
    * */
    private static File a(File arg4, String arg5) {
        File v0 = new File(arg4, arg5);
        if (!v0.exists()) {
            Context v1 = App.getContext();
            File v2 = v1.getDir(String.valueOf(arg5) + "temp", 0);
            if ((v2.exists()) && !v2.renameTo(v0)) {
                v2.delete();
            }

            v0 = new File(arg4, arg5);
            if (!v0.exists()) {
                v0.mkdirs();
                v0 = new File(arg4, arg5);
                if (!v0.exists()) {
                    v1.getFilesDir();
                    v0.mkdirs();
                    v0 = new File(arg4, arg5);
                }
            }

//            k.cmd("sh", "chmod 0771 " + v0.getAbsolutePath());
        }

        return v0;
    }

    /*
    *检测assets目录下的文件的是否检索完成
    * */
    public static boolean check(AssetManager arg8, String arg9, String arg10) {
        LogUtil.d("check file where assets directory ===== begin");
        File v5;
        boolean v0 = true;
        try {
            InputStream v1 = arg8.open(arg9);
            File v2 = new File(arg10);
            v2.mkdirs();
            ZipInputStream v3 = new ZipInputStream(v1);
            while (true) {
                ZipEntry v4 = v3.getNextEntry();
                if (v4 == null) {
//                goto label_9;goto
                    LogUtil.loge(" assets file extracted finished " + arg9 + ", path = " + arg10 + " return false");
                    break;
                } else if (v4.isDirectory()) {
                    v5 = new File(v2, v4.getName());
                    if (v5.exists()) {
                        continue;
                    }
                    LogUtil.d("KUSdkLog" + "xxx" + v5.getAbsolutePath() + " need update");
                } else {
                    v5 = new File(v2, v4.getName());
                    if ((v5.exists()) && (v5.isFile())) {
                        String v4_1 = c(v5);
                        String v6 = a(((InputStream) v3));
                        if (v4_1 != null && v6 != null && (v4_1.equalsIgnoreCase(v6))) {
                            LogUtil.d("md5 determinal ok "+"KUSdkLog" + "xxx" + v5.getAbsolutePath() + " no changed.");

                            continue;
                        }
                        break;
                    }
                    Utils.close(((Closeable) v1));
                    return v0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v0;
    }
    /*
    *
    * 文件的md5校验
    * */
    private static String c(File arg2) {
        String v1_1 = null;
        FileInputStream v0 = null;
        try {
            v0 = new FileInputStream(arg2);
            v1_1 = a(((InputStream) v0));
        } catch (Throwable v1) {
        }
        Utils.close(((Closeable) v0));
        return v1_1;
    }

    /*
    * 文件的md5校验
    * **/
    private static String a(InputStream arg6) {
        int v0 = 0;
        try {
            MessageDigest v1 = MessageDigest.getInstance("MD5");
            byte[] v2 = new byte[1024];
            while (true) {
                int v3 = arg6.read(v2);
                if (v3 == -1) {
                    break;
                }

                v1.update(v2, 0, v3);
            }

            byte[] v1_1 = v1.digest();
            if (v1_1 == null) {
                String v0_2 = "";
                return v0_2;
            }

            StringBuffer v2_1 = new StringBuffer(v1_1.length * 2);
            while (v0 < v1_1.length) {
                v2_1.append("0123456789ABCDEF".charAt(v1_1[v0] >> 4 & 15)).append("0123456789ABCDEF".charAt(v1_1[v0] & 15));
                ++v0;
            }

            return v2_1.toString();
        } catch (NoSuchAlgorithmException v0_1) {
            v0_1.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
