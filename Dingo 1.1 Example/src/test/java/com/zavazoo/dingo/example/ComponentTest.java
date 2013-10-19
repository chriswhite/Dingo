package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.zavazoo.dingo.Dingo;
import com.zavazoo.dingo.Scenario;
import com.zavazoo.dingo.Scenarios;

public class ComponentTest extends TestCase {

	@Test
	public void testAdd() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/add");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			int expected = scenario.result.integer();

			int x = scenario.criteria[0].integer();
			int y = scenario.criteria[1].integer();
			int z = scenario.criteria[2].integer();

			int actual = Component.add(x, y, z);

			assertThat(actual, equalTo(expected));

		}

	}

	@Test
	public void testMultiply() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/multiply");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			double expected = scenario.result.doubleint();

			int x = scenario.criteria[0].integer();
			double y = scenario.criteria[1].doubleint();

			double actual = Component.multiply(x, y);

			assertThat(actual, equalTo(expected));

		}

	}

	@Test
	public void testValues() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/values");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			List<Object> expected = scenario.result.list();

			Map<String, Object> map = scenario.criteria[0].map();

			List<Object> actual = Component.values(map);

			assertThat(actual, containsInAnyOrder(expected.toArray()));

		}

	}

}