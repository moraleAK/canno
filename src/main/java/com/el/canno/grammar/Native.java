package com.el.canno.grammar;

/**
 * User Canno
 * Date 2018/3/6
 * Time 21:50
 */
public class Native {
    public static void assertVar() {
        int a = 1;
        assert a != 1;
        System.out.println(a);
    }

    public static void main(String[] args) {
        assertVar();
        String s = null;
        assert s != null ? true : false;
        assert false;
        System.out.println("end");
    }
}
