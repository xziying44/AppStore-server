package com.xziying.appstorecloud.test.authorize;

import java.io.*;
import java.lang.reflect.Field;

/**
 * MyClassLoader
 *
 * @author : xziying
 * @create : 2021-04-11 12:15
 */
public class MyClassLoader  extends ClassLoader{

    public Class<?> load(String clazz, String fileName){
        byte[] bytes=null;
        try {
            InputStream is=new FileInputStream(fileName);
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            byte[] buf=new byte[1024];
            int r=0;
            while ((r=is.read(buf))!=-1){
                bos.write(buf,0,r);
            }
            bytes=bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(clazz,bytes,0,bytes.length);
    }

}