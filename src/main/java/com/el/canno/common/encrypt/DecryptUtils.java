package com.el.canno.common.encrypt;


import com.el.canno.common.encrypt.sm2.SM4Utils;
import org.springframework.util.Base64Utils;
//import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * Created by Administrator on 2017/10/9.
 */
public class DecryptUtils {

    public static String decrypt(byte[] bytes, String str) throws Exception {
        RSAUtils utils = new RSAUtils();

        String key1 = utils.RSADecrypt(Base64Utils.decode(bytes));
        String a[] = key1.split("\\|");
        String secretKey = a[1];

        SM4Utils sm4Utils = new SM4Utils();
        sm4Utils.secretKey = new String(HexUtils.hexStr2Bytes(secretKey));
        return sm4Utils.decryptDataByECB(str);
    }
}
