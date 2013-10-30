package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.zavazoo.dingo.Dingo;
import com.zavazoo.dingo.Scenario;
import com.zavazoo.dingo.Scenarios;

public class UserDaoTest extends TestCase {

	@Test
	public void testCreateUserSubscriptions() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/create-user");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			List<String> expected = scenario.result.list(String.class);

			User user = scenario.criteria[0].object(User.class);

			List<String> actual = UserDao.createUserSubscriptions(user);

			assertThat(actual, containsInAnyOrder(expected.toArray()));

		}

	}

}