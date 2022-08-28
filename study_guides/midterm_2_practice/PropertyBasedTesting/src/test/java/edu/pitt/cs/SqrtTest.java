package edu.pitt.cs;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.*;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class SqrtTest {
	@Property
	public void testSqrt(@InRange(minDouble = 0, maxDouble = 25) double d) {
		double ret = Math.sqrt(d);
		// TODO: Fill in
	}
}