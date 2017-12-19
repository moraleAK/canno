package com.el.canno.common.encrypt.sm2;

import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

public class SM2Result 
{
	public SM2Result() {
	}

	// 签名/验签
	public BigInteger r;
	public BigInteger s;
	public BigInteger R;

	// 密钥交换
	public byte[] sa;
	public byte[] sb;
	public byte[] s1;
	public byte[] s2;

	public ECPoint keyra;
	public ECPoint keyrb;

	public BigInteger getR() {
		return r;
	}

	public void setR(BigInteger r) {
		this.r = r;
	}

	public byte[] getSa() {
		return sa;
	}

	public void setSa(byte[] sa) {
		this.sa = sa;
	}

	public byte[] getSb() {
		return sb;
	}

	public void setSb(byte[] sb) {
		this.sb = sb;
	}

	public byte[] getS1() {
		return s1;
	}

	public void setS1(byte[] s1) {
		this.s1 = s1;
	}

	public byte[] getS2() {
		return s2;
	}

	public void setS2(byte[] s2) {
		this.s2 = s2;
	}

	public ECPoint getKeyra() {
		return keyra;
	}

	public void setKeyra(ECPoint keyra) {
		this.keyra = keyra;
	}

	public ECPoint getKeyrb() {
		return keyrb;
	}

	public void setKeyrb(ECPoint keyrb) {
		this.keyrb = keyrb;
	}

	public BigInteger getS() {
		return s;
	}

	public void setS(BigInteger s) {
		this.s = s;
	}
}
