package com.demo.test;

import android.content.Context;

import com.demo.entity.Entity;
import com.demo.utils.LogUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/10/8.
 */

public class Test {

    public Test(InputStream str , InputStream err){
        writeStr(str);
        writeStr(err);
    }

    private void writeStr(InputStream in){
        LogUtil.d("inputCopyToOutput str stream ");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = null;
        String data = null;
        try {
            while ((line = br.readLine())!=null){
                data+= line+"\r\n";
            }
            LogUtil.d("获取到的流信息是 ：==== "+data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  static String getkrcfg_txt(Context context , Entity entity)

    {
        if (entity != null && context != null)
        {
            String path = entity.file.getAbsolutePath()+ File.separator+"play";
            String name= "krcfg.txt";
            File file  = new File(path,name);
            if (file.exists() && file.isFile())
            {
                try {
                    FileInputStream fin = new FileInputStream(file);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    byte[] buf= new byte[1024];
                    int len =-1;
                    while (true)
                    {
                        len = fin.read(buf);
                        if (len != -1)
                        {
                            bs.write(buf,0,len);
                            bs.flush();
                        }
                        else
                        {
                            break;
                        }
                    }

                    bs.close();
                    String ret = new String(bs.toByteArray()).trim();
                    if ( ret == null)
                    {
                        return  "";
                    }
                    return  ret;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return  "";

    }




}
