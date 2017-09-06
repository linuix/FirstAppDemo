package com.demo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.demo.App;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.CRC32;

/**
 * Created by Administrator on 2016/9/27.
 */

public class Utils {
    public static void a(byte[] arg2, String arg3) {
        try {
            FileOutputStream v0 = new FileOutputStream(arg3, false);
            v0.write(arg2);
            v0.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 在执行root的时候会先清除掉指定文件夹下的文件，避免出现混乱
     * filePath == 指定文件夹的路径
     */
    public static boolean clearPlay(String filePath) {
        LogUtil.d("[ clear dir start ]");
        boolean v1 = false;
        if (!filePath.endsWith(File.separator)) {
            filePath = String.valueOf(filePath) + File.separator;
        }
        File fileDir = new File(filePath);
        if ((fileDir.exists()) && (fileDir.isDirectory())) {
            File[] files = fileDir.listFiles();
            if (files == null) {
                LogUtil.loge("files is NULL");
            } else {
                int v0 = 0;
                boolean v2 = true;
                while (v0 < files.length) {
                    if (files[v0].isFile()) {
                        v2 = c(files[v0].getAbsolutePath());
                        if (!v2) {
                            break;
                        }
                    } else {
                        v2 = clearPlay(files[v0].getAbsolutePath());
                        if (v2) {
                        } else {
                            break;
                        }
                    }

                    ++v0;
                }

                if (!v2) {
                    return v1;
                }

                if (!fileDir.delete()) {
                    return v1;
                }
                v1 = true;
            }
        }

        LogUtil.d("[ clear dir end ] " + v1);
        return v1;

    }

    public static boolean c(String arg1) {
        boolean v0_1;
        try {
            v0_1 = new File(arg1).delete();
        } catch (Exception v0) {
            v0_1 = false;
        }
        return v0_1;
    }

    /**
     * 这里是关闭所有流的主要函数
     */
    public static void close(Closeable arg1) {
        if (arg1 != null) {
            try {
                arg1.close();
            } catch (IOException v0) {
                LogUtil.exception("关闭流出异常 ", v0);
            }
        }
    }

    /**
     * 从Assets目录下读取文件
     **/
    public static void openAssets(Context context, String name, String path) {
        try {
            InputStream open = context.getAssets().open(name);
            File file = new File(path, name);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            while (open.read(buf) != -1) {
                fos.write(buf);
            }

            close(fos);
            close(open);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取网络数据加载回来的方法，把网络请求回来的数据保存，为后续调试程序做数据准备
     */

    static int fileindex = 0;

    public static void writeNetData(byte[] data) {
        String name = "savenetdata_" + fileindex;
        fileindex++;
        if (data.length > 0 && data != null) {

            if (isSdcardMounted()) {

                LogUtil.e("记录文件 ！！！");

                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                File dir = new File(path + "test");
                if (!dir.exists() || !dir.isDirectory()) {
                    LogUtil.e("mkdirs ");
                    dir.mkdirs();
                }

                File file = new File(path, name + ".txt");
                try {
                    FileOutputStream fos = new FileOutputStream(file, false);
                    fos.write(data);
                    fos.flush();
                    close(fos);
                    LogUtil.e("file inputCopyToOutput finished !!" + file.getAbsolutePath() + " , name = " + file.getName());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    LogUtil.exception("记录网络文件出现异常 file not found", e);
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.exception("记录网络文件出现异常 IO", e);
                }
            }
        }
    }

    /*写文件*/
    public static long writeFile(InputStream arg5, OutputStream arg6) {
        byte[] v2 = new byte[4096];
        long v0 = -1;
        try {
            int v3 = -1;
            for (v0 = 0; true; v0 += ((long) v3)) {
                v3 = arg5.read(v2);
                if (v3 == -1) {
                    return v0;
                }
                arg6.write(v2, 0, v3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v0;
    }

    public static void decodeFileKrStock() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DecodAES.decodeFile();
            }
        }).start();
    }


    public static byte[] a(String arg5) {
        try {
            FileInputStream v1 = new FileInputStream(arg5);
            ByteArrayOutputStream v2 = new ByteArrayOutputStream(v1.available());
            byte[] v0 = new byte[1024];
            while (true) {
                int v3 = v1.read(v0);
                if (v3 < 0) {
                    break;
                }
                v2.write(v0, 0, v3);
            }

            v0 = v2.toByteArray();
            v2.close();
            v1.close();
            if (v0 == null) {
                v0 = "".getBytes();
            }

            return v0;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.exception("调用com.demo.root.executor 写文件出错", e);
        }
        return null;
    }


    /**
     * 监测文件的属性
     */
    public static boolean c(File arg2) {
        if (arg2 == null) {
            throw new NullPointerException("File must not be null");
        }

        boolean v0 = false;
        try {
            v0 = arg2.getCanonicalFile().equals(arg2.getAbsoluteFile()) ? false : true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v0;
    }


    /**
     * 获取得到文件的 CRC32值
     */
    public static long a(File arg2) {
        long v0 = !arg2.exists() || !arg2.isFile() ? 0 : getCRC32(arg2);
        LogUtil.d("调用Utils .sdk_gt18(file) 获取到文件的crc32值  value ="+v0);
        return v0;
    }


    public static long getCRC32(File arg7) {
        int v3;
        FileInputStream v2_1 = null;
        CRC32 v4;
        long v0 = 0;
        try {
            v4 = new CRC32();
            v2_1 = new FileInputStream(arg7);
            v3 = 8192;
            byte[] v3_2 = new byte[v3];
            while (true) {
                int v5 = v2_1.read(v3_2);
                if (v5 == -1) {
                    break;
                }

                v4.update(v3_2, 0, v5);
            }

            v0 = v4.getValue();
            v2_1.close();
            return v0;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.exception("getCRC32 exceptions", e);
            if (v2_1 != null) {
                try {
                    v2_1.close();
                } catch (IOException v1) {
                }
            }
        }
        return v0;
    }

    /**
     * load沙箱内的so文件
     */
    public static int loadSo(String arg3) {
        File v0 = new File(arg3);
        if ((v0.exists()) && (v0.isFile())) {
            try {
                System.load(arg3);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.exception("load so " + arg3 + " exception ", e);
                return -1;
            }
        }
        return -1;
    }


    /*
* 查看写入进程中的数据是什么
* 就在/mnt/sdcard/record.txt 可以直接查看写好的数据
* */
    public static void writeFile(InputStream is, String name) {
        LogUtil.loge("inputCopyToOutput recode file == " + Environment.getExternalStorageDirectory().getAbsolutePath());
        String path = "/mnt" + File.separator + "sdcard";
        if (Utils.isSdcardMounted()) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        File file = new File(path, name);
        FileOutputStream fos = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.exception("create file exception ", e);
            }
        }
        try {
            LogUtil.loge("file name = " + file.getName() + "\t" + file.getAbsolutePath());
            fos = new FileOutputStream(file);
            writeFile(is, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtil.exception("inputCopyToOutput file exception ", e);
        } finally {
            Utils.close(fos);
        }
    }

    /**
     * 检查网络类型
     */
    public static byte checkNet(Context arg5) {
        byte v1 = -1;
        try {
            Object v0_1 = arg5.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (v0_1 == null) {
                byte v0_2 = v1;
                return v0_2;
            }
            NetworkInfo v0_3 = ((ConnectivityManager) v0_1).getActiveNetworkInfo();
            if (v0_3 == null || v0_3.getState() != NetworkInfo.State.CONNECTING && v0_3.getState() != NetworkInfo.State.CONNECTED) {
                return v1;
            }
            if (v0_3.getType() == 1) {
                return 0;
            }
            if (v0_3.getType() != 0) {
                return v1;
            }
            if (Proxy.getDefaultHost() == null) {
                if (Proxy.getHost(arg5) != null) {
                    return 2;
                }
                return 1;
            }
        } catch (Throwable v0) {
            return v1;
        }
        return 2;
    }


    /**
     * 写网络解决方案文件
     * -----------------------------------
     * 将data中的数据写入指定的文件
     */
    public static void writeSolutionFiles(File file, String[] data) {
        LogUtil.loge("写网络解决方案文件");
        Closeable v1_1;
        BufferedWriter bufferedWriter = null;
        Closeable v2 = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (Throwable v0) {
            v1_1 = v2;
            close(v1_1);

        }
        try {
            int v2_1 = data.length;
            int i;
            for (i = 0; i < v2_1; ++i) {
                bufferedWriter.write(String.valueOf(data[i]) + "\n");
            }

            bufferedWriter.flush();
        } catch (Throwable v0) {
//            goto label_23;
            close(bufferedWriter);
        }
        close(((Closeable) bufferedWriter));
        LogUtil.loge("网络所得的文件 写成功 " + file.getAbsolutePath() + " - " + file.getName());
        return;
    }

    /**
     * 监测sdcard的存在与否
     */
    public static boolean isSdcardMounted() {
        String v0 = Environment.getExternalStorageState();
        boolean v0_1 = v0 == null ? false : v0.equals("mounted");
        return v0_1;
    }

    public  static  String getSDCardPath()
    {
        return  Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 记录网络下载文件的方式
     */
    public static void writeSolutionFiles(byte[] data, String filePath) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filePath, false);
            fileOutputStream.write(data);
            fileOutputStream.close();
            LogUtil.loge("写入网络下发记录方案文件 完成 ！！！！ path =" + filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录失败的solutions_id
     */
    public static void recordFailedExploit(Context context, String name) {
        int v0 = SpfUtils.getIntFromMarsrootSharePreferences(context, "solution_fail_count_" + name) + 1;
        LogUtil.e("sid = " + name + ", failCount = " + v0);
        SpfUtils.a(context, "solution_fail_count_" + name, v0);
    }

    /**
     * 读取本地缓存是否存在，如果本地有解决方案，就不用网络下载，
     * 这里记载的是成功的解决方案
     **/
    public static String readLocalSolution(String path) {
        String ret = null;

        File file = new File(path);
        if (!file.exists()) {
            return ret;
        }
        try {
            FileInputStream fin = new FileInputStream(file);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int len = -1;
            byte[] buf = new byte[1024];
            while (true) {
                len = fin.read(buf);
                if (len == -1) {
                    break;
                }
                bo.write(buf, 0, len);
            }

            fin.close();
            bo.close();
            ret = new String(bo.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 读取手机的sdk版本
     **/
    public static int getSdk() {
        return new Integer(Build.VERSION.SDK).intValue();
    }


    /**
     *
     * 判断网络
     * **/
    public static boolean checkNetService(Context context)
    {
        boolean flag = true;

        ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = systemService.getActiveNetworkInfo();
        if (networkInfo != null)
        {
            if (!networkInfo.isConnected())
            {
                flag=false;
            }
        }
        return flag;
    }


    /**
     * 2017-8-11
     * 添加root检测，若果root过并且成功，那么不需要执行root
     *
     *
     * */
    private  void callProcess()
    {
        InputStream inputStream=null;
        java.lang.Process process =null;
        BufferedReader br = null;
        DataOutputStream outputStream = null;
        Log.d("tag----","测试root检测功能 。。。。 ");
        try {
            process = Runtime.getRuntime().exec("su");
            inputStream = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream));
            outputStream = new DataOutputStream(process.getOutputStream());
            String cmd = "id\n";
            outputStream.write(cmd.getBytes());
            outputStream.write("exit\n".getBytes());
            Log.e("tag----","inputCopyToOutput finished    === ");

            String line =null;
            while ((line = br.readLine())!= null)
            {
                Log.d("tag----",line);
                if(line.contains("uid=0(root)"))
                {
                    Log.d("tag----","get root ");
                    SpfUtils.set(App.getContext(),Const.ROOT_SUCESS,true);
                }
            }
            int value = process.waitFor();
            Log.d("tag----","value == "+value);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            if(br != null)
            {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(process != null)
            {
                process.destroy();
                process=null;
            }
        }

    }



}
