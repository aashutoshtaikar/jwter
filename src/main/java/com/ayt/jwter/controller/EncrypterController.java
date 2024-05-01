package com.ayt.jwter.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayt.jwter.encrypt.Encrypter;

@RestController
public class EncrypterController {

	private final Encrypter aesCbcEncrypter;

	private final Encrypter aesGcmEncrypter;
	
	public EncrypterController(
			@Qualifier("aesCbcEncrypter") Encrypter aesCbcEncrypter,
			@Qualifier("aesGcmEncrypter") Encrypter aesGcmEncrypter) {
		this.aesCbcEncrypter = aesCbcEncrypter;
		this.aesGcmEncrypter = aesGcmEncrypter;
	}

	@GetMapping("/encrypt")
	public String encrypt(@RequestParam String aesType, @RequestParam String val) {
		if (aesType.equals("CBC")) {
			return aesCbcEncrypter.encrypt(val);
		} else if (aesType.equals("GCM")) {
			return aesGcmEncrypter.encrypt(val);
		}
		return aesGcmEncrypter.encrypt(val);
	}
	
	@GetMapping("/createAESGCM")
	public String createAESGCM() {
		return aesGcmEncrypter.encrypt("aytasd3asdcsef");
	}
}
