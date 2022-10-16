package edu.pitt.cs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.pholser.junit.quickcheck.generator.*;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class ValidHTMLStringGenerator extends Generator<String> {
	public ValidHTMLStringGenerator() {
		super(String.class);
	}

	/**
	 * Overridden generate method. Generates a string that is a random sequence of
	 * nested "<b>", "</b>", "<i>", and "</i>" tags. The number of tag pairs in the
	 * sequence is equal to status.size() + 10. The nested structure is guaranteed
	 * using a LIFO stack such that an end tag (</b> or </i>) is always matched with
	 * the most recent start tag (<b> or <i>). Tags are generated using a
	 * psuedo-random number generator.
	 * 
	 * @param random QuickCheck random number generator (similar to
	 *               java.util.Random)
	 * @param status An object that can be used to influence the generated value.
	 *               For example, status.size() returns a value that
	 *               (probabilistically) increases for every successful string
	 *               generation. By using status.size() + 10 as the generated number
	 *               of tags, the generator starts with simple strings and
	 *               progressively tests longer strings.
	 * @return Generated random string
	 */
	@Override
	public String generate(SourceOfRandomness random, GenerationStatus status) {
		int length = status.size() + 10;
		String ret = "";
		Stack<String> stack = new Stack<String>();
		for (int i = 0; i < length; ++i) {
			String nextTag = "";
			int next = random.nextInt(2);
			switch (next) {
			case 0:
				nextTag = "<b>";
				break;
			case 1:
				nextTag = "<i>";
				break;
			default:
				assert false;
			}
			ret += nextTag;
			stack.push(nextTag);
			// Probabilistically pop and match start tags in stack
			while (!stack.empty()) {
				// Probability for popping stack is proportional to stack size
				float probability = (float) (stack.size() - 1) / (float) stack.size();
				float f = random.nextFloat();
				if (f < probability) {
					String tag = stack.pop();
					if (tag.equals("<b>")) {
						ret += "</b>";
					} else {
						assert tag.equals("<i>");
						ret += "</i>";
					}
				} else {
					break;
				}
			}
		}
		// Pop and match the remaining start tags in the stack
		while (!stack.empty()) {
			String tag = stack.pop();
			if (tag.equals("<b>")) {
				ret += "</b>";
			} else {
				assert tag.equals("<i>");
				ret += "</i>";
			}
		}

		return ret;
	}

	/**
	 * Overridden doShrink method. Returns a list of smaller strings to test, in the
	 * event that the larger string results in a test failure. If any of the smaller
	 * strings fail, QuickCheck shrinks the strings further by recursively calling
	 * the doShrink method. These are the two smaller strings to return in the list:
	 * 1. If there exists an innermost "<b></b>" pair in "larger", add a smaller
	 * string with the pair removed. 2. If there exists an innermost "<i></i>" pair
	 * in "larger", add a smaller string with the pair removed.
	 * 
	 * @param random QuickCheck random number generator (similar to
	 *               java.util.Random)
	 * @param larger The original larger string that you want to shrink to one or
	 *               more smaller strings
	 * @return List of shrunk smaller strings
	 */
	@Override
	public List<String> doShrink(SourceOfRandomness random, String larger) {
		// TODO: Fill in looking at the Javadoc comments above and ABCStringGenerator
		return Collections.emptyList();
		
	}
}