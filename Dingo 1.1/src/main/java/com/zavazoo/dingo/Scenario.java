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
 * Constitutes a test scenario comprising an expected result of the scenario given a set of criteria.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public class Scenario {

	/** The expected result of this scenario. */
	public final Value result;

	/** The criteria which define this scenario. */
	public final Value[] criteria;

	/** The hash code that uniquely identifies this scenario. */
	private Integer hashCode;

	/**
	 * Creates a scenario with the specified expected result given the specified criteria.
	 * 
	 * @param result
	 *            the result.
	 * @param criteria
	 *            the criteria.
	 */
	Scenario(Value result, Value[] criteria) {

		this.result = result;
		this.criteria = criteria;

	}

	/**
	 * Asserts that this scenario is equivalent to the specified scenario.
	 * 
	 * @param that
	 *            the specified scenario.
	 * @return true if the scenarios are equivalent, false otherwise.
	 */
	@Override
	public boolean equals(Object that) {

		return this.hashCode() == ((Scenario) that).hashCode();

	}

	/**
	 * Calculates and yields the hash code that uniquely identifies this scenario.
	 * 
	 * @return the hash code.
	 */
	@Override
	public int hashCode() {

		if (hashCode == null) {

			String impossible = "!\"£$%^&*()-=_+{}:@~;'#";

			StringBuilder builder = new StringBuilder();

			builder.append(result.toString());

			for (Value criterion : criteria) {

				builder.append(impossible);

				builder.append(criterion.toString());

			}

			hashCode = builder.toString().hashCode();

		}

		return hashCode;

	}

}