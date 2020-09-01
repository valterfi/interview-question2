package com.backbase.numbers.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.backbase.numbers.model.NumberContainer;
import com.backbase.numbers.service.NumbersService;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class NumbersControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private NumbersService numbersService;
	
	@Test
	public void shouldBadRequestStore() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
			      .post("/store")
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldStoreNumbersOk() throws Exception {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);
		
		NumberContainer numberContainer = NumberContainer.builder()
				.id(1L)
				.numbers(numbers)
				.build();
		
		given(numbersService.store(numbers)).willReturn(numberContainer);
		
		mockMvc.perform(MockMvcRequestBuilders
			      .post("/store?numbers=2,1,3,4,6,5,7")
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isOk())
				  .andExpect(jsonPath("$").isNotEmpty())
				  .andExpect(jsonPath("$").value("1"));
	}
	
	@Test
	public void shouldStoreEmptyNumbersOk() throws Exception {
		List<Integer> numbers = new ArrayList<Integer>();
		
		NumberContainer numberContainer = NumberContainer.builder()
				.id(2L)
				.numbers(numbers)
				.build();
		
		given(numbersService.store(numbers)).willReturn(numberContainer);
		
		mockMvc.perform(MockMvcRequestBuilders
			      .post("/store?numbers=")
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isOk())
				  .andExpect(jsonPath("$").isNotEmpty())
				  .andExpect(jsonPath("$").value("2"));
	}
	
	@Test
	public void shouldStoreThrowException() throws Exception {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);
		
		String errorMessage = "Test Exception";
		when(numbersService.store(numbers)).thenThrow(new Exception(errorMessage));
		
		mockMvc.perform(MockMvcRequestBuilders
			      .post("/store?numbers=2,1,3,4,6,5,7")
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isInternalServerError())
				  .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception))
			      .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(errorMessage)));
	}
	
	@Test
	public void shouldBadRequestPermutation() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
			      .get("/permutation")
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldPermutationOk() throws Exception {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);
		NumberContainer numberContainer = NumberContainer.builder()
				.id(1L)
				.numbers(numbers)
				.build();
		
		given(numbersService.findById(1L)).willReturn(numberContainer);
		
		List<Integer> expectedNumbers = Arrays.asList(2, 3, 6, 7, 1, 3, 5);
		given(numbersService.permute(numbers)).willReturn(expectedNumbers);
		
		mockMvc.perform(MockMvcRequestBuilders
			      .get("/permutation?id=1")
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isOk())
				  .andExpect(jsonPath("$", hasSize(7)))
			      .andExpect(jsonPath("$[0]").value(2))
			      .andExpect(jsonPath("$[1]").value(3))
			      .andExpect(jsonPath("$[2]").value(6))
			      .andExpect(jsonPath("$[3]").value(7))
			      .andExpect(jsonPath("$[4]").value(1))
			      .andExpect(jsonPath("$[5]").value(3))
			      .andExpect(jsonPath("$[6]").value(5));
	}
	
	@Test
	public void shouldNotFoundPermutation() throws Exception {
		given(numbersService.findById(1L)).willReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
			      .get("/permutation?id=1")
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldNotFoundWithIdNullPermutation() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
			      .get("/permutation?id=")
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldPermutationThrowException() throws Exception {
		List<Integer> numbers = Arrays.asList(2, 1, 3, 4, 6, 5, 7);
		NumberContainer numberContainer = NumberContainer.builder()
				.id(1L)
				.numbers(numbers)
				.build();
		
		given(numbersService.findById(1L)).willReturn(numberContainer);
		
		String errorMessage = "Test Exception";
		when(numbersService.permute(numbers)).thenThrow(new Exception(errorMessage));
		
		mockMvc.perform(MockMvcRequestBuilders
				  .get("/permutation?id=1")
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isInternalServerError())
				  .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception))
			      .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(errorMessage)));
	}

}
