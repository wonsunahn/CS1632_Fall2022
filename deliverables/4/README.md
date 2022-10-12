- [CS 1632 - Software Quality Assurance](#cs-1632-software-quality-assurance)
  * [Description](#description)
  * [How to Run SlowLifeGUI](#how-to-run-slowlifegui)
  * [What do do](#what-do-do)
    + [Task 1: Profile using VisualVM](#task-1-profile-using-visualvm)
    + [Task 2: Write Pinning Tests for the Three Methods](#task-2-write-pinning-tests-for-the-three-methods)
    + [Task 3: Refactor the Three Methods](#task-3-refactor-the-three-methods)
    + [Task 4: Rerun Profiles for the Three Methods](#task-4-rerun-profiles-for-the-three-methods)
  * [Report Format](#report-format)
- [Grading](#grading)
- [Submission](#submission)
- [GradeScope Feedback](#gradescope-feedback)
  * [Buggy implementation](#buggy-implementation)
- [Groupwork Plan](#groupwork-plan)
- [Resources](#resources)

# CS 1632 - Software Quality Assurance
Fall Semester 2022

* DUE: November 4 (Friday), 2022 11:59 PM

**GitHub Classroom Link:** https://classroom.github.com/a/4GkTBvY4

## Description

For this assignment, your group will profile a Conway's Game of Life
simulation, and improve its performance by refactoring several methods (to be
determined by the results of the profiling).  The program is assumed to be
functionally correct.  It's only problem is that certain features are too slow.
This will consist of the following steps for each method, as we discussed in
Exercise 4:

1. Profiling to determine the most CPU-intensive method that is suboptimal.
1. Adding method pinning tests to guard against unintended changes to functionality.
1. Refactoring the method to make it more performant.
1. Verifying that the pinning tests you added still pass.
1. Profiling again to confirm that your refactored method is now performant.

The code is available under the src/ directory.

## How to Run SlowLifeGUI

First let's invoke the Maven compile phase to generate the class files:

```
mvn compile
```

Then let's invoke the GameOfLife main method as such:

```
java -cp target/classes edu.pitt.cs.GameOfLife 10
```

The argument 10 is the dimensions of the world to generate.  This will generate
a matrix of 10 X 10 cells.

Now if you want to test the implementation using GameOfLifePinningTest.java,
you can invoke the Maven test phase as before:

```
mvn test
```

## What do do

The program is an implementation of Conway's Game of Life
(https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).  You can change the
state of a cell (from living to dead) by clicking on one of the buttons.  Cells
which are currently alive have an X and a red background; cells that are dead
now, but were at any point alive during the current run, will have a green
background.

There are several other buttons which invoke different features:

1. Run - this will run one iteration of the Game of Life
2. Run Continuous - This will run iterations until you press the Stop button.
3. Stop - This will stop the current "Run Continuous" run.  It will have no effect if the program is not running continuously already.
4. Write - This will write the state of the system to a backup file, to be loaded later.
5. Undo - This will undo the previous iteration.  It will only work for one iteration (that is, you cannot do multiple undos to get back multiple iterations).
6. Load - This will load a previously-saved backup file (created using the Write button) to the current world.
7. Clear - This will clear the current world.

### Task 1: Profile using VisualVM

For the purposes of performance testing, we will focus on a 5 X 5 world.  For
the initial pattern, we will use the "blinker" pattern shown in:  

https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns  

The actual pattern GIF is at:  

https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#/media/File:Game_of_life_blinker.gif  

We will start from the vertical bar on a 5 X 5 matrix as shown in the GIF: For
an actual full performance test suite, we would have to try multiple world
sizes and multiple patterns but for the purposes of this deliverable, we will
focus on performance debugging only the above scenario.  As it happens, once we
debug the above scenario, the application will start running quickly for all
scenarios.

Let's start by creating a 5 X 5 world:

```
java -cp target/classes edu.pitt.cs.GameOfLife 5
```

Now click on the appropriate cells to create the vertical bar pattern.

There are exactly **THREE** major performance issues with **THREE** methods in
the code.  They could be in any feature of the program!  I recommend you try
exploratory testing to try out different features to determine which features
may have performance problems before profiling the application.  There are
**TWO** features that have problems out of the 6 features listed above (if you
count "Run" and "Run Continuous" as the same feature).  Each feature can be
invoked by pressing the corresponding button at the bottom panel.  The three
performance problems are dispersed in those two features.

In order to determine the "hot spots" of the application, you will need to run
VisualVM.  Using the profiler, determine the THREE methods you can modify to
measurably increase the speed of the application without modifying behavior.
Refer to Exercise 4 for a detailed explanation of how to use VisualVM to
profile an application.

There are two things you have to do differently form Exercise 4, however:

1. You need to replace "GameOfLife" with "*" (wild card) in the "Profile
   classes:" window on the right before pressing on the "CPU" button to start
profiling.  This instructs VisualVM to instrument not only the GameOfLife class
(the class with the main method), but all classes in the application.  You did
not need to do this for the Exercise 4 MonkeySim application because it was
single-threaded application.  All code in a single-threaded application execute
starting from the main method, so the default behavior of VisualVM to
instrument starting from the main method class was just fine.  GameOfLife is a
GUI application and in a Java GUI application, there are multiple event handler
threads running in the background to handle events like button presses
concurrently with the application.  In this case, the code for these threads do
not start from the main method.

1. You need to collect profiles for each individual feature.  For Exercise 4,
   the only feature was to run MonkeySim from the commandline.  In this
application, there are 6 different features you need to test.  If you take a
snapshot of the profile at the very end of execution after having tried out all
6 features, just like you did for the exercise, you will not be able to tell
which feature has a performance problem.  Instead, create 6 individual
snapshots for the 6 features and analyze them separately.  Once you are done
profiling a feature, press the "Reset" button on VisualVM to clear the profile
before moving on to the next feature.  You should be able to find the 2
problematic features relatively easily (the slow methods have pretty glaring
performance problems).  Save the hot spots list for each of the two features in
these two files: **hotspots-feature1-before.png** and
**hotspots-feature2-before.png**.

### Task 2: Write Pinning Tests for the Three Methods

Before doing refactoring any method, you should create "pinning tests" (as
described in the section on legacy code earlier - please review the slides on
Writing Testable Code if you need a refresher).  These pinning tests should
check that the behavior of a modified method was not changed by your refactor.
The methods should work EXACTLY the same as before, except they should be
faster and take up less CPU time.  **There should be at least one pinning test
per method refactored.**

In general, a pinning test doesn't necessarily have to be a unit test; it can
be an end-to-end test that you slap on quickly for the purposes of refactoring
(without spending the effort to localize tests by mocking external objects).
The end-to-end pinning test is then gradually refined into more high quality
unit tests. Sometimes this 2-step process is necessary because sometimes you
cannot write high quality unit tests before refactoring to make the code more
testable (e.g. via dependency injection).  So you need a temporary end-to-end
pinning test to protect the code base meanwhile.  For this deliverable, there
is no reason you cannot write **unit tests** from the get-go for pinning tests
as the dependency injection(s) has already been done for you.  

Here are some requirements for your pinning tests:

1. You will use the 5 X 5 blinker pattern that I described above when a pattern
   is required:

   https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#/media/File:Game_of_life_blinker.gif  

   The vertical bar pattern should be your precondition and the next horizontal
bar pattern should be your postcondition.  **For the postcondition, make sure you
check all 25 cells in the 5 X 5 pattern**.

1. You are required to localize each pinning unit test within the tested class
   as we did for Deliverable 2 (meaning it should not exercise any code from
external classes). You will have to use Mockito mock objects to achieve this.

1. Also, you may have to use behavior verification instead of state
   verification to test some methods because the state change happens within a
mocked external object.  Remember that you can use behavior verification only
on mocked objects (technically, you can use Mockito.verify on real objects too
using something called a Spy, but you won't need it for this deliverable).  You
will get point deductions if you don't use mock objects and behavior
verification appropriately.

1. Note that even though the class is named GameOfLifePinningTest, the methods
   you test will not necessarily come from the GameOfLife class.  You will
create whatever objects from whatever classes are necessary to test the three
refactored methods.  Hint: there is no reason for you to create a GameOfLife
object as there are no methods that you need to refactor there.

You will write all your pinning tests in the class GameOfLifePinningTest by
completing the TODOs.  Please heed the comments.  

### Task 3: Refactor the Three Methods

Now refactor the three methods so that they are no longer performance problems.
If you look carefully, the three methods do a lot of wasted work for no reason.
It should be easy to refactor my removing that work.  Make sure that your
pinning tests pass after refactoring.

### Task 4: Rerun Profiles for the Three Methods

Save the hot spots list for each of the two optimized features in these two
files: **hotspots-feature1-after.png** and **hotspots-feature2-after.png**.
You should see a significant improvement over the initial profile.

## Report Format

Please use the [ReportTemplate.docx](ReportTemplate.docx) file provided in this
directory to write a short report.  A PDF version of the file is at
[ReportTemplate.pdf](ReportTemplate.pdf).

The report should have a title page with:
* Your name(s)

ON YOUR FIRST PAGE, please write a brief introduction on any issues you may
have had with VisualVM and also a division of work between partners.

ON A SEPARATE PAGE, write a brief report on the first feature you optimized.
Write the name of the feature, the methods you refactored, and a VisualVM
export of method "Hot spots" before and after refactoring.  Please refer to
Exercise 4 on how the Hot spots export file looks like.

ON A SEPARATE PAGE, do the same for the second feature optimized.

# Grading

* Report - 10%
* Performance tests on your optimized methods (autograder) - 45%
* Pinning tests on your optimized methods (autograder) - 15%
* Your pinning tests against your implementation (autograder) - 15%
* Whether you implemnted your pinning tests correctly (manual grading with autograder feedback) - 15%

Please read [grading_rubric.txt](grading_rubric.txt) before submitting!

Note that 75% of your grade (besides the report) will be graded by GradeScope
autograding.  However, adjustments to your autograded score may follow if you
make a bad faith attempt at tricking the autograder (e.g. write a pinning test
that does not properly test the method you are refactoring).

# Submission

Each pairwise group will do one submission to GradeScope as usual.  The
submitting member must use the "View or edit group" link at the top-right
corner of the assignment page after submission to add his/her partner.  

The submission is divided into two parts:

1.  Submit the repository created by GitHub Classroom for your team to
    GradeScope at the **Deliverable 4 GitHub** link.  Once you submit,
GradeScope will run the autograder to grade you and give feedback.  If you get
deductions, fix your code based on the feedback and resubmit.  Repeat until you
don't get deductions.

1. Submit your report to GradeScope at the **Deliverable 4 Report** link.

# GradeScope Feedback

It is encouraged that you submit to GradeScope early and often.  Please use the
feedback you get on each submission to improve your code!

The GradeScope autograder works in 3 phases:

1. GameOfLife method performance tests (45.0):

   I run performance tests on each of the three methods you should
optimize.  If any of those methods time out after 10 ms, you get a -15
deduction.

1. GameOfLife method pinning tests (15.0):

   I run my own pinning tests on each of the three methods you should
optimize.  These pinning tests pass without you having to do anything
(obviously because they are meant to test existing behavior of legacy code).
And they should stay that way.  If any of the pinning tests fail, you get a
-5 deduction.

1. GameOfLifePinningTest method tests (15.0):

   I run the pinning tests you wrote (GameOfLifePinningTest) on your
implementation.  If any of the pinning tests fail, you get a -5 deduction.

1. GameOfLifePinningTest Mocking and Behavior Verification (0.0):

   This section gives you feedback on whether you did proper mocking and
behavior verification.  It does three test runs using your
GameOfLifePinningTest:

   1) Output after injecting bug into real object: Since a real object used in
your test becomes buggy, the test case that uses that real object should fail.

   2) Output after injecting bug into mocked object: Since the code in a mocked
object is not exercised, the injected bug should have no effect and again, the
expected output is:

      ```
      --------------------------------------------------------------------
      ALL TESTS PASSED
      --------------------------------------------------------------------
      ```

   3) Output after injecting bug in method checked by behavior verification:
The method that does behavior verification should fail.

      If all goes well, you should see the followimg lines at the end of this section:

      ```
      PASSED (5/5): Bug injected into real object caused test failures (as it should).
      PASSED (5/5): Bug injected into mocked object did not cause test failures.
      PASSED (5/5): Behavior verification correctly detected change in behavior.
      ```

      If you see FAILED (0/5) instead, you need to fix your tests.  The buggy
implementation with the injected bugs has been included in the repository if
you want to see what the bugs are with your own eyes (see below).

      CAVEAT: Just because you got PASSED on all three, it does not mean that you
are guaranteed to get points for that rubric item.  You may have passed simply
because you did not yet write the relevant test!  So in the end, points will be
assigned through manual grading (hence the 0 points assigned in the
autograder).  But if you wrote the tests and you see FAILED, then you most
definitely have a problem.

## Buggy implementation

Please try the following if you want to try running the buggy implementation
yourself to see what the bugs are.

   1) Output after injecting bug into real object: Since a real object used in
your test becomes buggy, the test case that uses that real object should fail.

      Try running the following:

      ```
      java -jar libs\game-of-life-buggy-1.0-SNAPSHOT.jar 5 real
      ```

      And then create the vertical bar pattern.  Then, try pressing the "Write"
   button and then the "Load" button.  You will be surprised!

   2) Output after injecting bug into mocked object: Since the code in a mocked
object is not exercised, the injected bug should have no effect and again, the
expected output is:

      Try running the following:

      ```
      java -jar libs\game-of-life-buggy-1.0-SNAPSHOT.jar 5 mock
      ```

      And then try running the simulation after creating the vertical bar pattern.  Something is not quite right...

   3) Output after injecting bug in method checked by behavior verification:
The method that does behavior verification should fail.

      Try running the following:

      ```
      java -jar libs\game-of-life-buggy-1.0-SNAPSHOT.jar 5 behavior
      ```

      Again, try running the simulation after creating the vertical bar pattern.  This is even stranger.

# Groupwork Plan

Just like for Exercise 4, each of you should use VisualVM to profile the
application and come up with the three methods to refactor.  Note that unlike
Exercise 4, there are many more features for this program so it is going to be
slightly harder to find the methods.  After you are done, compare the three
methods, discuss, and agree upon the final three.

Next, split the refactoring and testing of the three methods between the two of
you.  The partner that focused more on testing for Deliverable 2 should now
focus more on refactoring, and vice versa, the partner that focused more on
implementation for Deliverable 2 should now focus more on the pinning tests.
The goal is for both of you to have a balanced set of experiences.

# Resources

* VisualVM Download:
https://visualvm.github.io/download.html

* VisualVM Documentation:
https://visualvm.github.io/documentation.html

Method profiling is not the only thing that VisualVM knows how to do.  It can
also profile overall CPU usage, heap memory usage, thread creation/termination,
class loading/unloading, Java just-in-time compiler activity, etc.  It can also
profile heap memory in a detailed way to show which types of objects are
filling the memory and where their allocation sites were.  And needless to say,
VisualVM is not the only profiling tool out there.

In the unlikely case you can't find what you are looking for in existing
profilers, you can even write your own profiler using the Java Virtual Machine
Tool Interface (JVMTI).  JVMTI is what was used to build VisualVM.

* Creating a Debugging and Profiling Agent with JVMTI
https://www.oracle.com/technical-resources/articles/javase/jvmti.html

* JVMTI Reference
https://docs.oracle.com/javase/8/docs/platform/jvmti/jvmti.html
