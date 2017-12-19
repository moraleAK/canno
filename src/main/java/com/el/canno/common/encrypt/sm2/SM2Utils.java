package com.el.canno.common.encrypt.sm2;

import org.bouncycastle.asn1.*;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;

public class SM2Utils 
{
	public static byte[] encrypt(byte[] publicKey, byte[] data)
	{
		if (publicKey == null || publicKey.length == 0)
		{
			return null;
		}
		
		if (data == null || data.length == 0)
		{
			return null;
		}
		
		byte[] source = new byte[data.length];
		System.arraycopy(data, 0, source, 0, data.length);

        byte[] formatPubKey;
        if (publicKey.length == 64){
            //添加一字节标识，用于ECPoint解析
            formatPubKey = new byte[65];
            formatPubKey[0] = 0x04;
            System.arraycopy(publicKey,0,formatPubKey,1,publicKey.length);
        }
        else {
			formatPubKey = publicKey;
		}
		
		SM2Cipher SM2Cipher = new SM2Cipher();
		SM2 sm2 = SM2.Instance();
		ECPoint userKey = sm2.ecc_curve.decodePoint(formatPubKey);
		
		ECPoint c1 = SM2Cipher.Init_enc(sm2, userKey);
		SM2Cipher.Encrypt(source);
		byte[] c3 = new byte[32];
		SM2Cipher.Dofinal(c3);

		ASN1Integer x = new ASN1Integer(c1.getXCoord().toBigInteger());
		ASN1Integer y = new ASN1Integer(c1.getYCoord().toBigInteger());
		DEROctetString derDig = new DEROctetString(c3);
		DEROctetString derEnc = new DEROctetString(source);
		ASN1EncodableVector v = new ASN1EncodableVector();
		v.add(x);
		v.add(y);
		v.add(derDig);
		v.add(derEnc);
		DERSequence seq = new DERSequence(v);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DEROutputStream dos = new DEROutputStream(bos);
        try {
            dos.writeObject(seq);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static byte[] decrypt(byte[] privateKey, byte[] encryptedData)
	{
		if (privateKey == null || privateKey.length == 0)
		{
			return null;
		}
		
		if (encryptedData == null || encryptedData.length == 0)
		{
			return null;
		}
		
		byte[] enc = new byte[encryptedData.length];
		System.arraycopy(encryptedData, 0, enc, 0, encryptedData.length);
		
		SM2 sm2 = SM2.Instance();
		BigInteger userD = new BigInteger(1, privateKey);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(enc);
		ASN1InputStream dis = new ASN1InputStream(bis);
        try {
			ASN1Object derObj = dis.readObject();
            ASN1Sequence asn1 = (ASN1Sequence) derObj;
            ASN1Integer x = (ASN1Integer) asn1.getObjectAt(0);
			ASN1Integer y = (ASN1Integer) asn1.getObjectAt(1);
            ECPoint c1 = sm2.ecc_curve.createPoint(x.getValue(), y.getValue(), true);

            SM2Cipher SM2Cipher = new SM2Cipher();
            SM2Cipher.Init_dec(userD, c1);
            DEROctetString data = (DEROctetString) asn1.getObjectAt(3);
            enc = data.getOctets();
            SM2Cipher.Decrypt(enc);
            byte[] c3 = new byte[32];
            SM2Cipher.Dofinal(c3);
            return enc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认ID计算
     * @param privateKey
     * @param sourceData
     * @return
     */
    public static byte[] sign(byte[] privateKey, byte[] sourceData) throws IOException {
        String userId = "1234567812345678";
        return sign(userId.getBytes(), privateKey, sourceData);
    }
	public static byte[] sign(byte[] userId, byte[] privateKey, byte[] sourceData) throws IOException {
		if (privateKey == null || privateKey.length == 0)
		{
			return null;
		}
		
		if (sourceData == null || sourceData.length == 0)
		{
			return null;
		}
		
		SM2 sm2 = SM2.Instance();
		BigInteger userD = new BigInteger(privateKey);
		System.out.println("userD: " + userD.toString(16));
		System.out.println("");
		
		ECPoint userKey = sm2.ecc_point_g.multiply(userD);
		System.out.println("椭圆曲线点X: " + userKey.getXCoord().toBigInteger().toString(16));
		System.out.println("椭圆曲线点Y: " + userKey.getYCoord().toBigInteger().toString(16));
		System.out.println("");
		
		SM3Digest sm3 = new SM3Digest();
		byte[] z = sm2.sm2GetZ(userId, userKey);
		System.out.println("SM3摘要Z: " + SMTypeCastUtil.getHexString(z));
	    System.out.println("");
	    
	    System.out.println("M: " + SMTypeCastUtil.getHexString(sourceData));
		System.out.println("");
		
		sm3.update(z, 0, z.length);
	    sm3.update(sourceData, 0, sourceData.length);
	    byte[] md = new byte[32];
	    sm3.doFinal(md, 0);
	    
	    System.out.println("SM3摘要值: " + SMTypeCastUtil.getHexString(md));
	    System.out.println("");
	    
	    SM2Result sm2Result = new SM2Result();
	    sm2.sm2Sign(md, userD, userKey, sm2Result);
	    System.out.println("r: " + sm2Result.r.toString(16));
	    System.out.println("s: " + sm2Result.s.toString(16));
	    System.out.println("");

		ASN1Integer d_r = new ASN1Integer(sm2Result.r);
		ASN1Integer d_s = new ASN1Integer(sm2Result.s);
	    ASN1EncodableVector v2 = new ASN1EncodableVector();
	    v2.add(d_r);
	    v2.add(d_s);
		ASN1Object sign = new DERSequence(v2);
        return sign.getEncoded();
	}

    /**
     * 使用默认id计算
     * @param publicKey
     * @param sourceData
     * @param signData
     * @return
     */
    public static boolean verifySign(byte[] publicKey, byte[] sourceData, byte[] signData){
        String userId = "1234567812345678";
        return verifySign(userId.getBytes(),publicKey,sourceData,signData);
    }
	@SuppressWarnings("unchecked")
	public static boolean verifySign(byte[] userId, byte[] publicKey, byte[] sourceData, byte[] signData)
	{
		if (publicKey == null || publicKey.length == 0)
		{
			return false;
		}
		
		if (sourceData == null || sourceData.length == 0)
		{
			return false;
		}

        byte[] formatPubKey;
        if (publicKey.length == 64){
            //添加一字节标识，用于ECPoint解析
            formatPubKey = new byte[65];
            formatPubKey[0] = 0x04;
            System.arraycopy(publicKey,0,formatPubKey,1,publicKey.length);
        }
        else
            formatPubKey = publicKey;
		
		SM2 sm2 = SM2.Instance();
		ECPoint userKey = sm2.ecc_curve.decodePoint(formatPubKey);

		SM3Digest sm3 = new SM3Digest();
		byte[] z = sm2.sm2GetZ(userId, userKey);
		sm3.update(z, 0, z.length);
		sm3.update(sourceData, 0, sourceData.length);
	    byte[] md = new byte[32];
	    sm3.doFinal(md, 0);
	    System.out.println("SM3摘要值: " + SMTypeCastUtil.getHexString(md));
	    System.out.println("");
		
	    ByteArrayInputStream bis = new ByteArrayInputStream(signData);
	    ASN1InputStream dis = new ASN1InputStream(bis);
		SM2Result sm2Result = null;
		try {
			ASN1Object derObj = dis.readObject();
			Enumeration<ASN1Integer> e = ((ASN1Sequence) derObj).getObjects();
			BigInteger r = e.nextElement().getValue();
			BigInteger s = e.nextElement().getValue();
			sm2Result = new SM2Result();
			sm2Result.r = r;
			sm2Result.s = s;
			System.out.println("r: " + sm2Result.r.toString(16));
			System.out.println("s: " + sm2Result.s.toString(16));
			System.out.println("");
			sm2.sm2Verify(md, userKey, sm2Result.r, sm2Result.s, sm2Result);
			return sm2Result.r.equals(sm2Result.R);
		} catch (IOException e1) {
			e1.printStackTrace();
            return false;
        }
	}

	public static Sm2KeyPair generateKeyPair(){
		SM2 sm2 = SM2.Instance();
		AsymmetricCipherKeyPair keypair = sm2.ecc_key_pair_generator.generateKeyPair();
		ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) keypair.getPrivate();
		ECPublicKeyParameters ecpub = (ECPublicKeyParameters) keypair.getPublic();

//        System.out.println("私钥: " + ecpriv.getD().toString(16).toUpperCase());
//        System.out.println("公钥: " + ecpub.getQ().getX().toBigInteger().toString(16).toUpperCase() +
//                ecpub.getQ().getY().toBigInteger().toString(16).toUpperCase());

		byte[] priKey = new byte[32];
		byte[] pubKey = new byte[64];

		byte[] bigNumArray = ecpriv.getD().toByteArray();
		System.arraycopy(bigNumArray, bigNumArray[0]==0?1:0, priKey, 0, 32);
		System.arraycopy(ecpub.getQ().getEncoded(), 1, pubKey, 0, 64);

//        System.out.println("私钥bigNumArray: " + SMTypeCastUtil.getHexString(bigNumArray));
//        System.out.println("私钥: " + SMTypeCastUtil.getHexString(priKey));
//        System.out.println("公钥: " + SMTypeCastUtil.getHexString(pubKey));

		return new Sm2KeyPair(priKey, pubKey);
	}

}
