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

/**
 * Constitutes an exception that may occur while Dingo is processing test data, wrapping any other exception that may
 * occur within this RuntimeException so that calling code is not statically linked to any checked exceptions and is
 * therefore more concise and focussed on its particular role such as testing other code.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public class DingoException extends RuntimeException {

	/** The serialization version unique identifier. */
	private static final long serialVersionUID = 1L;

	/** The error message that describes this exception. */
	private String message;

	/** The original cause of this exception. */
	private Throwable cause;

	/**
	 * Creates a Dingo exception with the specified error message.
	 * 
	 * @param message
	 *            the error message.
	 */
	public DingoException(String message) {

		this.message = message;

		cause = new Exception(message);

	}

	/**
	 * Creates a Dingo exception with the specified original cause of this exception.
	 * 
	 * @param cause
	 *            the cause.
	 */
	public DingoException(Throwable cause) {

		this.cause = cause;

		message = cause.getMessage();

	}

	/**
	 * Yields the message associated with this exception.
	 * 
	 * @return the message.
	 */
	@Override
	public String getMessage() {

		return message;

	}

	/**
	 * Yields the original cause of this exception.
	 * 
	 * @return the cause.
	 */
	public Throwable getCause() {

		return cause;

	}

}