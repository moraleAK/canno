package com.el.canno.quote;

import java.util.ArrayList;
import java.util.List;

/**
 * User Canno
 * Date 2017/12/1
 * Time 11:26
 */
public class QuoteTest {
    /**
     * quote transfer, the value is save in the memory,
     * and the var is save the address of the value.
     * in fact, the two arrays all point the same memory
     *
     * @param str
     */
    public static void main(String str[]) {
        List originList = new ArrayList();
        List copyList = originList;
        originList.add(100);
        originList.add(200);

        copyList.add(300);
        originList.forEach(System.out::println);
        copyList.forEach(System.out::println);
    }
}
