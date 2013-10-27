package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import junit.framework.TestCase;
import org.junit.Test;

import org.joda.time.DateTime;

import com.zavazoo.dingo.Dingo;
import com.zavazoo.dingo.Scenario;
import com.zavazoo.dingo.Scenarios;

public class TimeFunctionsTest extends TestCase {

	@Test
	public void testAddOneYear() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/add-one-year");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			DateTime expected = scenario.result.datetime();

			DateTime dateTime = scenario.criteria[0].datetime();

			DateTime actual = TimeFunctions.addOneYear(dateTime);

			assertThat(actual, equalTo(expected));

		}

	}

}