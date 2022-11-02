- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  - [Overview](#overview)
  - [Background](#background)
  - [Compiling and Running](#compiling-and-running)
    - [Luck Mode](#luck-mode)
    - [Skill Mode](#skill-mode)
    - [Text UI Mode](#text-ui-mode)
  - [What to do](#what-to-do)
    - [Task 1: Write Plain JUnit Tests](#task-1-write-plain-junit-tests)
    - [Task 2: Write BeanCounterLogicImpl and BeanImpl implementations](#task-2-write-beancounterlogicimpl-and-beanimpl-implementations)
    - [Task 3: Generate Paths for Different Machine Configurations in JPFJUnitTest.java](#task-3-generate-paths-for-different-machine-configurations-in-jpfjunittestjava)
    - [Task 4: Write Property-based tests in JPFJUnitTest.java](#task-4-write-property-based-tests-in-jpfjunittestjava)
    - [Task 5: Add an Extra Property-based Test to JPFJUnitTest.java](#task-5-add-an-extra-property-based-test-to-jpfjunittestjava)
    - [Task 6: Linting and Auditing](#task-6-linting-and-auditing)
    - [Task 7: Manual System Testing](#task-7-manual-system-testing)
- [Grading](#grading)
- [Submission](#submission)
- [GradeScope Feedback](#gradescope-feedback)
- [Resources](#resources)

# CS 1632 - Software Quality Assurance

Fall Semester 2022

DUE: December 16 (Friday), 2022 11:59 PM

Due to the grading deadline, there is no late submission.  Please work with your partner to complete the project.

**GitHub Classroom Link:** https://classroom.github.com/a/4cgs_K_t

## Overview

For this final deliverable, you will develop a full-fledged GUI program
(with the help of some skeleton code), along with various tests.  Don't
worry if you don't know GUI programming --- that part has already been done
for you.  In addition, there is a [Text UI mode](#text-ui-mode) that you can
use to manual test and debug your program.

All the projects so far have used some form of dynamic testing.  In this
project, we will focus grading on static testing.  You will be graded
upon is static testing techniques such as: linting, pattern-based bug finding,
and model checking.  

But you are still expected to use all the techniques we have learned so far:
test-driven development (TDD), automated unit testing, code coverage, and
manual testing to name a few to come up with good quality software.  The
GradeScope autograder will do extensive testing to test various features of
the software.  If any of the tests fail, that means you have not thoroughly
tested your software.

* IMPORTANT: You need Java 8 (1.8.0.231, preferably) to run the Java Path
  Finder model checker.  Make sure you have the correct Java version by doing
"java -version" and "javac -version" before going into the JPF section.  If you
don't have the correct version, here is a link to a folder with installation
packages for each OS:

https://drive.google.com/drive/folders/1E76H7y2nMsrdiBwJi0nwlzczAgTKKhv7

## Background

The bean counter is a device for statistics experiments devised by English
scientist Sir Francis Galton. It consists of an upright board with evenly
spaced pegs in a triangular form.  Beans are dropped from an opening at the top
of the board. Every time a bean hits a peg, it has a 50% chance of falling to
the left or to the right.  In this way, each bean takes a random path and
eventually falls into one of the slots at the bottom of the board.  After all
the beans fall through, the number of beans in each slot is counted.

See the following link for a more detailed description of the machine:
https://en.wikipedia.org/wiki/Bean_machine.

The bean counter had two contributions to statistics by demonstrating the following:
1. When the sample size is large enough, a [binomial distribution](https://en.wikipedia.org/wiki/Binomial_distribution) approaches a [bell curve](https://en.wikipedia.org/wiki/Normal_distribution).
2. It also demonstrated a phenomenon named [regression to the mean](https://en.wikipedia.org/wiki/Regression_toward_the_mean).

Regression to the mean had been (and still is) a source of numerous scientific
misconceptions.  People make conjectures all the time about all types of things
and provide reasons for it.

1. Why is my favorite sports team performing in a mediocre way when it won the championships last year?  Because my favorite player was traded.
2. Why did the crime rate in my city fall down to the national average?  Due to better policing.
3. Why did a student who did exceptionally well on the midterms perform just about average on the finals?  Because the student slacked off.

People always look for reasons for changes in data.  But often the reason
cannot be explained, because there was no reason for the change to begin with.
The change in data can just be due to a statistical anomaly called "regression
to the mean".  For example, an answer to question 3 can simply be that the
student was exceptionally lucky during the midterms (she guessed all multiple
choices and she got them all correct).  In the finals, her luck just wore off
and she got what she deserved.  This is called regression to the mean.
When a data point is on the extremes of the bell curve, it is often not because
there is anything special about that data point, it is because the laws of
probability worked in favor of it (or against it, depending on context) for
that particular trial.  If that's the case, chances are that the data point
will move to the mean in the next trial.

Now if the exceptional score was due to skill, then the regression would not
happen unless there was a regression in skill.  The problem is, it is very hard
to tell whether something was due to luck or skill just by looking at the
results, hence the numerous misconceptions.

## Compiling and Running

The program simulates a bean machine with 10 slots at the bottom (0-9).

Let's first compile the program by invoking Maven compile:

```
mvn compile
```

The program is executed with two commandline arguments:

```
$ java -cp target/classes edu.pitt.cs.BeanCounterGUI
Usage: java BeanCounterGUI <number of beans> <luck | skill>
Example: java BeanCounterGUI 500 luck 
```

The second argument "luck" or "skill" decides whether individual beans will use
luck or skill in navigating the bean machine.  

Let's do some exploratory testing.  You could use the class files that you have
just compiled, but the app doesn't do much at this point because the internal
logic has not yet been implemented (by you).  Instead, let's use a reference
implementation (that I wrote) named BeanCounterSolution.jar.

### Luck Mode

In luck mode, the bean counter operates conventionally as originally built by
Galton: a bean has an equal chance of going left or right on a peg.  So where
the bean lands at the bottom is purely due to luck.  Hence, you would expect
the beans to be heavily susceptible to regression to the mean.  Try the following:

1. Run BeanCounterSolution.jar in luck mode:
```
java -jar BeanCounterSolution.jar 500 luck
```
2. Press the "Fast" button to fast-forward to the end.
3. Note the average (should be close to 4.5 = 0 + 9 / 2).
4. Press the "Upper Half" button to just take the upper half of the sample.
5. Note the average (now it should be much higher since it's the upper half).
6. Press the "Repeat" button to scoop all the beans and bring them to the top.
7. Press the "Fast" button to restart the machine.
8. Note the average is again close to 4.5.

You have just observed regression to the mean.  You took the upper half of the
class, but when they were put through the exam again, they scored just about
average.  Did they slack off in the second exam?  No, they were just no better
than the other students to begin with.

To run your own (currently incomplete) code, you will be doing:

```
java -cp target/classes edu.pitt.cs.BeanCounterGUI 500 luck
```

### Skill Mode

In skill mode, the beans choose direction based on pure skill.  Each bean is
assigned a skill level from 0-9 on creation according to a bell curve with
average 4.5 and standard deviation 1.5.  A skill level of 9 means the bean
always makes the "right" choices (pun intended).  That means the bean will
always go right when a peg is encountered, resulting it falling into the
rightmost 9th slot. A skill level of 0 means that the bean will always go left,
resulting it falling into the leftmost 0th slot. For the in-between skill
levels, the bean will first go right then left. For example, for a skill level
of 7, the bean will go right 7 times then go left twice.  So where the bean
lands at the bottom would be determined entirely by the skill level of the
bean.  Try the following:

1. Run BeanCounterSolution.jar in skill mode:
```
java -jar BeanCounterSolution.jar 500 skill
```
2. Press the "Fast" button to fast-forward to the end.
3. Note the average (should be close to 4.5 = 0 + 9 / 2).
4. Press the "Upper Half" button to just take the upper half of the sample.
5. Note the average (now it should be much higher since it's the upper half).
6. Press the "Repeat" button to scoop all the beans and bring them to the top.
7. Press the "Fast" button to restart the machine.
8. Note the average is exactly the same as the average noted in Step 5.

You will observe that the average does not change at all when you repeat the
experiment with the upper half of the samples.  There is no regression to the
mean because the results are pre-determined by skill level.  In this case, the
student performed well on the first exam because they were actually skilled!

Also, you will notice the slots filling sequentially one by one in the repeat
run.  This is a side-effect of the slots at the bottom being collected one by
one when the repeat button is pressed.  All the beans in one slot have the same
skill level so the beans naturally get sorted out as a result of the
collection.

To run your own (currently incomplete) code, you will be doing:
```
java -cp target/classes edu.pitt.cs.BeanCounterGUI 500 skill
```

### Text UI Mode

You will notice that BeanCounterLogicImpl.java has an alternate main()
method.  This main() method is used to provide a rudimentary text user
interface.  You can invoke it by doing:

```
java -cp BeanCounterSolution.jar edu.pitt.cs.BeanCounterLogicSolution 10 500 luck debug
```

This is the usage information that gets printed when you don't pass any arguments:

```
Usage: java BeanCounterLogic slot_count bean_count <luck | skill> [debug]
Example: java BeanCounterLogic 10 400 luck
Example: java BeanCounterLogic 20 1000 skill debug
```

The last optional debug enables verbose output that prints the state of the
bean counter at each step.  This makes debugging easier as it is able to test
your bean counter logic in isolation from the GUI.  It also allows you to
adjust the slot count, something which is not possible with the GUI.

To run your own (currently incomplete) code, you will be doing:
```
java -cp target/classes edu.pitt.cs.BeanCounterLogicImpl 10 500 luck debug
```

## What to do

Here is a list of files and their contents:

* BeanCounterLogicImpl.java - The core logic of the bean machine.  Maintains a
  list of beans that fall one step in the machine whenever advanceStep() is
called.  Maps beans into a logical coordinate system.  Also contains a main
method which implements the Text UI of the program. (**modify**)

* BeanImpl.java - The Bean implementation.  Maintains the x-coordinate of the
  bean, as well as how many right moves are remaining if in skill mode.
Governs the movement of that particular bean when choose() is called, depending
on whether the bean is a skilled bean or a lucky bean.  The Random number
generator that gives randomness to the movement is injected in the BeanImpl
constructor for easier testing. (**modify**)

* JPFJUnitTest.java - The JUnit test class for the BeanCounterLogicImpl
  class composed entirely of property-based tests.  Depending on Config, it
either runs in plain JUnit mode or JPF on JUnit mode as can be seen in the
setUp() method.  In plain JUnit mode, a particular machine configuration is
chosen for testing (5 slots, 3 beans, luck mode).  Also the Random number
generator is seeded with 42 to make tests reproducible.  In JPF on JUnit mode,
you are asked to exhaustively test different machine configurations (see [Model
Checking Using JUnit](#model-checking-using-junit)) and all random numbers will
be exhaustively tested too. (**modify**)

* PlainJUnitTest.java - The JUnit test class used by GradeScope to autograde
  your implementation.  It only runs in plain JUnit mode since it contains
non-property-based tests.  It contains input-specific tests that are not
covered in JPFJUnitTest.java.  Currently, only the testReset method is
implemented and you need to fill in the // TODO comments to have a full test
suite.  (**modify**)

* Config.java - The high level configuration of the program.  Controls
  LogicType (your impl, buggy, or solution) and TestType (plain JUnit or JPF on
JUnit).

* BeanCounterLogic.java - The public interface of BeanCounterLogic.  The
  createInstance method creates an instance of BeanCounterLogicImpl (your
code), BeanCounterLogicBuggy (a buggy implementation), or
BeanCounterLogicSolution (the solution implementation) based on Config.  Do not
modify.

* Bean.java - The public interface of Bean.  The createInstance method creates
  an instance of BeanImpl (your code), BeanBuggy (a buggy implementation), or
BeanSolution (the solution implementation) based on Config.

* BeanCounterGUI.java - Contains the main method for the GUI interface of the
  program.  Creates a MainFrame.

* MainFrame.java - Contains the MainPanel and the ButtonPanel.

* MainPanel.java - The main display of the program where all the bean machine
  animations happen.  Renders the state of BeanCounterLogic on to the window
coordinate system (the "physical" coordinates).  In the process the logical
coordinates of Beans are translated to physical coordinates.

* <Some>Button.java - GUI buttons at the bottom of the main frame, along with
  event handlers when the button is pressed.

* BeanCounterSolution.jar - The solution implementation of bean counter.

* BeanCounterBuggy.jar - A buggy implementation of bean counter.

* runJPF\*.bat / runJPF\*.sh - Scripts to run JUnit tests on bean machine with JPF enabled.

* BeanCounter.win.jpf, BeanCounter.macos.jpf - JPF configuration files for Windows or Mac/Linux

You are asked to fill in and modify 3 files: BeanCounterLogicImpl.java,
BeanImpl.java, JPFJUnitTest.java.  The first two files complete the
bean counter implementation.  The last file tests the implementation using the
Java Path Finder model checker via the JUnit framework.  Take care that you
limit your modifications to these three files as all the other files will be
ignored in your submission.  Also, take care that you do not change the public
interfaces of BeanCounterLogic and Bean as GradeScope relies on them.

I expect you to employ test-driven development (TDD) for this project and fully
embrace it.  I can guarantee you that it will shorten development time.  You
are going to write the tests anyway.  Why not write them at the beginning when
they will be the most useful?  I will lay down the steps, roughly in the order
you should perform them.

### Task 1: Write Plain JUnit Tests

Start by completing the tests in [PlainJUnitTest.java](src/test/java/edu/pitt/cs/PlainJUnitTest.java)
by replacing the // TODO comments.  One test testReset() is already implemented
for you.  These are the tests that GradeScope is going to run to verify your
implementation.

Note that we are not mocking the Bean objects, even though the Bean class is an
external class from the perspective of the BeanCounterLogicImpl class that we
are testing.  This is intentional.  In this case, I made a conscious decision
that I wanted to integration test the entire application, instead of mocking
external objects for unit testing.

1. To test your BeanCounterLogicImpl implementation simply do Maven test:

   ```
   mvn test
   ```

   This will invoke the two JUnit classes in the source tree: PlainJUnitTest.java
   and JPFJUnitTest.java.  When run as part of Maven test, JPFJUnitTest runs
   in plain JUnit mode and tests a machine with slotCount = 5 and beanCount = 3
   in luck mode.  The tests for JPFJUnitTest is yet to be implemented so they will
   all pass.  PlainJUnitTest has the testReset method implemented, and it should
   fail since BeanCounterLogicImpl currently does not properly reset:

   ```
   ...
   -------------------------------------------------------
   T E S T S
   -------------------------------------------------------
   Running edu.pitt.cs.JPFJUnitTest
   Failure in (slotCount=5, beanCount=3, isLucky=true):
   Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.092 sec
   Running edu.pitt.cs.PlainJUnitTest
   Tests run: 8, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.006 sec <<< FAILURE!
   testReset(edu.pitt.cs.PlainJUnitTest)  Time elapsed: 0.001 sec  <<< FAILURE!
   java.lang.AssertionError: [Slot Count = 1] Test case with 2 initial beans failed. Check on remaining bean count expected:<1> but was:<0>
   ...
   ```

1. To test your test cases against the BeanCounterLogicSolution implementation:

   First, modify the following line in Config.java:

   ```
   private static LogicType logicType = LogicType.IMPL;
   ```

   to:

   ```
   private static LogicType logicType = LogicType.SOLUTION;
   ```

   This ensures that all objects created by
BeanCounterLogic.createInstance(int) are objects of the corresponding logic
type.  Now, there is one place where the class name is hard-coded in your code
and that is in the call to BeanCounterLogicImpl.main(String[]) in
PlainJUnitTest.testMain().  So please change the call to either
BeanCounterLogicSolution.main(String[]), if you want to call testMain() against
the solution.

   After you make these changes, do Maven test:

   ```
   mvn test
   ```

   Since this is the defect-free solution implementation, it should not display any failures:


   ```
   -------------------------------------------------------
    T E S T S
   -------------------------------------------------------
   Running edu.pitt.cs.JPFJUnitTest
   Failure in (slotCount=5, beanCount=3, isLucky=true):
   Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.092 sec
   Running edu.pitt.cs.PlainJUnitTest
   Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.001 sec

   Results :

   Tests run: 15, Failures: 0, Errors: 0, Skipped: 0

   [INFO] ------------------------------------------------------------------------
   [INFO] BUILD SUCCESS
   [INFO] ------------------------------------------------------------------------
   ...
   ```

   This configuration can be used to verify that your test cases correctly
pass a defect-free implementation, so that you can be sure you have a correct
understanding of expected behavior.

   WARNING: Don't forget to revert back the changes, or you will keep
testing the solution implementation while developing your implementation.

1. To test your test cases against the BeanCounterLogicBuggy implementation:

   Firt, modify the following line in Config.java:

   ```
   private static LogicType logicType = LogicType.IMPL;
   ```

   to:

   ```
   private static LogicType logicType = LogicType.BUGGY;
   ```

   Again, just like when testing the solution,
BeanCounterLogicImpl.main(String[]) in PlainJUnitTest.testMain() needs to be
changed to BeanCounterLogicBuggy.main(String[]), , if you want to call
testMain() against the buggy implementation.

   After the changes, do Maven test:

   ```
   mvn test
   ```

   And you will again get BUILD SUCCESS with no failures.  So the reset(Bean[])
method is implmented correct apparently even in the buggy implementation, but
many parts are not.  Most of the bugs in this buggy implementation will remain
hidden until we do rigorous state space exploration using JPF.

   To see the bugs with your own eyes, you only need to try invoking the main method inside
BeanCounterLogicBuggy to see that something is not quite right:

   ```
   java -cp BeanCounterBuggy.jar edu.pitt.cs.BeanCounterLogicBuggy 20 400 luck
   ```

   Then you will get something like (with some random variations):

   ```
   Slot bean counts:
     76   0   0   0   2   8  10  36  73  65   0  62  37  24   6   0   1   0   0   0
   ```

   Note that there are a lot of beans in the first slot for some reason.

   On the other hand, if you experiment with 10 slots:

   ```
   java -cp BeanCounterBuggy.jar edu.pitt.cs.BeanCounterLogicBuggy 10 400 luck
   ```

   Then you will get something like (with some random variations):

   ```
   Slot bean counts:
     0   7  22  69  92 100  49  21   3   0
   ```
   
   Note that now things look pretty normal, but if you look closely, you will
notice that if you add up all the beans they sum up to only 363 and not 400!
As you can see, it is going to be very hard to find these kind of bugs with
just plain JUnit testing unless you know exactly what you are looking for and
what commandline arguments to pass.  Also, the results are non-deterministic and
hence unreproducible, making testing unreliable.  We need to run JUnit on top
of JPF to find these types of bugs in a reliable manner (later in Task 3).

   WARNING: Again, don't forget to revert back you changes.

### Task 2: Write BeanCounterLogicImpl and BeanImpl implementations

All the GUI coding has already been done for you, since some of you are not
familiar with Java AWT and event-driven programming.  You only need to
implement the logic of the machine.  You will only need to modify files
BeanCounterLogicImpl.java and BeanImpl.java.  You will not need to modify any
of the other files.  As you are coding, regularly run the JUnit tests you wrote
in Task 1, both to check that the coded feature was properly implemented and
that you have not regressed.  Also, manual test the feature using either the
GUI or the Text UI as you see fit.  Your goal in coding should be to make those
tests pass (TDD).

The parts in code that you will likely have to fill in is marked with // TODO
comments.  Please add private member variables and helper methods as needed.
Inside BeanCounterLogicImpl, you will need data structures to represent 1)
beans that are waiting to fall down in the top reservoir, 2) beans that are
in-flight bouncing on pegs, and 3) beans that have fallen into slots at the
bottom.

It is your decision what data structures you use.  Note that the in-flight
beans form a "stream" when falling down, meaning that there is always at most
one bean per peg level, and there is a sequential order in the beans as they
fall down.  So the most appropriate data structure would be a linear data
structure with a notion of ordering.

Each in-flight bean has a logical X-coordinate and Y-coordinate in the logical
coordinate system displayed at the top of BeanCounterLogicImpl.java.  At each
simulation step, BeanCounterLogicImpl.advanceStep() is called (see the main
method in BeanCounterLogicImpl that implements the Text UI).  In
BeanCounterLogicImpl.advanceStep(), call advanceStep() on each of the in-flight
Beans to update their X and Y coordinates accordingly.

In order to get the bell curve in skill mode, you will have to use the
Random.nextGaussian() method.  A bell curve is synonymous with normal
distribution is synonymous with Gaussian distribution, hence the name.  Here is
the formula you should use:

```
SKILL_AVERAGE = (double) (SLOT_COUNT - 1) * 0.5
SKILL_STDEV = (double) Math.sqrt(SLOT_COUNT * 0.5 * (1 - 0.5))
SKILL_LEVEL = (int) Math.round(rand.nextGaussian() * SKILL_STDEV + SKILL_AVERAGE)

SKILL_LEVEL = SKILL_LEVEL > (SLOT_COUNT - 1) ? (SLOT_COUNT - 1) : SKILL_LEVEL;
SKILL_LEVEL = SKILL_LEVEL < 0 ? 0 : SKILL_LEVEL;
```

SKILL\_AVERAGE and SKILL\_DEV are the average and standard deviation of the
skill level that would get you the normal distribution that approximates the
binomial distribution created by "luck" mode.  If you are curious about how
those values were computed, refer to this [Wikipedia section on binomial
distribution approximation](https://en.wikipedia.org/wiki/Binomial_distribution#Normal_approximation).
The last two lines make sure that SKILL_LEVEL is within the bounds of \[0, SLOT_COUNT - 1\].

### Task 3: Generate Paths for Different Machine Configurations in JPFJUnitTest.java

By now we are somewhat confident that we have a mostly working application.
But as noted earlier with the buggy implementation, there still might be bugs
lurking because we did not test all different combinations of machine
configurations and also did not test all the random variations that a machine
go through even for a single configuration.  For that we need model checking
using JPF.

Notice that I have intentionally separated out the logic part of the program
from the GUI.  This was done to make model checking easier.  Model checking a
GUI is tricky and so is a multi-threaded event-driven program like
BeanCounterGUI.  Yes, JPF can model check even multi-threaded programs (!) by
exhaustively going through all the interleavings.  But it is complicated and it
takes a long time because it has to go through many more states.  So we will
just check the core logic (BeanCounterLogic), which is the important part
anyway.

Just like for Exercise 5, we are going to employ a TestRunner.java that can
invoke the JPFJUnitTest JUnit class running on top of JPF.  The PlainJUnitTest
JUnit class is not suitable for running on JPF so we will ignore it.  Running
TestRunner without any arguments will result in the following message:

```
$ java -cp target/test-classes edu.pitt.cs.TestRunner
Usage: TestRunner <logic type (impl | solution | buggy)> <test type (junit | trace)>
```

The logic type can be either "impl", "solution", or "buggy" and decides whether
you want to test your own implementation, or the solution implementation, or
the buggy implementation.  

The test type can be either "junit" or "trace",  The former invokes the JUnit
framework on JPFJUnitTest that allows you to see all failures at once.  The
latter calls the @Test methods in JPFJUnitTest directly without giving the
JUnit framework a chance to catch any exceptions raised.  That allows the very
first exception raised due to a failure to be caught by JPF and have it
generate a trace of the path leading to that failure.

Running JPFJUnitTest on JPF using TestRunner is going to allow the model
checker to exhaustively explore all paths generated by random number generation
and user inputs (when replaced as Verify API calls), just like we did for
Exercise 5.  So that, if the property-based tests in JPFJUnitTest all pass,
then we can be assured that they all pass for all random executions for all the
enumerated inputs.

In fact, the first thing you should do in JPFJUnitTest.java is to modify the
setUp() method to insert the Verify calls afore mentioned.  The three input
values relevant here are: slot count, bean count, and the boolean value isLuck
("luck" or "skill" mode).  Once you insert the Verify calls, JPF will explore
each combination of input values.  As described in the "// TODO" comment in the
setUp() method, verify 1-5 slot count, 0-3 bean count, and both "luck" and
"skill" modes.  We will not test slot count 0 because then it means there are
no slots to receive beans and the machine basically falls apart.  Although the
range of values is not exhaustive, these are enough values to give us
confidence that our machine works, while ensuring that JPF terminates within
more or less 5 seconds to not lengthen turnaround time.

The testReset() method contains a println statement inserted in order to
demonstrate to you all the combinations of input values JPF explores.  Let's
see what it prints out initially without Verify API calls.  Try the following
command.

On Windows:

```
.\runJPF.bat BeanCounter.win.jpf
```

Or on Mac or Linux:

```
bash runJPF.sh BeanCounter.macos.jpf
```

That should print out an output like the following:

```
JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.


====================================================== system under test
edu.pitt.cs.TestRunner.main("impl","junit")

====================================================== search started: 7/27/22 2:00 PM
TESTING YOUR IMPLEMENTATION WITH JPF USING JUNIT FRAMEWORK

Failure in (slotCount=0, beanCount=0, isLucky=false):

====================================================== results
no errors detected

====================================================== statistics
...
```

After inserting the Verify calls, JPF should give you an output like this:

```
JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.


====================================================== system under test
edu.pitt.cs.TestRunner.main("impl","junit")

====================================================== search started: 7/27/22 10:14 PM
TESTING YOUR IMPLEMENTATION WITH JPF USING JUNIT FRAMEWORK

Failure in (slotCount=1, beanCount=0, isLucky=false):
Failure in (slotCount=1, beanCount=0, isLucky=true):
Failure in (slotCount=1, beanCount=1, isLucky=false):
Failure in (slotCount=1, beanCount=1, isLucky=true):
...
Failure in (slotCount=5, beanCount=3, isLucky=false):
Failure in (slotCount=5, beanCount=3, isLucky=true):

====================================================== results
no errors detected

====================================================== statistics
...
```

You can see how JPF exhaustively tries out all possible combinations of machine
configurations.  Since the println was just for demonstration purposes, please
remove it in your final submission and replace it with the actual test.

### Task 4: Write Property-based tests in JPFJUnitTest.java

Complete [JPFJUnitTest.java](src/test/java/pitt/edu/cs/JPFJUnitTest.java) by
replacing the // TODO comments.  Pay close attention to the invariants you are
asked to test described in the Javadoc comment above each @Test method.  I
recommend that you always insert the failString that I initialized for you as
the first argument of every JUnit assert call so that you get that as part of
your failure message.  The failString describes the machine configuration that
is being currently tested and it will tell you which configuration led to the
failure.

A good place to start is the testReset method, since there is already a very
similar test method in PlainJUnitTest.java.  In fact, I suggest that you copy
over the code from that class (along with any helper methods that it calls).
You only need to make minor modifications to make it work for the JPFJUnitTest.
This does violate DRY principles, but I will let you off the hook for this one.
:)

After having completed testReset, let's try testing it on your implementation,
the solution implementation, and the buggy implementation as we did for plain
JUnit testing.

1. To test your BeanCounterLogicImpl implementation on Windows:

   ```
   .\runJPF.bat BeanCounter.win.jpf
   ```

   Or on Mac or Linux:

   ```
   bash runJPF.sh BeanCounter.macos.jpf
   ```

   Hopefully, you have properly implemented the reset method by now, so you
should see no failures.  Just for the sake of making sure that testReset is
working properly, let's do a simple experiment.  Back up your
BeanCounterLogicImpl.java and BeanImpl.java files, and replace them with the
original versions in the course repository.  After having done so, you would
get the following failures:

   ```
   JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.


   ====================================================== system under test
   edu.pitt.cs.TestRunner.main("impl","junit")

   ====================================================== search started: 7/27/22 11:33 PM
   TESTING YOUR IMPLEMENTATION WITH JPF USING JUNIT FRAMEWORK

   testReset(edu.pitt.cs.JPFJUnitTest): Failure in (slotCount=1, beanCount=1, isLucky=false):. Check on in-flight bean count expected:<1> but was:<0>
   testReset(edu.pitt.cs.JPFJUnitTest): Failure in (slotCount=1, beanCount=1, isLucky=true):. Check on in-flight bean count expected:<1> but was:<0>
   testReset(edu.pitt.cs.JPFJUnitTest): Failure in (slotCount=1, beanCount=2, isLucky=false):. Check on remaining bean count expected:<1> but was:<0>
   testReset(edu.pitt.cs.JPFJUnitTest): Failure in (slotCount=1, beanCount=2, isLucky=true):. Check on remaining bean count expected:<1> but was:<0>
   ...
   testReset(edu.pitt.cs.JPFJUnitTest): Failure in (slotCount=5, beanCount=2, isLucky=false):. Check on remaining bean count expected:<1> but was:<0>
   testReset(edu.pitt.cs.JPFJUnitTest): Failure in (slotCount=5, beanCount=2, isLucky=true):. Check on remaining bean count expected:<1> but was:<0>
   testReset(edu.pitt.cs.JPFJUnitTest): Failure in (slotCount=5, beanCount=3, isLucky=false):. Check on remaining bean count expected:<2> but was:<0>
   testReset(edu.pitt.cs.JPFJUnitTest): Failure in (slotCount=5, beanCount=3, isLucky=true):. Check on remaining bean count expected:<2> but was:<0>

   ====================================================== results
   no errors detected

   ====================================================== statistics
   ```

1. To generate a trace of the first failure in BeanCounterLogicImpl, on Windows:

   ```
   .\runJPFTrace.bat BeanCounter.win.jpf
   ```

   Or on Mac or Linux:

   ```
   bash runJPFTrace.sh BeanCounter.macos.jpf
   ```

   If you haven't yet reverted BeanCounterLogicImpl and BeanImpl, you would see the following trace for the first failure seen above:

   ```
   JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.


   ====================================================== system under test
   edu.pitt.cs.TestRunner.main("impl","trace")

   ====================================================== search started: 7/28/22 1:03 AM
   TESTING YOUR IMPLEMENTATION WITH JPF USING JUNIT EMULATION FOR TRACING


   ====================================================== error 1
   gov.nasa.jpf.vm.NoUncaughtExceptionsProperty
   java.lang.reflect.InvocationTargetException: java.lang.AssertionError
	   at org.junit.Assert.fail(org/junit/Assert.java:88)
	   at org.junit.Assert.failNotEquals(org/junit/Assert.java:834)
	   at org.junit.Assert.assertEquals(org/junit/Assert.java:645)
	   at edu.pitt.cs.JPFJUnitTest.testReset(edu/pitt/cs/JPFJUnitTest.java:138)
	   at java.lang.reflect.Method.invoke(gov.nasa.jpf.vm.JPF_java_lang_reflect_Method)
	   at edu.pitt.cs.TestRunner.main(edu/pitt/cs/TestRunner.java:65)
   Caused by: java.lang.AssertionError: Failure in (slotCount=1, beanCount=1, isLucky=false):. Check on in-flight bean count expected:<1> but was:<0>
	   at org.junit.Assert.fail(org/junit/Assert.java:88)
	   at org.junit.Assert.failNotEquals(org/junit/Assert.java:834)
	   at org.junit.Assert.assertEquals(org/junit/Assert.java:645)
	   at edu.pitt.cs.JPFJUnitTest.testReset(edu/pitt/cs/JPFJUnitTest.java:138)
	   at java.lang.reflect.Method.invoke(gov.nasa.jpf.vm.JPF_java_lang_reflect_Method)
	   at edu.pitt.cs.TestRunner.main(edu/pitt/cs/TestRunner.java:65)


   ====================================================== trace #1
   ------------------------------------------------------ transition #0 thread: 0
   gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"ROOT" ,1/1,isCascaded:false}
	 [3168 insn w/o sources]
   edu/pitt/cs/TestRunner.java:25 : Config.setTestType(TestType.JPF_ON_JUNIT);
   ...
   edu/pitt/cs/JPFJUnitTest.java:138 : assertEquals(failString + ". Check on in-flight bean count",
   [2 insn w/o sources]
   edu/pitt/cs/JPFJUnitTest.java:139 : inFlightExpected, inFlightObserved);
   edu/pitt/cs/JPFJUnitTest.java:138 : assertEquals(failString + ". Check on in-flight bean count",
   [169 insn w/o sources]

   ====================================================== snapshot #1
   ...
   ```

   Whenever there is a test failure, you can use the above trace to trace
through the code and figure out what may have gone wrong.  Now please revert
BeanCounterLogicImpl and BeanImpl back to your working files now that testReset
is working!

1. To test the BeanCounterLogicSolution implementation on Windows:

   ```
   .\runJPFSolution.bat BeanCounter.win.jpf
   ```
   Or on Mac or Linux:

   ```
   bash runJPFSolution.sh BeanCounter.macos.jpf
   ```

   Your testReset should definitely pass.  If it doesn't, the test has a problem.

1. To test the BeanCounterLogicBuggy implementation on Windows:

   ```
   .\runJPFBuggy.bat BeanCounter.win.jpf
   ```
   Or on Mac or Linux:

   ```
   bash runJPFBuggy.sh BeanCounter.macos.jpf
   ```

   Again, the reset method in the buggy implementation is fine.  But once you
complete the implementation of JPFJUnitTest, JPF will uncovers some hidden
defects that we missed before in plain JUnit testing:

   ```
   JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.


   ====================================================== system under test
   edu.pitt.cs.TestRunner.main("buggy","junit")

   ====================================================== search started: 7/28/22 1:13 AM
   TESTING BUGGY IMPLEMENTATION WITH JPF USING JUNIT FRAMEWORK

   testAdvanceStepCoordinates(JPFJUnitTest): Failure in (slotCount=2, beanCount=1, isLucky=true):
   testLowerHalf(JPFJUnitTest): Failure in (slotCount=2, beanCount=3, isLucky=false): expected:<2> but was:<1>
   testAdvanceStepBeanCount(JPFJUnitTest): Failure in (slotCount=2, beanCount=3, isLucky=false): expected:<3> but was:<2>
   testAdvanceStepPostCondition(JPFJUnitTest): Failure in (slotCount=2, beanCount=3, isLucky=false): expected:<3> but was:<2>
   testUpperHalf(JPFJUnitTest): Failure in (slotCount=2, beanCount=3, isLucky=false): expected:<2> but was:<1>
   testRepeat(JPFJUnitTest): Failure in (slotCount=4, beanCount=3, isLucky=false): expected:<3> but was:<2>

   ====================================================== results
   no errors detected

   ====================================================== statistics
   elapsed time:       00:00:06
   states:             new=4155,visited=3529,backtracked=7684,end=467
   search:             maxDepth=65,constraints=0
   choice generators:  thread=3 (signal=0,lock=1,sharedRef=0,threadApi=0,reschedule=2), data=3834
   heap:               new=383657,released=242713,maxLive=2321,gcCycles=7659
   instructions:       15150745
   max memory:         700MB
   loaded code:        classes=343,methods=4714

   ====================================================== search finished: 7/28/22 1:13 AM
   ```    

Now go ahead and implement all the other property-based tests and see if you
can catch the defects in the buggy implementation as seen above.  Also make
sure that all test cases pass with the solution implementation.  Now armed with
confidence in the efficacy and accuracy of your tests, apply them to your own
implementation and verify that it is defect free (at least in regards to the
properties tested).

### Task 5: Add an Extra Property-based Test to JPFJUnitTest.java

_Add one more test case of your own that helps you verify some invariant
property_.  Add that test at the very end.  Make sure you test a new invariant
that has not yet been tested (as in all the previous tests can pass and the new
test still fail).  There are many many invariants yet to be tested: be
creative!  Remember, it has to be an invariant.  If you write a test applicable
to a specific input combination using if statements, it doesn't count.  Make
sure that the test case is well documented with a Javadoc comment just like
other methods.

### Task 6: Linting and Auditing

Run the CheckStyle linter and the SpotBugs tool regularly while and after
coding.  Use the Maven site generation phase to generate reports as in Exercise 5:

```
mvn site
```

### Task 7: Manual System Testing

Even after doing model checking, you still need to verify that the program
"looks" right end-to-end in the GUI.  This is hard to do using automated
testing so you will do manual testing for this.  Refer to the
[requirements.md](requirements.md) file for the features that need testing.  I
am not going to make you write a test plan for this, but you will be graded on
how closely the GUI follows the requirements as demonstrated in BeanCounterSolution.jar

# Grading

* GradeScope autograder - 80%
* Verify API used properly - 5%
* Extra test checking new invariant - 5%
* Visual inspection of GUI application - 10%

Please review the rubric in GradeScope for details.  I reserve the right to
deduct points for any attempt to try to game GradeScope into giving you more
points.  Also, plagiarism will get you a zero for the project.

# Submission

You will create a GitHub repository just for deliverable 5.  Make sure you keep
the repository *PRIVATE* so that nobody else can access your repository.  Once
you are done modifying code, don't forget to commit and push your changes to
the github repository.  Submit your GitHub repository to GradeScope at the
"Deliverable 5 GitHub" link.  Don't forget to add your partner, if you have one.
Once you submit, GradeScope will run the autograder to grade you and give feedback.
If you get deductions, fix your code based on the feedback and resubmit.  Repeat
until you don't get deductions.

# GradeScope Feedback

It is encouraged that you submit to GradeScope early and often.  Please use the
feedback you get on each submission to improve your code!

The GradeScope autograder works in 5 phases:

1. BeanCounterLogicImpl.java functionality testing (16 points) 

    The purpose of this phase is to test BeanCounterLogicImpl for defects.  I
do this by running the JUnit class
[PlainJUnitTest.java](src/PlainJUnitTest.java) against BeanCounterLogicImpl.
On a failure, read the feedback to get a hint on which situation led to the
defect.

1. CheckStyle (10 points)

    This phase runs the CheckStyle tool on your source code.  Each warning will
get you a point deduction.

1. SpotBugs (10 points)

    This phase runs the SpotBugs tool on your class files.  Each warning will
get you a point deduction.

1. PlainJUnitTest on BeanCounterLogicSolution (9 points)

   All your plain JUnit tests should pass on the solution implementation.

1. PlainJUnitTest on BeanCounterLogicBuggy (14 points)

   Most of your plain JUnit tests should fail on the buggy implementation,
except testReset.  Your test results are compared against those of a reference
PlainJUnitTestSolution test, and if the former is different from the latter,
you get a deduction (the former passes and the latter fails, and vice-versa).
Read the feedback to get a hint on what the problem is.  Try invoking the exact
scenario on the buggy implementation to see the bug for yourself and figure out
why your test cases are not handling it correctly.

   For example, if the configuration that failed was 10 slots, 200 beans, in skill
mode, you may try the following:

   ```
   java -cp BeanCounterBuggy.jar edu.pitt.cs.BeanCounterLogicBuggy 10 200 skill debug
   ```

   Or you can try the GUI interface (which is limited to 10 slots):


   ```
   java -cp BeanCounterBuggy.jar edu.pitt.cs.BeanCounterLogicBuggy 200 skill
   ```

1. JPFJUnitTest on BeanCounterLogicSolution (7 points)

   All your property-based tests should pass on the solution implementation.

1. JPFJUnitTest on BeanCounterLogicBuggy (14 points)

   Most of your property-based tests should fail on the buggy implementation as
well.  Again your tests results are compared against a reference
JPFJUnitTestSolution class.  Any deviations are deducted.

# Resources

These links are the same ones posted at the end of the slides:

* JDK 8 installation packages:  
https://drive.google.com/drive/folders/1E76H7y2nMsrdiBwJi0nwlzczAgTKKhv7

* Java Path Finder manual:  
https://github.com/javapathfinder/jpf-core/wiki/How-to-use-JPF
http://javapathfinder.sourceforge.net/

* Java Path Finder Verify API:  
https://github.com/javapathfinder/jpf-core/wiki/Verify-API-of-JPF

* CheckStyle reference:  
https://checkstyle.sourceforge.io/checks.html  
If you don't understand a CheckStyle warning, read the corresponding entry inside google\_checks\_modified.xml under the checkstyle-jars folder and the above reference.

* SpotBugs reference:  
https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html

