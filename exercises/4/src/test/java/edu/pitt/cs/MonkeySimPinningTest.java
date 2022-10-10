package edu.pitt.cs;

import java.io.*;
import java.lang.reflect.*;

import org.junit.*;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MonkeySimPinningTest {
	MonkeySim ms;
	MonkeyWatcher mw;
	List<Monkey> ml;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private PrintStream stdout;

	@Before
	public void setUp() {
		// Back up the old output stream
		stdout = System.out;
		// Redirect the output stream
		System.setOut(new PrintStream(out));

		// Force reset the monkey counter to 0 before initializing the test fixture, to
		// make sure the monkey numbering starts from 0 each time.
		try {
			Field f = Monkey.class.getDeclaredField("monkeyNum");
			f.setAccessible(true);
			f.set(null, 0);
		} catch (Exception e) {
			fail(e.toString());
		}

		int s = 5; // Starting monkey with the banana

		// Initialize the test fixture. Note that we are creating real objects, not mock
		// objects, even for external classes. Hence, we are de facto doing integration
		// testing instead of unit testing for the pinning tests.
		ms = new MonkeySim();
		ml = new LinkedList<Monkey>();
		Banana b = new Banana();
		mw = new MonkeyWatcher();
		for (int i = 0; i < s + 1; i++) {
			ml.add(new Monkey());
		}

		// Initially, throw the banana to Monkey 5
		ml.get(s).throwBananaTo(b);
	}

	@After
	public void tearDown() {
		System.setOut(stdout);
	}

	@Test
	public void testGetFirstMonkey() {
		Monkey m = ms.getFirstMonkey(ml);
		assertNotNull("getFirstMonkey returns null", m);
		assertEquals("getFirstMonkey returns a monkey with monkey != 1", 1, m.getMonkeyNum());
	}

	@Test
	public void testNextMonkeyAndResizeTo16() {
		ms.nextMonkeyAndResize(ml.get(5), ml);
		assertEquals("Monkey list size not 17 after resizing to monkey 16 (5*3 + 1)", 17, ml.size());
	}

	@Test
	public void testStringifyResults() {
		String ret = ms.stringifyResults(5, ml.get(2), ml.get(1));
		assertEquals("Defect when stringifying round 5, monkey 2, monkey 1",
				"//Round 5: Threw banana from Monkey (#2 / ID 223494) to Monkey (#1 / ID 223493)", ret);
	}

	@Test
	public void testMonkeyWithBanana() {
		int ret = ms.monkeyWithBanana(ml);
		assertEquals(5, ret);
	}

	@Test
	public void testArgument5RunSimulation() {
		ms.runSimulation(ml, mw);
		String nl = System.getProperty("line.separator");
		assertEquals("Defect in the output for running simulation with argument 5",
				"//Round 1: Threw banana from Monkey (#5 / ID 223497) to Monkey (#16 / ID 223508)" + nl
						+ "//Round 2: Threw banana from Monkey (#16 / ID 223508) to Monkey (#8 / ID 223500)" + nl
						+ "//Round 3: Threw banana from Monkey (#8 / ID 223500) to Monkey (#4 / ID 223496)" + nl
						+ "//Round 4: Threw banana from Monkey (#4 / ID 223496) to Monkey (#2 / ID 223494)" + nl
						+ "//Round 5: Threw banana from Monkey (#2 / ID 223494) to Monkey (#1 / ID 223493)" + nl
						+ "First monkey has the banana!" + nl,
				out.toString());
	}
}