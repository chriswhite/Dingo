package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import junit.framework.TestCase;

import org.junit.Test;

import com.zavazoo.dingo.Dingo;
import com.zavazoo.dingo.Scenario;

public class ComponentTest extends TestCase {

	@Test
	public void testAdd() {

		for (Scenario scenario : Dingo.scenarios("com/zavazoo/dingo/example/add")) {

			int expected = scenario.result.integer;

			int x = scenario.criteria[0].integer;
			int y = scenario.criteria[1].integer;
			int z = scenario.criteria[2].integer;

			int actual = Component.add(x, y, z);

			assertThat(actual, equalTo(expected));

		}

	}

	@Test
	public void testMultiply() {

		for (Scenario scenario : Dingo.scenarios("com/zavazoo/dingo/example/multiply")) {

			double expected = scenario.result.doubleint;

			int x = scenario.criteria[0].integer;
			double y = scenario.criteria[1].doubleint;

			double actual = Component.multiply(x, y);

			assertThat(actual, equalTo(expected));

		}

	}

	@Test
	public void testConcatenate() {

		for (Scenario scenario : Dingo.scenarios("com/zavazoo/dingo/example/concatenate")) {

			String expected = scenario.result.string;

			String x = scenario.criteria[0].string;
			char y = scenario.criteria[1].character;

			String actual = Component.concatenate(x, y);

			assertThat(actual, equalTo(expected));

		}

	}

}