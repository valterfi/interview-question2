package com.backbase.numbers.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backbase.numbers.model.NumberContainer;
import com.backbase.numbers.repository.NumbersMemoryRepository;

@Service
public class NumbersService {
	
	@Autowired
	private NumbersMemoryRepository numbersRepository;
	
	public NumberContainer store(List<Integer> numbers) {
		NumberContainer numberContainer = NumberContainer.builder().numbers(numbers).build();
		return numbersRepository.save(numberContainer);
	}
	
	public List<Integer> permute(List<Integer> numbers) {
		Collections.shuffle(numbers);
		return numbers;
	}
	
	public NumberContainer findById(Long id) {
		return numbersRepository.findById(id).orElse(null);
	}

}
