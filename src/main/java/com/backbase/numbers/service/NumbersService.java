package com.backbase.numbers.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backbase.numbers.model.NumberContainer;
import com.backbase.numbers.repository.NumbersJpaRepository;

/**
 * @author valterfi
 *
 */
@Service
public class NumbersService {

	@Autowired
	private NumbersJpaRepository numbersRepository;

	/**
	 * Create a new instance of NumberContainer with the list passed by parameter and saved in the database
	 *
	 * @param numbers
	 * @return new NumberContainer created in the database
	 * @throws Exception
	 */
	public NumberContainer store(List<Integer> numbers) throws Exception {
		NumberContainer numberContainer = NumberContainer.builder().numbers(numbers).build();
		return numbersRepository.save(numberContainer);
	}

	/**
	 * Change the position of elements in a random way
	 *
	 * @param numbers
	 * @return list with elements in new positions
	 * @throws Exception
	 */
	public List<Integer> permute(List<Integer> numbers) throws Exception {
		Collections.shuffle(numbers);
		return numbers;
	}

	/**
	 * Retrieves a NumberContainer by the id called by the implemented repository
	 *
	 * @param id
	 * @return NumberContainer
	 */
	public NumberContainer findById(Long id) {
		NumberContainer numberContainer = null;
		if (id != null) {
			numberContainer = numbersRepository.findById(id).orElse(null);
		}
		return numberContainer;
	}

}
