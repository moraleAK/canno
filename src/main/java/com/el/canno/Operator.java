package com.el.canno;

/**
 * User Canno
 * Date 2017/12/1
 * Time 15:52
 */
public class Operator {
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(0 | 1);
        System.out.println(0 | 0);
        System.out.println(0 & 1);
        System.out.println(0 & 0);
        System.out.println(0 ^ 1);
        System.out.println(0 ^ 0);
        System.out.println(1 >> 100);
        System.out.println((1 << 30) >>> 60);
        System.out.println(1 << 30);
        // ~x equals -(x+1)
        System.out.println(~0);
        System.out.println(~(-1));
        System.out.println(~(-2));
    }
}
