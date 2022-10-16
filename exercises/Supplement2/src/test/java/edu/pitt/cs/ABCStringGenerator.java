package edu.pitt.cs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.pholser.junit.quickcheck.generator.*;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class ABCStringGenerator extends Generator<String> {
	public ABCStringGenerator() {
		super(String.class);
	}

	/**
	 * Overridden generate method. Generates a string that is status.size() bytes
	 * long. The string is randomly filled with 'A's, 'B's, or 'C's.
	 * 
	 * @param random QuickCheck random number generator (similar to
	 *               java.util.Random)
	 * @param status An object that can be used to influence the generated value.
	 *               For example, status.size() returns a value that
	 *               (probabilistically) increases for every successful string
	 *               generation. By using status.size() as the generated string
	 *               length, the generator starts with simple strings and
	 *               progressively tests longer strings.
	 * @return Generated random string
	 */
	@Override
	public String generate(SourceOfRandomness random, GenerationStatus status) {
		int[] codePoints = new int[status.size()];

		for (int i = 0; i < codePoints.length; ++i) {
			codePoints[i] = random.nextInt('A', 'C');
		}

		return new String(codePoints, 0, codePoints.length);
	}

	/**
	 * Overridden doShrink method. Returns a list of smaller strings to test, in the
	 * event that the larger string results in a test failure. If any of the smaller
	 * strings fail, QuickCheck shrinks the strings further by recursively calling
	 * the doShrink method. In this way, the original string is shrunk to the
	 * smallest substring that triggers the defect. The smaller string makes
	 * debugging much easier.
	 * 
	 * @param random QuickCheck random number generator (similar to
	 *               java.util.Random)
	 * @param larger The original larger string that you want to shrink to one or
	 *               more smaller strings
	 * @return List of shrunk smaller strings
	 */
	@Override
	public List<String> doShrink(SourceOfRandomness random, String larger) {
		if (larger.length() == 0)
			return Collections.emptyList();

		// In this case, the string is shrunk simply by chopping it in half.
		// Both the left and right half are added to the list of strings to check.
		List<String> list = new ArrayList<>();
		list.add(larger.substring(0, larger.length() / 2));
		list.add(larger.substring(larger.length() / 2));
		return list;
	}
}