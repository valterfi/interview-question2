package com.backbase.numbers.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumbersController {
	
	@PostMapping("/store")
	public ResponseEntity<Long> store(@RequestParam List<Integer> numbers) {
		return null;
	}

}
