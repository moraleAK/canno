package com.el.canno.test.charset;

import com.el.canno.charset.CharsetName;
import com.el.canno.common.encrypt.MD5Utils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharsetEncoder;

/**
 * User Canno
 * Date 2018/4/2
 * Time 10:29
 */
public class MD5test {

    @Test
    public void charsetMd5Test() throws UnsupportedEncodingException {
        String utf8 = new String("打卡".getBytes(), CharsetName.UTF_8);
        String gbk = new String("打卡".getBytes("GBK"), CharsetName.GBK);
        System.out.println("utf-8 :" + utf8);
        System.out.println("gbk：" + gbk);
        System.out.println(MD5Utils.signature(utf8));
        System.out.println(MD5Utils.signature(gbk));
        System.out.println(utf8.getBytes(CharsetName.ASCII));
        System.out.println(System.currentTimeMillis());
    }
}
