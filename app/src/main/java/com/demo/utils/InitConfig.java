package com.demo.utils;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.demo.MainActivity;
import com.demo.handler.MyHandler;
import com.demo.entity.Entity;
import com.demo.entity.EntityManager;
import com.demo.process.ProcessManager;
import com.kingroot.sdk.root.CommonLog;
import com.kingroot.sdk.root.YunRootExcutor;
import com.kingroot.sdk.util.Cryptor;
import com.kingroot.sdk.util.Posix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import http.demo.UrlTest;

public class InitConfig {

    static CommonLog commonLog;
    public static Entity entity;
    public static Context mContext;
    private static HandlerThread handlerThread;
    public static Handler handler;
    /*
    * 首先，init
    * -->getExecutor()
    * -->prepare(){准备的是网络请求的jar文件，需要请求网络}
    * -->execut(){下在解决方案，同时执行方案信息}
    * */
    private static YunRootExcutor yunRootExcutor;

    public static YunRootExcutor getExecutor() {
        yunRootExcutor = new YunRootExcutor(mContext, entity);
        return yunRootExcutor;
    }
    private static boolean isInit = false;
    /**
     * 在Application层面执行之后，就会实则和isInit =false,
     * 也就是说，初始化位置，只会执行一次，就不在执行，那么所有需要的初始化参数。都应该在这里完成执行
     */
    public static boolean initSdk() {
//        if (!isInit) {
//            handlerThread = new HandlerThread("initThread");
//            handlerThread.start();
//            handler = new MyHandler(mContext, entity, handlerThread.getLooper());
//            handler.sendEmptyMessage(Const.INIT_SOURCE);
//            MainActivity.setHandler(handler);
//            isInit = true;
//            SpfUtils.set(mContext,"initsdk",isInit);
//            return isInit;
//        }
        handlerThread = new HandlerThread("initThread");
        handlerThread.start();
        handler = new MyHandler(mContext, entity, handlerThread.getLooper());
        handler.sendEmptyMessage(Const.INIT_SOURCE);
        MainActivity.setHandler(handler);
        isInit = true;
        SpfUtils.set(mContext,"initsdk",isInit);
        return isInit;
    }

