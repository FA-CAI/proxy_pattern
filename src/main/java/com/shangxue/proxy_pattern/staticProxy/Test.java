package com.shangxue.proxy_pattern.staticProxy;

import com.shangxue.proxy_pattern.Person;

public class Test {

    public static void main(String[] args) {
        Person son = new Son();
        Father father = new Father(son);
        father.findLove();


    }
}

//因为要增加代理的扩展性，所以有了接口
//当业务要扩展，Person接口了加个方法，那目标类和代理类都要增加实现方法（我觉得扩展功能时肯定有侵入啊，这里不爽的其实是要改的太多了吧），很麻烦。

//静态代理：在代理之前，所有东西都是已知的（否则就要去改内部实现代码，破坏封装性，不符合开闭原则） 人工  手动
//动态代理：在代理之前，所有东西都是未知的 （在运行时才知：用反射？） 自动化、智能化   自动