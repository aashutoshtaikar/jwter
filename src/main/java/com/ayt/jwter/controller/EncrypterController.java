package com.ayt.jwter.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayt.jwter.encrypt.AESCBCEncoder;
import com.ayt.jwter.encrypt.AESGCMEncoder;
import com.ayt.jwter.encrypt.Encrypter;

@RestController
public class EncrypterController {

	private Encrypter aesCbcEncrypter;

	private Encrypter aesGcmEncrypter;
	
	public EncrypterController(
			@Qualifier("aesCbcEncrypter") Encrypter aesCbcEncrypter,
			@Qualifier("aesGcmEncrypter") Encrypter aesGcmEncrypter) {
		this.aesCbcEncrypter = aesCbcEncrypter;
		this.aesGcmEncrypter = aesGcmEncrypter;
	}

	@GetMapping("/encrypt")
	public String encrypt(@RequestParam String aesType, @RequestParam String val, 
			@RequestParam(required = false) String secretKey) {
		
		if (aesType.equals("CBC")) {
			if (secretKey != null) aesCbcEncrypter = new AESCBCEncoder(secretKey);
			return aesCbcEncrypter.encrypt(val);
		} else if (aesType.equals("GCM")) {
			if (secretKey != null) aesGcmEncrypter = new AESGCMEncoder(secretKey);
			return aesGcmEncrypter.encrypt(val);
		}
		return aesGcmEncrypter.encrypt(val);
	}
	
	@GetMapping("/decrypt")
	public String decrypt(@RequestParam String aesType, @RequestParam String val, 
			@RequestParam(required = false) String secretKey) {
		
		if (aesType.equals("CBC")) {
			if (secretKey != null) aesCbcEncrypter = new AESCBCEncoder(secretKey);
			return aesCbcEncrypter.decrypt(val);
		} else if (aesType.equals("GCM")) {
			if (secretKey != null) aesGcmEncrypter = new AESGCMEncoder(secretKey);
			return aesGcmEncrypter.decrypt(val);
		}
		return aesGcmEncrypter.decrypt(val);
	}
}
