package com.backbase.numbers.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class NumbersService {
	
	public List<Integer> permute(List<Integer> numbers) {
		Collections.shuffle(numbers);
		return numbers;
	}

}
