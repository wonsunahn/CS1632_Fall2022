- [Introduction](#introduction)
- [JUnit Problems](#junit-problems)
  * [ValueTest.java](#valuetestjava)
  * [SquareTest.java](#squaretestjava)
    + [Integration Test](#integration-test)
    + [Unit Test](#unit-test)
  * [DeathStarTest.java](#deathstartestjava)
    + [Integration Test](#integration-test-1)
    + [Unit Test](#unit-test-1)
  * [RandomValueTest.java](#randomvaluetestjava)
- [Cucumber Problems](#cucumber-problems)
  * [Testing Value.java](#testing-valuejava)
    + [value_incVal.feature](#value-incvalfeature)
    + [ValueStepDefinitions.java](#valuestepdefinitionsjava)
  * [Testing DeathStar.java](#testing-deathstarjava)
    + [deathStar_shoot.feature](#deathstar-shootfeature)
    + [DeathStarStepDefinitions.java](#deathstarstepdefinitionsjava)

# Introduction

You can open this folder from VSCode using the "Open Folder" menu.  It is a
Maven project so you will be able to run Cucumber as well as JUnit.  There are
a bunch of problems that you can solve in this project listed below.  But you
can also use this project for the purposes of testing code that you write
during the exam.  You can add new Java and JUnit classes and try running them
on the project.

# JUnit Problems

Running the Maven test phase will run all JUnit tests (including the Cucumber tests):

```
mvn test
```

## ValueTest.java

There is a bug in the JUnit class.  What is the bug and how would you fix it?

## SquareTest.java

### Integration Test

We want to integration test Square.setSquared.  Implement the test according
to the preconditions, execution steps, and postconditions described in the
comment.

### Unit Test

Now we want to unit test Square.setSquared using mocks.  Modify the above
code to convert it to a unit test.

## DeathStarTest.java

### Integration Test

We want to integration test DeathStar.shoot. As before, implement the test
according to the preconditions, execution steps, and postconditions
described in the comment.

### Unit Test

Now we want to unit test DeathStar.shoot again using mocks.  Modify the
above code to convert it to a unit test.

## RandomValueTest.java

As things stand, test results are unreproducible (test cases will fail
intermittently).  This is because the RandomValue program is
nondeterministic due to the internal Random object.  Modify RandomValue.java
to make the code testable by injecting the Random object, thereby decoupling
RandomValue from java.util.Random.

Then, modify RandomValueTest.java to create a mock Random object and emulate
behavior where nextInt(2) always returns 1 such that testIncValOnce and
testIncValTwice now pass.  Also, add two more test cases
testIncValOnceNextIntZero and testIncValTwiceNextIntZero which checks that
value.getVal() always returns 0 if nextInt(2) returns 0.

# Cucumber Problems

The below scenarios can be executed through Run As > Cucumber Feature on
Eclipse for individual scenarios, or Run As > Maven Test if you want to run
the entire test suite, including the JUnit tests.  You can also run "mvn
test" on the commandline as we did for Supplementary Exercise 1.

The below scenarios can be executed through Run As > Maven Test on Eclipse
to run the entire test suite, including the JUnit tests.  You can also
run "mvn test" on the commandline as we did for Supplementary Exercise 1.

## Testing Value.java

### value_incVal.feature

Fill in the 3 scenarios using Gherkin Given, When, and Then steps.

### ValueStepDefinitions.java

Fill in the class with step definitions for the above steps.  Use the same
Value class we used for JUnit testing.  If you do this properly, you should
be able to implement everything with just 3 steps.

## Testing DeathStar.java

### deathStar_shoot.feature

Add a deathStar_shoot.feature file to the project under src/test/resources/
and complete the Gherkin file with a scenario that is identical to the
DeathStarTest.testShootPlanet JUnit test case.

### DeathStarStepDefinitions.java

Add a DeathStarStepDefinitions.java file to the project under src/test/java/
and implement the step definitions corresponding to the Gherkin steps that
you used above.
