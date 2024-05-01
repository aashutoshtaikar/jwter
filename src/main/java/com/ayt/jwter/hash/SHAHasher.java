package com.ayt.jwter.hash;

import java.security.MessageDigest;
import java.util.Base64;

public class SHAHasher {
	
	public static String createHash(String val)  {
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(val.getBytes());
			byte[] digest = messageDigest.digest();
			
			return Base64.getEncoder().encodeToString(digest);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	public static void main(String[] args)  {
		String s = SHAHasher.createHash("aytasd3asdcsef");
		System.out.println("encrypted: " +  s);

	}

}
