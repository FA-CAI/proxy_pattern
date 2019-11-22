package com.shangxue.proxy_pattern.dynamicProxy.cglibproxy;

public class Test {


    public static void main(String[] args) {

        try {
            Zhangsan obj=(Zhangsan)new CglibMeipo().getInstance(Zhangsan.class);
            obj.findLove();
          //  System.out.println(obj);  //这行虽然的确是废话，但是这行写了会报不能转换为Number，好奇怪   //sout默认调用了方法，会走代理类




        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
