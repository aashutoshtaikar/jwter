package com.ayt.jwter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ayt.jwter.encrypt.AESCBCEncoder;
import com.ayt.jwter.encrypt.Encrypter;
import com.ayt.jwter.hash.Hasher;
import com.ayt.jwter.hash.HmacSHAHasher;

@Configuration
public class BeanConfig {

	@Bean("hmacSHAHasher")
	Hasher hmacSHAHasher() {
		return new HmacSHAHasher("asdw34sdf43tas");
	}
	
	@Bean("aesCbcEncrypter")
	Encrypter AESCBCEncrypter() {
		return new AESCBCEncoder("asdw34sdf43taswdcasd3asdcsefqwed");
	}
	
	@Bean("aesGcmEncrypter")
	Encrypter AESGCMEncrypter() {
		return new AESCBCEncoder("asdw34sdf43taswdcasd3asdcsefqwed");
	}
}
