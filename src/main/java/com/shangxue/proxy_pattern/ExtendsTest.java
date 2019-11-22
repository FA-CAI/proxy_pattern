package com.shangxue.proxy_pattern;

import com.shangxue.proxy_pattern.staticProxy.Son;

public class ExtendsTest {

    public static void main(String[] args) {
        BaBa b = new Son();
        b.playJJ1();
        b.playJJ2();
       // b.playJJ3();  //找不到该方法。不能调！
        b.playJJ4();  //不管是从父角度调，而是子角度掉，因为继承且子里没重写，那么，其实一码事呀是对同一个方法呀！（ 那么，java寻找路径是"子找不到该玩意，去父里找（因为"继承"关系呀），看有没有，再没有就报错"，还是"直接去父里找该玩意"
                      // ，（当这种父类接收子类对象的情况时，java机器好像是后者思路。但是重写怎么想呢？又会再去子里找一些判断一次覆盖一次？那父类型句柄调父没有、子有的方法，又怎么想呢，为啥又是直接报调不了呢） ）

        System.out.println(b.name1);
        System.out.println(b.getName2());        //正确！getName2()可是方法哦~
       // System.out.println(b.onlySonName); ////找不到该属性。不能调！
        System.out.println(b.onlyFatherName);//废话！不论从哪个角度，父句柄，还是子对象，都可，前者是本身就有，后者是因为子继承了父嘛~ 但实际原因估计是前者吧(从第17行代码看出/猜测/倒推?)，那就又不是废话了

        /** 总结
         * 继承时方法是看右边吧？！！！属性看右边 (这其实是对于父类接收子对象，而父子间属性方法有重写性质的时候总结的一条结论/规律)
         *
         * 那么，Father里听讲师说的这句话"并且能够调用到目标类的业务方法（所以Object类型是不行的，Object里没啥（你要代理的）方法）"，我就有点不懂了呀。可能他讲错了吧（后续补：讲师没有说错！），
         * 他没讲错！这是类型，他们是用什么类型接收，而不是直接new子类强转Object，这可以调方法。但是，通过其他法子得到的是Object，本身就得到Object，那么，你是向下转，给它一个类型，可以调该类型的方法。而不是向上转型。
         *
         * 例子：
         *   Zhangsan obj=(Zhangsan)new CglibMeipo().getInstance(Zhangsan.class); //调方法得到的对象该方法本身就只以了Object返回（为了多态、适配），而不是本身是Zhangsan返回（因为若这样设计就写死了硬编码，只能代理张三了，换人得改源码，不利于维护扩展，不符合开闭原则，垃圾代码）
             obj.findLove();  // 找得到

              Object obj=new CglibMeipo().getInstance(Zhangsan.class);
              obj.findLove();  //  不存在该方法
         总结☆☆☆：
         子有，父没有，以父类型接收子对象之后，该父句柄不能调父没有的子有的方法！
         子有，父也有，【就是重写了】，以父类型接收子对象之后，该父句柄调方法看子类，调属性看父类。
         父有，子该类"没有"（是指没覆盖），以父类型接收子对象之后，能调父类属性方法吗？不测了，当然了，因为子〖继承〗了父的所有属性方法包括私有的了~。这不废话嘛。
         */
    }
}
