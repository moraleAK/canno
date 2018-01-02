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


    /**
     * 2^(x -1) < n  < 2^x
     * print 2^x - 1
     * @param n
     * @return
     */
    public int get2Int(int n) {
        System.out.println(n);
        n |= n >>> 1;
        System.out.println(n);
        n |= n >>> 2;
        System.out.println(n);
        n |= n >>> 4;
        System.out.println(n);
        n |= n >>> 8;
        System.out.println(n);
        n |= n >>> 16;
        System.out.println(n);
        return n + 1;
    }
}
