package com.el.canno;

import com.el.canno.common.encrypt.GenerateStringUtils;
import com.el.canno.common.encrypt.MD5Utils;

/**
 * User Canno
 * Date 2017/11/30
 * Time 10:18
 */
public class Test {
    public static void main(String[] args) {
        String str = "";
        long count = 0;
        long time = System.currentTimeMillis();
        while (!(str = GenerateStringUtils.getHEX32()).equals(MD5Utils.signature(str))){
            count ++ ;
            if(count%100000000L == 0){
                System.out.println(count);
                long t2 = System.currentTimeMillis();
                System.out.println(t2 - time);
                time = t2;
            }
        }
        System.out.println(str);
    }
}
