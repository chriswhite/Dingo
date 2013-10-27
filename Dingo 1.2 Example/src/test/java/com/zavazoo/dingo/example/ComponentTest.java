package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
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