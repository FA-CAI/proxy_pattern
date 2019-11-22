package com.shangxue.proxy_pattern.dynamicProxy.handwritingjdkproxy;

import com.shangxue.proxy_pattern.Person;
import com.shangxue.proxy_pattern.dynamicProxy.jdkproxy.Girl;

public class Test {

    public static void main(String[] args) {

        try {
            Person obj=(Person)new ZKMeipo().getInstance(new Girl());
			obj.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
