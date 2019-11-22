package com.shangxue.proxy_pattern.dynamicProxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**

 **/
public class JDKMeipo implements InvocationHandler {
    private Object target;
    public Object getInstance(Object target){
        this.target=target;
        Class<?> clazz = target.getClass();  //为嘛不用反射根据字符串类型的全路径去获取Class对象，而用这么low的方式？
        //用来生成一个新的对象（字节码重组来实现） （相当于我们静态代理的代理类）
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        before();

        Object obj= method.invoke(this.target,args);

        after();

        return obj;
    }

    private void before(){
        System.out.println("我是媒婆，我要给你找对象，现在已经拿到你的需求");
        System.out.println("开始物色");
    }

    private void after(){
        System.out.println("如果合适的话，准备办事");
    }
}
