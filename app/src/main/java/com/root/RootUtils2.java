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
    public static void writDataToFile(File file, String[] datas)
    {
        BufferedWriter bufferedWriter = null;
        Closeable v2 = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            int v2_1 = datas.length;
            int i;
            for (i = 0; i < v2_1; ++i) {
                bufferedWriter.write(String.valueOf(datas[i]) + "\n");
                bufferedWriter.flush();
            }
            Utils.close(bufferedWriter);

        } catch (IOException e) {
            e.printStackTrace();
            Utils.close( bufferedWriter);
            LogUtil.exception("rootUtils2 excepiton", e);

        }
    }

    public static String a(String arg2, String arg3) {
        String v0 = arg2 == null || !arg2.contains(((CharSequence)arg3)) ? null : arg2.substring(arg2
                .indexOf(arg3) + arg3.length()).trim();
        return v0;
    }

}
