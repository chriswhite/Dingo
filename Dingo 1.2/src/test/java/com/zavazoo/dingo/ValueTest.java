/*
 * Zavazoo Dingo 1.2 - Java API for Data-Driven Testing 
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Tests the value component.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public class ValueTest extends TestCase {

	/**
	 * Tests the parsing of string/character values.
	 */
	@Test
	public void testString() {

		Value value = new Value("some text");

		assertThat("some text", equalTo(value.string()));

		try {

			value.integer();

			fail();

		} catch (NumberFormatException expected) {
		}

		try {

			value.precise();

			fail();

		} catch (NumberFormatException expected) {
		}

		assertThat('s', equalTo(value.character()));

		value = new Value("x");

		assertThat('x', equalTo(value.character()));

	}

	/**
	 * Tests the parsing of boolean values.
	 */
	@Test
	public void testBoolean() {

		Value value = new Value("true");

		assertTrue(value.truth());

		value = new Value("false");

		assertFalse(value.truth());

		value = new Value("random");

		assertFalse(value.truth());

		value = new Value("5");

		assertFalse(value.truth());

	}

	/**
	 * Tests the parsing of small signed integers.
	 */
	@Test
	public void testSmallInteger() {

		Value value = new Value("5");

		assertThat(5, equalTo(value.integer()));
		assertThat((short) 5, equalTo(value.shortint()));

		value = new Value("-100");

		assertThat(-100, equalTo(value.integer()));
		assertThat((short) -100, equalTo(value.shortint()));

		value = new Value(Short.toString(Short.MAX_VALUE));

		assertThat(Short.MAX_VALUE, equalTo(value.shortint()));

	}

	/**
	 * Tests the parsing of large signed integers.
	 */
	@Test
	public void testLargeInteger() {

		Value value = new Value("500000");

		assertThat(500000, equalTo(value.integer()));
		assertThat(500000l, equalTo(value.longint()));

		value = new Value("-100000000");

		assertThat(-100000000, equalTo(value.integer()));
		assertThat(-100000000l, equalTo(value.longint()));

		value = new Value(Long.toString(Long.MAX_VALUE));

		assertThat(Long.MAX_VALUE, equalTo(value.longint()));

	}

	/**
	 * Tests the parsing of floating-point numbers.
	 */
	@Test
	public void testFloatingPoint() {

		Value value = new Value("12.34");

		assertThat(12.34f, equalTo(value.floating()));
		assertThat(12.34d, equalTo(value.doubleint()));

		value = new Value("-123456789.123456789");

		assertThat(-123456789.123456789f, equalTo(value.floating()));
		assertThat(-123456789.123456789d, equalTo(value.doubleint()));

		value = new Value(Float.toString(Float.MAX_VALUE));

		assertThat(Float.MAX_VALUE, equalTo(value.floating()));

		value = new Value(Double.toString(Double.MAX_VALUE));

		assertThat(Double.MAX_VALUE, equalTo(value.doubleint()));

	}

	/**
	 * Tests the parsing of big decimal numbers.
	 */
	@Test
	public void testBigDecimal() {

		Value value = new Value("10");

		assertThat(BigDecimal.TEN, equalTo(value.precise()));

		value = new Value("123.456");

		assertThat(new BigDecimal("123.456"), equalTo(value.precise()));

	}

	/**
	 * Tests the parsing of ISO-8601 compatible date/times.
	 */
	@Test
	public void testDateTime() {

		String datetime = "2004-12-13T21:39:45.618-08:00";

		Value value = new Value(datetime);

		assertThat(new DateTime(datetime), equalTo(value.datetime()));

	}

	/**
	 * Tests the parsing of lists using raw/untyped JSON binding.
	 */
	@Test
	public void testRawList() {

		List<Object> expected = new LinkedList<Object>();

		Value value = new Value("[]");

		assertThat(expected, equalTo(value.list()));

		expected.add("entry1");

		value = new Value("[\"entry1\"]");

		assertThat(expected, equalTo(value.list()));

		expected.add("entry2");
		expected.add("entry3");

		value = new Value("[\"entry1\", \"entry2\", \"entry3\"]");

		assertThat(expected, equalTo(value.list()));

	}

	/**
	 * Tests the parsing of lists using JSON data binding with generics.
	 */
	@Test
	public void testGenericList() {

		List<String> strings = new LinkedList<String>();
		strings.add("criteria1");
		strings.add("criteria2");
		strings.add("criteria3");

		Value value = new Value("[\"criteria1\", \"criteria2\", \"criteria3\"]");

		assertThat(strings, equalTo(value.list(String.class)));

		List<Integer> integers = new LinkedList<Integer>();
		integers.add(10);
		integers.add(25);
		integers.add(2);

		value = new Value("[10, 25, 2]");

		assertThat(integers, equalTo(value.list(Integer.class)));

	}

	/**
	 * Tests the parsing of maps using raw/untyped JSON binding.
	 */
	@Test
	public void testRawMap() {

		Map<String, Object> expected = new HashMap<String, Object>();

		Value value = new Value("{}");

		assertThat(expected, equalTo(value.map()));

		expected.put("entry1key", "entry1value");

		value = new Value("{\"entry1key\": \"entry1value\"}");

		assertThat(expected, equalTo(value.map()));

		expected.put("entry2key", "entry2value");
		expected.put("entry3key", "entry3value");

		value = new Value(
				"{\"entry1key\": \"entry1value\", \"entry2key\": \"entry2value\", \"entry3key\": \"entry3value\"}");

		assertThat(expected, equalTo(value.map()));

	}

	/**
	 * Tests the parsing of maps using JSON data binding with generics.
	 */
	@Test
	public void testGenericMap() {

		Map<String, Object> stringObjects = new HashMap<String, Object>();
		stringObjects.put("criteria1", "string");
		stringObjects.put("criteria2", 55);
		stringObjects.put("criteria3", 10.78);

		Value value = new Value("{\"criteria1\": \"string\", \"criteria2\": 55, \"criteria3\": 10.78}");

		assertThat(stringObjects, equalTo(value.map(String.class, Object.class)));

		Map<String, Integer> stringIntegers = new HashMap<String, Integer>();
		stringIntegers.put("apple", 44);
		stringIntegers.put("orange", 1893);
		stringIntegers.put("banana", -99);

		value = new Value("{\"apple\": 44, \"orange\": 1893, \"banana\": -99}");

		assertThat(stringIntegers, equalTo(value.map(String.class, Integer.class)));

	}

	/**
	 * Tests the parsing of data using JSON full data binding.
	 */
	@Test
	public void testObject() {

		User expected = new User(User.Gender.MALE, "Joe", "Sixpack", 30, false);

		Value value = new Value(
				"{\"name\": {\"first\": \"Joe\", \"last\": \"Sixpack\"}, \"gender\": \"MALE\", \"age\": 30, \"verified\": false}");

		assertThat(expected, equalTo(value.object(User.class)));

		expected = new User(User.Gender.FEMALE, "Holly", "Molly", 25, true);

		value = new Value(
				"{\"name\": {\"first\": \"Holly\", \"last\": \"Molly\"}, \"gender\": \"FEMALE\", \"age\": 25, \"verified\": true}");

		assertThat(expected, equalTo(value.object(User.class)));

	}

	static class User {

		private Gender gender;
		private Name name;
		private int age;
		private boolean verified;

		public enum Gender {
			MALE, FEMALE
		};

		public static class Name {

			private String first;
			private String last;

			public Name() {
			}

			public Name(String first, String last) {
				this.first = first;
				this.last = last;
			}

			public String getFirst() {
				return first;
			}

			public String getLast() {
				return last;
			}

			public void setFirst(String first) {
				this.first = first;
			}

			public void setLast(String last) {
				this.last = last;
			}

		}

		public User() {
		}

		public User(Gender gender, String first, String last, int age, boolean verified) {
			this.gender = gender;
			this.name = new Name(first, last);
			this.age = age;
			this.verified = verified;
		}

		public Name getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

		public boolean isVerified() {
			return verified;
		}

		public Gender getGender() {
			return gender;
		}

		public void setName(Name name) {
			this.name = name;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public void setVerified(boolean verified) {
			this.verified = verified;
		}

		public void setGender(Gender gender) {
			this.gender = gender;
		}

		@Override
		public boolean equals(Object that) {

			return this.hashCode() == that.hashCode();

		}

		@Override
		public int hashCode() {

			String impossible = "!\"£$%^&*()_+{}@~:?><";

			StringBuilder hash = new StringBuilder();

			if (gender == Gender.MALE) {

				hash.append("MALE");

			} else {

				hash.append("FEMALE");

			}

			hash.append(impossible);
			hash.append(name.first);
			hash.append(impossible);
			hash.append(name.last);
			hash.append(impossible);
			hash.append(age);
			hash.append(impossible);
			hash.append(new Boolean(verified).toString());

			return hash.toString().hashCode();

		}

	}

}