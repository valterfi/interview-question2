package com.backbase.numbers.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.backbase.numbers.data.NumbersDatabase;
import com.backbase.numbers.model.NumberContainer;

@Repository
public class NumbersMemoryRepository {
	
	@Autowired
	private NumbersDatabase numbersDatabase;
	
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
	
	public Optional<NumberContainer> findById(Long id) {
		NumberContainer numberContainer = numbersDatabase.getNumbersData().get(id);
		return Optional.ofNullable(numberContainer);
	}
	
}
