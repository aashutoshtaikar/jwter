package com.ayt.jwter.hash;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHAHasher implements Hasher {
	
	private final String secret; 
	
	public HmacSHAHasher(String secret) {
		this.secret = secret;
	}
	
	public String createHash(String val)  {
		
		try {
			Mac mac = Mac.getInstance("hmacSHA256");
			Key key = new SecretKeySpec(secret.getBytes(), "hmacSHA256");
			mac.init(key);
			return Base64.getEncoder().encodeToString(mac.doFinal());
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
