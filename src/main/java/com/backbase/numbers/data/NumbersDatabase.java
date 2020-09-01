package com.backbase.numbers.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.backbase.numbers.model.NumberContainer;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class NumbersDatabase {
	
	private Long identity = 0L;
	
	private Map<Long, NumberContainer> numbersData = new HashMap<Long, NumberContainer>();

}
