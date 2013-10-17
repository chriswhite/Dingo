package com.zavazoo.dingo.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Component {

	public static int add(int x, int y, int z) {

		return x + y + z;

	}

	public static double multiply(int x, double y) {

		return x * y;

	}

	public static List<String> concatenate(Map<String, String> strings) {

		List<String> list = new LinkedList<String>();

		for (Map.Entry<String, String> entry : strings.entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			String concatenated = key + value;

			list.add(concatenated);

		}

		return list;

	}

}