package com.demo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.demo.App;
import com.demo.entity.util.PhoneInfoUtil;
import com.kingroot.common.utils.encode.Encode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import http.demo.SolutionHelpers;

public class FileUtils {

    /**
     * 删除掉xml,.jars文件夹
     */
    public static boolean isDelete(String arg1) {
        boolean v0_1;
        try {
            v0_1 = new File(arg1).delete();
        } catch (Exception v0) {
            v0_1 = false;
        }

        return v0_1;
    }

    /*
    *  解读assets目录下的40236.dat文件，写文件保存
    * com.kinguser.sdk.ret/cmd:cmd(int)
    * **/
    private static void openDatFile(Context context) {
        byte[] data;
        InputStream is = null;
        try {
            is = context.getAssets().open("40236.dat");
            DatFile datFile = DatFile.stream2DatFile(is);
            //判断并且输出数据
            if (judgeDat(datFile)) {
                LogUtil.d("bytec = " + new String(datFile.c) + " =cmd= " + datFile.a + " =stdout= " + datFile.b);
            }
            //--------------

//            decodeAssetFile2Stream(datFile.err);//使用aes解密不成功

            writeByteBuff(datFile.c);

            //-------
            //调用native加密
            data = encode(context, is);
            if (judgeDat(data)) {
                LogUtil.d("native encode  = " + new String(data));
            }
            //判断并输出数据
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void writeByteBuff(byte[] arg) {
        ByteBuffer bb = ByteBuffer.wrap(arg);
        int a = bb.getInt();
        int b = bb.getShort();
        int c = bb.get();
        char d = bb.getChar();
        LogUtil.loge("byte buf = " + a + " - " + b + " - " + c + " - " + d + " - " + new String(bb.toString()));
    }

    private static boolean judgeDat(Object object) {

        if (object != null) {
            LogUtil.d("the data = " + new String(object + ""));
            return true;
        } else {
            LogUtil.loge("object  == nulll");
            return false;
        }
    }

    private static byte[] encode(Context context, InputStream is) {
        byte[] v0_2 = null;
        try {
            v0_2 = new byte[is.available()];
            is.read(v0_2);
            LogUtil.loge("v0_2 len = " + v0_2.length + "  obj = " + new String(v0_2));
            byte[] data = NativeHelper.a(context, v0_2);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 读取config.properties文件
    *
    *
    * */
    public static String getChannelId(Context context) {
        InputStream v1_4;
        int v1_3;
        InputStream v0;
        byte[] buf = null;
        ByteArrayOutputStream baos = null;
        AssetManager assetManager = context.getAssets();
        LogUtil.d("GOIT config ===>");
        try {
            v0 = assetManager.open("config.properties");
            LogUtil.d("GOIT config ===>");
            v1_3 = 1024;
            buf = new byte[v1_3];
            baos = new ByteArrayOutputStream();
            while (true) {
                int index = v0.read(buf);
                if (index <= 0) {
                    break;
                }

                baos.write(buf, 0, index);
            }
            LogUtil.d("encode ===>");
            return write2String(baos);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("Exc = " + e.getCause() + "  " + e.getMessage().toString());
        }
        return null;
    }

    public static String write2String(ByteArrayOutputStream baos) {
        try {
            LogUtil.d("GOIT config ===>");
            byte[] buf = new byte[1024];
            byte[] config_Array = baos.toByteArray();
            //调用加密函数
            buf = Encode.y(App.getContext(), config_Array);
            String config = new String(baos.toByteArray(), "UTF-8");

            LogUtil.d("encode = " + config);
//            decodeStock2String(config);
//            writeByteBuff(baos.toByteArray());
            LogUtil.d("decode the stream ===>");
            return getChannel(buf);//获取channel

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 根据配置文件的内容获取channel
    * */
    public static String channelId;


    private static String getChannel(byte[] buf) {
        Properties properties = new Properties();
        try {
            properties.load(new ByteArrayInputStream(buf));
            String channel = properties.getProperty("channel_id");
            if (channel == null) {
                return "105006";
            }
            LogUtil.d(" === channel = " + channel);
            channelId = channel;
            //这里可以写kr-stock-conf
//            String stocl_conf = properties.getProperty("kr-stock-conf");
//            LogUtil.d("stock_conf = " + stocl_conf);//解密这个文件信息，看看是什么
//            writeFile(stocl_conf);//写文件
            //解密文件
//            decodeStock2String("");


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtil.loge("properties exception = " + e.getCause() + " " + e.getMessage().toString());
        }
        return channelId;
    }

    private static InputStream string2InputStream(String msg) {
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(msg.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (is == null) {
            return null;
        }
        return is;
    }

    private static byte[] getKeyByte() {
        return key(key1().getBytes()).getBytes();
    }

    private static SecretKeySpec getKey() {
        SecretKeySpec v3 = null;
        try {
            v3 = new SecretKeySpec(getKeyByte(), "AES");
        } catch (Exception e) {
            LogUtil.exception("get SecretKeySpec v3 exception ", e);
        }
        return v3;
    }

    private static Cipher getCipher() {
        Cipher v2 = null;
        try {
            v2 = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return v2;
    }


    public static String key1() {
        StringBuilder v1 = new StringBuilder();
        Field[] v2 = Build.class.getFields();
        int v0;
        for (v0 = 0; v0 < 10; ++v0) {
            int v3 = (v0 << 3) % v2.length;
            Field v4 = v2[v3];
            if (v4 != null && (v4.getType().equals(String.class))) {
                Object v5 = null;
                try {
                    v1.append(v4.get(v5));
                } catch (IllegalAccessException v4_1) {
                    v1.append(v3);
                } catch (IllegalArgumentException v4_2) {
                    v1.append(v3);
                }
            }
        }

        return v1.toString();
    }


    public static String key(byte[] arg7) {
        byte[] v2 = c(arg7);

        StringBuffer v3 = new StringBuffer(v2.length);
        int v0;
        for (v0 = 0; v0 < v2.length; ++v0) {
            String v4 = Integer.toHexString(v2[v0] & 255);
            if (v4.length() < 2) {
                v3.append(0);
            }

            v3.append(v4.toUpperCase());
        }

        return v3.toString();
    }

    private static byte[] c(byte[] arg3) {
        byte[] v0 = null;
        try {
            MessageDigest v1_1 = MessageDigest.getInstance(new String(getMd5("4D4435")));
            v1_1.update(arg3);
            v0 = v1_1.digest();
        } catch (NoSuchAlgorithmException v1) {
            v1.printStackTrace();
        }

        return v0;
    }

    public static byte[] getMd5(String arg5) {
        int v1 = arg5.length() / 2;
        byte[] v2 = new byte[v1];
        int v0;
        for (v0 = 0; v0 < v1; ++v0) {
            v2[v0] = Integer.valueOf(arg5.substring(v0 * 2, v0 * 2 + 2), 16).byteValue();
        }

        return v2;
    }


    /**
     * 初始化kr-stock-conf文件
     */
    static Context context;
    static String path;

    public static void initkrstock() {
        context = InitConfig.mContext;
        path = context.getFilesDir() + File.separator + "kr-stock-conf";

        if ("mounted".equals(Environment.getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory() + File.separator + "kr-stock-conf";
        }
        if (path != null && new File(path).exists()) {
            properties = getProperties(path);
            storeData(properties, path);
        } else {
            if (properties == null) {
                properties = new Properties();
            }
        }
    }

    public static void setKrsdkconfData(String data) {
        if (!TextUtils.isEmpty(data)) {
            storeData("w.g", data);
        }
    }

    private static void storeData(Properties properties1, String path) {
        try {
            ByteArrayOutputStream v0_1 = new ByteArrayOutputStream();
            properties1.store(((OutputStream) v0_1), "");
            byte[] v1 = getKeyByte();
            byte[] v2 = v0_1.toByteArray();
            SecretKeySpec v3 = getKey();
            Cipher v1_1 = getCipher();
            v1_1.init(1, ((Key) v3));
            v1 = v1_1.doFinal(v2);
            v0_1.close();
            //写文件
            writekrStockFile(v1, path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }


    private static void storeData(String key, String value) {
        if (properties != null) {
            properties.setProperty(key, value);
            storeData(properties, path);
        }
    }


    private static void writekrStockFile(byte[] arg2, String arg3) {
        FileOutputStream v0 = null;
        try {
            v0 = new FileOutputStream(arg3, false);
            v0.write(arg2);
            v0.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加lguid，从网络请求的数据中读取
     */
    public static String getLguid() {
        return get("w.g");
    }

    /**
     * 读取网络反馈数据 guid
     */
    public static String getGuid() {
        String ret = get("s.i");
        if (TextUtils.isEmpty(ret)) {
            ret = PhoneInfoUtil.getIMEI(context);
            if (ret == null) {
                ret = "";
            } else if (ret != "00000000000001") {
                storeData("s.i", ret);
            }
        }
        return ret;
    }

    /*
    *
    * 在这里try{}catch{}一下
    * */
    public static String get(String arg) {
        String ret = "";
        try {
            if (properties != null) {
                ret = properties.getProperty(arg);
                if (ret == null) {
                    LogUtil.e("读取出现null " + arg + "出现空值");
                    return ret;
                }
                LogUtil.d("读取得到" + arg + " =" + ret);
            }

        } catch (Exception e) {
            LogUtil.exception("在getguid出现异常信息，", e);
            return ret;
        }

        return ret;
    }

    static Properties properties;

    /**
     * 读取出kr-stock-conf.内的数据
     */
    private static Properties getProperties(String path) {
        if (new File(path).exists()) {
            try {
                properties = new Properties();
                byte[] data = getData(path);
                SecretKeySpec key = getKey();
                Cipher cipher = getCipher();
                cipher.init(2, key);
                ByteArrayInputStream bais = new ByteArrayInputStream(cipher.doFinal(data));
                properties.load(bais);
                bais.close();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    private static byte[] getData(String path) {
        byte[] ret = null;
        FileInputStream v1 = null;
        try {
            v1 = new FileInputStream(path);
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * md5拼接校验位置
     */

    public static String orginizatedMd5(byte[] arg5) {
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
        }

        return v0;
    }

    /**
     * 检测下载文件是否合法性
     */
    public static boolean checkJars(SolutionHelpers solutionHelpers) {
        FileInputStream fin = null;
        MessageDigest md5;
        int size = 1024;
        File file = new File(solutionHelpers.n);
        try {
            if (file.exists() && file.isFile()) {

                if (file.length() == solutionHelpers.e) {
                    md5 = MessageDigest.getInstance("MD5");
                    fin = new FileInputStream(file);
                    byte[] buf = new byte[size];
                    int len = -1;
                    while (true) {
                        len = fin.read(buf);
                        if (len == -1) {
                            break;
                        }
                        md5.update(buf, 0, len);
                    }
                    String checkSum = FileUtils.orginizatedMd5(md5.digest());
                    if (checkSum.compareToIgnoreCase(solutionHelpers.g) == 0) {
                        Utils.close(fin);
                        LogUtil.e("md5 校验完成 !!");
                        return true;
                    }
                    LogUtil.e("校验文件出现错误~~ ，md5 not match@!!!");
                }
            }
            LogUtil.e("没有本地文件");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.close(fin);
        }
        return false;
    }
}