    public  static Handler getHandler()
    {
        return  handler;

    }
    /**
     * init 一些常用的类，包括数据类型的参数赋值等
     **/
    public static int init() {
        commonLog = new CommonLog(mContext);
        LogUtil.w("init start");
        UrlTest.init(entity.d, entity.e);//设置url的参数，false,false
        readKrsdkso(mContext, "libkrsdk.1.0.154.so", new File(entity.file, "libkrsdk.1.0.154.so"));
        loadkrsdkso();//load so
        FileUtils.initkrstock();//初始化准备kr-stock-conf文件 ，目的是为了后续的网络请求准备
        EntityManager.init(0,mContext);
        LogUtil.w("EntityMgr init ok");
        openDiR(mContext, "krsdk.res", entity.file);
        readAndDecodeKRSDK(mContext);// 解密文件
        readSuAndSuperApk(mContext, "su", new File(entity.file, "su"));
        readSuAndSuperApk(mContext, "superuser.apk", new File(entity.file, "superuser.apk"));
        //读取krsdk.cert 读取channlid,packge_name字段信息
        OpenKrsdkCert krsdkCert = OpenKrsdkCert.init(mContext.getAssets(), "krsdk.cert");
        String channelId = krsdkCert.getChanel();
        String package_name = krsdkCert.getPackageName();
        LogUtil.e("package_name =" + package_name + " ,channelId =" + channelId);
        EntityManager.setChannelId(channelId);
        //修改文件属性，操作进程
        int ret = startPosixChmod(entity.file);
        LogUtil.d("init  end !!!! ret = ");
        entity.channelId = channelId;//FileUtils.getChannelId(mContext);
        commonLog.recordEMID(200046, 0, "", "", null, new Object[0]);
        //-----------------------------------
        LogUtil.d("初始化完成！点击获取root ");
        handler.sendEmptyMessage(Const.INIT_OK);
        /**
         * vroot.jar是oppo的类型的手机需要加载的classes.dex文件,
         * */
        return ret;
    }
    private static void loadkrsdkso() {
        try {
            File file = new File(entity.file.getAbsolutePath(), "libkrsdk.1.0.154.so");
            if (!file.exists()) {
                LogUtil.w("krsdk.so not found");
                return;
            }
            System.load(file.getAbsolutePath());
            LogUtil.w(file.getName() + " load done");
        } catch (Exception e) {
            LogUtil.exception("loadkrsdkso exception ", e);
        }
    }
    /**
     * 这里是初始化最后一步调用
    * 调用so文件
    * chmod src_file.getAbsolutePath()
     * 修改文件夹中的文件权限
    * */
    private static int startPosixChmod(File srcFile) {
        LogUtil.d("startPosixChmod(File srcFile) " + srcFile.getName());
        File[] files = null;
        int len = 0;
        int index = 0;
        files = srcFile.listFiles();
        LogUtil.loge("files = " + files.length);
        if (files.length < 0 || files == null) {
            LogUtil.loge("there is not file ,are you sure?????");
            return -1;
        }
        len = files.length;
        while (true) {
            try {

                if (index >= len) {
                    //调用process执行sh
                    LogUtil.d("index >= krsdk.dir length ,call ProcessUtils.test()" + "index =" + index + "\tlen = " + len);
                    LogUtil.loge("Excute Process inputCopyToOutput commands =====^^^");
                    int ret = ProcessManager.test("ls -lZ " + entity.file.getAbsolutePath() + "/");
                    LogUtil.loge("iterator file finish ===> process finish ");
                    return ret;
                }
                LogUtil.d("index = " + index);
                File file = files[index];
                ++index;
                if (!file.isFile()) {
                    LogUtil.loge("fileName =" + file.getName() + " ,is not file!!!, continue!!");
                    continue;
                }
                int ret = Posix.chmod(file.getAbsolutePath(), 493);
                LogUtil.d("Posix ret = " + ret + "\t fileName = " + file.getName());

            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.loge("chmod file exception !!!!!" + e.getCause() + " " + e.getMessage().toString());
                break;
            }
        }
        return -1;
    }


