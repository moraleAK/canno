package com.el.canno.common.encrypt;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;

public class RSAUtils {
    private static Logger LOG = LoggerFactory.getLogger(RSAUtils.class);
    private static String SIGN_ALGORITHM = "SHA256withRSA";
    //private static String SIGN_ALGORITHM = "RSA2048withSHA256";
    private String privateKeyFile = "G:/wc_payment/doc/epcc/keys/signature/server_pri.pem";
    private String publicKeyFile = "G:/wc_payment/doc/epcc/keys/signature/Z2020361000013_4002485098_RSA_SignCert.cer";

    // EPCC 加密（sm2）证书路径
    //private String certPath = "/Users/Roland/Work/elink/WP 2C/网联平台证书-提供给机构/wanglian-sm2（双证）加密.cer";
    private String certPath = "D:/westCoal/wc_payment/doc/epcc/keys/digital_envelop_sm2/wanglian-sm2（双证）加密.cer";


    /**
     * 获取 EPCC 证书公钥
     * @return
     * @throws Exception
     */
    public byte[] getEPCCPubKey() throws Exception {
        File certFile = new File(certPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(certFile)));
        String base64Str = br.readLine();

        InputStream inStream = new ByteArrayInputStream(Base64Utils.decodeFromString(base64Str));
        ASN1Sequence seq = null;
        ASN1InputStream aIn;
        try {
            aIn = new ASN1InputStream(inStream);
            seq = (ASN1Sequence) aIn.readObject();
            org.bouncycastle.asn1.x509.Certificate cert = org.bouncycastle.asn1.x509.Certificate.getInstance(seq);
            SubjectPublicKeyInfo subjectPublicKeyInfo = cert.getSubjectPublicKeyInfo();
            DERBitString publicKeyData = subjectPublicKeyInfo.getPublicKeyData();
            byte[] eP = new byte[64];
            System.arraycopy(publicKeyData.getEncoded(), 4, eP, 0, eP.length);
            return eP;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取SM2证书私钥
     * @return
     * @throws Exception
     */
    public byte[] getEPCCPrivateKey() throws Exception {
        File certFile = new File(certPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(certFile)));
        String base64Str = br.readLine();

        InputStream inStream = new ByteArrayInputStream(Base64Utils.decodeFromString(base64Str));
        ASN1Sequence seq = null;
        ASN1InputStream aIn;
        try {
            aIn = new ASN1InputStream(inStream);
            seq = (ASN1Sequence) aIn.readObject();
            org.bouncycastle.asn1.x509.Certificate cert = org.bouncycastle.asn1.x509.Certificate.getInstance(seq);
            SubjectPublicKeyInfo subjectPublicKeyInfo = cert.getSubjectPublicKeyInfo();
            DERBitString publicKeyData = subjectPublicKeyInfo.getPublicKeyData();
            byte[] eP = new byte[64];
            System.arraycopy(publicKeyData.getEncoded(), 4, eP, 0, eP.length);
            return eP;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 签名
     * @param content
     * @return
     * @throws Exception
     */
    public  byte[] sign(byte[] content) throws Exception {
        PrivateKey privateKey = createPrivateKey(new FileInputStream(privateKeyFile));
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(content);
        return signature.sign();
    }

    public  String getSign(byte[] content) throws Exception {
        return new String(Base64.encode(sign(content)).toString());
    }

    /**
     * 验签
     * @param content
     * @param signValue
     * @return
     * @throws Exception
     */
    public boolean verify(byte[] content, byte[] signValue) throws Exception {
        InputStream inStream = null;
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);

            inStream = new FileInputStream(publicKeyFile);
            Certificate cert = CertificateFactory.getInstance("X.509").generateCertificate(inStream);

            signature.initVerify(cert.getPublicKey());
            signature.update(content);

            return signature.verify(signValue);
        } finally {
            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public PrivateKey createPrivateKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }

        return createPrivateKey(sb.toString());
    }

    public PrivateKey createPrivateKey(String privateKeyStr) throws Exception {
        byte[] buffer = Base64.decode(privateKeyStr.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public String RSADecrypt(byte[] bytes) throws Exception {
        PrivateKey privateKey = createPrivateKey(new FileInputStream(privateKeyFile));
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(bytes);
            return new String(result,"utf-8");
        } catch (Exception ex) {
            LOG.info("解密失败:{}", ex.toString());
        }
        return null;
    }


    public void setPrivateKeyFile(String privateKeyFile) {
        this.privateKeyFile = privateKeyFile;
    }

    public void setPublicKeyFile(String publicKeyFile) {
        this.publicKeyFile = publicKeyFile;
    }
}
