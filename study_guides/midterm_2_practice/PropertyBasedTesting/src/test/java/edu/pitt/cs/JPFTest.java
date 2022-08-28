package edu.pitt.cs;

import static org.junit.Assert.*;

import java.util.Random;

import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import gov.nasa.jpf.vm.Verify;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JPFTest {
	private static int x;
	private static int y;
	
	@BeforeClass
	public static void setUp() {
		// TODO: Fill in.  Test all x, y where -10 <= x <= 10 and -10 <= y <= 10.
	}

	@Test
	public void testSquare() {
		 int ret = IntegerOps.square(x);
		 // TODO: Fill in.
	}

	@Test
	public void testAdd() {
		int ret = IntegerOps.add(x, y);
		// TODO: Fill in.
	}
	
}