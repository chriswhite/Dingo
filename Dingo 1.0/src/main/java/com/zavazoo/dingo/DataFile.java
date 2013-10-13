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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

	/** The logical name of the test data file. */
	private String name;

	/**
	 * True if the test data file is in tab-separated variable format, false if comma-separated variable format.
	 */
	private boolean tsv;

	/**
	 * Creates a data file associated with the specified logical name.
	 * 
	 * @param name
	 *            the logical name.
	 */
	DataFile(String name) {

		this.name = name;

		tsv = true;

	}

	/**
	 * Parses the specified lines of test data into scenarios.
	 * 
	 * @param lines
	 *            the lines.
	 * @return the scenarios.
	 */
	List<Scenario> parseLines(List<String> lines) {

		List<Scenario> scenarios = new LinkedList<Scenario>();

		for (String line : lines) {

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

			scenarios.add(scenario);

		}

		return scenarios;

	}

	/**
	 * Reads and yields all the lines comprised by the test data file.
	 * 
	 * @return the lines.
	 */
	List<String> readLines() {

		List<String> lines = new LinkedList<String>();

		try {

			InputStream input = null;

			try {

				String tsvName = "/" + name + ".tsv";

				input = DataFile.class.getResourceAsStream(tsvName);

				if (input == null) {

					String csvName = "/" + name + ".csv";

					input = DataFile.class.getResourceAsStream(csvName);

					if (input == null) {

						throw new DingoException("Unable to find a data file with resource name '" + tsvName + "' or '"
								+ csvName + "'");

					}

					tsv = false;

				}

				BufferedReader reader = new BufferedReader(new InputStreamReader(input));

				String line;

				while ((line = reader.readLine()) != null) {

					lines.add(line);

				}

			} finally {

				if (input != null) {

					input.close();

				}

			}

		} catch (IOException error) {

			throw new DingoException(error);

		}

		return lines;

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