package com.demo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public final class OpenKrsdkCert {
    private static RSAPublicKey rsaPublicKey;
    private Properties data;
    private static  char[] symbols;

    static {
        symbols = "0123456789abcdef".toCharArray();
    }
    static {
        RSAPublicKey publicKey = null;
        X509EncodedKeySpec v1 = new X509EncodedKeySpec(Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCTrqGfyNYDKZEFfvXuYOu9mSCNu6ri10PMG2xJ5sBuUN2OFBT1W5n/dyUkR+Xgnd6w9arSFnU/8fpiP4DRZPL7pkmgzJvjoPqrreXO4nGRQtVbp6sD/gWCKsTlJ9bk01W32gfSOrCNch8BQJO8nE01ffnWmyRiqVTbuh9KEGgcwIDAQAB", 0));
        try {
            OpenKrsdkCert.rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(((KeySpec)v1));
        }
        catch(NoSuchAlgorithmException v0) {
            v0.printStackTrace();
            OpenKrsdkCert.rsaPublicKey = publicKey;
        }
        catch(InvalidKeySpecException v0_1) {
            v0_1.printStackTrace();
            OpenKrsdkCert.rsaPublicKey = publicKey;
        }
    }

    private OpenKrsdkCert(Properties arg1) {
        super();
        this.data = arg1;
    }

    public static OpenKrsdkCert init(AssetManager assetManager, String fileName) {
        OpenKrsdkCert openKrsdkCert = null;
       LogUtil.d("KRSDKCertificate loadFromAsset >>>>>>>>>>");
        InputStream inputStream=null;
        try {
             inputStream = assetManager.open(fileName, 1);
            if(OpenKrsdkCert.readInt(inputStream) != 1413698123) {//KRCT头部标识鉴定
                throw new DataFormatException("Not sdk_gt18 kingroot sdk certification file");
            }
            int v0_1 = OpenKrsdkCert.readInt(inputStream);
            byte[] v2 = new byte[OpenKrsdkCert.readInt(inputStream)];
            byte[] v3 = new byte[v0_1];
            inputStream.read(v2);
            Inflater decompresser = new Inflater();// 对byte[]进行解压，同时可以要解压的数据包中的某一段数据，就好像从zip中解压出某一个文件一样。
            decompresser.setInput(v2);
            if(v0_1 != decompresser.inflate(v3)) {
                throw new DataFormatException("Unexpected data length");
            }
            decompresser.end();
            Properties properties = new Properties();
            properties.loadFromXML(new ByteArrayInputStream(v3));
            v3 = new byte[OpenKrsdkCert.readInt(inputStream)];
            inputStream.read(v3);
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(OpenKrsdkCert.rsaPublicKey);
            signature.update(v2);
            if(!signature.verify(v3)) {//签名验证-----cert文件长度0xde----后面为签名信息
                throw new SignatureException("Bad signature");
            }

            openKrsdkCert = new OpenKrsdkCert(properties);
        }
        catch(Throwable v0) {
            Utils.close(inputStream);
        }

        Utils.close(inputStream);
        return openKrsdkCert;
    }

    public final String getChanel() {
        return this.data.getProperty("channel_id");
    }

    public final boolean a(Context arg5) {
        boolean v0 = false;
        String v1 = null;
        try {
            v1 = OpenKrsdkCert.b(arg5);
            if(!arg5.getPackageName().equals(this.getPackageName())) {
                LogUtil.e("Certifacate Fail, PackageName wrong.");
            }
            else if(!v1.equals(this.c())) {
                LogUtil.e("Certifacate Fail, PackageMD5 wrong.");
            }
            else {
                LogUtil.e("Certifacate Succeed.");
                v0 = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }


        return v0;
    }

    private static int readInt(InputStream arg2) {
        int ret =0;
        try {
             ret =arg2.read() | arg2.read() << 8 | arg2.read() << 16 | arg2.read() << 24;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public final String getPackageName() {
        return this.data.getProperty("package_name");
    }

    public static PackageInfo getChanel(Context arg3, String arg4) {
        PackageInfo v0 = null;
        try {
            v0 = arg3.getPackageManager().getPackageInfo(arg4, PackageManager.GET_SIGNATURES);
        }
        catch(Throwable v1) {
            v1.printStackTrace();
        }

        return v0;
    }


    private static byte[] md5(byte[] arg1) {
        byte[] v0_2;
        try {
            MessageDigest v0_1 = MessageDigest.getInstance("MD5");
            v0_1.update(arg1);
            v0_2 = v0_1.digest();
        }
        catch(NoSuchAlgorithmException v0) {
            v0.printStackTrace();
            v0_2 = null;
        }

        return v0_2;
    }

    public static String b(byte[] arg7) {
        byte[] v1 = md5(arg7);
        StringBuilder v2 = new StringBuilder(v1.length * 3);
        int v3 = v1.length;
        int v0;
        for(v0 = 0; v0 < v3; ++v0) {
            int v4 = v1[v0] & 255;
            v2.append(symbols[v4 >> 4]);
            v2.append(symbols[v4 & 15]);
        }

        return v2.toString().toUpperCase();
    }
    private static String b(Context arg4) throws IOException, CertificateException {
        CertificateException v1_1;
        IOException v1_2;
        String v0_3;
        PackageInfo v0 = getChanel(arg4, arg4.getPackageName());
        String v1 = null;
        if(v0 != null) {
            ByteArrayInputStream v2 = new ByteArrayInputStream(v0.signatures[0].toByteArray());
            try {
                v0_3 = b(CertificateFactory.getInstance("X.509").generateCertificate(((InputStream)v2)).getEncoded());
            } catch(CertificateException v0_2) {
                CertificateException v3 = v0_2;
                v0_3 = v1;
                v1_1 = v3;
                v1_1.printStackTrace();
                return v0_3;
            }

            v2.close();
            return v0_3;

        }
        else {
            v0_3 = v1;
        }

        return v0_3;
    }

    public final String c() {
        return this.data.getProperty("cert_md5");
    }
}

