package com.el.canno.proxy;

/**
 * User Canno
 * Date 2017/11/30
 * Time 9:47
 */
public class CatImpl implements Cat {
    @Override
    public void run() {
        System.out.println("cat running");
    }

    @Override
    public void go() {
        System.out.println("cat going");
    }
}
