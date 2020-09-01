package com.backbase.numbers.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.backbase.numbers.data.NumbersDatabase;
import com.backbase.numbers.model.NumberContainer;

/**
 * Class that implements the repository that saves and retrieves NumberContainer records in memory
 *
 * @author valterfi
 *
 */
@Repository
public class NumbersMemoryRepository {

	@Autowired
	private NumbersDatabase numbersDatabase;

	/**
	 * Creates a new NumberContainer if it has no id, otherwise it updates the record
	 *
	 * @param numberContainer
	 * @return a new or updated NumberContainer
	 */
	public NumberContainer save(NumberContainer numberContainer) {
    	Long id = numberContainer.getId();
    	if (id != null) {
    		numbersDatabase.getNumbersData().put(id, numberContainer);
    		return numberContainer;
    	} else {
    		id = numbersDatabase.getIdentity() + 1;
    		numberContainer.setId(id);
    		numbersDatabase.setIdentity(id);
    		numbersDatabase.getNumbersData().put(id, numberContainer);
    		return numberContainer;
    	}
    }

	/**
	 * Retrieves a NumberContainer by id
	 *
	 * @param id
	 * @return Optional<NumberContainer> found in the in-memory database
	 */
	public Optional<NumberContainer> findById(Long id) {
		NumberContainer numberContainer = numbersDatabase.getNumbersData().get(id);
		return Optional.ofNullable(numberContainer);
	}

}
