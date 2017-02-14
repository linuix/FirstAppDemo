package com.demo.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2016/9/30.
 */

public class DecodAES {

    public static void decodeFile() {
        LogUtil.d("begin decode ^^^^^ ");
        String path = null;
        if (!checkSdCard()) {
            LogUtil.loge("no sdcard found !!!");
            return;
        }
        path = Environment.getExternalStorageDirectory() + File.separator + "kr-stock-conf";
        String pt = "/mnt" + File.separator + "sdcard" + File.separator + "kr-stock-conf";
        String dp = "/mnt" + File.separator + "sdcard" + File.separator + "decode.txt";
        LogUtil.d("path === " + path);
        File file = new File(pt);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File decode = new File(dp);
        if (!decode.exists()) {
            try {
                decode.createNewFile();
                LogUtil.d("=======name = " + decode.getAbsolutePath() + " " + decode.getName());
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.exception("---------IOException", e);
            }
        }
        int tmp = -1;
        byte[] buf = null;
        if (file.exists() && file.isFile()) {
            LogUtil.d(file.getAbsolutePath());
            try {
                fis = new FileInputStream(file);
                buf = new byte[fis.available()];
                fis.read(buf);
                LogUtil.d("buf len == " + buf.length);
                LogUtil.loge("buf =" + new String(buf));
                byte[] ret = aes(buf);
                fos = new FileOutputStream(decode);

                if (ret == null) {
                    return;
                }

                fos.write(ret);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                LogUtil.exception("FileNotFoundException ", e);
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.exception("IOException ", e);
            } finally {
                Utils.close(fis);
                Utils.close(fos);
            }
        }
    }

    public static boolean checkSdCard() {
        String path = Environment.getExternalStorageState();
        boolean flag = path == null ? false : path.equals("mounted");
        return flag;
    }

    private static byte[] aes(byte[] buf) {
        LogUtil.d("begin decode ^^^^^^");

        byte[] key = FileUtils.key(FileUtils.key1().getBytes()).getBytes();

        LogUtil.d("key = " + new String(key));

        SecretKeySpec v3 = new SecretKeySpec(key, "AES");

        try {

            Cipher v1_1 = Cipher.getInstance("AES");

            v1_1.init(2, ((Key) v3));
            LogUtil.d("AES init finish ===");
            buf = v1_1.doFinal(buf);

            String decod = new String(buf);
            LogUtil.loge("decode = " + decod);
            return buf;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            LogUtil.exception("NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            LogUtil.exception("NoSuchPaddingException", e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            LogUtil.exception("InvalidKeyException", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            LogUtil.exception("BadPaddingException", e);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            LogUtil.exception("IllegalBlockSizeException", e);
        }
        return null;
    }

}
