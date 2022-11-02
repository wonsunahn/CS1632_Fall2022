package edu.pitt.cs;

public class Config {
	// Type of logic to use for BeanCounterLogic.
	// Choose between LogicType.IMPL, LogicType.SOLUTION, and LogicType.BUGGY.
	private static LogicType logicType = LogicType.IMPL;
	
	// Type of testing to do in JPFJUnitTest.java.
	// By default it is JUNIT, but TestRunner may change it to JPF_ON_JUNIT when it is used with JPF.
	private static TestType testType = TestType.JUNIT;

	public static void setLogicType(LogicType type) {
		logicType = type;
	}

	public static LogicType getLogicType() {
		return logicType;
	}

	public static void setTestType(TestType type) {
		testType = type;
	}

	public static TestType getTestType() {
		return testType;
	}
}
