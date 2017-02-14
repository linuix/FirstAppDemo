package com.demo.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/9/28.
 */

public class DatFile {

    public static int a;
    public static int b;
    public static byte[] c;
    public DatFile(){

    }
    public static DatFile stream2DatFile(InputStream arg3){
        DatFile datFile = new DatFile();
        int v0 = 4;
        try {
            byte[] v0_2 = new byte[v0];
            arg3.read(v0_2);
            datFile.a = byte2Int(v0_2);
            arg3.read(v0_2);
            datFile.b = byte2Int(v0_2);
            v0_2 = new byte[16];
            arg3.read(v0_2);
            datFile.c = v0_2;
            return datFile;
        }
        catch(IOException v0_1) {
            v0_1.printStackTrace();
        }
        return null;
    }

    private static int byte2Int(byte[] arg3) {
        int v0 = 0;
        if(arg3.length == 4) {
            v0 = arg3[0] & 255 | (arg3[1] & 255) << 8 | (arg3[2] & 255) << 16 | (arg3[3] & 255) << 24;
        }

        return v0;
    }





}
