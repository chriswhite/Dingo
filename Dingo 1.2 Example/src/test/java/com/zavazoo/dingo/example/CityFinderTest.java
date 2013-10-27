package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import junit.framework.TestCase;
import org.junit.Test;

import com.zavazoo.dingo.Dingo;
import com.zavazoo.dingo.Scenario;
import com.zavazoo.dingo.Scenarios;

public class CityFinderTest extends TestCase {

	@Test
	public void testMatchCityForSuburbs() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/city-suburbs");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			String expected = scenario.result.string();

			String firstSuburb = scenario.criteria[0].string();
			String secondSuburb = scenario.criteria[1].string();
			String thirdSuburb = scenario.criteria[2].string();

			String actual = CityFinder.matchCityForSuburbs(firstSuburb, secondSuburb, thirdSuburb);

			assertThat(actual, equalTo(expected));

		}

	}

}