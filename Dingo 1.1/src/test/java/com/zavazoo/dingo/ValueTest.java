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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

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
	 * Tests the parsing of lists.
	 */
	@Test
	public void testList() {

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
	 * Tests the parsing of maps.
	 */
	@Test
	public void testMap() {

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

}