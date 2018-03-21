package com.el.canno.common.encrypt;

import java.security.MessageDigest;

/**
 * User: Rolandz
 * Date: 5/26/16
 * Time: 4:12 PM
 */
public class MD5Utils {
    private MD5Utils() {
        throw new Error("Do not instantiate me");
    }

    /**
     * 生成有效签名
     *
     * @param original
     * @return
     */
    public static String signature(String original) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(original.toString().getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }
        return result;
    }


    public static String md5(byte[] bytes) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(bytes));
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }
        return result;
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
}
