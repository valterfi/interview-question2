package com.backbase.numbers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.backbase.numbers.model.NumberContainer;
import com.backbase.numbers.service.NumbersService;

@RestController
public class NumbersController {
	
	@Autowired
	private NumbersService numbersService;
	
	@PostMapping("/store")
	public ResponseEntity<Long> store(@RequestParam List<Integer> numbers) {
		NumberContainer numberContainer = numbersService.store(numbers);
		return new ResponseEntity<Long>(numberContainer.getId(), HttpStatus.OK);
	}
	
	@GetMapping("/permutation")
	public ResponseEntity<List<Integer>> permute(@RequestParam Long id) {
		NumberContainer numberContainer = numbersService.findById(id);
		if (numberContainer != null) {
			List<Integer> permutation = numbersService.permute(numberContainer.getNumbers());
			return new ResponseEntity<List<Integer>>(permutation, HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found");
		}
	}

}
