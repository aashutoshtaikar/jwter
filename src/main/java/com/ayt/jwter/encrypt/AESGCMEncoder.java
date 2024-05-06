package com.ayt.jwter.encrypt;

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
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESGCMEncoder implements Encrypter {
	
	private final String secretKey;
	
	public AESGCMEncoder(String secretKey) {
		this.secretKey = secretKey;
		
	}
	
	public String encrypt(String val)  {
		
		try {
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);
	        byte[] ivs = new SecureRandom().generateSeed(16); //new SecureRandom().generateSeed(16);
	        String iv = Base64.getEncoder().encodeToString(ivs);
	        
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	        GCMParameterSpec paramSpec = new GCMParameterSpec(128, ivs);
	        
	        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, paramSpec);
	        String ecrypted = Base64.getEncoder().encodeToString(cipher.doFinal(val.getBytes(StandardCharsets.UTF_8)));
	        String encrypted = iv+ecrypted;
	        return encrypted;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | 
				 InvalidKeyException | 
				InvalidAlgorithmParameterException | 
				IllegalBlockSizeException | BadPaddingException e ) {
			e.printStackTrace();
			return null;
		}

	}
	
	public String decrypt(String encrypted) {
		
		try {
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			byte[] key = secretKey.getBytes("UTF-8");
	        byte[] ivs = getIv(encrypted); 
	        byte[] enc = getEncrypted(encrypted);
	        
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	        GCMParameterSpec paramSpec = new GCMParameterSpec(128, ivs);
	        
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
	
	public static void main(String[] args) {
		AESGCMEncoder aesGCMEncoder = new AESGCMEncoder("aytasd3asdcsef45sda34fer336q34sd");
		String encrypted = aesGCMEncoder.encrypt("aytasd3asdcsef");
		System.out.println("encrypted: " +  encrypted);
		
		String decrypted = aesGCMEncoder.decrypt(encrypted);
		System.out.println("decrypted: " +  decrypted);
	}
}
