package com.el.canno.common.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Enumeration;

/**
 * Created by Ak_Guili on 2017/9/11.
 */
public class PrivateKeyUtil {
    private static Logger LOG = LoggerFactory.getLogger(PrivateKeyUtil.class);
    @SuppressWarnings("rawtypes")
    public static PrivateKey GetPvkformPfx(InputStream in, String strPassword) {
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            InputStream fis = in;
            char[] nPassword = null;
            if ((strPassword == null) || strPassword.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = strPassword.toCharArray();
            }
            ks.load(fis, nPassword);
            fis.close();
            System.out.println("keystore type=" + ks.getType());
            Enumeration enumas = ks.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements()) {
                keyAlias = (String)enumas.nextElement();
            }
            PrivateKey prikey = (PrivateKey)ks.getKey(keyAlias, nPassword);
            Certificate cert = ks.getCertificate(keyAlias);
            PublicKey pubkey = cert.getPublicKey();
            System.out.println("cert class = " + cert.getClass().getName());
            System.out.println("cert = " + cert);
            System.out.println("public key = " + pubkey);
            System.out.println("private key = " + prikey);
            return prikey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    public static PublicKey GetPbkformPfx(InputStream in, String strPassword) {
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            InputStream fis = in;
            char[] nPassword = null;
            if ((strPassword == null) || strPassword.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = strPassword.toCharArray();
            }
            ks.load(fis, nPassword);
            fis.close();
            System.out.println("keystore type=" + ks.getType());
            Enumeration enumas = ks.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements()) {
                keyAlias = (String)enumas.nextElement();
                System.out.println("alias=");
            }
            System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
            Certificate cert = ks.getCertificate(keyAlias);
            PublicKey pubkey = cert.getPublicKey();
            System.out.println("cert class = " + cert.getClass().getName());
            System.out.println("cert = " + cert);
            System.out.println("public key = " + pubkey);
            return pubkey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] signature(PrivateKey privateKey, byte[] data) {
        try {
            Signature signet = Signature.getInstance("MD5withRSA");
            signet.initSign(privateKey);
            signet.update(data);
            byte[] signed = signet.sign();
            return signed;
        } catch (Exception ex) {
            LOG.info("签名失败：{}", ex.toString());
        }
        return null;
    }

    public static boolean verifySignature(PublicKey key, byte[] data, byte[] signatureData) {
        try {
            Signature signet = Signature.getInstance("MD5withRSA");
            signet.initVerify(key);
            signet.update(data);
            boolean result = signet.verify(signatureData);
            return result;
        } catch (Exception ex) {
            LOG.info("验证签名失败:{}", ex.toString());
        }
        return false;
    }

    public static byte[] encrypt(Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte encryptedData[] = cipher.doFinal(data);
            return encryptedData;
        } catch (Exception ex) {
            LOG.info("加密失败{}", ex.toString());
        }
        return null;
    }

    public static byte[] encryptWithPublicKey(PublicKey publicKey, byte[] data) {
        try {
            byte encryptedData[] = encrypt(publicKey, data);
            return encryptedData;
        } catch (Exception ex) {
            LOG.info("公钥加密失败:{}", ex.toString());
        }
        return null;
    }

    public static byte[] encryptWithPrivateKey(PrivateKey privateKey, byte[] data) {
        try {
            byte encryptedData[] = encrypt(privateKey, data);
            return encryptedData;
        } catch (Exception ex) {
            LOG.info("私钥加密失败:{}", ex.toString());
        }
        return null;
    }

    public static byte[] decrypt(Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (Exception ex) {
           LOG.info("解密失败:{}", ex.toString());
        }
        return null;
    }

    public static byte[] decryptWithPrivateKey(PrivateKey privateKey, byte[] data) {
        try {
            byte[] result = decrypt(privateKey, data);
            return result;
        } catch (Exception ex) {
            LOG.info("私钥解密失败:{}", ex.toString());
        }
        return null;
    }

    public static byte[] decryptWithPublicKey(PublicKey publicKey, byte[] data) {
        try {
            byte[] result = decrypt(publicKey, data);
            return result;
        } catch (Exception ex) {
            LOG.info("公钥解密失败:{}", ex.toString());
        }
        return null;
    }

}
