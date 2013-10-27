Dingo
Data-Driven Testing

    Dingo enables the parameterization of tests with structured data comprising a range of criteria, for consumption by the component being tested, and an expected result for comparison with the actual outcome of the test

    Test methods can easily request test scenarios from Dingo and then iterate over these scenarios to invoke the component being tested with a much wider range of test data than would otherwise be practical to hand-code in Java

    Supports the definition of test data as strings, characters and booleans, signed integers and floating-point numbers, ISO-8601 date/times and JSON strings to express lists and maps and bespoke data structures, defined within ordinary text files on the class path

    Ultimately provides a lightweight and non-intrusive alternative to Cucumber JVM, that also enables parameterization of tests, without the overhead of an extensive BDD framework and runtime environment

    Dingo is released under the GNU General Public License

Introduction
Current Methods

Test-Driven Development currently dictates that the test class must comprise all the functionality and data required to run the test. This mandate aims to ensure that test classes remain simple and transparent to avoid mistakes in developing components which are test classes and therefore cannot themselves be tested. However, due to this constraint, test classes often become very large and cumbersome with extensive repetition of trivial code required to instantiate test data.

Let us consider a simple component that implements a method which produces a list of the values comprised by a given map:

package com.zavazoo.dingo.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Component {

	public static List<Object> values(Map<String, Object> map) {

		List<Object> list = new LinkedList<Object>();

		for (Map.Entry<String, Object> entry : map.entrySet()) {

			Object value = entry.getValue();

			list.add(value);

		}

		return list;

	}

}

This component will be complemented with a test class that invokes the Component.values method with various permutations of parameter values:

package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.junit.Test;

public class ComponentTest extends TestCase {

	@Test
	public void testOneValue() {

		List<Object> expected = new LinkedList<Object>();
		expected.add("value");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", "value");

		List<Object> actual = Component.values(map);

		assertThat(actual, containsInAnyOrder(expected.toArray()));

	}

	@Test
	public void testTwoValues() {

		List<Object> expected = new LinkedList<Object>();
		expected.add("apple");
		expected.add("tomato");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fruit", "apple");
		map.put("vegetable", "tomato");

		List<Object> actual = Component.values(map);

		assertThat(actual, containsInAnyOrder(expected.toArray()));

	}

}

The test class above comprises 14 lines of functional code which tests only 2 permutations of parameter values invoking a component method that itself comprises only 4 lines of functional code. In order to test 10 permutations of parameter values, and thereby create an extensive test, the test class would need to comprise around 100 lines of code that mostly repeats the same trivial task of creating and populating maps and lists with test data.

Data-Driven Testing

Dingo aims to separate the concerns of data and functionality resulting with more concise and transparent test classes, and more concise and readable test data comprised by separate text files. The test class produced in the previous section would be expressed using Dingo as follows:

package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.junit.Test;

import com.zavazoo.dingo.Dingo;
import com.zavazoo.dingo.Scenario;
import com.zavazoo.dingo.Scenarios;

public class ComponentTest extends TestCase {

	@Test
	public void testValues() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/values");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			List<Object> expected = scenario.result.list();

			Map<String, Object> map = scenario.criteria[0].map();

			List<Object> actual = Component.values(map);

			assertThat(actual, containsInAnyOrder(expected.toArray()));

		}

	}

}

Dingo will open the test data file, that corresponds to the specified logical name; in this case com/zavazoo/dingo/example/values, that comprises the following contents:

["value"]	{"key": "value"}
["apple", "tomato"]	{"fruit": "apple", "vegetable": "tomato"}

The test method subsequently utilises a more / next strategy to iterate over each test scenario, where a given scenario corresponds to one line of the test data file, and finally the test method obtains the expected result of the test, and also the criteria used to invoke the test, using helper methods such as list() or map() exposed by composite members of the Dingo scenario object.

After that the test method is free to perform whatever operations are required to carry out the test. For example some of the criteria may be used to establish an environment for the test such as start-up configuration or logging-in to a database. Dingo does not place any constraint on how the scenario result or criteria are used by the test class.

Test Scenarios

Let us examine various excerpts from the Dingo test class defined above starting with the initial call to Dingo that opens the test data file:

Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/values");

This call to Dingo.scenarios will yield an abstraction over all the test scenarios associated with the specified logical name that corresponds to a file comprising test data located under the top-level Maven project directory named src/test/resources

For example the test data file located at src/test/resources/values.tsv would be associated with a logical name of values and the test data file located at src/test/resources/com/zavazoo/dingo/example/values.tsv would be associated with a logical name of com/zavazoo/dingo/example/values

Note that the logical name does not include the file name extension of the test data file, such as tsv or csv, because Dingo test classes are decoupled from the particular file format of the test data file. Dingo will automatically search for test data files which match the logical name regardless of the file name extension.

For non-Maven projects, that may not define a src/test/resources directory, the test data file can be placed under your source directory within the Java package that reflects the logical name. The test data file must be subsequently included alongside the compiled Java class files during the build process and thereby included in the class path for the JVM in which the tests are running.

