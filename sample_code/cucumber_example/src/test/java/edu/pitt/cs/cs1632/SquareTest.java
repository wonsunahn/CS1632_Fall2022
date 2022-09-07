package edu.pitt.cs.cs1632;
import static org.junit.Assert.*;
import org.mockito.*;

import org.junit.Test;

public class SquareTest {

	/*
	 * Preconditions: A new Square square is created
	 *                A new Number number is created
	 * Execution Steps: Call sq.setSquared(n, 3)
	 * PostConditions: The value of number is set to 9
	 */
	@Test
	public void testSetSquared() {
		Square square = new Square();
		Number number = Mockito.mock(Number.class);
		square.setSquared(number, 3);
		Mockito.verify(number).setVal(9);
	}

}
