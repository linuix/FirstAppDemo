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
     *
     * */
    public static void writDataToFile(File file, String[] datas)
    {
        BufferedWriter bufferedWriter = null;
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

    public static String isContains(String src, String dst) {
        String result = src == null || !src.contains(dst) ? null : src.substring(src.indexOf(dst) + dst.length()).trim();
        return result;
    }

}
