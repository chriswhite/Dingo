package com.zavazoo.dingo.example;

import java.math.BigDecimal;

public class Equations {

	public static BigDecimal addSubtractDivideMultiply(int add, BigDecimal subtract, BigDecimal divide,
			BigDecimal multiply) {

		BigDecimal result = new BigDecimal(add);

		result = result.subtract(subtract);

		result = result.divide(divide);

		result = result.multiply(multiply);

		return result;

	}

}