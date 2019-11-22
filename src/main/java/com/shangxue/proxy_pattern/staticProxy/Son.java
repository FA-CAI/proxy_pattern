package com.shangxue.proxy_pattern.staticProxy;

import com.shangxue.proxy_pattern.BaBa;
import com.shangxue.proxy_pattern.Person;

/**
 *
 **/
public class Son extends BaBa implements Person {
    public void findLove() {
        System.out.println("找对象要求：肤白貌美大长腿");
    }


    public String name1="子1";

    private String name2="子2";

    public String onlySonName="子独有属性值";

    public void playJJ1(){
        System.out.println("测试继承时方法是看右边吧？--子方法JJ1----play子JJ1 （子类重写的方法）");
    }

    public void playJJ3(){
        System.out.println("子有，父没有，看看以父类型接收子对象之后，该父句柄能不能调父没有的子有的方法");
    }

    @Override
    public String getName2() {
        return name2;
    }

    @Override
    public void setName2(String name2) {
        this.name2 = name2;
    }
}
