package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import junit.framework.TestCase;
import org.junit.Test;

import com.zavazoo.dingo.Dingo;
import com.zavazoo.dingo.Scenario;
import com.zavazoo.dingo.Scenarios;

public class EquationsTest extends TestCase {

	@Test
	public void testAddSubtractDivideMultiply() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/algebra");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			BigDecimal expected = scenario.result.precise();

			int add = scenario.criteria[0].integer();
			BigDecimal subtract = scenario.criteria[1].precise();
			BigDecimal divide = scenario.criteria[2].precise();
			BigDecimal multiply = scenario.criteria[3].precise();

			BigDecimal actual = Equations.addSubtractDivideMultiply(add, subtract, divide, multiply);

			actual = actual.stripTrailingZeros();

			assertThat(actual, equalTo(expected));

		}

	}

}