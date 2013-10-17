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
 * Constitutes an exception that may occur while Dingo is processing test data, wrapping any other exception that may
 * occur within this RuntimeException so that calling code is not statically linked to any checked exceptions and is
 * therefore more concise and focused on its particular role that is testing other code.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public class DingoException extends RuntimeException {

	/** The serialization version unique identifier. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a Dingo exception with the specified error message.
	 * 
	 * @param message
	 *            the error message.
	 */
	public DingoException(String message) {

		super(message);

	}

	/**
	 * Creates a Dingo exception with the specified original cause of this exception.
	 * 
	 * @param cause
	 *            the cause.
	 */
	public DingoException(Throwable cause) {

		super(cause);

	}

}