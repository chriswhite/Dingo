/*
 * Zavazoo Dingo 1.0 - Java API for Data-Driven Testing 
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

import java.util.LinkedList;
import java.util.List;

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
	 * Tests the read lines operation.
	 */
	@Test
	public void testReadLines() {

		List<String> expected = new LinkedList<String>();
		expected.add("line 1");
		expected.add("line 2");
		expected.add("line 3");

		DataFile dataFile = new DataFile("test");

		List<String> actual = dataFile.readLines();

		assertThat(actual, equalTo(expected));

		assertThat(dataFile.isTsv(), equalTo(false));

		dataFile = new DataFile("com/zavazoo/dingo/test");

		actual = dataFile.readLines();

		assertThat(actual, equalTo(expected));

		assertThat(dataFile.isTsv(), equalTo(true));

	}

	/**
	 * Tests the parse lines operation.
	 */
	@Test
	public void testParseLines() {

		List<String> tsvLines = new LinkedList<String>();
		tsvLines.add(" result 1\tcriteria 1-1\tcriteria 1-2");
		tsvLines.add("result 2\t criteria 2-1 \tcriteria 2-2");
		tsvLines.add("result 3\tcriteria 3-1\t  criteria 3-2");

		List<Scenario> expected = new LinkedList<Scenario>();
		expected.add(new Scenario(new Value("result 1"), new Value[] { new Value("criteria 1-1"),
				new Value("criteria 1-2") }));
		expected.add(new Scenario(new Value("result 2"), new Value[] { new Value("criteria 2-1"),
				new Value("criteria 2-2") }));
		expected.add(new Scenario(new Value("result 3"), new Value[] { new Value("criteria 3-1"),
				new Value("criteria 3-2") }));

		DataFile dataFile = new DataFile(null);

		List<Scenario> actual = dataFile.parseLines(tsvLines);

		assertThat(actual, equalTo(expected));

		List<String> csvLines = new LinkedList<String>();
		csvLines.add("result 1,criteria 1-1,criteria 1-2 ");
		csvLines.add("result 2,criteria 2-1, criteria 2-2");
		csvLines.add("result 3 ,\tcriteria 3-1,criteria 3-2");

		dataFile = new DataFile(null);
		dataFile.setTsv(false);

		actual = dataFile.parseLines(csvLines);

		assertThat(actual, equalTo(expected));

	}

}