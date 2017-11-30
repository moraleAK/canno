package com.el.canno.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * User Canno
 * Date 2017/11/30
 * Time 9:48
 */
public class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 在目标对象的方法执行之前简单的打印一下
        System.out.println("------------------before------------------");

        // 执行目标对象的方法
        Object result = method.invoke(target, args);

        // 在目标对象的方法执行之后简单的打印一下
        System.out.println("-------------------after------------------");

        return result;
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) (Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this));
    }
}
