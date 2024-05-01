package com.ayt.jwter.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayt.jwter.hash.Hasher;

@RestController
public class HasherController {

	private final Hasher hmacSHAHasher;
	
	public HasherController(@Qualifier("hmacSHAHasher") Hasher hmacSHAHasher) {
		this.hmacSHAHasher = hmacSHAHasher;
	}

	@GetMapping("/createHmacHash")
	public String hash() {
		return hmacSHAHasher.createHash("aytasd3asdcsef");
	}
}
