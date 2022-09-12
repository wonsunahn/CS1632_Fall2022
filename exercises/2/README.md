- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Before You Begin](#before-you-begin)
    + [Install Apache Maven](#install-apache-maven)
    + [Install VSCode](#install-vscode)
  * [Description](#description)
  * [Running the Program](#running-the-program)
  * [Running Unit Tests](#running-unit-tests)
    + [The POM Maven build configuration](#the-pom-maven-build-configuration)
  * [Using TDD to Complete the Implementation](#using-tdd-to-complete-the-implementation)
    + [Red Phase](#red-phase)
    + [Green Phase](#green-phase)
    + [Refactor Phase](#refactor-phase)
  * [Verifying the Test Cases](#verifying-the-test-cases)
  * [Measuring Code Coverage](#measuring-code-coverage)
  * [Using VSCode](#using-vscode)
- [Submission](#submission)
- [GradeScope Feedback](#gradescope-feedback)
- [Groupwork Plan](#groupwork-plan)
- [Resources](#resources)

# CS 1632 - Software Quality Assurance
Summer Semester 2022 - Exercise 2

* DUE: July 12 (Tuesday), 2022 11:30 AM

**GitHub Classroom Link:** https://classroom.github.com/a/ICHUm7XF

## Before You Begin

### Install Apache Maven

In this class, we will be using the Apache Maven build framework to build and
test our code.  Please download the binary zip file from:
https://maven.apache.org/download.cgi

Unzip the file at your preferred location and add the bin directory to your PATH enviornment variable as instructed in:
https://maven.apache.org/install.html

### Install VSCode

In this class, we will be using VSCode as our default IDE.  It makes
collaboration, code sharing, and other tasks much easier.  You may use other
IDEs if you choose to do so.  All exercises and deliverables are designed so
that they can be done independent of an IDE.

If you choose to use VSCode, please download and install:
https://code.visualstudio.com/download

Please also install the "Extension Pack for Java" on VSCode by searching for it on the Extensions menu:
https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack

If you are working with a partner, you may also want to familiarize yourself with the Live Share feature on VSCode:
https://code.visualstudio.com/learn/collaboration/live-share

## Description

In this exercise, we will simulate the main Rent-A-Cat rental system software.
This is obviously a "toy" implementation of the vast and powerful Rent-A-Cat
apparatus.

I have created some skeleton code for you to fill in for this exercise.  It is
up to you to fill in the `returnCat()`, `rentCat()`, `listCats()` and
`catExists()` methods, and write unit tests for them.  Unit tests must use
doubles for the Cat object with appropriate stubbing.  I have intentionally
inserted a defect on every Cat method such that an exception is fired if you
try to use a real Cat object in any way during your unit testing!  Those
defects are turned off when Cat is used within the main RentACat program.

Rent-A-Cat rents cats to customers for various needs (mousing, companionship,
homework help, etc.).  From the main menu, users may:

1. See list of cats for rent
2. Rent a cat to a customer
3. Return a cat
4. Quit

A cat which is out for rental cannot be rented and will not be listed until it
has been returned.  We will not charge money for this exercise.

## Running the Program

This is an example expected interaction with the program:

```
Option [1,2,3,4] > 1
Cats for Rent
ID 1. Jennyanydots
ID 2. Old Deuteronomy
ID 3. Mistoffelees
Option [1,2,3,4] > 2
Rent which cat? > 1
Jennyanydots has been rented.
Option [1,2,3,4] > 1
Cats for Rent
ID 2. Old Deuteronomy
ID 3. Mistoffelees
Option [1,2,3,4] > 2
Rent which cat? > 1
Sorry, Jennanydots is not here!
Rent which cat? > 7
Invalid cat ID.
Rent which cat? > 3
Mistoffelees has been rented.
Option [1,2,3,4] > 1
Cats for Rent
ID 2. Old Deuteronomy
Option [1,2,3,4] > 3
Return which cat? > 7
Invalid cat ID.  
Return which cat? Jennyanydots
Invalid cat ID.
Return which cat? 1
Welcome back, Jennyanydots!
Option [1,2,3,4] > 1
Cats for Rent
ID 1. Jennyanydots
ID 2. Old Deuteronomy
Option [1,2,3,4] > 4
Closing up shop for the day!
```

Let's try running the program and observe the output for ourselves.

1. First let's compile the program using the 'compile' phase on Maven:

   ```
   mvn compile
   ```

   If the compilation is successful, all the Java soure codes under src/ are
compiled to class files under target/classes.

1. Next, invoke the 'exec' phase designating RentACatImpl class:

   ```
   mvn exec:java -D"exec.mainClass"="edu.pitt.cs.RentACatImpl"
   ```

   And then, try listing the cats available for rent:

   ```
   ...
   Option [1,2,3,4] > 1
   Cats for Rent
   WRITE CODE FOR THISOption [1,2,3,4] >
   ```

That's not what you expected!  That is because the Rent-A-Cat system is
incomplete.  For this exercise, you will modify two classes to complete the
system: **RentACatImpl.java** and **RentACatTest.java**.  The RentACatImpl
class is an (incomplete) implementation of the Rent-A-Cat system.  The
RentACatTest class is a JUnit test class that tests RentACatImpl.  All
locations where you should add code is marked with // TODO comments.

## Running Unit Tests

1. First let's see if we can find any bugs by running unit tests by invoking
   the 'test' phase in Maven:

   ```
   mvn test
   ```

   The Maven framework looks for any JUnit test classes under src/test/, and
invokes them one by one.  You should get a result that looks like this:

   ```
   ...
   -------------------------------------------------------
    T E S T S
   -------------------------------------------------------
   Running edu.pitt.cs.RentACatTest
   Tests run: 13, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.116 sec

   Results :

   Tests run: 13, Failures: 0, Errors: 0, Skipped: 0

   [INFO]
   [INFO] --- jacoco-maven-plugin:0.8.4:report (post-unit-test) @ rentacat ---
   [INFO] Loading execution data file C:\Users\mrabb\Documents\github\cs1632\CS1632_Summer2022\exercises\2\target\jacoco.exec
   [INFO] Analyzed bundle 'rentacat' with 5 classes
   [INFO]
   [INFO] --- jacoco-maven-plugin:0.8.4:check (check-unit-test) @ rentacat ---
   [INFO] Loading execution data file C:\Users\mrabb\Documents\github\cs1632\CS1632_Summer2022\exercises\2\target\jacoco.exec
   [INFO] Analyzed bundle 'rentacat' with 5 classes
   [WARNING] Rule violated for class edu.pitt.cs.RentACatImpl: instructions covered ratio is 0.02, but expected minimum is 0.20
   [INFO] ------------------------------------------------------------------------
   [INFO] BUILD FAILURE
   [INFO] ------------------------------------------------------------------------
   [INFO] Total time:  2.986 s
   [INFO] Finished at: 2022-07-06T22:09:19-04:00
   [INFO] ------------------------------------------------------------------------
   ...
   ```

   Note that out of the 13 unit tests run, 0 tests were failures.  Apparently,
all tests passed!  So are done?  Far from it!  The reason that there are no
failures is because all test cases are currently empty.  Pay attention to the
following line in the output:

   ```
   [WARNING] Rule violated for class edu.pitt.cs.RentACatImpl: instructions covered ratio is 0.02, but expected minimum is 0.20
   ```

   It is saying that the test phase expected a minimum of 20% instruction
coverage for the RentACatImpl class, but the tests achieved only 2%.  Hence
that is why it says 'BUILD FAILURE' in the end.  We were only able to cover 2%
exactly becauase all test cases are empty.  You can see for yourself in
RentACatTest.java under the src/test/ folder that all test cases only have //
TODO comments in them.

### The POM Maven build configuration

   As a side note, how did Maven know it had to achieve 20% coverage?
Everything about the Maven build and test processes is governed by the
[pom.xml](pom.xml) file which describes the POM (Project Object Model) of the
project.  In the pom.xml file, there is a section that says:

   ```
   ...
   <goals>
     <goal>check</goal>
   </goals>
   <configuration>
     <dataFile>${project.build.directory}/jacoco.exec</dataFile>
     <rules>
       <rule>
	 <element>CLASS</element>
	 <limits>
	   <limit>
	     <counter>INSTRUCTION</counter>
	     <value>COVEREDRATIO</value>
	     <minimum>20%</minimum>
	   </limit>
	 </limits>
	 <includes>
	    <include>edu.pitt.cs.RentACatImpl</include>
	 </includes>
       </rule>
     </rules>
   </configuration>
   ...
   ```

   Jacoco is short for the **Ja**va **Co**de **Co**verage tool.  The
documentation on how to configure like the above is given at:
https://www.eclemma.org/jacoco/trunk/doc/check-mojo.html

   That is as much as I am going to say about the Maven build system, since we
don't have all day.  Please feel free to look over the Apache Maven
documentation if you want to learn more about it.  It is a widely used build
tool, mainly thanks to superior dependency management of Java packages.  Note
that even though I am using external packages like JUnit and Jacoco, I don't
need to download them manually from somewhere.  By simply adding them as
dependencies to the POM file, Maven will automatically download them from
[Maven Central](https://search.maven.org/).

## Using TDD to Complete the Implementation

We will try to apply the Test Driven Development (TDD) model and the
Red-Green-Refactor (RGR) loop.  Try writing the test case(s) FIRST before
writing the code for a feature.  This way, you will always have 100% test
coverage for the code you have written and are writing.  Hence, if you break
any part of it in the course of adding a feature or refactoring your code, you
will know immediately.  Otherwise, if you test at the very end, it will be much
harder to find the defect and fix it.

### Red Phase

This is the phase where you start by writing a test case.  Let's start with the
first test case in RentACatTest.java: testGetCatNullNumCats0.

Let's start by doing a sanity test to see if the following test will pass by replacing the // TODO comment with this line:

```
assertTrue(true);
```

And then running the test phase again:

```
mvn test
```

If you see all 13 test cases pass as before, great!  Now let's try an assertion that fails.  Change the above to:

```
assertFalse(true);
```

And then run 'mvn test' again.  You should see one failure:

```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running edu.pitt.cs.RentACatTest
Tests run: 13, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.216 sec <<< FAILURE!
testGetCatNullNumCats0(edu.pitt.cs.RentACatTest)  Time elapsed: 0 sec  <<< FAILURE!
java.lang.AssertionError
        at org.junit.Assert.fail(Assert.java:87)
        at org.junit.Assert.assertTrue(Assert.java:42)
        at org.junit.Assert.assertTrue(Assert.java:53)
        at edu.pitt.cs.RentACatTest.testGetCatNullNumCats0(RentACatTest.java:71)
...
Results :

Failed tests:   testGetCatNullNumCats0(edu.pitt.cs.RentACatTest)

Tests run: 13, Failures: 1, Errors: 0, Skipped: 0
...
```

Now, instead of just doing 'assertFalse(true)', you also have the option of supplying a failure message as the first argument to assertFalse:

```
assertFalse("True is not false", true);
```

If you run 'mvn test' again, you will get the failure message on the output:

```
...
Failed tests:   testGetCatNullNumCats0(edu.pitt.cs.RentACatTest): True is not false
...
```

Every JUnit assertion has an optional first argument where you can supply the
failure message.  It is a good habit to always supply a failure message which
describes what failed and why it failed.  That way, the tester is not forced to
parse the exception stack trace and then read the relevant source code line on
the JUnit test to try to figure it out.

Now you should be confident enough to start writing the RentACatTest class for
real.  Start by adding very simple tests to gain confidence.  Next, try adding
more complex cases towards the bottom of RentACatTest.java that require Cat
objects.  For that, you will have to modify setUp() to create some Cat mock
objects with proper stubs.  We learned how to do that in the unit testing
lectures.  If you are still unsure, look at the
[LinkedListUnitTest.java](https://github.com/wonsunahn/CS1632_Summer2022/blob/main/sample_code/junit_example/LinkedListUnitTest.java)
sample code.

Some tips you may find useful while writing test cases:

1. Each @Test method represents a test case.  A JUnit class with one or more
   @Test methods represents a test plan. A JUnit class is usually named after
whichever class it is testing, with the string `Test` appended to the tested
class name.  For example, Foo.java would be tested by FooTest.java.  But this
is not necessarily the case.  A JUnit class may test multiple classes with
related functionality; in that case, it is typically named after the
functionality it is testing.
  
1. Make use of the @Before and @After methods in your JUnit testing.  @Before
   and @After methods are invoked before and after each @Test method.  They are
used to set up and tear down test fixtures.  Test fixtures in JUnit are objects
that need to be created and initialized before performing each test case.  You
can think of them as "actors" in your test script.  Having the @Before and
@After allows us to avoid duplicating test fixture code in each test case.

1. In RentACatTest.java, pay close attention to the Javadoc comments on top of
   each @Test method which describe the preconditions, execution steps, and
postconditions.  Remember, part of the preconditions may be already fulfilled
with the test fixture initialized in the @Before method.

1. You should use test doubles/mocks for any references to classes other than
   the one under test that the tested class is dependent upon (i.e. you need to
mock any Cat objects).  In fact, if you don't mock Cat and use the actual Cat
objects, your tests will most likely fail.  I have injected artificial defects
in the form of exceptions into the Cat class to emulate a buggy Cat class.
Because your tests should work regardless of what's inside Cat.  As to the
ArrayList class used to store the list of Cats, you do not need to mock it even
if RentACatImpl is dependent upon it.  ArrayList is a Java standard library
class so we will assume that it is fully tested and defect-free at this point.
:)

1. Remember to _not_ mock the class under test (i.e. RentACat), only external
   classes that it depends upon (i.e. Cat).  If you mock RentACat, and then
test the behavior that you are mocking, what are you testing?  You are testing
your test code and not your implementation!  In fact, this type of test is
called a **tautological test** since it will always pass, regardless of whether
your implementation has a defect or not.

1. The easiest thing to do is assert against a return value, but you can also
   assert against attributes of an object.  For example:

    ```
    @Test
    public void testCatName() {
       assertEquals("Expected name", cat.getName());
    }
    ```
    You can also use the Mockito verify method to perform behavior verification.

### Green Phase

This is where you complete the relevant method in RentACatImpl.java.  There are
a few methods that have already been filled in, which should pass without
having to do anything more.  There are also methods with // TODO comments that
you have to fill in before passing the test cases.

### Refactor Phase

Look for opportunities to streamline your code or make it more readable.  After
you are done with the refactor phase, all test cases should pass again and you
should be ready for the next iteration of RGR loop for the next test case.

## Verifying the Test Cases

By now, all test cases should have been implemented and all test cases should
pass.  You have come full circle!  But wait, does this mean RentACat is
bug-free?  How do you know if your unit tests themselves had defects and that's
why they passed, even when RentACat is buggy?  

Let's perform an extra step to verify the unit tests themselves to make sure
that they themselves are not defective.  One way to verify unit tests is to
test them on buggy programs to see if they detect the bugs as they are intended
to.  I have created a buggy version of Rent-A-Cat just for this purpose named
RentACatBuggy.java.  

In order to apply your unit tests to RentACatBuggy, add the following line to
the beginning of the @Before setUp() method:

```
Config.setBuggyRentACat(true);
```

This will ensure that a RentACatBuggy instance is returned when you later do 'r
= RentACat.createInstance();' in the setUp() method.

Now if you do 'mvn test' once more, you should get output that looks like this:

```
...
Failed tests:   testCatAvailableFalseNumCats0(edu.pitt.cs.RentACatTest): No cats but catAvailable(2) returns true
  testCatAvailableFalseNumCats3(edu.pitt.cs.RentACatTest): 3 cats and cat 2 is rented but catAvailable(2) returns true
  testCatAvailableTrueNumCats3(edu.pitt.cs.RentACatTest): 3 cats and cat 2 is not rented but catAvailable(2) returns false
  testCatExistsFalseNumCats0(edu.pitt.cs.RentACatTest): No cats but catExists(2) returns true
  testCatExistsTrueNumCats3(edu.pitt.cs.RentACatTest): 3 cats but catExists(2) returns false
  testListCatsNumCats0(edu.pitt.cs.RentACatTest): No cats but listCats() returns non-empty string expected:<[]> but was:<[empty]>
  testListCatsNumCats3(edu.pitt.cs.RentACatTest): 3 cats and listCats() does not return the expected string expected:<ID 1. Jennyanydots[(..)
  testRentCatFailureNumCats0(edu.pitt.cs.RentACatTest): No cats but rentCat(2) returns true
  testRentCatFailureNumCats3(edu.pitt.cs.RentACatTest): 3 cats and cat 2 is rented but rentCat(2) returns true
  testReturnCatFailureNumCats0(edu.pitt.cs.RentACatTest): No cats but returnCat(2) returns true
  testReturnCatNumCats3(edu.pitt.cs.RentACatTest): (..)

Tests run: 13, Failures: 11, Errors: 0, Skipped: 0
...
```

You can see that all tests fail except the ones for getCat(int id).  That is
because I've inserted bugs into RentACatBuggy except for that method.  If your
unit tests pass any other method, it must be defective.  Time to fix your test!

Important: don't forget to revert RentACatTest.java by removing the 'Config.setBuggyRentACat(true);' line when you are satisfied!

## Measuring Code Coverage

Code coverage is a metric that measures what percentage of the code base a
particular test run covered.  There are several ways to measure code coverage,
but the most widespread method is to measure the percentage of code lines
covered.  Typically a code coverage of above 80\% or 90\% is targeted in
software organizations.  I will require that level of coverage for the
Deliverable.  Since this is just an exercise, the minimum coverage is set to be
20%, which you should be able to achieve easily.

Jacoco (**Ja**va **Co**de **Co**verage tool), is one of the most popular code
coverage measurement tools among Java developers, and that's what we will use
in this class.  Jacoco has already been integrated into the test phase of our
Maven project, so you should already have coverage statistics generated from
your last 'mvn test' run at:

```
target/site/jacoco/
```

The statistics are generated XML (jacoco.xml), CSV (jacoco.csv), and HTML
(index.html) formats.  The XML and CSV formats are designed to be easily
readable by later stages of the testing pipeline that automatically generate
reports or send notifications to developers.  The HTML format is meant for
human cosumption.  Try opening index.html and drill down to the RentACatImpl
class, which is the class under test which we are interested in measuring code
coverage for.  If you have implemented all the code, it should look similar to
the following screenshot:

<img alt="Code Coverage Jacoco" src=code_coverage_jacoco.png width=700>

## Using VSCode

As you can see, all instructions on this README are given based on the
commandline.  You can use these instructions regardless of what IDE or editor
you use for coding.  If you decide to use an IDE like VSCode, typically there
are integrations inside the IDE to build, execute, and test your code.  I will
demonstrate VSCode integrations in class.

# Submission

Each pairwise group will do one submission to GradeScope as usual.  The
submitting member must use the "View or edit group" link at the top-right
corner of the assignment page after submission to add his/her partner.  

The submission this time is divided into two parts:

1.  Submit the repository created by GitHub Classroom for your team to
    GradeScope at the **Exercise 2 GitHub** link.  Once you submit, GradeScope
will run the autograder to grade you and give feedback.  If you get deductions,
fix your code based on the feedback and resubmit.  Repeat until you don't get
deductions.

1. Create a screenshot of code coverage stats using one of the two
   methodologies shown above.  Make sure you take the screenshot of the correct
screen.  After you have created the screenshot, save the picture to a PDF file
and submit to GradeScope at the **Exercise 2 Coverage** link.  Make sure the
picture fits in one page for easy viewing and grading.

# GradeScope Feedback

The GradeScope autograder works in 3 phases:
1. RentACatTestSolution.(some method) on RentACatImpl: RentACatTestSolution is the solution implementation of RentACatTest.  The purpose of this phase is to verify that RentACatImpl (your RentACat implementation) does not have any defects.
1. RentACatTest.(some method) on RentACatSolution: RentACatTest is your submitted JUnit test for RentACat.  The purpose of this phase is to test RentACatTest itself for defects.  RentACatSolution is the solution implementation of RentACat and contains no defects (that I know of).  Hence, all tests in RentACatTest should pass.
1. RentACatTest.(some method) on RentACatBuggy: RentACatTest is your submitted JUnit test for RentACat and you are testing against the buggy RentACatBuggy implementation.  The purpose of this phase is to further test RentACatTest for defects.  It does this by testing whether RentACatTest finds all the bugs that RentACatTestSolution is able to find within RentACatBuggy.
If you see test failures, read the feedback given by the autograder, fix your code, and retry.

Beside the feedback given by the autograder, the TA or myself will leave more detailed feedback on the "Feedback on source code" question.  We will also check your code coverage screenshot submission and give feedback.

# Groupwork Plan

There are two files needing modification: RentACatTest.java and
RentACatImpl.java.  One way you can divide up the work is like the following:

[RentACatTest.java]

* Partner 1:
  * setUp
  * testCatAvailableFalseNumCats0
  * testCatAvailableFalseNumCats3
  * testCatAvailableTrueNumCats3
  * testCatExistsFalseNumCats0
  * testCatExistsTrueNumCats3
  * testReturnCatFailureNumCats0
  * testReturnCatNumCats3
* Partner 2:
  * setUp
  * testGetCatNullNumCats0
  * testGetCatNumCats3
  * testListCatsNumCats0
  * testListCatsNumCats3
  * testRentCatFailureNumCats0
  * testRentCatFailureNumCats3

[RentACatImpl.java]

* Partner 1:
  * catExists
  * returnCat
* Partner 2:
  * listCats
  * rentCat

Notice that the setUp method is repeated for both partners.  Both of you will
have to implement parts of the setUp method to have your assigned tests
working.  To avoid merge conflicts on GitHub while working on the same file, I
suggest that you use the Live Share feature in VSCode to work on the same
shared copy of code, when you are working concurrently in real time (as in the
classroom).  After you are done with the coding session, don't forget to commit
and push the code to the GitHub repository so that both partners have access to
it later.  Outside of the classroom when you are not working concurrently, you
will mostly collaborate through GitHub.  Push frequently and also pull
frequently from your GitHub repository whenever you are done finishing a method
to merge changes as you go along.  Please communicate frequently and help each
other out!

# Resources

These links are the same ones posted at the end of the slides:

* JUnit User Manual:  
https://junit.org/junit4/

* JUnit Reference Javadoc:  
http://junit.sourceforge.net/javadoc/  
For looking up methods only, not a user guide.

* Mockito User Manual:  
https://javadoc.io/static/org.mockito/mockito-core/3.2.4/org/mockito/Mockito.html  
Most useful is the sections about verification and stubbing.

* Jacoco User Manual:  
https://www.jacoco.org/userdoc/index.html
