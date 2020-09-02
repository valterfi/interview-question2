package com.backbase.numbers.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.backbase.numbers.model.NumberContainer;

/**
 * Integration Test for NumbersService
 *
 * @author valterfi
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class NumbersServiceIT {

	@Autowired
	private NumbersService numbersService;

	/**
	 * Given a list of numbers
	 * Then it will return the instance of a NumberContainer saved in the database
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldStoreNumberContainer() throws Exception {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);

		NumberContainer expectedNumberContainer = NumberContainer.builder().id(1L).numbers(numbers).build();

		assertThat(expectedNumberContainer, is(numbersService.store(numbers)));
	}

	/**
	 * Given a list of numbers
	 * Then a list will be returned with the same elements in different positions
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldPermuteNumbers() throws Exception {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);

		List<Integer> permutedNumbers = numbersService.permute(numbers);

		assertThat(permutedNumbers, hasSize(7));
		assertThat(permutedNumbers, not(contains(2, 1, 3, 4, 6, 5, 7)));
		assertThat(permutedNumbers, containsInAnyOrder(numbers.toArray()));
	}

	/**
	 * Given an id that exists in the database
	 * Then it should return the NumberContainer instance with that id
	 * @throws Exception
	 */
	@Test
	public void shouldFindNumberContainerById() throws Exception {
		Long id = 1L;
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);

		numbersService.store(numbers);

		NumberContainer expectedNumberContainer = NumberContainer.builder().id(1L).numbers(numbers).build();
		NumberContainer foundNumberContainer = numbersService.findById(id);
		assertThat(expectedNumberContainer, is(foundNumberContainer));
	}

	/**
	 * Given an id that does not exist in the database
	 * Then it should return null
	 */
	@Test
	public void shouldNotFindNumberContainerById() {
		Long id = 1L;

		NumberContainer foundNumberContainer = numbersService.findById(id);
		assertThat(foundNumberContainer, nullValue());
	}

}
