package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by secidea on 2017/9/5.
 */

public class FileUtil{

    public static void saveObjectToFile(Object o,String filePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(o);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public static <T> T getObjectFromFile(File objFile) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(objFile));
        T o = (T) objectInputStream.readObject();
        return o;
    }

    public static void main(String []arg){

    }

}
