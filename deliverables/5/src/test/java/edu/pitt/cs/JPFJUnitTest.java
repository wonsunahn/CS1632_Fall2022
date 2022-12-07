package edu.pitt.cs;

import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.nasa.jpf.vm.Verify;

import static org.junit.Assert.*;

/**
 * Code by @author Wonsun Ahn
 * 
 * <p>
 * Uses the Java Path Finder model checking tool to check BeanCounterLogic in
 * various modes of operation. It checks BeanCounterLogic in both "luck" and
 * "skill" modes for various numbers of slots and beans. It also goes down all
 * the possible random path taken by the beans during operation.
 */

public class JPFJUnitTest {
	private static BeanCounterLogic logic; // The core logic of the program
	private static Bean[] beans; // The beans in the machine
	private static String failString; // A descriptive fail string for assertions

	private static int slotCount; // The number of slots in the machine we want to test
	private static int beanCount; // The number of beans in the machine we want to test
	private static boolean isLuck; // Whether the machine we want to test is in "luck" or "skill" mode

	/**
	 * Sets up the test fixture.
	 */
	@BeforeClass
	public static void setUp() {
		if (Config.getTestType() == TestType.JUNIT) {
			slotCount = 5;
			beanCount = 3;
			isLuck = true;
		} else if (Config.getTestType() == TestType.JPF_ON_JUNIT) {
			/*
			 * TODO: Use the Java Path Finder Verify API to generate choices for slotCount,
			 * beanCount, and isLuck: slotCount should take values 1-5, beanCount should
			 * take values 0-3, and isLucky should be either true or false. For reference on
			 * how to use the Verify API, look at:
			 * https://github.com/javapathfinder/jpf-core/wiki/Verify-API-of-JPF
			 */
		} else {
			assert (false);
		}

		// Create the internal logic
		logic = BeanCounterLogic.createInstance(slotCount);
		// Create the beans
		beans = new Bean[beanCount];
		for (int i = 0; i < beanCount; i++) {
			beans[i] = Bean.createInstance(slotCount, isLuck, new Random(42));
		}

		// A failstring useful to pass to assertions to get a more descriptive error.
		failString = "Failure in (slotCount=" + slotCount
				+ ", beanCount=" + beanCount + ", isLucky=" + isLuck + "):";
	}

	@AfterClass
	public static void tearDown() {
	}

	/**
	 * Test case for void reset(Bean[] beans).
	 * 
	 * <pre>
	 * Preconditions: logic has been initialized with an instance of BeanCounterLogic.
	 *                beans has been initialized with an array of Bean objects.
	 * Execution steps: Call logic.reset(beans).
	 * Invariants: If beanCount is greater than 0,
	 *             remaining bean count is beanCount - 1
	 *             in-flight bean count is 1 (the bean initially at the top)
	 *             in-slot bean count is 0.
	 *             If beanCount is 0,
	 *             remaining bean count is 0
	 *             in-flight bean count is 0
	 *             in-slot bean count is 0.
	 * </pre>
	 */
	@Test
	public void testReset() {
		// TODO: Implement
		/*
		 * Currently, it just prints out the failString to demonstrate to you all the
		 * cases considered by Java Path Finder. If you called the Verify API correctly
		 * in setUp(), you should see all combinations of machines
		 * (slotCount/beanCount/isLucky) printed here:
		 * 
		 * Failure in (slotCount=1, beanCount=0, isLucky=false):
		 * Failure in (slotCount=1, beanCount=0, isLucky=true):
		 * Failure in (slotCount=1, beanCount=1, isLucky=false):
		 * Failure in (slotCount=1, beanCount=1, isLucky=true):
		 * ...
		 * 
		 * PLEASE REMOVE when you are done implementing.
		 */
		System.out.println(failString);
	}

	/**
	 * Test case for boolean advanceStep().
	 * 
	 * <pre>
	 * Preconditions: logic has been initialized with an instance of BeanCounterLogic.
	 *                beans has been initialized with an array of Bean objects.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After each advanceStep(),
	 *             getInFlightBeanXPos(ypos) for all rows in machine returns a legal xpos.
	 *             (For example, getInFlightBeanXPos(0) can either return 0 or BeanCounterLogic.NO_BEAN_IN_YPOS,
	 *              and getInFlightBeanXPos(1) can return 0, 1, or BeanCounterLogic.NO_BEAN_IN_YPOS.  And so on.)
	 * </pre>
	 */
	@Test
	public void testAdvanceStepCoordinates() {
		// TODO: Implement
	}

	/**
	 * Test case for boolean advanceStep().
	 * 
	 * <pre>
	 * Preconditions: logic has been initialized with an instance of BeanCounterLogic.
	 *                beans has been initialized with an array of Bean objects.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After each advanceStep(),
	 *             the sum of remaining, in-flight, and in-slot beans is equal to beanCount.
	 * </pre>
	 */
	@Test
	public void testAdvanceStepBeanCount() {
		// TODO: Implement
	}

	/**
	 * Test case for boolean advanceStep().
	 * 
	 * <pre>
	 * Preconditions: logic has been initialized with an instance of BeanCounterLogic.
	 *                beans has been initialized with an array of Bean objects.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After the machine terminates,
	 *             remaining bean count is 0
	 *             in-flight bean count is 0
	 *             in-slot bean count is beanCount.
	 * </pre>
	 */
	@Test
	public void testAdvanceStepPostCondition() {
		// TODO: Implement
	}

	/**
	 * Test case for void lowerHalf()().
	 * 
	 * <pre>
	 * Preconditions: logic has been initialized with an instance of BeanCounterLogic.
	 *                beans has been initialized with an array of Bean objects.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * 	                Calculate expected bean counts for each slot after having called logic.lowerHalf(),
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
	 * Test case for void upperHalf().
	 * 
	 * <pre>
	 * Preconditions: logic has been initialized with an instance of BeanCounterLogic.
	 *                beans has been initialized with an array of Bean objects.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Calculate expected bean counts for each slot after having called logic.upperHalf(),
	 *                  from current slot bean counts, and store into an expectedSlotCounts array.
	 *                  (The total count should be N/2 or (N+1)/2 depending on whether N is even or odd,
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
	 * Test case for void repeat().
	 * 
	 * <pre>
	 * Preconditions: logic has been initialized with an instance of BeanCounterLogic.
	 *                beans has been initialized with an array of Bean objects.
	 * Execution steps: If beans are created in skill mode (if isLuck is false),
	 *                  Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Construct an expectedSlotCounts array that stores current bean counts for each slot.
	 *                  Call logic.repeat();
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Construct an observedSlotCounts array that stores current bean counts for each slot.
	 * Invariants: expectedSlotCounts matches observedSlotCounts exactly.
	 * </pre>
	 */
	@Test
	public void testRepeat() {
		if (!isLuck) {
			// TODO: Implement
		}
	}
}
