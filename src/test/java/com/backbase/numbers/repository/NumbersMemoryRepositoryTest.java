package com.backbase.numbers.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.backbase.numbers.data.NumbersDatabase;
import com.backbase.numbers.model.NumberContainer;

import static org.hamcrest.CoreMatchers.*;

/**
 * Unit Test for NumbersMemoryRepository
 *
 * @author valterfi
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class NumbersMemoryRepositoryTest {

	@Autowired
	private NumbersMemoryRepository numbersMemoryRepository;

	@MockBean
	private NumbersDatabase numbersDatabase;

	/**
	 * Given an instance of NumberContainer without id
	 * Then it should save it to the database in memory, increment a new id and return an instance of NumberContainer with id
	 */
	@Test
	public void shouldSaveNewNumberContainer() {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);
		NumberContainer numberContainer = NumberContainer.builder().numbers(numbers).build();

		given(numbersDatabase.getIdentity()).willReturn(0L);

		NumberContainer expectedNumberContainer = NumberContainer.builder().id(1L).numbers(numbers).build();

		NumberContainer newNumberContainer = numbersMemoryRepository.save(numberContainer);

		assertThat(expectedNumberContainer, is(newNumberContainer));
		assertThat(1L, is(newNumberContainer.getId()));
	}

	/**
	 * Given an instance of NumberContainer with id
	 * Then it should just update the value of the NumberContainer instance in the in-memory database
	 */
	@Test
	public void shouldUpdateNumberContainer() {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);
		NumberContainer numberContainer = NumberContainer.builder().id(2L).numbers(numbers).build();

		NumberContainer expectedNumberContainer = NumberContainer.builder().id(2L).numbers(numbers).build();

		NumberContainer updatedNumberContainer = numbersMemoryRepository.save(numberContainer);

		assertThat(expectedNumberContainer, is(updatedNumberContainer));
	}

	/**
	 * Given an id that exists in the database
	 * Then it should return an optional with NumberContainer for that id
	 */
	@Test
	public void shouldFindNumberContainerById() {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);
		NumberContainer numberContainer = NumberContainer.builder().id(3L).numbers(numbers).build();

		Map<Long, NumberContainer> numbersData = new HashMap<Long, NumberContainer>();
		numbersData.put(3L, numberContainer);

		given(numbersDatabase.getNumbersData()).willReturn(numbersData);

		Optional<NumberContainer> optional = numbersMemoryRepository.findById(3L);
		assertTrue(optional.isPresent());

		NumberContainer foundNumberContainer = optional.get();
		assertThat(3L, is(foundNumberContainer.getId()));

		List<Integer> expectedNumbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);
		assertThat(expectedNumbers, is(foundNumberContainer.getNumbers()));
	}

	/**
	 * Given an id that does not exist in the database
	 * Then an empty optional should be returned
	 */
	@Test
	public void shouldNotFindNumberContainerById() {
		Map<Long, NumberContainer> numbersData = new HashMap<Long, NumberContainer>();

		given(numbersDatabase.getNumbersData()).willReturn(numbersData);

		Optional<NumberContainer> optional = numbersMemoryRepository.findById(4L);
		assertFalse(optional.isPresent());
	}

}
