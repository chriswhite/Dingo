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

/**
 * Enumerates all data file types recognised by Dingo.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
enum DataFileType {

	TSV("tsv", "\t"), CSV("csv", ","), BSV("bsv", "|");

	/** The file name extension. */
	private String extension;

	/** The data column delimiter. */
	private String delimiter;

	/**
	 * Creates a data file type associated with the specified file name extension and data column delimiter.
	 * 
	 * @param extension
	 *            the file name extension.
	 * @param delimiter
	 *            the data column delimiter.
	 */
	private DataFileType(String extension, String delimiter) {

		this.extension = extension;
		this.delimiter = delimiter;

	}

	/**
	 * Yields the file name extension associated with this data file type.
	 * 
	 * @return the file name extension.
	 */
	public String getExtension() {

		return extension;

	}

	/**
	 * Yields the data column delimiter associated with this data file type.
	 * 
	 * @return the data column delimiter.
	 */
	public String getDelimiter() {

		return delimiter;

	}

}