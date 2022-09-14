- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Deliverable 2](#deliverable-2)
  * [Running the Program](#running-the-program)
  * [Running Unit Tests](#running-unit-tests)
  * [Development Methodology](#development-methodology)
  * [Expected Outcome](#expected-outcome)
  * [Verifying the Test Cases](#verifying-the-test-cases)
  * [Additional Requirements](#additional-requirements)
- [Grading](#grading)
- [Submission](#submission)
- [GradeScope Feedback](#gradescope-feedback)
- [Groupwork Plan](#groupwork-plan)
- [Resources](#resources)

# CS 1632 - Software Quality Assurance
Fall Semester 2022

* DUE: October 3, 2022 before start of class

**GitHub Classroom Link:** https://classroom.github.com/a/a3CjQ2DK

## Deliverable 2

For this assignment, your group will write code and unit tests for an
authorized reproduction of Coffee Maker Quest.  

Requirements for this program is that you mimic the behavior of the program
coffeemaker.jar given in this folder exactly.  This is a version of the Coffee
Maker Quest game we tested for Deliverable 1, but with defects removed.

Some of the work has already been done for you.  Classes such as
CoffeeMakerQuest.java, Config.java, Game.java, Player.java, Room.java, and
RoomsJSONParser.java are already complete.  You need only modify
CoffeeMakerQuestImpl.java and CoffeeMakerQuestTest.java.  As in the exercise,
the places where you need to modify code are marked by the // TODO comments.
DO NOT TOUCH the already complete classes as they will be used AS IS during
grading.  Here is a brief rundown of the files:

* CoffeeMakerQuestImpl.java - an implementation of CoffeeMakerQuest (**modify**)
* CoffeeMakerQuestTest.java - JUnit test class CoffeeMakerQuest (**modify**)
* CoffeeMakerQuest.java - the interface for the CoffeeMakerQuest game engine
* Config.java - allows configuration of bug injection into various classes (for grading purposes mostly)
* Game.java - contains the main method; generates rooms and runs the game using the CoffeeMakerQuest engine
* Player.java - player object with inventory information
* Room.java - room object with furnishings and items
* RoomsJSONParser.java - uses JSON parser to read in rooms.config file
* coffeemaker.jar - reference implementation
* rooms.config - the rooms map configuration file

## Running the Program

Let's try compiling the game and running using the Maven build system:

```
mvn compile
```

```
mvn exec:java 
```

When you run it as-is, you will get the following output:

```
...
Please make sure that the rooms.config file has doors at all interconnected rooms.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.593 s
[INFO] Finished at: 2022-09-13T16:33:14-04:00
[INFO] ------------------------------------------------------------------------
```

The game fails to start up properly because it fails a sanity check of the
rooms.config that checks whether all rooms have doors to neighboring rooms.
And that is not happening not because rooms.config really has a configuration
problem, but because your CoffeeMakerQuestImpl is yet incomplete.  When you are
done implementing, you should get identical behavior as running the reference
jar file:

```
java -jar coffeemaker.jar
```

## Running Unit Tests

As in the Exercise, invoke the Maven 'test' phase:

```
mvn test
```

You should get output that looks like this:

```
...
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running edu.pitt.cs.CoffeeMakerQuestTest
Tests run: 11, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.112 sec

Results :

Tests run: 11, Failures: 0, Errors: 0, Skipped: 0

[INFO]
[INFO] --- jacoco-maven-plugin:0.8.4:report (post-unit-test) @ coffeemaker ---
[INFO] Loading execution data file C:\Users\mrabb\Documents\github\cs1632\CS1632_CoffeeMaker\target\jacoco.exec
[INFO] Analyzed bundle 'coffeemaker' with 9 classes
[INFO]
[INFO] --- jacoco-maven-plugin:0.8.4:check (check-unit-test) @ coffeemaker ---
[INFO] Loading execution data file C:\Users\mrabb\Documents\github\cs1632\CS1632_CoffeeMaker\target\jacoco.exec
[INFO] Analyzed bundle 'coffeemaker' with 9 classes
[WARNING] Rule violated for class edu.pitt.cs.CoffeeMakerQuestImpl: instructions covered ratio is 0.17, but expected minimum is 0.90
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.144 s
[INFO] Finished at: 2022-09-13T16:42:54-04:00
[INFO] ------------------------------------------------------------------------
...
```

Again, don't get too excited about all the unit tests passing --- that is only
because the test cases are currently empty.  In fact, the build fails in the
code coverage verification phase because the current coverage is only 17% which
is way below our target of 90%.  You will have to fill in all the provided test
cases, and perhaps add a few test cases of your own, to hit 90%.

## Development Methodology

Like Exercise 2, we will try to apply the Test Driven Development (TDD) model
here.  Try writing the test case(s) FIRST before writing the code for a
feature.  This way, you will always have test coverage for the code you have
written and are writing.  Hence, if you break any part of it in the course of
adding a feature or refactoring your code, you will know immediately.

## Expected Outcome

When all is done, you should see something like the following when running 'mvn
test' (number of tests may vary depending on how many tests are required to
reach 90% coverage):

```
...
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running edu.pitt.cs.CoffeeMakerQuestTest
Tests run: 24, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.46 sec

Results :

Tests run: 24, Failures: 0, Errors: 0, Skipped: 0

[INFO]
[INFO] --- jacoco-maven-plugin:0.8.4:report (post-unit-test) @ coffeemaker ---
[INFO] Loading execution data file C:\Users\mrabb\Documents\github\cs1632\CS1632_CoffeeMaker_Solution\target\jacoco.exec
[INFO] Analyzed bundle 'coffeemaker' with 7 classes
[INFO]
[INFO] --- jacoco-maven-plugin:0.8.4:check (check-unit-test) @ coffeemaker ---
[INFO] Loading execution data file C:\Users\mrabb\Documents\github\cs1632\CS1632_CoffeeMaker_Solution\target\jacoco.exec
[INFO] Analyzed bundle 'coffeemaker' with 7 classes
[INFO] All coverage checks have been met.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.949 s
[INFO] Finished at: 2022-09-07T02:52:08-04:00
[INFO] ------------------------------------------------------------------------
...
```

## Verifying the Test Cases

Just like for the exercises, as an extra precaution, we would like to verify
our test cases against a buggy implementation to make sure that they are able
to catch defects.

In order to apply your unit tests to CoffeeMakerQuestBuggy, add the following line to
the beginning of the @Before setUp() method:

```
Config.setBuggyCoffeeMakerQuest(true);
```

If you run 'mvn test' after having done so, you should be detecting lots of defects:

```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running edu.pitt.cs.CoffeeMakerQuestTest
Tests run: 23, Failures: 21, Errors: 1, Skipped: 0, Time elapsed: 0.61 sec <<< FAILURE!
...
Results :

Failed tests:   testAreDoorsPlacedCorrectly(edu.pitt.cs.CoffeeMakerQuestTest)
  testAreDoorsPlacedCorrectlyMissingSouthDoor(edu.pitt.cs.CoffeeMakerQuestTest)
  testGetCurrentRoom(edu.pitt.cs.CoffeeMakerQuestTest): expected same:<Mock for Room, hashCode: 503144273> was not:<Mock for Room, hashCode: 1009916891>        
  testGetHelpString(edu.pitt.cs.CoffeeMakerQuestTest): java.lang.IllegalArgumentException: object is not an instance of declaring class
  testGetInstructionsString(edu.pitt.cs.CoffeeMakerQuestTest): expected:< INSTRUCTIONS [(N,S,L,I,D,H) ]> > but was:< INSTRUCTIONS []> >
  testHelpString(edu.pitt.cs.CoffeeMakerQuestTest): expected:<[N - Go north(..)
  testIsRoomUnique(edu.pitt.cs.CoffeeMakerQuestTest): java.lang.IllegalArgumentException: object is not an instance of declaring class
  testProcessCommandDLose(edu.pitt.cs.CoffeeMakerQuestTest): expected:<...GAR!(..)
  testProcessCommandDLoseCoffee(edu.pitt.cs.CoffeeMakerQuestTest): expected:<(..)
  testProcessCommandDLoseCoffeeCream(edu.pitt.cs.CoffeeMakerQuestTest): expected:<(..)
  testProcessCommandDLoseCoffeeSugar(edu.pitt.cs.CoffeeMakerQuestTest): expected:<(..)
  testProcessCommandDLoseCream(edu.pitt.cs.CoffeeMakerQuestTest): expected:<(..)
  testProcessCommandDLoseCreamSugar(edu.pitt.cs.CoffeeMakerQuestTest): expected:<(..)
  testProcessCommandDLoseSugar(edu.pitt.cs.CoffeeMakerQuestTest): expected:<(..)
  testProcessCommandDWin(edu.pitt.cs.CoffeeMakerQuestTest): expected:<...gar.(..)
  testProcessCommandI(edu.pitt.cs.CoffeeMakerQuestTest): expected:<[YOU HAVE NO COFFEE!(..)
  testProcessCommandLCoffee(edu.pitt.cs.CoffeeMakerQuestTest): expected:<[There might be something here...(..)
  testProcessCommandLCream(edu.pitt.cs.CoffeeMakerQuestTest): expected:<[There might be something here...(..)
  testProcessCommandLNone(edu.pitt.cs.CoffeeMakerQuestTest): expected:<[You don't see anything out of the ordinary.](..)
  testProcessCommandS(edu.pitt.cs.CoffeeMakerQuestTest): expected:<Mock for Room, hashCode: 254955665> but was:<null>
  testSetCurrentRoom(edu.pitt.cs.CoffeeMakerQuestTest)

Tests in error:
  testProcessCommandLSugar(edu.pitt.cs.CoffeeMakerQuestTest): Index: 6, Size: 6

Tests run: 23, Failures: 21, Errors: 1, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.223 s
[INFO] Finished at: 2022-09-13T16:50:31-04:00
[INFO] ------------------------------------------------------------------------
...
```

Your list of failures may differ depending on what additional test cases you
added.  If see your RentACatTest not catching defects in the buggy
implementation on GradeScope, you can try to see what you may have done wrong
by experimenting CoffeeMakerQuestBuggy (it is the same exact version used on
GradeScope).

**Don't forget to revert your CoffeeMakerQuestTest.java by removing the
'Config.setBuggyCoffeeMakerQuest(true);' line after you are done!**

## Additional Requirements

* Code coverage of the class CoffeeMakerQuestImpl when the JUnit TestRunner is
  run should be at an absolute minimum of **90%**.  If coverage falls below
that number, add more unit tests to CoffeeMakerQuestTest.  View the detailed
line-by-line Jacoco coverage report for CoffeeMakerQuestImpl to see which lines
you are missing and come up with test cases that are able to hit those lines.

* For this program, no requirements are given as the requirement is that you
  mimic the output of the given **coffeemaker.jar** file (note that this jar
file is slightly different from the version provided to you for Deliverable 1
as I have fixed most of the bugs!).  If GradeScope gives you a failure because
your output is different from the reference output, it will show you where the
difference is between brackets [].  In fact, GradeScope itself uses JUnit
behind the scenes to test your program and showing the difference in brackets
is a JUnit assertEquals feature.

* You are asked to complete CoffeeMakerQuestImpl, but there are other support
  classes as well such as Player and Room.  You are expected to use the methods
provided in those classes and not repeat the code somewhere else.  In fact,
this is an important software testability principle called **DRY (Don't Repeat
Yourself)**.  For example, the Player class has the method
**Player.getInventoryString** that prints out the inventory contents based on
the current items.  You are required to use that method and not implement a
similar method of your own.

* Write at least one **private method** while implementing
  CoffeeMakerQuestImpl.  In general, private methods of a Java class work as
"helper" methods that implement a sub-functionality of a public method.  You
have the freedom to choose what sub-functionality you want to encapsulate
within a private method.  Also, add at least one unit test that directly tests
a private method at the very bottom of CoffeeMakerQuestTest.  Use **Java
reflection** to do this.

* Coding style is also important for software quality in the long run (even
  though they are not technically defects as we learned).  In particular, a
uniform naming convention greatly improves the readability of your code.  A
widely used convention is called
[lowerCamelCase](https://en.wikipedia.org/wiki/Naming_convention_(programming)#Java)
convention.  That is the convention that was [first adopted when Sun
Microsystems first created the Java
language](https://www.oracle.com/technetwork/java/codeconventions-135099.html).
This is still the convention at the biggest companies using Java like
[Oracle](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/variables.html)
and [Google](https://google.github.io/styleguide/javaguide.html#s5-naming).
Please make sure you follow the lower camel case convention for all your
variables and methods for this project.  There is less agreement on other
formatting issues like indentation and line wrapping, but try to maintain a
uniform convention whatever you choose.

# Grading

* GradeScope autograder: 70% of grade
* Private method added and tested: 5% of grade
* Source code style (lower camel case naming / formatting): 10% of grade
* Report (including coverage stats): 15% of grade

Please review the grading_rubric.txt for details.

# Submission

Each group will do one submissions to GradeScope as usual.

The submission is done in two parts:

1. Submit your GitHub Classroom Deliverable 2 repository to GradeScope at the
   **Deliverable 2 GitHub** link.  Once you submit, GradeScope will run the
autograder to grade you and give feedback.  If you get deductions, fix your
code based on the feedback and resubmit.  Repeat until you don't get
deductions.

1.  Please use the [ReportTemplate.docx](ReportTemplate.docx) file provided in
    this directory to write a short report.  A PDF version of the file is at
[ReportTemplate.pdf](ReportTemplate.pdf).  On the first page introduction,
please describe the division of work between group members and also any
difficulties you faced while using JUnit.  On the second page, paste a
screenshot of code coverage stats **after** having completed the coding.
Please refer to [Exercise 2](/exercises/2#measuring-code-coverage) on how to
create the screenshot.  Submit to GradeScope at the **Deliverable 2 Coverage**
link.  Your screenshot should look like this:

   <img alt="Code Coverage Jacoco" src=code_coverage_jacoco.png width=700>

   Make sure that the coverage of CoffeeMakerQuestImpl is showing and the
overall coverage is above **90%** as shown above.

# GradeScope Feedback

It is encouraged that you submit to GradeScope early and often.  Please use the
feedback you get on each submission to improve your code!

The GradeScope autograder works in 3 phases:

1. CoffeeMakerQuestImpl verification using CoffeeMakerQuestTestSolution:
   CoffeeMakerQuestTestSolution is the solution implementation of
CoffeeMakerQuestTest.  The purpose of this phase is to verify that CoffeeMakerQuestImpl (your CoffeeMakerQuest implementation) does not have any defects.

1. CoffeeMakerQuestTest on CoffeeMakerQuestSolution: CoffeeMakerQuestTest is your submitted JUnit test for CoffeeMakerQuest.  The purpose of this phase is
   to test CoffeeMakerQuestTest itself for defects.  CoffeeMakerQuestSolution is the solution implementation of CoffeeMakerQuest and contains no defects (that I know of).  Hence, all tests in CoffeeMakerQuestTest should pass.

1. CoffeeMakerQuestTest on CoffeeMakerQuestBuggy: CoffeeMakerQuestTest is your submitted JUnit test for CoffeeMakerQuest.  The purpose of this phase is
   to test CoffeeMakerQuestTest against the buggy CoffeeMakerQuestBuggy
implementation.  The class CoffeeMakerQuestBuggy is given to you as part of
the coffeemaker.jar file.  Since CoffeeMakerQuestBuggy is buggy, you
expect the tests to fail this time.  If CoffeeMakerQuestTestSolution fails a
test but CoffeeMakerQuestTest passes a test (or vice versa), then this indicates a problem.

# Groupwork Plan

Just like for Exercise 2, I recommend that you divide the list of methods to
implement / test into two halves and working on one half each.  Please document
how you divided the work in your report.

# Resources

These links are the same ones posted at the end of the slides:

* JUnit User manual:  
https://junit.org/junit5/docs/current/user-guide/  
The Writing Tests section is probably the most useful.

* JUnit Reference Javadoc:  
http://junit.sourceforge.net/javadoc/  
For looking up methods only, not a user guide.

* Mockito User Manual:  
https://javadoc.io/static/org.mockito/mockito-core/3.2.4/org/mockito/Mockito.html  
Most useful is the sections about verification and stubbing.

* Jacoco User Manual:  
https://www.jacoco.org/userdoc/index.html
