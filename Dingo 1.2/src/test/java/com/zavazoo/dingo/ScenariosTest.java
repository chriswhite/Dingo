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

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Tests the scenarios component.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public class ScenariosTest extends TestCase {

	/**
	 * Tests the various permutations of calling the <code>more</code> and <code>next</code> operations when the test
	 * data file is empty.
	 */
	@Test
	public void testEmptyScenarios() {

		DataFile dataFile = new DataFile("com/zavazoo/dingo/empty-scenarios", null);
		dataFile.openFile();

		Scenarios scenarios = new Scenarios(dataFile);

		assertFalse(scenarios.more());
		assertFalse(scenarios.more());
		assertFalse(scenarios.more());

		dataFile = new DataFile("com/zavazoo/dingo/empty-scenarios", null);
		dataFile.openFile();

		scenarios = new Scenarios(dataFile);

		assertNull(scenarios.next());
		assertNull(scenarios.next());
		assertNull(scenarios.next());

		dataFile = new DataFile("com/zavazoo/dingo/empty-scenarios", null);
		dataFile.openFile();

		scenarios = new Scenarios(dataFile);

		assertFalse(scenarios.more());
		assertNull(scenarios.next());
		assertFalse(scenarios.more());
		assertNull(scenarios.next());

		dataFile = new DataFile("com/zavazoo/dingo/empty-scenarios", null);
		dataFile.openFile();

		scenarios = new Scenarios(dataFile);

		assertNull(scenarios.next());
		assertFalse(scenarios.more());
		assertNull(scenarios.next());
		assertFalse(scenarios.more());

	}

	/**
	 * Tests the various permutations of calling the <code>more</code> and <code>next</code> operations when the test
	 * data file comprises three scenarios.
	 */
	@Test
	public void testThreeScenarios() {

		DataFile dataFile = new DataFile("com/zavazoo/dingo/three-scenarios", null);
		dataFile.openFile();

		Scenarios scenarios = new Scenarios(dataFile);

		assertTrue(scenarios.more());
		assertTrue(scenarios.more());
		assertTrue(scenarios.more());
		assertTrue(scenarios.more());
		assertTrue(scenarios.more());
		assertTrue(scenarios.more());

		dataFile = new DataFile("com/zavazoo/dingo/three-scenarios", null);
		dataFile.openFile();

		scenarios = new Scenarios(dataFile);

		assertNotNull(scenarios.next());
		assertNotNull(scenarios.next());
		assertNotNull(scenarios.next());
		assertNull(scenarios.next());
		assertNull(scenarios.next());
		assertNull(scenarios.next());

		dataFile = new DataFile("com/zavazoo/dingo/three-scenarios", null);
		dataFile.openFile();

		scenarios = new Scenarios(dataFile);

		assertTrue(scenarios.more());
		assertNotNull(scenarios.next());
		assertTrue(scenarios.more());
		assertNotNull(scenarios.next());
		assertTrue(scenarios.more());
		assertNotNull(scenarios.next());
		assertFalse(scenarios.more());
		assertNull(scenarios.next());
		assertFalse(scenarios.more());
		assertNull(scenarios.next());

		dataFile = new DataFile("com/zavazoo/dingo/three-scenarios", null);
		dataFile.openFile();

		scenarios = new Scenarios(dataFile);

		assertNotNull(scenarios.next());
		assertTrue(scenarios.more());
		assertNotNull(scenarios.next());
		assertTrue(scenarios.more());
		assertNotNull(scenarios.next());
		assertFalse(scenarios.more());
		assertNull(scenarios.next());
		assertFalse(scenarios.more());
		assertNull(scenarios.next());

		dataFile = new DataFile("com/zavazoo/dingo/three-scenarios", null);
		dataFile.openFile();

		scenarios = new Scenarios(dataFile);

		assertTrue(scenarios.more());
		assertTrue(scenarios.more());
		assertTrue(scenarios.more());
		assertTrue(scenarios.more());
		assertTrue(scenarios.more());
		assertNotNull(scenarios.next());
		assertTrue(scenarios.more());
		assertNotNull(scenarios.next());
		assertNotNull(scenarios.next());
		assertFalse(scenarios.more());
		assertFalse(scenarios.more());
		assertFalse(scenarios.more());
		assertNull(scenarios.next());

	}

}