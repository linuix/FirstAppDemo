package com.root;

import com.demo.utils.LogUtil;
import com.demo.utils.Utils;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/7.
 * 第二种方案的常用工具
 */

public class RootUtils2 {

    /**
     * 读取包内的文件
     * */
    public static void a(File arg5, String[] arg6)
    {
        BufferedWriter v1 = null;
        Closeable v2 = null;
        try {
            v1 = new BufferedWriter(new FileWriter(arg5));
            int v2_1 = arg6.length;
            int v0_1;
            for (v0_1 = 0; v0_1 < v2_1; ++v0_1) {
                v1.write(String.valueOf(arg6[v0_1]) + "\n");
                v1.flush();
            }
            Utils.close(v1);

        } catch (IOException e) {
            e.printStackTrace();
            Utils.close(((Closeable) v1));
            LogUtil.exception("rootUtils2 excepiton", e);

        }
    }

    public static String a(String arg2, String arg3) {
        String v0 = arg2 == null || !arg2.contains(((CharSequence)arg3)) ? null : arg2.substring(arg2
                .indexOf(arg3) + arg3.length()).trim();
        return v0;
    }

}
