package com.backbase.numbers.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 *
 * Model that represents the list of numbers that are stored
 *
 * @author valterfi
 *
 */
@Data
@Builder
public class NumberContainer {

	private Long id;

	private List<Integer> numbers;

}
