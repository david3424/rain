package org.david.rain.base;

/**
 * Created by mac on 15-3-31.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 加载网络class的ClassLoader
 */
public class NetWorkClassLoader extends ClassLoader {

    private String rootUrl;

    public NetWorkClassLoader(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = null;//this.findLoadedClass(name); // 父类已加载
        //if (clazz == null) {	//检查该类是否已被加载过
        byte[] classData = getClassData(name);    //根据类的二进制名称,获得该class文件的字节码数组
        if (classData == null) {
            throw new ClassNotFoundException();
        }
        clazz = defineClass(name, classData, 0, classData.length);    //将class的字节码数组转换成Class类的实例
        //}
        return clazz;
    }

    private byte[] getClassData(String name) {
        InputStream is = null;
        try {
            String path = classNameToPath(name);
            URL url = new URL(path);
            byte[] buff = new byte[1024 * 4];
            int len = -1;
            is = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String classNameToPath(String name) {
        return rootUrl + "/" + name.replace(".", "/") + ".class";
    }



    public static void main(String[] args) {
        try {
            /*ClassLoader loader = ClassLoaderTest.class.getClassLoader();  //获得ClassLoaderTest这个类的类加载器
            while(loader != null) {
                System.out.println(loader);
                loader = loader.getParent();    //获得父加载器的引用
            }
            System.out.println(loader);*/


            String rootUrl = "http://localhost:8080/httpweb/classes";
            NetWorkClassLoader networkClassLoader = new NetWorkClassLoader(rootUrl);
            String classname = "org.classloader.simple.NetClassLoaderTest";
            Class clazz = networkClassLoader.loadClass(classname);
            System.out.println(clazz.getClassLoader());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

