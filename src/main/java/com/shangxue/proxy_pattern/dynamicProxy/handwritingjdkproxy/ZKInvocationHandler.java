package com.shangxue.proxy_pattern.dynamicProxy.handwritingjdkproxy;

import java.lang.reflect.Method;

/**

 * @description: 手写的handler

 **/
public interface ZKInvocationHandler {
    //调用
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