Note that some IDEs including Eclipse will execute JUnit tests natively within the IDE. The directory that comprises the test data files, such as src/test/resources for Maven projects, should be defined as a source folder so that the IDE will include the test data files on the class path when executing JUnit. In Eclipse you may navigate to the src/test/resources directory, visible within the Project Explorer view, and right-click on that directory and then click Build Path -> Use as Source Folder.

Test Data

The test data file may be authored in tab-separated, comma-separated or bar-separated variable format. The example test class defined above utilises test data defined as collections where the test scenario criteria, that will be used to invoke the component being tested, and the test scenario result, that will be matched against the actual return value from the component being tested, are represented by a map and list respectively.

Dingo supports the definition of test data, comprised by test data files, as plain strings which may represent snippets of text, signed integers and floating-point numbers, ISO-8601 date/times, and boolean values of true or false. Dingo also supports the definition of test data using JSON so that lists and maps, and even more bespoke data structures, may be expressed within test data files. The example test class defined above utilises the test data file in tab-separated variable format, where the format is specified using the file name extension of tsv, located at src/test/resources/com/zavazoo/dingo/example/values.tsv which may have the following contents:

["value"]	{"key": "value"}
["apple", "tomato"]	{"fruit": "apple", "vegetable": "tomato"}

Dingo alternatively supports comma-separated variable format when the file name extension of the test data file is csv and may have the following contents:

result1, criteria1-1, criteria1-2
result2, criteria2-1, criteria2-2

Finally Dingo supports bar-separated variable format when the file name extension of the test data file is bsv and may have the following contents:

result1 | criteria1-1 | criteria1-2
result2 | criteria2-1 | criteria2-2

Note that Dingo expects the test scenario result to be defined as the first column of any given row. All subsequent columns are regarded as test scenario criteria and there is no limit to the number of criteria which may be defined for any row in a test data file.

Furthermore Dingo does not place any constraint on the relative number of criteria defined for any given row. Some rows may define more criteria than others, within the same test data file, and some rows may define zero criteria, while other rows define many criteria, but the first column of any given row must define the expected result of the test even if the result is an empty string or some other placeholder.

Scenario Iteration

Dingo provides a simple way to iterate over test scenarios;

while (scenarios.more()) {

	Scenario scenario = scenarios.next();

}

This more / next strategy is used, in preference to list iteration, so that Dingo can optimise the reading and parsing of lines from the test data file. Dingo will only perform the work involved to read and parse the next line when the scenarios.next() method is invoked by the test class.

Result and Criteria

Dingo provides two members of the Scenario class named result and criteria. The Scenario.result member references a Dingo Value object that represents the result column of the current row as defined in the test data file. The Scenario.criteria member references an array of Dingo Value objects which represent the remaining columns of the current row used to define the criteria of the test.

The Dingo Value object provides a range of methods which may be called by the test class to obtain the test data value as a wide range of different types:

/**
 * Constitutes a value of some test data, providing easy access to a wide range of value types so that calling code may
 * avoid parsing string values to other types.
 */
public class Value {

	/**
	 * Yields this value as a string.
	 * 
	 * @return the string.
	 */
	public String string();

	/**
	 * Yields this value as a character.
	 * 
	 * @return the character.
	 */
	public char character();

	/**
	 * Yields this value as a boolean.
	 * 
	 * @return the boolean.
	 */
	public boolean truth();

	/**
	 * Yields this value as an integer.
	 * 
	 * @return the integer.
	 */
	public int integer();

	/**
	 * Yields this value as a short integer.
	 * 
	 * @return the short integer.
	 */
	public short shortint();

	/**
	 * Yields this value as a long integer.
	 * 
	 * @return the long integer.
	 */
	public long longint();

	/**
	 * Yields this value as a short floating-point number.
	 * 
	 * @return the short floating-point number.
	 */
	public float floating();

	/**
	 * Yields this value as a long floating-point number.
	 * 
	 * @return the long floating-point number.
	 */
	public double doubleint();

	/**
	 * Yields this value as a big decimal to help with precise calculations.
	 * 
	 * @return the big decimal.
	 */
	public BigDecimal precise();

	/**
	 * This value as a Joda date/time object that is compatible with the ISO-8601 standard.
	 * 
	 * @return the Joda date/time object.
	 */
	public org.joda.time.DateTime datetime();

	/**
	 * Yields this value as a list created using raw/untyped JSON binding.
	 * 
	 * @return the list.
	 */
	public List<Object> list();

	/**
	 * Yields this value as a list created using JSON data binding with generics.
	 * 
	 * @param type
	 *            the type of each list entry.
	 * @return the list.
	 */
	public <EntryType> List<EntryType> list(Class<EntryType> type);

	/**
	 * Yields this value as a map created using raw/untyped JSON binding.
	 * 
	 * @return the map.
	 */
	public Map<String, Object> map();

	/**
	 * Yields this value as a map created using JSON data binding with generics.
	 * 
	 * @param keyType
	 *            the type of the key of each map entry.
	 * @param valueType
	 *            the type of the value of each map entry.
	 * @return the map.
	 */
	public <KeyType, ValueType> Map<KeyType, ValueType>
		map(Class<KeyType> keyType, Class<ValueType> valueType);

