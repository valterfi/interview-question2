package com.backbase.numbers.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.backbase.numbers.model.NumberContainer;
import com.backbase.numbers.repository.NumbersJpaRepository;

/**
 * Unit Test for NumbersService
 *
 * @author valterfi
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class NumbersServiceTest {

	@Autowired
	private NumbersService numbersService;

	@MockBean
	private NumbersJpaRepository numbersRepository;

	/**
	 * Given a list of numbers
	 * Then it will return the instance of a NumberContainer saved in the database
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldStoreNumberContainer() throws Exception {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);

		NumberContainer numberContainer = NumberContainer.builder().numbers(numbers).build();
		NumberContainer storedNumberContainer = NumberContainer.builder().id(1L).numbers(numbers).build();

		given(numbersRepository.save(numberContainer)).willReturn(storedNumberContainer);

		NumberContainer expectedNumberContainer = NumberContainer.builder().id(1L).numbers(numbers).build();

		assertThat(expectedNumberContainer, is(numbersService.store(numbers)));

		verify(numbersRepository, times(1)).save(numberContainer);
		verifyNoMoreInteractions(numbersRepository);
	}

	/**
	 * Given a list of numbers that throws an error when being saved to the database
	 * Then it will throw an Exception
	 *
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void shouldStoreThrowExceptionNumberContainer() throws Exception {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);
		NumberContainer numberContainer = NumberContainer.builder().numbers(numbers).build();

		when(numbersRepository.save(numberContainer)).thenThrow(Exception.class);

		numbersService.store(numbers);
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
	 */
	@Test
	public void shouldFindNumberContainerById() {
		Long id = 1L;
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);
		NumberContainer expectedNumberContainer = NumberContainer.builder().id(1L).numbers(numbers).build();

		Optional<NumberContainer> optional = Optional.ofNullable(expectedNumberContainer);
		given(numbersRepository.findById(id)).willReturn(optional);

		NumberContainer foundNumberContainer = numbersService.findById(id);

		assertThat(expectedNumberContainer, is(foundNumberContainer));

		verify(numbersRepository, times(1)).findById(id);
		verifyNoMoreInteractions(numbersRepository);
	}

	/**
	 * Given an id that does not exist in the database
	 * Then it should return null
	 */
	@Test
	public void shouldNotFindNumberContainerById() {
		Long id = 1L;

		given(numbersRepository.findById(id)).willReturn(Optional.empty());

		NumberContainer foundNumberContainer = numbersService.findById(id);

		assertThat(foundNumberContainer, nullValue());

		verify(numbersRepository, times(1)).findById(id);
		verifyNoMoreInteractions(numbersRepository);
	}

}
