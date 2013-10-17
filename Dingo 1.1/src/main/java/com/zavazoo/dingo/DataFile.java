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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Reads and parses a file comprising test data.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
class DataFile {

	/** The prefix used for all test report information. */
	static final String REPORT_PREFIX = "Dingo test data: ";

	/** The logical name of the test data file. */
	private String name;

	/**
	 * True if the test data file is in tab-separated variable format, false if comma-separated variable format.
	 */
	private boolean tsv;

	/** The buffered reader used to retrieve the next line of the test data file. */
	private BufferedReader reader;

	/** The line number of the current line of the test data file. */
	private int number;

	/** The print stream for report information, or null to switch off test reporting. */
	private PrintStream report;

	/**
	 * Creates a data file associated with the specified logical name.<br/>
	 * <br/>
	 * Dingo will write all test report information to the specified print stream, unless the specified print stream is
	 * null in which case Dingo will not write any test report information whatsoever to any stream or other
	 * destination.
	 * 
	 * @param name
	 *            the logical name.
	 * @param report
	 *            the print stream for report information, or null to switch off test reporting.
	 */
	DataFile(String name, PrintStream report) {

		this.name = name;
		this.report = report;

		tsv = true;

		number = 0;

	}

	/**
	 * Opens the test data file, and determines the file format, ready to read and parse scenarios.
	 */
	void openFile() {

		String tsvName = "/" + name + ".tsv";

		InputStream input = DataFile.class.getResourceAsStream(tsvName);

		if (input == null) {

			String csvName = "/" + name + ".csv";

			input = DataFile.class.getResourceAsStream(csvName);

			if (input == null) {

				throw new DingoException("Unable to find a data file with resource name '" + tsvName + "' or '"
						+ csvName + "'");

			}

			tsv = false;

		}

		reader = new BufferedReader(new InputStreamReader(input));

	}

	/**
	 * Reads and parses the next line of test data to create the next scenario.
	 * 
	 * @return the scenario, or null if there are no more scenarios.
	 */
	Scenario nextScenario() {

		String line = null;

		try {

			line = reader.readLine();

			if (line == null) {

				return null;

			}

			number++;

		} catch (IOException error) {

			throw new DingoException(error);

		}

		if (report != null) {

			StringBuilder information = new StringBuilder();

			information.append(REPORT_PREFIX);
			information.append(name);
			information.append(".");
			information.append(number);
			information.append(": ");
			information.append(line);

			report.println(information.toString());

		}

		return parseLine(line);

	}

	/**
	 * Parses the specified line of test data into a scenario.
	 * 
	 * @param line
	 *            the line.
	 * @return the scenario.
	 */
	Scenario parseLine(String line) {

		String delimiter = "\t";

		if (!tsv) {

			delimiter = ",";

		}

		StringTokenizer tokenizer = new StringTokenizer(line, delimiter);

		String result = null;
		List<String> criteria = new LinkedList<String>();

		while (tokenizer.hasMoreTokens()) {

			String element = tokenizer.nextToken();

			element = element.trim();

			if (result == null) {

				result = element;

			} else {

				criteria.add(element);

			}

		}

		int size = criteria.size();

		Value[] values = new Value[size];

		int index = 0;

		for (String criterion : criteria) {

			values[index] = new Value(criterion);

			index++;

		}

		Scenario scenario = new Scenario(new Value(result), values);

		return scenario;

	}

	/**
	 * Closes the test data file.
	 */
	@Override
	public void finalize() {

		if (reader != null) {

			try {

				reader.close();

			} catch (IOException error) {

				throw new DingoException(error);

			}

		}

	}

	/**
	 * Yields true if the test data file is in tab-separated variable format, false if comma-separated variable format.
	 * 
	 * @return true if TSV, false if CSV.
	 */
	boolean isTsv() {

		return tsv;

	}

	/**
	 * Specifies that the test data file is in tab-separated variable format.
	 * 
	 * @param tsv
	 *            true if TSV, false if CSV.
	 */
	void setTsv(boolean tsv) {

		this.tsv = tsv;

	}

}