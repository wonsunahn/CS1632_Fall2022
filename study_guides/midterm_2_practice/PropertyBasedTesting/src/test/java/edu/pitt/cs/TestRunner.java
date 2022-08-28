package edu.pitt.cs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	/**
	 * Main method.
	 *
	 * @param args IGNORED, kept for compatibility
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {

		boolean trace = false;
		if (args.length > 0 && args[0].equals("trace")) {
			trace = true;
			System.out.println("TRACE GENERATION FOR FIRST FAILURE\n");
		}

		ArrayList<Class> classesToTest = new ArrayList<Class>();

		// ADD ANY CLASSES YOU WISH TO TEST HERE
		classesToTest.add(JPFTest.class);

		// For all test classes added, loop through and use JUnit
		// to run them.

		for (Class c : classesToTest) {
			if (trace == false) {
				// Invoke JUnitCore to obtain list of failures

				Result r = JUnitCore.runClasses(c);

				// Print out any failures for this class.
				for (Failure f : r.getFailures()) {
					System.out.println(f.toString());
					// System.out.println(f.getTrace());
				}
			} else {
				// Call @Test methods directly to generate a trace for first failure
				
				Object testObject = c.newInstance();
				assert testObject != null;
				for (Method method : c.getMethods()) {
					if (method.isAnnotationPresent(BeforeClass.class)) {
						// do something
						method.invoke(testObject);
					}
				}
				for (Method method : c.getMethods()) {
					if (method.isAnnotationPresent(Test.class)) {
						// do something
						method.invoke(testObject);
					}
				}
				for (Method method : c.getMethods()) {
					if (method.isAnnotationPresent(AfterClass.class)) {
						// do something
						method.invoke(testObject);
					}
				}
			}
		}
	}
}
