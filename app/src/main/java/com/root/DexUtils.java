package com.root;

import com.demo.utils.LogUtil;

import dalvik.system.DexClassLoader;

/**
 * Created by Administrator on 2016/11/5.
 */

public class DexUtils {
public static Class<?> loadClass(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent)
{
    Class<?> clzz = null;

    String v1 =null;
   do {
       try {
           DexClassLoader dexClassLoader = new DexClassLoader(dexPath,optimizedDirectory,v1,parent);
           clzz = dexClassLoader.loadClass(libraryPath);
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
           LogUtil.exception("load dexClass exception "+dexPath+" failed .",e);
           sleep();
           LogUtil.loge("will break do while() DexUtils ");
       }
       if (clzz != null)
       {
           LogUtil.d("load classLoader ");
           return clzz;
       }
       return clzz;


   }
   while (true);
}

    private static  void sleep()
    {
        int time = 100;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