	/**
	 * Yields this value as an arbitrary object created using JSON full data binding.
	 * 
	 * @param type
	 *            the type of the object.
	 * @return the object.
	 */
	public <ObjectType> ObjectType object(Class<ObjectType> type);

}

Working Examples

This section provides examples of various forms of test data and the commensurate test classes which could be developed to access and utilise the test data.

Working with Strings

The following test data located at the relative filesystem path src/test/resources/com/zavazoo/dingo/example/city-suburbs.csv lists three suburbs of various Cities:

London, Mayfair, Bloomsbury, Covent Garden
New York City, Manhattan, Brooklyn, Queens
Paris, Neuilly-sur-Seine, Arrondissement de Passy, Île Saint-Louis

The test data may be used to assert the validity of an operation named CityFinder.matchCityForSuburbs that matches and yields the name of a City given the names of three suburbs of that City:

package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import junit.framework.TestCase;
import org.junit.Test;

import com.zavazoo.dingo.Dingo;
import com.zavazoo.dingo.Scenario;
import com.zavazoo.dingo.Scenarios;

public class CityFinderTest extends TestCase {

	@Test
	public void testMatchCityForSuburbs() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/city-suburbs");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			String expected = scenario.result.string();

			String firstSuburb = scenario.criteria[0].string();
			String secondSuburb = scenario.criteria[1].string();
			String thirdSuburb = scenario.criteria[2].string();

			String actual = CityFinder.matchCityForSuburbs(firstSuburb, secondSuburb, thirdSuburb);

			assertThat(actual, equalTo(expected));

		}

	}

}

Working with Numbers

The following test data located at the relative filesystem path src/test/resources/com/zavazoo/dingo/example/algebra.bsv lists the variables for an equation that adds the first criterion to zero, then subtracts the second criterion, then divides by the third criterion and finally multiplies by the fourth criterion, calculating the expected result defined in the first column of each row of the test data, assuming that the first criterion is always an integer but all other values may be floating-point numbers:

0.5	|	4	|	3	|	2	|	1
-7.5	|	5	|	10	|	20	|	30
14	|	20	|	10	|	2.5	|	3.5

The test data may be used to assert the validity of an operation named Equations.addSubtractDivideMultiply that performs the calculation and yields the result:

package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import junit.framework.TestCase;
import org.junit.Test;

import com.zavazoo.dingo.Dingo;
import com.zavazoo.dingo.Scenario;
import com.zavazoo.dingo.Scenarios;

public class EquationsTest extends TestCase {

	@Test
	public void testAddSubtractDivideMultiply() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/algebra");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			BigDecimal expected = scenario.result.precise();

			int add = scenario.criteria[0].integer();
			BigDecimal subtract = scenario.criteria[1].precise();
			BigDecimal divide = scenario.criteria[2].precise();
			BigDecimal multiply = scenario.criteria[3].precise();

			BigDecimal actual = Equations.addSubtractDivideMultiply(add, subtract, divide, multiply);

			actual = actual.stripTrailingZeros();

			assertThat(actual, equalTo(expected));

		}

	}

}

The reader will note that the Dingo Value object provides methods to obtain test data as a variety of primitive numeric types, such as float or double, however it is advisable to obtain a BigDecimal, by calling the Value.precise() method, to ensure precision when working with floating-point numbers.

Working with ISO Date/Times

Dingo fully supports ISO-8601 date and time formats using the Joda-Time API. The following test data located at the relative filesystem path src/test/resources/com/zavazoo/dingo/example/add-one-year.tsv lists moments in time, defined as the only criterion in the second column of each row, and the moment in time that will occur exactly one year later, defined as the expected result in the first column of each row:

2005-12-13T21:30:45.618-08:00	2004-12-13T21:30:45.618-08:00
2010-04-28T07:10:28.000+02:00	2009-04-28T07:10:28.000+02:00
2016-11-09T18:13:54.999+00:00	2015-11-09T18:13:54.999+00:00

The test data may be used to assert the validity of an operation named TimeFunctions.addOneYear that adds one year to a given date/time and yields the result:

package com.zavazoo.dingo.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import junit.framework.TestCase;
import org.junit.Test;

import org.joda.time.DateTime;

import com.zavazoo.dingo.Dingo;
import com.zavazoo.dingo.Scenario;
import com.zavazoo.dingo.Scenarios;

public class TimeFunctionsTest extends TestCase {

	@Test
	public void testAddOneYear() {

		Scenarios scenarios = Dingo.scenarios("com/zavazoo/dingo/example/add-one-year");

		while (scenarios.more()) {

			Scenario scenario = scenarios.next();

			DateTime expected = scenario.result.datetime();

			DateTime dateTime = scenario.criteria[0].datetime();

			DateTime actual = TimeFunctions.addOneYear(dateTime);

			assertThat(actual, equalTo(expected));

		}

	}

}

© 2010-2013 Chris White. All rights reserved
