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
    private static RSAPublicKey a;
    private Properties b;
    private static  char[] symbols;

    static {
        symbols = "0123456789abcdef".toCharArray();
    }
    static {
        RSAPublicKey v2 = null;
        X509EncodedKeySpec v1 = new X509EncodedKeySpec(Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCTrqGfyNYDKZEFfvXuYOu9mSCNu6ri10PMG2xJ5sBuUN2OFBT1W5n/dyUkR+Xgnd6w9arSFnU/8fpiP4DRZPL7pkmgzJvjoPqrreXO4nGRQtVbp6sD/gWCKsTlJ9bk01W32gfSOrCNch8BQJO8nE01ffnWmyRiqVTbuh9KEGgcwIDAQAB", 0));
        try {
            OpenKrsdkCert.a = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(((KeySpec)v1));
        }
        catch(NoSuchAlgorithmException v0) {
            v0.printStackTrace();
            OpenKrsdkCert.a = v2;
        }
        catch(InvalidKeySpecException v0_1) {
            v0_1.printStackTrace();
            OpenKrsdkCert.a = v2;
        }
    }

    private OpenKrsdkCert(Properties arg1) {
        super();
        this.b = arg1;
    }

    public static OpenKrsdkCert init(AssetManager arg6, String arg7) {
        OpenKrsdkCert v2_1 = null;
       LogUtil.d("KRSDKCertificate loadFromAsset >>>>>>>>>>");
        InputStream v1=null;
        try {
             v1 = arg6.open(arg7, 1);
            if(OpenKrsdkCert.a(v1) != 1413698123) {
                throw new DataFormatException("Not sdk_gt18 kingroot sdk certification file");
            }
            int v0_1 = OpenKrsdkCert.a(v1);
            byte[] v2 = new byte[OpenKrsdkCert.a(v1)];
            byte[] v3 = new byte[v0_1];
            v1.read(v2);
            Inflater v4 = new Inflater();
            v4.setInput(v2);
            if(v0_1 != v4.inflate(v3)) {
                throw new DataFormatException("Unexpected data length");
            }
            v4.end();
            Properties v0_2 = new Properties();
            v0_2.loadFromXML(new ByteArrayInputStream(v3));
            v3 = new byte[OpenKrsdkCert.a(v1)];
            v1.read(v3);
            Signature v4_1 = Signature.getInstance("SHA1WithRSA");
            v4_1.initVerify(OpenKrsdkCert.a);
            v4_1.update(v2);
            if(!v4_1.verify(v3)) {
                throw new SignatureException("Bad signature");
            }

            v2_1 = new OpenKrsdkCert(v0_2);
        }
        catch(Throwable v0) {
            Utils.close(v1);
        }

        Utils.close(v1);
        return v2_1;
    }

    public final String a() {
        return this.b.getProperty("channel_id");
    }

    public final boolean a(Context arg5) {
        boolean v0 = false;
        String v1 = null;
        try {
            v1 = OpenKrsdkCert.b(arg5);
            if(!arg5.getPackageName().equals(this.b())) {
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

    private static int a(InputStream arg2) {
        int ret =0;
        try {
             ret =arg2.read() | arg2.read() << 8 | arg2.read() << 16 | arg2.read() << 24;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public final String b() {
        return this.b.getProperty("package_name");
    }

    public static PackageInfo a(Context arg3, String arg4) {
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
        PackageInfo v0 = a(arg4, arg4.getPackageName());
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
        return this.b.getProperty("cert_md5");
    }
}

