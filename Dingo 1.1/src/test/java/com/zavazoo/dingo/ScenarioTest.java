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

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Tests the scenario component.
 * 
 * @author Chris White <chriswhitelondon@gmail.com>
 * @since JDK6
 */
public class ScenarioTest extends TestCase {

	/**
	 * Tests the equals operation.
	 */
	@Test
	public void testEquals() {

		Value firstResult = new Value("result1");
		Value[] firstCriteria = new Value[] { new Value("criteria1"), new Value("criteria2") };

		Value secondResult = new Value("result1");
		Value[] secondCriteria = new Value[] { new Value("criteria1"), new Value("criteria2") };

		Scenario firstScenario = new Scenario(firstResult, firstCriteria);
		Scenario secondScenario = new Scenario(secondResult, secondCriteria);

		assertTrue(firstScenario.equals(secondScenario));

	}

}