package com.shangxue.proxy_pattern.dynamicProxy.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;

public class CglibMeipo  implements MethodInterceptor{


    public Object getInstance(Class<?> clazz) throws Exception{
        Enhancer enhancer=new Enhancer();// Enhancer 增强
        //要把哪个设置为即将声称的新类(代理类)的父类（目标类）
        enhancer.setSuperclass(clazz);

        enhancer.setCallback(this);

        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("我是媒婆，我要给你找对象，现在已经拿到你的需求");
        System.out.println("开始物色");

        methodProxy.invokeSuper(o,objects);

        System.out.println("如果合适的话，准备办事");
        return o;
    }
}
