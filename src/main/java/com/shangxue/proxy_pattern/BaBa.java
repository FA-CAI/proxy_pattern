package com.shangxue.proxy_pattern;

public class BaBa {


    public String name1 = "父1";

    private String name2 = "父2";

    public String onlyFatherName="父独有属性值";

    public void playJJ1() {
        System.out.println("测试继承时方法是看右边吧？！！！属性看右边--父方法JJ1----play父JJ1 （子类中存在重写）");
    }

    public void playJJ2() {
        System.out.println("测试继承时方法是看右边吧？--父方法JJ2--play父JJ2 （子类中光继承未重写。如果调用，子类中没有，会往上去父类里找）");
    }

    public void playJJ4() {
        System.out.println("父有，而子那个类里'没'写（那不就是继承了但未重写覆盖的情况嘛），看看以父类型接收子对象之后，该父句柄能不能调父有的子'没有'(不是真的没有，是子类当类该类里没再写一遍【重写】)的方法。废话，都不想测了不必测了，继承了，当然能调了，不管是父类型接收而是仍用子类型句柄接收子自己对象引用");
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