    private static void readSuAndSuperApk(Context context, String name, File file) {
        if (file.exists() && file.isFile()) {
            return;
        }
        FileOutputStream fos = null;
        InputStream in = null;
        byte[] v3_2 = new byte[1024];
        byte[] v1_2 = null;
        int len = -1;
        try {
            in = context.getAssets().open(name);
            fos = new FileOutputStream(file);
            while (true) {
                len = in.read(v3_2);
                if (len == -1) {
                    Utils.close(in);
                    Utils.close(fos);
                    LogUtil.loge("read " + file.getName() + " finished ");
                    return;
                }
                if (len <= v3_2.length) {
                    v1_2 = new byte[len];
                    System.arraycopy(v3_2, 0, v1_2, 0, len);
//                    LogUtil.loge("lastLen = " + v1_2.length);
                } else {
//                    LogUtil.loge("break  -- - ");
                    break;
                }
                v3_2 = Cryptor.z(v1_2);
//                LogUtil.loge("srcLen = " + v3_2.length);
                fos.write(v3_2, 0, v3_2.length);
                v3_2 = v1_2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.close(in);
            Utils.close(fos);
        }
    }

    /*
    * 读取assets目录下的,libkrsdk.1.0.5.so文件
    * */
    private static void readKrsdkso(Context context, String soName, File soFile) {
        if (soFile.exists() && soFile.isFile()) {//避免每次都会调用，那么在这里做判断如果是第一次调用，就会生成文件，如果是多次调用，也不会多次生成
            LogUtil.loge(soFile.getName() + " has already exists,return" + ": " + soFile.getAbsolutePath());
            return;
        }
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(soName);
            fileOutputStream = new FileOutputStream(soFile);
            inputCopyToOutput(inputStream, fileOutputStream);
            LogUtil.loge("read assets file ok  !!!");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.loge("read " + soFile.getName() + " accurred exception " + e.getCause() + " " + e.getMessage().toString());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void readKRSDK_RES(Context context) {
        LogUtil.loge("ready dir krsdk ");
        entity = new Entity();
        File krsdk = context.getDir("krsdk", 0);
        entity.file = krsdk;
        entity.d = false;
        entity.e = false;
        mContext =context;
        isInit= SpfUtils.get(context,"initsdk");
        /**********************************/
        String apk="MySuperuser.apk";
        String mysu ="mysu";
        String msu="msu";//修改的开源的su文件，不能编译通过
        String sub ="sub";//编译的android 源码的su文件，不能拿到权限
        String au="au";//修改的Android的源码下的su文件，
        String ssu = "ssu";

        String my ="my";

        test(krsdk,context, apk);
        test(krsdk,context, mysu);
        test(krsdk,context, my);
        test(krsdk,context, msu);
        test(krsdk,context, sub);//su source binary file
        test(krsdk,context, Const.SU_NAME);
        test(krsdk,context, ssu);
        /*************************************/
        /**测试使用，把自己编译的superuser.apk文件放入到krsdk目录下，同时在执行root的时候，把自定义的superuser.apk替代掉**/

    }

    /****************************
     * test start
     *********************/
    /**
     * 检测文件是否存在
     * @param krsdk
     * @param context
     * @param name
     */
    private static void test(File krsdk, Context context, String name) {
        try {
            InputStream open = context.getAssets().open(name);
            File file = new File(krsdk, name);
            if (file.exists()) {
                return;
            }
            FileOutputStream fout = new FileOutputStream(file);
            int len = -1;
            byte[] buf = new byte[1024];
            while (true) {
                len = open.read(buf);
                if (len == -1) {
                    break;
                }
                fout.write(buf, 0, len);
            }
            open.close();
            fout.close();
            LogUtil.e("读取自定义的可以执行文件");
        } catch (Throwable v) {
            LogUtil.exception("测试使用自己的superuser.apk文件出现异常信息", v);
        }
    }


/************************test end*************************/

    /**
     * 读取assest目录下的krsdk.res文件， 然后解密这个文件调用getKRSDK_RES(File,File)
     */
    private static void openDiR(Context context, String krsdk, File krsdk_dir) {
        // TODO Auto-generated method stub
        openDiRAndWrite(context, krsdk, new File(krsdk_dir, krsdk));
    }

    /**
     * 将asset中的文件copy到/data/data/packetName/fileDir中
     * @param context
     * @param name
     * @param krsdk_file
     */
    private static void openDiRAndWrite(Context context, String name, File krsdk_file) {
        if (krsdk_file.exists() && krsdk_file.isFile()) {
            LogUtil.d(krsdk_file.getName() + " has already exists !!! ,======> return");
            return;
        }
        FileOutputStream fileOutput = null;
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(name);
            LogUtil.d("GOIT Assets=====<<<");
            fileOutput = new FileOutputStream(krsdk_file);// 向指定的文件写入数据
            long ret = inputCopyToOutput(inputStream, fileOutput);
            LogUtil.d("ret=====>");
            if (ret > 0) {
                LogUtil.d("succd ret = " + ret);
            } else {
                LogUtil.d("fail ret = " + ret);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogUtil.d("exception = " + e.getCause() + "\t" + e.getMessage().toString());
        } finally {
            Utils.close(fileOutput);
            Utils.close(inputStream);
        }
    }

    /*
    * 根据文件流写文件,krsdk.1.0.5.so
    *
    * */

    /**
     * 将输入流中的数据copy到输出流中
     * @param inputStream
     * @param outputStream
     * @return
     */
    public static long inputCopyToOutput(InputStream inputStream, OutputStream outputStream) {
        byte[] buffer = new byte[1024];
        long length = 0;
        int size = 0;
        try {
            for (length = 0; true; length += ((long) size)) {
                size = inputStream.read(buffer);
                if (size == -1) {
                    return length;
                }
                outputStream.write(buffer, 0, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("exception = " + e.getCause() + "\t" + e.getMessage().toString());
        }
        return length;
    }

    private static File krsdk_dir;//src_file
    private static File krsdk;//dst_file

    /*
     * 读取文件信息
     * 解密文件
     * 这里边读取了许多需要的文件
     * chattr,chown,等文件
     *
     */
    private static void readAndDecodeKRSDK(Context context) {
        krsdk_dir = new File(entity.file, entity.krsdk_res);
        krsdk = new File(entity.file, "__krsdk.res__");
        getKRSDK_RES(krsdk_dir, krsdk);//读取文件信息
        LogUtil.d("krsdk_dir = " + krsdk_dir.toString() + "\t" + krsdk.toString());
        zipFile(krsdk_dir, krsdk);//解压文件
    }

    /*
     * 解压文件
     */
    @SuppressWarnings("unused")
    private static void zipFile(File src, File dst) {
        try {
            FileInputStream dstFileInputStream = new FileInputStream(dst);
            if (dstFileInputStream == null) {
                LogUtil.loge("dstFileInputStream ==null" + "entity filepath = " + entity.file.getAbsolutePath());
                return;
            }
            // 解压
            // zip
            LogUtil.d("after = " + dstFileInputStream.toString());
            zipFile(dstFileInputStream, entity.file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtil.d(" jie ya exception = " + e.getCause() + "\t" + e.getMessage().toString());
        }
    }

    /*
     * 第一步
     * 这里会检测是否需要更新文件,在执行解压之前
     *
     */
    private static void zipFileByInputStream(InputStream arg7, String arg8) {
        LogUtil.d("pan duan jieya wenjian ");
        File v4;
        File v1 = new File(arg8);
        v1.mkdirs();
        try {
            ZipInputStream v2 = new ZipInputStream(arg7);
            while (true) {
                ZipEntry v3 = v2.getNextEntry();
                if (v3 == null) {
                    LogUtil.d("no more zip entity judge-=---->>>");
                    return;
                }
                //-------------------
                else if (v3.isDirectory()) {
                    v4 = new File(v1, v3.getName());
                    if (v4.exists()) {
                        continue;
                    }

                    LogUtil.d("kingroot-sdk\t" + "xxx" + v4.getAbsolutePath() + " need update");

                } else {
                    v4 = new File(v1, v3.getName());
                    if ((v4.exists()) && (v4.isFile())) {
                        if ("ku-config".equals(v4.getName())) {
                            LogUtil.d("kingroot-sdk\t" + "xxx ku-config no check.");
                            continue;
                        } else {
                            String v3_1 = md5(v4);
                            String v5 = md5(v2);
                            //MD5校验
                            if (v3_1 != null && v5 != null && (v3_1.equalsIgnoreCase(v5))) {
                                LogUtil.d("kingroot-sdk\t" + "xxx" + v4.getAbsolutePath() + " no changed.");
                                continue;
                            }
                            break;
                        }
                    } else {
                        LogUtil.d("!v4.exists() && v4.isFile() " + v4.getName() + "\t" + v4.getAbsolutePath());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("exception =" + e.getCause() + "\t" + e.getMessage().toString());
        }
    }

    private static String md5(File file) {
        String v1_1 = null;
        try {
            FileInputStream v0 = new FileInputStream(file);
            v1_1 = md5(v0);
            return v1_1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
    *
    * MD5校验
    * */
    private static String md5(InputStream arg4) {
        try {

            MessageDigest v0_1 = MessageDigest.getInstance("MD5");
            byte[] v1 = new byte[1024];
            while (true) {
                int v2 = arg4.read(v1);
                if (v2 == -1) {
                    break;
                }
                v0_1.update(v1, 0, v2);
            }

            String v0_2 = update(v0_1.digest());
            LogUtil.d("md5 = " + v0_2);
            return v0_2;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogUtil.d("md5 exception ==>>" + e.getCause() + "\t" + e.getMessage().toString());
        }
        return "";
    }

    private static String update(byte[] arg5) {
        String v0;
        if (arg5 == null) {
            v0 = "";
        } else {
            StringBuffer v1 = new StringBuffer(arg5.length * 2);
            int v0_1;
            for (v0_1 = 0; v0_1 < arg5.length; ++v0_1) {
                v1.append("0123456789ABCDEF".charAt(arg5[v0_1] >> 4 & 15)).append("0123456789ABCDEF".charAt(arg5[v0_1] & 15));
            }
            v0 = v1.toString();
            return v0;
        }
        return "";
    }

    public static void zipFile(File file, String arg) {
        LogUtil.e("第三个 javaRoot 调用解压");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            zipFile(fileInputStream, arg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Utils.close(fileInputStream);
        }
        Utils.close(fileInputStream);
    }

    /***
     * 解压文件并且写出每一个文件
     * 存放在data/data/com.demo/app_krsdk
     * 写文件貌似存在问题
     * @param inputStream
     * @param fileDirStr
     */
    private static void zipFile(InputStream inputStream, String fileDirStr) {
        LogUtil.d("kai shi jie ya ==========>>>");
        FileOutputStream fileOutputStream = null;
        File fileDir = new File(fileDirStr);
        if (!fileDir.isDirectory() && !fileDir.exists()) {
            LogUtil.loge(fileDir.getName() + "is not exists" + "\t" + ",and create it now !");
            fileDir.mkdirs();
        }

        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        try {
            while (true) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                if (zipEntry == null) {
                    LogUtil.d("no more zipEntities====>>> zipEntry == null");
                    return;
                }
                LogUtil.d("KRSLOG\t" + "entry = " + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    new File(fileDir, zipEntry.getName()).mkdirs();
                    LogUtil.d("KRSLOG" + "mkDir : " + fileDir.getAbsolutePath());
                    continue;
                }
                File entryFile = new File(fileDir, zipEntry.getName());
                File parentFile = entryFile.getParentFile();
                LogUtil.d("KRSLOG" + "parent : " + parentFile.getAbsolutePath());
                if (parentFile != null && !parentFile.exists()) {
                    LogUtil.d("KRSLOG \t" + "mkDir2 : " + parentFile.getAbsolutePath());
                    parentFile.mkdirs();
                }
                entryFile.delete();
                LogUtil.d("-----delete----- " + entryFile.getName());
                fileOutputStream = new FileOutputStream(entryFile);
                LogUtil.d("+++++++new File " + fileOutputStream.toString());
                inputCopyToOutput(zipInputStream, fileOutputStream);
                fileOutputStream.flush();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogUtil.d("exception  = " + e.getCause() + "\t" + e.getMessage().toLowerCase());
        } finally {
            if (zipInputStream != null) {
                try {
                    LogUtil.d("close zip");
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    LogUtil.d("close fos");
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * 在调用了openDIR()函数之后。开始调用这个方法 这里暂时不管这个方法，主要是解决掉krsdk.res文件的读取工作
     */

    /**
     * 读取file1中文件解密并写入file2中
     * @param file1
     * @param file2
     */
    public static void getKRSDK_RES(File file1, File file2) {
        byte[] v1_2;
        byte[] readBuffer;
        FileInputStream fileInput = null;
        int size;
        FileOutputStream fileOutput = null;
        try {
            fileInput = new FileInputStream(file1);
            fileOutput = new FileOutputStream(file2);
            size = 1024;
            readBuffer = new byte[size];
            LogUtil.d("zhun bei jie mi le ===");
            while (true) {
                int readLength = (fileInput).read(readBuffer);
                if (readLength == -1) {
                    LogUtil.loge("file length < 0");
                    break;
                }

                if (readLength <= readBuffer.length) {

                    v1_2 = new byte[readLength];
                    System.arraycopy(readBuffer, 0, v1_2, 0, readLength);
                    LogUtil.d("lastLen = " + v1_2.length);
                } else {
                    LogUtil.d("file length >1024 ");
                    break;
                }
                readBuffer = Cryptor.z(v1_2);
                fileOutput.write(readBuffer, 0, readBuffer.length);
                readBuffer = v1_2;
            }
            LogUtil.d("krsdk.res decode finish");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogUtil.loge("exception = " + e.getCause() + "\t" + e.getMessage().toString());
        } finally {
            if (fileInput != null) {
                try {
                    fileInput.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (fileOutput != null) {
                try {
                    fileOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
