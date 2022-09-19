- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Description](#description)
  * [Prerequisites](#prerequisites)
  * [Running Cucumber Tests](#running-cucumber-tests)
    + [Running Cucumber Tests on VSCode](#running-cucumber-tests-on-vscode)
    + [Running Cucumber Tests on Commandline](#running-cucumber-tests-on-commandline)
    + [Expected Outcome](#expected-outcome)
  * [What To Do](#what-to-do)
    + [Updating RentACatImpl.java](#updating-rentacatimpljava)
    + [Adding Steps in StepDefinitions.java for the "rent cats" Feature](#adding-steps-in-stepdefinitionsjava-for-the--rent-cats--feature)
    + [Further Modifying StepDefinitions.java and User Story for the "return cats" Feature](#further-modifying-stepdefinitionsjava-and-user-story-for-the--return-cats--feature)
- [Submission](#submission)
- [GradeScope Feedback](#gradescope-feedback)
- [Groupwork Plan](#groupwork-plan)
- [Resources](#resources)

# CS 1632 - Software Quality Assurance
Fall Semester 2022 - Supplementary Exercise 1

* DUE: September 23 (Friday ), 2022 11:59 PM 

**GitHub Classroom Link:** https://classroom.github.com/a/QD_BdChz

## Description

In this exercise, we will test the Rent-A-Cat rental system software once more,
but this time with BDD (Behavior Driven Development).  

We will use the Gherkin language to specify behaviors for Rent-A-Cat and use
the Cucumber framework to test those behaviors.

## Prerequisites

If you are using VSCode as your IDE, I recommend that you install the official Cucumber extension:
https://marketplace.visualstudio.com/items?itemName=CucumberOpen.cucumber-official

It is the first extension that pops up when you search for Cucumber on the Extensions menu.  

As before, all instructions are going to be based on the Maven commandline tool
and will be IDE-neutral.  However, an IDE like VSCode may help you read and
edit Gherkin files through features like syntax highlighting and autocomplete.

If you haven't already, please install the Apache Maven commandline tool:
https://maven.apache.org/download.cgi

## Running Cucumber Tests

### Running Cucumber Tests on VSCode

1. Choose "Explorer" from the left hand side vertical menu.
2. Choose MAVEN > RentACat-Cucumber > Lifecycle > test.
3. Press the triangular play button next to the test life cycle.

That is going to be equivalent to running 'mvn test' on the commandline.
Alternatively, you can use the "Testing" option from the left vertical menu,
but I noticed that this doesn't seem to auto-generate Java code snippets for
missing Gherkin steps like the Maven test life cycle phase does.

### Running Cucumber Tests on Commandline

You simply have to invoke 'mvn test' on the exercise folder, either through the
integrated terminal on VSCode ("View > Terminal") or a stand-alone terminal:

```
mvn test
```

### Expected Outcome

Initially when you run the Cucumber tests, all your tests will fail, partly
because RentACatImpl is incomplete and partly because your Cucumber testing
code is incomplete.  You will get a long list of failures followed by this summary text:

```
...
Tests run: 14, Failures: 9, Errors: 1, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  6.192 s
[INFO] Finished at: 2022-09-11T16:53:57-04:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:2.12.4:test (default-test) on project RentACat-Cucumber: There are test failures.
[ERROR]
[ERROR] Please refer to c:\Users\mrabb\Documents\github\cs1632\CS1632_Fall2022\exercises\Supplement1\target\surefire-reports for the individual test results.
...
```

The above tells you that out of 14 tests, 9 tests failed, and there was an
error in one test.  An error happens when a test is incomplete or is otherwise
malformed.  In order to get the details about the failures and errors, you can
read the messages that precede the summary output, or you can also read the
Cucumber report which is much nicer.  Somewhere above that summary is going to
be a link to a report that looks like this:

```
????????????????????????????????????????????????????????????????????????????
? View your Cucumber Report at:                                            ?
? https://reports.cucumber.io/reports/0334222f-8b03-4075-80ba-a63d2c887c90 ?
?                                                                          ?
? This report will self-destruct in 24h unless it is claimed or deleted.   ?
????????????????????????????????????????????????????????????????????????????
```

Copy and paste that link on a web browser and you should see a report that
looks like the following:

<img alt="Cucumber Report" src=img/cucumber_report.png width=700>

The green check marks indicate steps that have passed.  The red cross marks
indicate steps that have failed.  The blue square marks indicate steps that
were skipped because a previous step failed.  The yellow question marks
indicate steps that were erroneous (e.g. matching Cucumber step does not exist
for that step).  For the failed steps, a Java stack trace is attached so that
you can track down the failure.

## What To Do

You will modify three files: **RentACatImpl.java**, **StepDefinitions.java**,
and **rent_a_cat_return_cats.feature**.  The RentACatImpl class is the
(incomplete) implementation of the Rent-A-Cat system.  The StepDefinitions
class is the (incomplete) implementation of Cucumber steps corresponding to the
Gherkin steps.  The rent_a_cat_return_cats.feature file is a description of the
"return cat" feature in the Rent-A-Cat system written in the Gherkin language.
All the places to modify have been marked by // TODO comments.

### Updating RentACatImpl.java

Let's first start by completing src/main/java/edu/pitt/cs/RentACatImpl.java.  You can just
copy the version that you completed for Exercise 2.

Just by doing that, many tests will pass now.  Try invoking the Maven testing
phase after having copied the file and you will get:

```
> mvn test
...
Tests run: 14, Failures: 5, Errors: 1, Skipped: 0
...
```

Now we only have **5** failures where as previously we had **9** failures.  In
fact, all the tests in Feature: Rent-A-Cat listing (in the
src/test/resources/edu/pitt/cs/rent_a_cat_list_cats.feature file) pass.

So why are the rest of the failures and errors happening?  We have rigorously
tested RentACatImpl using JUnit testing for Exercise 2, so hopefully by now it
does not contain any defects.  So then, there must be something wrong with the
Cucumber tests themselves!  Henceforward, we will fix the problems in the
Cucumber tests one by one and you will be able to learn Cucumber through that
process.

### Adding Steps in StepDefinitions.java for the "rent cats" Feature

Let's look at the src/test/resources/edu/pitt/cs/rent_a_cat_rent_cats.feature
file to see what the problem is.  Well, the Gherkin feature description makes
logical sense.  So it must be the Cucumber steps that implement the Gherkin
steps that must be the problem.  All the Cucumber steps are inside the
src/test/java/edu/pitt/cs/StepDefinitions.java file.  In that file, you
can see all corresponding methods for each Gherkin step.  Some of the methods
have the // TODO: Implement comment and the default action is to fail().  The
failing methods are the ones used for the renting feature in Gherkin.  Replace
fail() with the proper implementation of each method.  Observe how other steps
were implemented to get hints.  After this, if you run Cucumber again, you will
get:

```
...
Tests run: 14, Failures: 0, Errors: 1, Skipped: 0
...
```

### Further Modifying StepDefinitions.java and User Story for the "return cats" Feature

So where did this error come from?  If you scroll up in the Cucumber output a
little bit, you will see the following messages:


```
...
Attempt to return a cat that does not exist(Rent-A-Cat returning)  Time elapsed: 0.07 sec  <<< ERROR!
io.cucumber.junit.UndefinedStepException: The step "I return cat number 4" is undefined. You can implement it using the snippet(s) below:

@When("I return cat number {int}")
public void iReturnCatNumber(Integer int1) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}


Some other steps were also undefined:

@Then("the return is unsuccessful")
public void theReturnIsUnsuccessful() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
...
```

Cucumber is telling you that there is no corresponding Cucumber step for the
Gherkin step "I return cat number 4".  And then, it is kind enough to even give
a code snippet you can use to start implementing that step!  Of course, you
will have to replace "throw new io.cucumber.java.PendingException();" with code
to actually implement that step.  Cucumber also tells you some additional steps
that were undefined as a courtesy.

Once you implement those steps, you may suffer a NullPointerException due to
the "r" reference being null.  Now why would that happen all of a sudden?
Hint: compare rent_a_cat_return_cats.feature where the error happened to the
rent_a_cat_rent_cats.feature, focusing on the "Background:" section.  Once you
fix that, you should finally get the following:

```
...
Tests run: 14, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.983 sec
...
```

Congratulations!  If you have time, try to complete the other 4 scenarios in
rent_a_cat_return_cats.feature and see if you can have them pass too!

# Submission

Each pairwise group will do one submission to GradeScope as usual.  The
submitting member must use the "View or edit group" link at the top-right
corner of the assignment page after submission to add his/her partner.  

Submit the repository created by GitHub Classroom for your team to GradeScope
at the **Supplementary Exercise 2 GitHub** link.  Once you submit, GradeScope
will run the autograder to grade you and give feedback.  If you get deductions,
fix your code based on the feedback and resubmit.  Repeat until you don't get
deductions.

# GradeScope Feedback

The feedback you get from the GradeScope autograder is based on the Cucumber
summary output.  For example, if you get the following output:

```
...
Tests run: 14, Failures: 9, Errors: 1, Skipped: 0
...
```

There were 9 failures and 1 errors so the final score is: 14 - 9 - 1 = 4.

# Groupwork Plan

For this exercise, I recommend that you both try to do the full exercise to get
the full range of experience with Gherkin and Cucumber.  Compare your solutions
in the end, discuss, and submit!

# Resources

* Gherkin Syntax Reference:  
https://cucumber.io/docs/gherkin/reference/

* Tutorial on how to write Cucumber steps:
https://cucumber.io/docs/cucumber/step-definitions/

* Cucumber API reference:
https://cucumber.io/docs/cucumber/api/

* Introduction to Behavior Driven Development (BDD):
https://cucumber.io/docs/bdd/

* Maven CLI tool download:
https://maven.apache.org/download.cgi

* Introduction to the Maven POM project file:
https://maven.apache.org/guides/introduction/introduction-to-the-pom.html

* Introduction to the Maven standard directory layout:
https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html
