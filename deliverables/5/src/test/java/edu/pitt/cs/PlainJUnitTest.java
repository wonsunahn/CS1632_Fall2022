package edu.pitt.cs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
		    value = "UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR", 
		    justification = "SpotBugs apparently does not know about @Before methods so "
			+ "it keeps complaining about the logics field not getting initialized in constructor")

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlainJUnitTest {

	private final int[] beanCounts = { 0, 2, 20, 200 };
	private final int[] logicSlotCounts = { 1, 10, 20 };
	private BeanCounterLogic[] logics;
	private Random rand;

	ByteArrayOutputStream out;
	PrintStream stdout;

	private String getFailString(int logicIndex, int beanCount) {
		return "[Slot Count = " + logicSlotCounts[logicIndex] + "] Test case with " + beanCount
				+ " initial beans failed";
	}

	private Bean[] createBeans(int slotCount, int beanCount, boolean luck) {
		Bean[] beans = new Bean[beanCount];
		for (int i = 0; i < beanCount; i++) {
			beans[i] = Bean.createInstance(slotCount, luck, rand);
		}
		return beans;
	}

	private int getInFlightBeanCount(BeanCounterLogic logic, int slotCount) {
		int inFlight = 0;
		for (int yPos = 0; yPos < slotCount; yPos++) {
			int xPos = logic.getInFlightBeanXPos(yPos);
			if (xPos != BeanCounterLogic.NO_BEAN_IN_YPOS) {
				inFlight++;
			}
		}
		return inFlight;
	}

	private int getInSlotsBeanCount(BeanCounterLogic logic, int slotCount) {
		int inSlots = 0;
		for (int i = 0; i < slotCount; i++) {
			inSlots += logic.getSlotBeanCount(i);
		}
		return inSlots;
	}

	/**
	 * The test fixture. Creates multiple machines (logics) with different slot
	 * counts. It also creates a real random object. But the random object is seeded
	 * with 42 so the tests will be reproducible.
	 */
	@Before
	public void setUp() {
		logics = new BeanCounterLogic[logicSlotCounts.length];
		for (int i = 0; i < logics.length; i++) {
			logics[i] = BeanCounterLogic.createInstance(logicSlotCounts[i]);
		}
		rand = new Random(42);

		out = new ByteArrayOutputStream();
		stdout = System.out;
		try {
			System.setOut(new PrintStream(out, false, Charset.defaultCharset().toString()));
		} catch (UnsupportedEncodingException uex) {
			fail();
		}
	}

	@After
	public void tearDown() {
		logics = null;
		rand = null;
		out = null;

		System.setOut(stdout);
	}

	/**
	 * Test reset(Bean[]).
	 * 
	 * <pre>
	 * Preconditions: logics for each slot count in logicSlotCounts are created.
	 * Execution steps: For each logic, and for each bean count in beanCounts,
	 *                  Call createBeans to create lucky beans for the slot count and bean count
	 *                  Call logic.reset(beans). 
	 * Invariants: If initial bean count is greater than 0,
	 *             remaining bean count is beanCount - 1
	 *             in-flight bean count is 1 (the bean initially at the top)
	 *             in-slot bean count is 0.
	 *             If initial bean count is 0,
	 *             remaining bean count is 0
	 *             in-flight bean count is 0
	 *             in-slot bean count is 0.
	 * </pre>
	 */
	@Test
	public void testReset() {
		for (int i = 0; i < logics.length; i++) {
			for (int beanCount : beanCounts) {
				String failString = getFailString(i, beanCount);
				Bean[] beans = createBeans(logicSlotCounts[i], beanCount, true);
				logics[i].reset(beans);

				int remainingObserved = logics[i].getRemainingBeanCount();
				int inFlightObserved = getInFlightBeanCount(logics[i], logicSlotCounts[i]);
				int inSlotsObserved = getInSlotsBeanCount(logics[i], logicSlotCounts[i]);
				int remainingExpected = (beanCount > 0) ? beanCount - 1 : 0;
				int inFlightExpected = (beanCount > 0) ? 1 : 0;
				int inSlotsExpected = 0;

				assertEquals(failString + ". Check on remaining bean count",
						remainingExpected, remainingObserved);
				assertEquals(failString + ". Check on in-flight bean count",
						inFlightExpected, inFlightObserved);
				assertEquals(failString + ". Check on in-slot bean count",
						inSlotsExpected, inSlotsObserved);
			}
		}
	}

	/**
	 * Test advanceStep() in luck mode.
	 * 
	 * <pre>
	 * Preconditions: logics for each slot count in logicSlotCounts are created.
	 * Execution steps: For each logic, and for each bean count in beanCounts,
	 *                  Call createBeans to create lucky beans for the slot count and bean count
	 *                  Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After each advanceStep(),
	 *             1) The remaining bean count, 2) the in-flight bean count, and 3) the in-slot bean count
	 *             all reflect the correct values at that step of the simulation.
	 * </pre>
	 */
	@Test
	public void testAdvanceStepLuckMode() {
		// TODO: Implement
	}

	/**
	 * Test advanceStep() in skill mode.
	 * 
	 * <pre>
	 * Preconditions: logics for each slot count in logicSlotCounts are created.
	 *                rand.nextGaussian() always returns 0 (to fix skill level to 5).
	 * Execution steps: For the logic with 10 slot counts,
	 *                  Call createBeans to create 200 skilled beans
	 *                  Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After the machine terminates,
	 *             logics.getSlotBeanCount(5) returns 200,
	 *             logics.getSlotBeanCount(i) returns 0, where i != 5.
	 * </pre>
	 */
	@Test
	public void testAdvanceStepSkillMode() {
		// TODO: Implement
	}

	/**
	 * Test lowerHalf() in luck mode.
	 * 
	 * <pre>
	 * Preconditions: logics for each slot count in logicSlotCounts are created.
	 * Execution steps: For the logic with 10 slot counts, and for each bean count in beanCounts,
	 *                  Call createBeans to create lucky beans for the slot count and bean count
	 *                  Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Calculate expected bean counts for each slot after having called logic.lowerHalf(),
	 *                  from current slot bean counts, and store into an expectedSlotCounts array.
	 *                  (The total count should be N/2 or (N+1)/2 depending on whether N is even or odd,
	 *                  where N is the original bean count.)
	 *                  Call logic.lowerHalf().
	 *                  Construct an observedSlotCounts array that stores current bean counts for each slot.
	 * Invariants: expectedSlotCounts matches observedSlotCounts exactly.
	 * </pre>
	 */
	@Test
	public void testLowerHalf() {
		// TODO: Implement
	}

	/**
	 * Test upperHalf() in luck mode.
	 * 
	 * <pre>
	 * Preconditions: logics for each slot count in logicSlotCounts are created.
	 * Execution steps: For the logic with 10 slot counts, and for each bean count in beanCounts,
	 *                  Call createBeans to create lucky beans for the slot count and bean count
	 *                  Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Calculate expected bean counts for each slot after having called logic.upperHalf(),
	 *                  from current slot bean counts, and store into an expectedSlotCounts array.
	 * 	                (The total count should be N/2 or (N+1)/2 depending on whether N is even or odd,
	 *                  where N is the original bean count.)
	 *                  Call logic.upperHalf().
	 *                  Construct an observedSlotCounts array that stores current bean counts for each slot.
	 * Invariants: expectedSlotCounts matches observedSlotCounts exactly.
	 * </pre>
	 */
	@Test
	public void testUpperHalf() {
		// TODO: Implement
	}

	/**
	 * Test repeat() in skill mode.
	 * 
	 * <pre>
	 * Preconditions: logics for each slot count in logicSlotCounts are created.
	 * Execution steps: For the logic with 10 slot counts, and for each bean count in beanCounts,
	 *                  Call createBeans to create skilled beans for the slot count and bean count
	 *                  Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Construct an expectedSlotCounts array that stores current bean counts for each slot.
	 *                  Call logic.repeat().
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Construct an observedSlotCounts array that stores current bean counts for each slot.
	 * Invariants: expectedSlotCounts matches observedSlotCounts exactly.
	 * </pre>
	 */
	@Test
	public void testRepeat() {
		// TODO: Implement
	}

	/**
	 * Test getAverageSlotBeanCount() in luck mode.
	 * 
	 * <pre>
	 * Preconditions: logics for each slot count in logicSlotCounts are created.
	 * Execution steps: For the logic with 10 slot counts,
	 *                  Call createBeans to create 200 lucky beans
	 *                  Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Store an expectedAverage, an average of the slot number for each bean.
	 *                  Store an observedAverage, the result of logic.getAverageSlotBeanCount().
	 *                  Store an idealAverage, which is 4.5.
	 * Invariants: {@literal Math.abs(expectedAverage - observedAverage) < 0.01}.
	 *             {@literal Math.abs(idealAverage - observedAverage) < 0.5}.
	 * </pre>
	 */
	@Test
	public void testGetAverageSlotBeanCount() {
		// TODO: Implement
	}

	/**
	 * Test main(String[] args).
	 * 
	 * <pre>
	 * Preconditions: None.
	 * Execution steps: Check invariants after either calling
	 *                  BeanCounterLogicImpl.main("10", "500", "luck"), or
	 *                  BeanCounterLogicImpl.main("10", "500", "skill").
	 * Invariants: There are two lines of output.
	 *             There are 10 slot counts on the second line of output.
	 *             The sum of the 10 slot counts is equal to 500.
	 * </pre>
	 */
	@Test
	public void testMain() {
		// TODO: Implement using out.toString() to get output stream
	}

}
