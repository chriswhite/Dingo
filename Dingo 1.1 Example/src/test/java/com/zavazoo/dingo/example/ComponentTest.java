package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	public void testConcatenate() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/concatenate");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			List<Object> result = scenario.result.list();

			Map<String, Object> criteria = scenario.criteria[0].map();

			// coming in Dingo version 1.2 - a better way to adapt lists and maps from JSON objects

			List<String> expected = new LinkedList<String>();

			for (Object object : result) {

				String string = object.toString();

				expected.add(string);

			}

			Map<String, String> strings = new HashMap<String, String>();

			for (Entry<String, Object> entry : criteria.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue().toString();

				strings.put(key, value);

			}

			List<String> actual = Component.concatenate(strings);

			assertThat(actual, containsInAnyOrder(expected.toArray()));

		}

	}

}