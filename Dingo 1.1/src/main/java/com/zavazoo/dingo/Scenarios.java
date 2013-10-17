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

/**
 * Constitutes an abstraction over all of the test scenarios defined within a given test data file.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public class Scenarios {

	/** The data file that comprises the scenarios. */
	private DataFile dataFile;

	/** The next scenario or null when there are no more scenarios. */
	private Scenario next;

	/** Asserts that the assigned next scenario has been dispatched to a calling component. */
	private boolean used;

	/** Asserts that all of the scenarios have been dispatched to calling components. */
	private boolean done;

	/**
	 * Creates a abstraction over the scenarios defined within the specified data file.
	 * 
	 * @param dataFile
	 *            the dataFile.
	 */
	Scenarios(DataFile dataFile) {

		this.dataFile = dataFile;

		used = true;
		done = false;

	}

	/**
	 * Asserts that there are more scenarios.
	 * 
	 * @return true if there are more scenarios, false otherwise.
	 */
	public boolean more() {

		if (done) {

			return false;

		}

		moreNext();

		if (next == null) {

			return false;

		}

		return true;

	}

	/**
	 * Yields the next scenario, or null if there are no more scenarios.
	 * 
	 * @return the next scenario, or null if there are no more scenarios.
	 */
	public Scenario next() {

		if (done) {

			return null;

		}

		moreNext();

		used = true;

		return next;

	}

	/**
	 * Asserts that there are more scenarios and assigns the next scenario, or assigns null if there are no more
	 * scenarios.
	 */
	private void moreNext() {

		if (used) {

			next = dataFile.nextScenario();

			if (next == null) {

				done = true;

			} else {

				used = false;

			}

		}

	}

}