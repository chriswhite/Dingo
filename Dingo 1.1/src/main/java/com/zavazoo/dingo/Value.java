/*
 * Zavazoo Dingo 1.1 - Java API for Data-Driven Testing 
 * Copyright (C) 2013 Chris White <chriswhitelondon@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.zavazoo.dingo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Constitutes a value of some test data, providing easy access to a wide range of value types so that calling code may
 * avoid parsing string values to other types.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public class Value {

	/** This value as a string. */
	private String string;

	/** This value as a character. */
	private Character character;

	/** This value as a boolean. */
	private Boolean truth;

	/** This value as an integer. */
	private Integer integer;

	/** This value as a short integer. */
	private Short shortint;

	/** This value as a long integer. */
	private Long longint;

	/** This value as a short floating-point number. */
	private Float floating;

	/** This value as a long floating-point number. */
	private Double doubleint;

	/** This value as a big decimal to help with precise calculations. */
	private BigDecimal precise;

	/** This value as a list created using "raw" "untyped" JSON binding. */
	private List<Object> list;

	/** This value as a map created using "raw" "untyped" JSON binding. */
	private Map<String, Object> map;

	/**
	 * Creates a value equivalent to the specified string.
	 * 
	 * @param value
	 *            the string.
	 */
	Value(String value) {

		string = value;

	}

	/**
	 * Yields this value as a string.
	 * 
	 * @return the string.
	 */
	public String string() {

		return string;

	}

	/**
	 * Yields this value as a character.
	 * 
	 * @return the character.
	 */
	public char character() {

		if (character == null) {

			character = string.charAt(0);

		}

		return character;

	}

	/**
	 * Yields this value as a boolean.
	 * 
	 * @return the boolean.
	 */
	public boolean truth() {

		if (truth == null) {

			truth = Boolean.parseBoolean(string);

		}

		return truth;

	}

	/**
	 * Yields this value as an integer.
	 * 
	 * @return the integer.
	 */
	public int integer() {

		if (integer == null) {

			integer = Integer.parseInt(string);

		}

		return integer;

	}

	/**
	 * Yields this value as a short integer.
	 * 
	 * @return the short integer.
	 */
	public short shortint() {

		if (shortint == null) {

			shortint = Short.parseShort(string);

		}

		return shortint;

	}

	/**
	 * Yields this value as a long integer.
	 * 
	 * @return the long integer.
	 */
	public long longint() {

		if (longint == null) {

			longint = Long.parseLong(string);

		}

		return longint;

	}

	/**
	 * Yields this value as a short floating-point number.
	 * 
	 * @return the short floating-point number.
	 */
	public float floating() {

		if (floating == null) {

			floating = Float.parseFloat(string);

		}

		return floating;

	}

	/**
	 * Yields this value as a long floating-point number.
	 * 
	 * @return the long floating-point number.
	 */
	public double doubleint() {

		if (doubleint == null) {

			doubleint = Double.parseDouble(string);

		}

		return doubleint;

	}

	/**
	 * Yields this value as a big decimal to help with precise calculations.
	 * 
	 * @return the big decimal.
	 */
	public BigDecimal precise() {

		if (precise == null) {

			precise = new BigDecimal(string);

		}

		return precise;

	}

	/**
	 * Yields this value as a list created using "raw/untyped" JSON binding.
	 * 
	 * @return the list.
	 */
	public List<Object> list() {

		if (list == null) {

			TypeReference<LinkedList<Object>> type = new TypeReference<LinkedList<Object>>() {
			};

			ObjectMapper mapper = new ObjectMapper();

			try {

				list = mapper.readValue(string, type);

			} catch (Throwable error) {

				throw new DingoException(error);

			}

		}

		return list;

	}

	/**
	 * Yields this value as a map created using "raw/untyped" JSON binding.
	 * 
	 * @return the map.
	 */
	public Map<String, Object> map() {

		if (map == null) {

			TypeReference<HashMap<String, Object>> type = new TypeReference<HashMap<String, Object>>() {
			};

			ObjectMapper mapper = new ObjectMapper();

			try {

				map = mapper.readValue(string, type);

			} catch (Throwable error) {

				throw new DingoException(error);

			}

		}

		return map;

	}

	/**
	 * Yields a string representation of this value.
	 * 
	 * @return the representation.
	 */
	@Override
	public String toString() {

		return string;

	}

}