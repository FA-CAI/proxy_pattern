package com.shangxue.proxy_pattern.staticProxy;

import com.shangxue.proxy_pattern.Person;

/**
 * 代理：不管是什么代理，目的就是要
 * 拿到目标类的引用（比如代理完了可以通知被代理类[目标类]），
 * 并且能够调用到目标类的业务方法（所以Object类型是不行的，Object里没啥（你要代理的）方法）
 **/

//缺点：对于要代理的对象，若对象增加/更新的方法，其代理的对象也需要对应的更新，违背了开闭原则
public class Father {

    //  private Son son;   //发现静态代理没法扩展（写死/硬编码），如果要给你表弟堂妹做代理，得改源代码内部代码

    private Person person;

    //这种编码方式，易知只能帮儿子找对象。无法扩展。
    /* Father(Son son){
        this.son=son;
    }*/

    // 把儿子变成接口下的类，就提高了点扩展性
    Father(Person p) {
        this.person = p;
    }

    public void findLove() {
        System.out.println("根据你的要求出门物色");
        this.person.findLove();
        System.out.println("双方父母是不是同意");
    }

}
