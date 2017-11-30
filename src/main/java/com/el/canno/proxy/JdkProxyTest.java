package com.el.canno.proxy;

/**
 * User Canno
 * Date 2017/11/30
 * Time 9:56
 */
public class JdkProxyTest {

    public static void main(String args[]){
        Cat cat = new CatImpl();
        MyInvocationHandler invocationHandler = new MyInvocationHandler(cat);
        Cat catProxy = invocationHandler.getProxy(Cat.class);
        catProxy.go();
        cat.run();
    }
}
