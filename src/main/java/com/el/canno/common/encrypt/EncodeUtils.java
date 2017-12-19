package com.el.canno.common.encrypt;


import com.el.canno.common.encrypt.sm2.SM4Utils;

/**
 * Created by Administrator on 2017/10/9.
 */
public class EncodeUtils {
    public static String encode(String content, String key) {
        //return new String(Base64.encode(AESUtils.Aes256Encode(content, key.getBytes()))).toString();
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = key;
        return sm4.encryptDataByECB(content);
    }
}
