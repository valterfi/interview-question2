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

import io.swagger.annotations.ApiOperation;

/**
 * @author valterfi
 *
 */
@RestController
public class NumbersController {

	@Autowired
	private NumbersService numbersService;

	/**
	 * Implements the request to store an integer list
	 * @param numbers
	 * @return Response with the new record id
	 */
	@PostMapping("/store")
	@ApiOperation(value = "Send an array of numbers to be stored and return an ID", response = Long.class)
	public ResponseEntity<Long> store(@RequestParam List<Integer> numbers) {
		try {
			NumberContainer numberContainer = numbersService.store(numbers);
			return new ResponseEntity<Long>(numberContainer.getId(), HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	/**
	 * Implements the exchange of the list with such an id.
	 * For each request a random list is returned
	 * @param id
	 * @return Response with the list using exchanged positions
	 */
	@GetMapping("/permutation")
	@ApiOperation(value = "Send an ID to return a random permutation of the array", response = Long.class)
	public ResponseEntity<List<Integer>> permute(@RequestParam Long id) {
		NumberContainer numberContainer = numbersService.findById(id);
		if (numberContainer != null) {
			try {
				List<Integer> permutation = numbersService.permute(numberContainer.getNumbers());
				return new ResponseEntity<List<Integer>>(permutation, HttpStatus.OK);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found");
		}
	}

}
