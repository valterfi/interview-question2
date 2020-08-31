package com.backbase.numbers.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NumberContainer {
	
	private Long id;
	
	private List<Integer> numbers;

}
