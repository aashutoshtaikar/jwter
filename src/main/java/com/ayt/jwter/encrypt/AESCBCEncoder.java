package com.ayt.jwter.encrypt;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCBCEncoder implements Encrypter {
	
	private final String secretKey;
	
	public AESCBCEncoder(String secretKey) {
		this.secretKey = secretKey;
	}
	
	private String encyrpt(String val) throws UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		byte[] key = secretKey.getBytes("UTF-8");
        byte[] ivs = new SecureRandom().generateSeed(16);
        String iv = Base64.getEncoder().encodeToString(ivs);
        
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec paramSpec = new IvParameterSpec(ivs);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, paramSpec);
        String encrypted = iv+Base64.getEncoder().encodeToString(cipher.doFinal(val.getBytes("UTF-8")));
        
        return encrypted;
	}
	
	public String encrypt(String val) {
		try {
			return encyrpt(val);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
