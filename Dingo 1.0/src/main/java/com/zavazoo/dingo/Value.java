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

import java.math.BigDecimal;

/**
 * Constitutes a value of some test data, providing easy access to a wide range of value types so that calling code may
 * avoid parsing string values to other types.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public class Value {

	/** This value as a string. */
	public final String string;

	/** This value as a character. */
	public final char character;

	/** This value as a boolean. */
	public final boolean truth;

	/** This value as an integer. */
	public final int integer;

	/** This value as a short integer. */
	public final short shortint;

	/** This value as a long integer. */
	public final long longint;

	/** This value as a short floating-point number. */
	public final float floating;

	/** This value as a long floating-point number. */
	public final double doubleint;

	/** This value as a big decimal to help with precise calculations. */
	public final BigDecimal precise;

	/**
	 * Creates a value equivalent to the specified string.
	 * 
	 * @param value
	 *            the string.
	 */
	Value(String value) {

		string = value;

		character: {

			char character;

			try {

				character = string.charAt(0);

			} catch (IndexOutOfBoundsException possible) {

				this.character = '?';

				break character;

			}

			this.character = character;

		}

		truth = Boolean.parseBoolean(string);

		integer: {

			int integer;

			try {

				integer = Integer.parseInt(string);

			} catch (NumberFormatException possible) {

				this.integer = 0;

				break integer;

			}

			this.integer = integer;

		}

		shortint: {

			short shortint;

			try {

				shortint = Short.parseShort(string);

			} catch (NumberFormatException possible) {

				this.shortint = 0;

				break shortint;

			}

			this.shortint = shortint;

		}

		longint: {

			long longint;

			try {

				longint = Long.parseLong(string);

			} catch (NumberFormatException possible) {

				this.longint = 0;

				break longint;

			}

			this.longint = longint;

		}

		floating: {

			float floating;

			try {

				floating = Float.parseFloat(string);

			} catch (NumberFormatException possible) {

				this.floating = 0;

				break floating;

			}

			this.floating = floating;

		}

		doubleint: {

			double doubleint;

			try {

				doubleint = Double.parseDouble(string);

			} catch (NumberFormatException possible) {

				this.doubleint = 0;

				break doubleint;

			}

			this.doubleint = doubleint;

		}

		precise: {

			BigDecimal precise;

			try {

				precise = new BigDecimal(string);

			} catch (NumberFormatException possible) {

				this.precise = BigDecimal.ZERO;

				break precise;

			}

			this.precise = precise;

		}

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