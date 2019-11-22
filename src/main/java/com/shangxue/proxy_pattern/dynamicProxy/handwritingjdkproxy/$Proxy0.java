package com.shangxue.proxy_pattern.dynamicProxy.handwritingjdkproxy;

//Test手动生成的代理类源代码.java
import com.shangxue.proxy_pattern.Person;
import java.lang.reflect.*;
public class $Proxy0 implements Person{
ZKInvocationHandler h;
public $Proxy0(ZKInvocationHandler h) { 
this.h = h;}
public void findLove() {
try{
Method m = Person.class.getMethod("findLove",new Class[]{});
this.h.invoke(this,m,new Object[]{});
//throwable和Exception的区别  throwable中包括exception（异常）和error（错误） https://blog.csdn.net/u012373281/article/details/90690361
}catch(Error _ex) { }catch(Throwable e){
throw new UndeclaredThrowableException(e);
}}}
