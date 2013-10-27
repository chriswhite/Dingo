package com.zavazoo.dingo.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Component {

	public static List<Object> values(Map<String, Object> map) {

		List<Object> list = new LinkedList<Object>();

		for (Map.Entry<String, Object> entry : map.entrySet()) {

			Object value = entry.getValue();

			list.add(value);

		}

		return list;

	}

}