package edu.pitt.cs;

import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	/**
	 * Main method.
	 *
	 * @param args (impl | solution | buggy) (junit | trace).
	 * @throws InvocationTargetException Thrown when @Test method invocation fails.
	 * @throws IllegalAccessException Thrown when @Test method invocation is an illegal access.
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {

		// The TestRunner is used only for running the JPFJUnitTest JUnit class on JPF.
		// You can use "mvn test" to run JPFJUnitTest as a plain JUnit test without JPF.
		Config.setTestType(TestType.JPF_ON_JUNIT);

		if (args.length != 2) {
			System.out.println("Usage: TestRunner <logic type (impl | solution | buggy)> <test type (junit | trace)>");
			return;
		}

		if (args[0].equals("impl")) {
			Config.setLogicType(LogicType.IMPL);
			System.out.print("TESTING YOUR IMPLEMENTATION ");
		} else if (args[0].equals("buggy")) {
			Config.setLogicType(LogicType.BUGGY);
			System.out.print("TESTING BUGGY IMPLEMENTATION ");
		} else if (args[0].equals("solution")) {
			Config.setLogicType(LogicType.SOLUTION);
			System.out.print("TESTING SOLUTION IMPLEMENTATION ");
		} else {
			System.out.println("Usage: TestRunner <logic type> <test type>\n");
			return;
		}

		if (args[1].equals("junit")) {
			System.out.println("WITH JPF USING JUNIT FRAMEWORK\n");

			// Invoke JUnit framework on JPFJUnitTest to aggreggate all errors
			Result r = JUnitCore.runClasses(JPFJUnitTest.class);
			for (Failure f : r.getFailures()) {
				System.out.println(f.toString());
				// System.out.println(f.getTrace());
			}
			return;
		} else if (args[1].equals("trace")) {
			System.out.println("WITH JPF USING JUNIT EMULATION FOR TRACING\n");

			// Invoke tests in JPFJUnitTest using Java reflection to get a trace
			JPFJUnitTest test = new JPFJUnitTest();
			test.setUp(); // @BeforeClass
			for (Method method : test.getClass().getMethods()) {
				if (method.isAnnotationPresent(Test.class)) {
					// do something
					method.invoke(test);
				}
			}
			test.tearDown(); // @AfterClass
			return;
		} else {
			System.out.println("\nUsage: TestRunner <logic type> <test type>\n");
			return;
		}
	}
}
