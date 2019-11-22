package com.shangxue.proxy_pattern.dynamicProxy.jdkproxy;

import com.shangxue.proxy_pattern.Person;

/**
 *
 **/
public class Girl implements Person {  //如果不实现接口，调用那个jdk的‘获得代理对象“的方法时会异常，它变不了$Proxy$，无法强转
    public void findLove() {
        System.out.println("高富帅");
    }
}

