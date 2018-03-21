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
      //  SpringApplication.run(JdkProxyTest.class, args);
        System.out.println(getTotal(1, 1, 2, 9));
    }

    public static int getTotal(int startCount, int salt,int multiple,  int round){
        int total = 0;
        startCount = (startCount + salt) * multiple;
        if(round != 0) {
            round --;
            startCount = getTotal(startCount, salt, multiple, round);
        }
        total += startCount;
        return total;

    }
}
