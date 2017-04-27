package com.hh.system.action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.hh.system.util.LogUtil;
import com.hh.system.util.base.BaseServletAction;

public class ActionWx extends BaseServletAction {
	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;

	public Object findEchostr() throws NoSuchAlgorithmException {
		String token = "huanghua";
		String tmpStr = findSHA1(token, timestamp, nonce);
		
		LogUtil.info(signature);

		if (tmpStr.equals(signature)) {
			LogUtil.info("signature===success");
			return new StringBuffer(echostr);
		} else {
			LogUtil.info("signature===unsuccess");
			return null;
		}
	}

	public String findSHA1(String token, String timestamp, String nonce) throws NoSuchAlgorithmException {
		String[] array = new String[] { token, timestamp, nonce };
		StringBuffer sb = new StringBuffer();
		// 字符串排序
		Arrays.sort(array);
		for (int i = 0; i < 3; i++) {
			sb.append(array[i]);
		}
		String str = sb.toString();
		// SHA1签名生成
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(str.getBytes());
		byte[] digest = md.digest();

		StringBuffer hexstr = new StringBuffer();
		String shaHex = "";
		for (int i = 0; i < digest.length; i++) {
			shaHex = Integer.toHexString(digest[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexstr.append(0);
			}
			hexstr.append(shaHex);
		}
		return hexstr.toString();
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}

	public String getEchostr() {
		return echostr;
	}

}
