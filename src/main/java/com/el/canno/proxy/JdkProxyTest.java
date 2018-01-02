package com.el.canno.proxy;

import com.el.canno.controller.HelloWorldController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * User Canno
 * Date 2017/11/30
 * Time 9:56
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.el.canno.controller")
public class JdkProxyTest {

    public static void main(String args[]){
        /*Cat cat = new CatImpl();
        MyInvocationHandler invocationHandler = new MyInvocationHandler(cat);
        Cat catProxy = invocationHandler.getProxy(Cat.class);
        catProxy.go();
        cat.run();*/
        SpringApplication.run(JdkProxyTest.class, args);
    }
}
