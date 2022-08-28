import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mockito.*;

import com.github.tkutche1.jgrade.*;
import com.github.tkutche1.jgrade.gradedtest.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SortedCollectionGrading {

	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private PrintStream stdout;

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	SortedCollection collection;

	@Before
	public void setUp() throws Exception {
		collection = new SortedCollection();
		stdout = System.out;
		try {
			System.setOut(new PrintStream(out, false, Charset.defaultCharset().toString()));
		} catch (UnsupportedEncodingException uex) {

		}
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(stdout);
	}

	@Test
	@GradedTest(name = "Test add 1 then remove once", points = 10)
	public void testAdd_1() {
		try {
			collection.add(1);
			assertEquals("[Error in remove() return value]", 1, collection.remove());
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			fail("Exception while testing: " + sw);
		}
	}

	@Test
	@GradedTest(name = "Test add 1, add 2 then remove twice", points = 10)
	public void testAdd_1_2() {
		try {
			collection.add(1);
			collection.add(2);
			assertEquals("[Error in 1st remove() return value]", 1, collection.remove());
			assertEquals("[Error in 2nd remove() return value]", 2, collection.remove());
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			fail("Exception while testing: " + sw);
		}
	}

	@Test
	@GradedTest(name = "Test add 1, add 2, add 3 then remove thrice", points = 10)
	public void testAdd_1_2_3() {
		try {
			collection.add(1);
			collection.add(2);
			collection.add(3);
			assertEquals("[Error in 1st remove() return value]", 1, collection.remove());
			assertEquals("[Error in 2nd remove() return value]", 2, collection.remove());
			assertEquals("[Error in 3rd remove() return value]", 3, collection.remove());
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			fail("Exception while testing: " + sw);
		}
	}

	@Test
	@GradedTest(name = "Test add 3, add 2, add 1 then remove thrice", points = 10)
	public void testAdd_3_2_1() {
		try {
			collection.add(3);
			collection.add(2);
			collection.add(1);
			assertEquals("[Error in 1st remove() return value]", 1, collection.remove());
			assertEquals("[Error in 2nd remove() return value]", 2, collection.remove());
			assertEquals("[Error in 3rd remove() return value]", 3, collection.remove());
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			fail("Exception while testing: " + sw);
		}
	}

	@Test
	@GradedTest(name = "Test add 1, add 3, add 2 then remove thrice", points = 10)
	public void testAdd_1_3_2() {
		try {
			collection.add(1);
			collection.add(3);
			collection.add(2);
			assertEquals("[Error in 1st remove() return value]", 1, collection.remove());
			assertEquals("[Error in 2nd remove() return value]", 2, collection.remove());
			assertEquals("[Error in 3rd remove() return value]", 3, collection.remove());
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			fail("Exception while testing: " + sw);
		}
	}

	@Test(expected = NoSuchElementException.class)
	@GradedTest(name = "Test remove once on an empty collection", points = 10)
	public void testRemoveOnEmpty() {
		collection.remove();
	}

	@Test
	@GradedTest(name = "Test main() with no args", points = 10)
	public void testMain() {
		String outputString = "";
		String[] args = {};

		SortedCollection.main(args);

		try {
			outputString = out.toString(Charset.defaultCharset().toString());
		} catch (UnsupportedEncodingException uex) {

		}
		assertEquals("Usage: java SortedCollection [num1] [num2] [num3] ...", outputString.trim());
		out.reset();
	}

	@Test
	@GradedTest(name = "Test main() with args: {3, 1, 2}", points = 10)
	public void testMain_3_1_2() {
		String outputString = "";
		String[] args = { "3", "1", "2" };

		SortedCollection.main(args);

		try {
			outputString = out.toString(Charset.defaultCharset().toString());
		} catch (UnsupportedEncodingException uex) {

		}
		assertEquals("sorted: 1 2 3", outputString.trim());
		out.reset();
	}

	@Test
	@GradedTest(name = "Test main() with args: {-3, -1, -2}", points = 10)
	public void testMain_minus3_minus1_minus2() {
		String outputString = "";
		String[] args = { "-3", "-1", "-2" };

		SortedCollection.main(args);

		try {
			outputString = out.toString(Charset.defaultCharset().toString());
		} catch (UnsupportedEncodingException uex) {

		}
		assertEquals("sorted: -3 -2 -1", outputString.trim());
		out.reset();
	}

	@Test
	@GradedTest(name = "Test main() with args: {a, b, c}", points = 10)
	public void testMain_a_b_c() {
		String outputString = "";
		String[] args = { "a", "b", "c" };

		SortedCollection.main(args);

		try {
			outputString = out.toString(Charset.defaultCharset().toString());
		} catch (UnsupportedEncodingException uex) {

		}
		assertEquals("Usage: java SortedCollection [num1] [num2] [num3] ...", outputString.trim());
		out.reset();
	}
}
