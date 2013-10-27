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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Tests the data file component.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public class DataFileTest extends TestCase {

	/**
	 * Tests the opening of test data files and determination of the file format.
	 */
	@Test
	public void testOpenFile() {

		DataFile dataFile = new DataFile("empty-toplevel", null);
		dataFile.openFile();

		assertTrue(dataFile.getType() == DataFileType.CSV);

		dataFile = new DataFile("com/zavazoo/dingo/empty-nested", null);
		dataFile.openFile();

		assertTrue(dataFile.getType() == DataFileType.TSV);

		dataFile = new DataFile("com/zavazoo/dingo/empty-scenarios", null);
		dataFile.openFile();

		assertTrue(dataFile.getType() == DataFileType.BSV);

		dataFile = new DataFile("non-existent", null);

		try {

			dataFile.openFile();

			fail();

		} catch (DingoException expected) {
		}

	}

	/**
	 * Tests the parsing of the next scenario comprised by a test data file.
	 */
	@Test
	public void testNextScenario() {

		DataFile dataFile = new DataFile("com/zavazoo/dingo/empty-scenarios", null);
		dataFile.openFile();

		assertNull(dataFile.nextScenario());
		assertNull(dataFile.nextScenario());
		assertNull(dataFile.nextScenario());

		dataFile = new DataFile("com/zavazoo/dingo/three-scenarios", null);
		dataFile.openFile();

		assertNotNull(dataFile.nextScenario());
		assertNotNull(dataFile.nextScenario());
		assertNotNull(dataFile.nextScenario());
		assertNull(dataFile.nextScenario());
		assertNull(dataFile.nextScenario());
		assertNull(dataFile.nextScenario());

		Scenario firstExpected = new Scenario(new Value("result1"), new Value[] { new Value("criteria1-1"),
				new Value("criteria1-2") });

		Scenario secondExpected = new Scenario(new Value("result2"), new Value[] { new Value("criteria2-1"),
				new Value("criteria2-2") });

		Scenario thirdExpected = new Scenario(new Value("result3"), new Value[] { new Value("criteria3-1"),
				new Value("criteria3-2") });

		dataFile = new DataFile("com/zavazoo/dingo/three-scenarios", null);
		dataFile.openFile();

		Scenario actual = dataFile.nextScenario();

		assertThat(actual, equalTo(firstExpected));

		actual = dataFile.nextScenario();

		assertThat(actual, equalTo(secondExpected));

		actual = dataFile.nextScenario();

		assertThat(actual, equalTo(thirdExpected));

		assertNull(dataFile.nextScenario());
		assertNull(dataFile.nextScenario());
		assertNull(dataFile.nextScenario());

	}

	/**
	 * Tests the parsing of lines of test data.
	 */
	@Test
	public void testParseLine() {

		String line = "result, criteria1, criteria2, criteria3";

		Scenario expected = new Scenario(new Value("result"), new Value[] { new Value("criteria1"),
				new Value("criteria2"), new Value("criteria3") });

		DataFile dataFile = new DataFile(null, null);
		dataFile.setType(DataFileType.CSV);

		Scenario actual = dataFile.parseLine(line);

		assertThat(actual, equalTo(expected));

		line = "123 \t true \t 123 \t 123.456\t 2004-12-13T21:39:45.618-08:00";

		expected = new Scenario(new Value("123"), new Value[] { new Value("true"), new Value("123"),
				new Value("123.456"), new Value("2004-12-13T21:39:45.618-08:00") });

		dataFile = new DataFile(null, null);
		dataFile.setType(DataFileType.TSV);

		actual = dataFile.parseLine(line);

		assertThat(actual, equalTo(expected));

		line = "-0.999	|	some text	|	{\"key\": \"value\"}";

		expected = new Scenario(new Value("-0.999"), new Value[] { new Value("some text"),
				new Value("{\"key\": \"value\"}") });

		dataFile = new DataFile(null, null);
		dataFile.setType(DataFileType.BSV);

		actual = dataFile.parseLine(line);

		assertThat(actual, equalTo(expected));

	}

	/**
	 * Tests the writing of test report information.
	 */
	@Test
	public void testReport() {

		String newline = System.getProperty("line.separator");

		String name = "com/zavazoo/dingo/three-scenarios";

		StringBuilder expected = new StringBuilder();

		expected.append(DataFile.REPORT_PREFIX);
		expected.append(name);
		expected.append(".1: result1, criteria1-1, criteria1-2");
		expected.append(newline);

		expected.append(DataFile.REPORT_PREFIX);
		expected.append(name);
		expected.append(".2: result2, criteria2-1, criteria2-2");
		expected.append(newline);

		expected.append(DataFile.REPORT_PREFIX);
		expected.append(name);
		expected.append(".3: result3, criteria3-1, criteria3-2");
		expected.append(newline);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		PrintStream mock = new PrintStream(buffer);

		DataFile dataFile = new DataFile(name, mock);
		dataFile.openFile();

		assertNotNull(dataFile.nextScenario());
		assertNotNull(dataFile.nextScenario());
		assertNotNull(dataFile.nextScenario());

		String actual = buffer.toString();

		assertThat(actual, equalTo(expected.toString()));

	}

}