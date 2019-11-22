package com.shangxue.proxy_pattern.dynamicProxy.handwritingjdkproxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**@description:  类加载器
 * */
public class ZKClassLoader extends ClassLoader{

    private File classPathFile;

    public ZKClassLoader(){
        String classPath = ZKClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(classPath);
    }

    //重写一个增强方法，替代ClassLoader这个方法( 父类已经实现了。但我们要任性一下，不用super.findClass(name); )
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String className = ZKClassLoader.class.getPackage().getName() + "." + name;

        if(classPathFile  != null){
            //开始加载。返回一个.class文件
            File classFile = new File(classPathFile,name.replaceAll("\\.","/") + ".class");
            if(classFile.exists()){
                FileInputStream in = null;
                //往JVM中写东西
                ByteArrayOutputStream out = null;
                try{
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte [] buff = new byte[1024];
                    int len;
                    while ((len = in.read(buff)) != -1){
                        out.write(buff,0,len);
                    }
                    //字节加载到JVM中
                    return defineClass(className,out.toByteArray(),0,out.size());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //把流关掉
                    if(null!=in){
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(null!=out){
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
