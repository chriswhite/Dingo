package com.zavazoo.dingo.example;

public class CityFinder {

	public static String matchCityForSuburbs(String first, String second, String third) {

		// the functionality is mocked in this example but in practice this method would query a database

		if (first.equals("Mayfair")) {

			return "London";

		}

		if (first.equals("Manhattan")) {

			return "New York City";

		}

		return "Paris";

	}

}