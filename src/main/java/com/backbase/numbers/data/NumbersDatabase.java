package com.backbase.numbers.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.backbase.numbers.model.NumberContainer;

import lombok.Getter;
import lombok.Setter;

/**
 * Class responsible for keeping all NumberContainer in memory
 *
 * @author valterfi
 *
 */
@Component
@Getter
@Setter
public class NumbersDatabase {

	/**
	 * Attribute that control the current id used to store NumberContainer
	 */
	private Long identity = 0L;

	/**
	 * Map responsible for storing all NumberContainers
	 */
	private Map<Long, NumberContainer> numbersData = new HashMap<Long, NumberContainer>();

}
