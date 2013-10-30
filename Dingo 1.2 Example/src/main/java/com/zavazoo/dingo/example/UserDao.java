package com.zavazoo.dingo.example;

import java.util.LinkedList;
import java.util.List;

public class UserDao {

	public static List<String> createUserSubscriptions(User user) {

		// the functionality is mocked in this example but in practice this method would call a database

		List<String> subscriptions = new LinkedList<String>();

		if (user.getGender() == User.Gender.MALE) {

			subscriptions.add("Weightlifting");
			subscriptions.add("Fast Cars");

		} else {

			subscriptions.add("Fashion");
			subscriptions.add("Yoga");
			subscriptions.add("Food");

		}

		return subscriptions;

	}

}