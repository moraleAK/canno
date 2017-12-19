package com.el.canno.common.encrypt.sm2;

import org.springframework.util.Base64Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SM4Utils
{
	public String secretKey = "";
	
	public String iv = "";
	
	public boolean hexString = false;
	
	public SM4Utils()
	{
	}
	
	public String encryptDataByECB(String plainText)
	{
		try 
		{
			SM4Context ctx = new SM4Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_ENCRYPT;
			
			byte[] keyBytes;
			if (hexString)
			{
				keyBytes = SMTypeCastUtil.hexStringToBytes(secretKey);
			}
			else
			{
				keyBytes = secretKey.getBytes();
			}
			
			SM4 sm4 = new SM4();
			sm4.sm4_setkey_enc(ctx, keyBytes);
			byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("UTF-8"));
			String cipherText = Base64Utils.encodeToString(encrypted);
			if (cipherText != null && cipherText.trim().length() > 0)
			{
				Pattern p = Pattern.compile("\\s*|\t|\r|\n");
				Matcher m = p.matcher(cipherText);
				cipherText = m.replaceAll("");
			}
			return cipherText;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String decryptDataByECB(String cipherText)
	{
		try 
		{
			SM4Context ctx = new SM4Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_DECRYPT;
			
			byte[] keyBytes;
			if (hexString)
			{
				keyBytes = SMTypeCastUtil.hexStringToBytes(secretKey);
			}
			else
			{
				keyBytes = secretKey.getBytes();
			}
			
			SM4 sm4 = new SM4();
			sm4.sm4_setkey_dec(ctx, keyBytes);
			byte[] decrypted = sm4.sm4_crypt_ecb(ctx, Base64Utils.decodeFromString(cipherText));
			return new String(decrypted, "UTF-8");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String encryptDataByCBC(String plainText)
	{
		try 
		{
			SM4Context ctx = new SM4Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_ENCRYPT;
			
			byte[] keyBytes;
			byte[] ivBytes;
			if (hexString)
			{
				keyBytes = SMTypeCastUtil.hexStringToBytes(secretKey);
				ivBytes = SMTypeCastUtil.hexStringToBytes(iv);
			}
			else
			{
				keyBytes = secretKey.getBytes();
				ivBytes = iv.getBytes();
			}
			
			SM4 sm4 = new SM4();
			sm4.sm4_setkey_enc(ctx, keyBytes);
			byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("GBK"));
			String cipherText = Base64Utils.encodeToString(encrypted);
			if (cipherText != null && cipherText.trim().length() > 0)
			{
				Pattern p = Pattern.compile("\\s*|\t|\r|\n");
				Matcher m = p.matcher(cipherText);
				cipherText = m.replaceAll("");
			}
			return cipherText;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String decryptDataByCBC(String cipherText)
	{
		try 
		{
			SM4Context ctx = new SM4Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_DECRYPT;
			
			byte[] keyBytes;
			byte[] ivBytes;
			if (hexString)
			{
				keyBytes = SMTypeCastUtil.hexStringToBytes(secretKey);
				ivBytes = SMTypeCastUtil.hexStringToBytes(iv);
			}
			else
			{
				keyBytes = secretKey.getBytes();
				ivBytes = iv.getBytes();
			}
			
			SM4 sm4 = new SM4();
			sm4.sm4_setkey_dec(ctx, keyBytes);
			byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, Base64Utils.decodeFromString(cipherText));
			return new String(decrypted, "UTF-8");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
