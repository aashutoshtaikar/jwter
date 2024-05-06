package com.ayt.jwter.encrypt;

import java.io.UnsupportedEncodingException;
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
        String encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(val.getBytes("UTF-8")));
        encrypted = iv+encrypted;
        
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

	public String decrypt(String encrypted) {
		
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] key = secretKey.getBytes("UTF-8");
	        byte[] ivs = getIv(encrypted);
	        byte[] enc = getEncrypted(encrypted);
	        
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	        IvParameterSpec paramSpec = new IvParameterSpec(ivs);
	        
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, paramSpec);
			String decrypted =  new String(cipher.doFinal(enc), "UTF-8");
			return decrypted;			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private byte[] getIv(String encrypted) {
		String iv = encrypted.substring(0, 24);
		return Base64.getDecoder().decode(iv);
	}
	
	private byte[] getEncrypted(String encrypted) {
		String encryptedValue = encrypted.substring(24, encrypted.length());
		return Base64.getDecoder().decode(encryptedValue);
	}

}
