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

import java.io.PrintStream;

/**
 * Yields test scenarios associated with a logical name that corresponds to a file comprising test data in tab-separated
 * or comma-separated format.<br/>
 * <br/>
 * Dingo is abstract, immutable and is also thread-safe assuming that the test data file is not being modified while
 * simultaneously being read by Dingo.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public abstract class Dingo {

	/**
	 * Yields all the test scenarios associated with the specified logical name that corresponds to a file comprising
	 * test data located under the top-level project directory named 'src/test/resources'.<br/>
	 * <br/>
	 * For example the test data file located at 'src/test/resources/com/domain/project/test-data.tsv' would be
	 * associated with the logical name 'com/domain/project/test-data'.<br/>
	 * <br/>
	 * Dingo will write all test report information to the default <code>System.out</code> print stream.
	 * 
	 * @param name
	 *            the logical name.
	 * @return the test scenarios.
	 */
	public static Scenarios scenarios(String name) {

		return scenarios(name, System.out);

	}

	/**
	 * Yields all the test scenarios associated with the specified logical name that corresponds to a file comprising
	 * test data located under the top-level project directory named 'src/test/resources'.<br/>
	 * <br/>
	 * For example the test data file located at 'src/test/resources/com/domain/project/test-data.tsv' would be
	 * associated with the logical name 'com/domain/project/test-data'. <br/>
	 * <br/>
	 * Dingo will write all test report information to the specified print stream, unless the specified print stream is
	 * null in which case Dingo will not write any test report information whatsoever to any stream or other
	 * destination.
	 * 
	 * @param name
	 *            the logical name.
	 * @param report
	 *            the print stream for report information, or null to switch off test reporting.
	 * @return the test scenarios.
	 */
	public static Scenarios scenarios(String name, PrintStream report) {

		DataFile dataFile = new DataFile(name, report);
		dataFile.openFile();

		return new Scenarios(dataFile);

	}

}