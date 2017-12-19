package com.el.canno.common.encrypt;

import java.util.Random;

/**
 * Created by Ak_Guili on 2017/9/17.
 */
public class GenerateStringUtils {
    private static String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static String flowNm = "0123456789";

    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getFlowNm(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);
            sb.append(flowNm.charAt(number));
        }
        return sb.toString();
    }
}
