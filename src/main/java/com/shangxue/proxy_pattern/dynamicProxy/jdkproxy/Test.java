package com.shangxue.proxy_pattern.dynamicProxy.jdkproxy;

import com.shangxue.proxy_pattern.Person;
import sun.misc.ProxyGenerator;


import java.io.FileOutputStream;

public class Test {

    public static void main(String[] args) {
        //获得了动态生成的代理对象
        Object obj=new JDKMeipo().getInstance(new Girl());
        System.out.println(obj);
        System.out.println(obj.getClass());
        //通过反编译工具可以查看生成的代理类的源代码
        byte[] bytes=ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{Person.class}); //从JVM中把该名字的.class找出来 //第二个参数是class类型的数组？ 我本还以为Class是不能直接被new的因为构造器私有了，我还以为在写new抽象类的实现类呢，但是漏看了[]
        try {
            FileOutputStream os=new FileOutputStream("D:\\javademo\\$Proxy0.class");
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//原理
//1.拿到被代理对象的引用，并且获取到它的所有的接口（通过反射获取）
//2. JDK Proxy类重新生成一个新的类，同时新的类要实现被代理类所有实现的接口(不能漏被代理类的东西呀。所以1里要也获取它实现的接口~)
//3.动态生成java代码，把新加的业务逻辑方法由一定的逻辑代码去调用（在代码中体现）  【运行时才干的这些事，也就是动态。通过反射。】
//4.编译新生成的java代码（多诡异多牛逼，还真能这么干啊），变成.class
//5.在重新加载到JVM中运行
//以上这个过程就叫做字节码重组。也就是手写jdk代理需要：自己用（我写）代码来写（增强类/代理类）代码，用代码来编译代码，用代码来重新加载代码，用代码来运行代码。


//java自动生成的代理类举例 ： $proxy0 (JDK中有个规范，只要是$开头的一般都是自动生成的（$可以取名，但因为绝大多数人取名不会用$，那架构师就用去取名做一些特殊的标识了）。比如内部类： 父类$类名 )。
// 找不到的，除非自己通过ProxyGenerator,io流输出来.class，通过反编译看java（IDEA会自动转，这也是IDEA里看jar包可以看到java的原因）。

/**
 *总结：
 *jdk的动态代理需要实现一个接口。（cglib不用，普通类就行了）
 */
