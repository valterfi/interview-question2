package com.backbase.numbers.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.oneOf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Integration Test for NumbersController
 *
 * @author valterfi
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class NumbersControllerIT {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Given a POST "/store" request without RequestParams
	 * Then a bad request response will return
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldBadRequestStore() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post("/store")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Given a POST "/store" request with RequestParams
	 * Then the new id will return
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldStoreNumbersOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post("/store?numbers=2,1,3,4,6,5,7")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$").value("1"));
	}

	/**
	 * Given a POST "/store" request with empty RequestParams
	 * Then the new id will return
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldStoreEmptyNumbersOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post("/store?numbers=")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$").value("1"));
	}

	/**
	 * Given a GET "/permutation" request without RequestParams
	 * Then a bad request response will return
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldBadRequestPermutation() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/permutation")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Given a GET "/permutation" request with RequestParams
	 * Then it will return the new list with the exchanged positions
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldPermutationOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post("/store?numbers=2,1,3,4,6,5,7")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$").value("1"));

		Integer[] expetectedElements = new Integer[] {2, 1, 3, 4, 6, 5, 7};

		mockMvc.perform(MockMvcRequestBuilders
				.get("/permutation?id=1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(7)))
				.andExpect(jsonPath("$[0]").value(is(oneOf(expetectedElements))))
				.andExpect(jsonPath("$[1]").value(is(oneOf(expetectedElements))))
				.andExpect(jsonPath("$[2]").value(is(oneOf(expetectedElements))))
				.andExpect(jsonPath("$[3]").value(is(oneOf(expetectedElements))))
				.andExpect(jsonPath("$[4]").value(is(oneOf(expetectedElements))))
				.andExpect(jsonPath("$[5]").value(is(oneOf(expetectedElements))))
				.andExpect(jsonPath("$[6]").value(is(oneOf(expetectedElements))));
	}

	/**
	 * Given a GET "/permutation" request with RequestParams that returns null
	 * Then a Not Found response will be returned
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldNotFoundPermutation() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/permutation?id=1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	/**
	 * Given a GET "/permutation" request with empty RequestParams
	 * Then a Not Found response will be returned
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldNotFoundWithIdNullPermutation() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/permutation?id=")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
