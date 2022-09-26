- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Description](#description)
  * [Task 1: Write test cases](#task-1-write-test-cases)
    + [Tips for Writing assertions for each Test](#tips-for-writing-assertions-for-each-test)
    + [Other Tips](#other-tips)
  * [Task 2: Add test cases to test suite and save project](#task-2-add-test-cases-to-test-suite-and-save-project)
  * [Task 3: Export test suite to JUnit class](#task-3-export-test-suite-to-junit-class)
    + [Why export to a JUnit class?](#why-export-to-a-junit-class)
    + [How to export to JUnit for Selenium IDE](#how-to-export-to-junit-for-selenium-ide)
    + [JUnit set up (Chrome browser specific)](#junit-set-up-chrome-browser-specific-)
    + [JUnit set up (Firefox browser specific)](#junit-set-up-firefox-browser-specific-)
    + [Running the JUnit class](#running-the-junit-class)
  * [Tips for JUnit + Selenium problem solving](#tips-for-junit--selenium-problem-solving)
    + [How to disable pop-ups (Chrome browser specific)](#how-to-disable-pop-ups-chrome-browser-specific)
    + [How to disable pop-ups (Firefox browser specific)](#how-to-disable-pop-ups-firefox-browser-specific)
- [Submission](#submission)
- [GradeScope Feedback](#gradescope-feedback)
- [Groupwork Plan](#groupwork-plan)
- [Resources](#resources)
- [Extra Credit](#extra-credit)
  * [Description](#description-1)
  * [Submission](#submission-1)

# CS 1632 - Software Quality Assurance

* DUE: September 30 (Friday), 2022 11:59 PM

**GitHub Classroom Link:** https://classroom.github.com/a/Y_8ZHDtn

## Description

For this assignment, you and a partner will write a systems-level, automated
black-box tests for the Reddit website using the Selenium IDE.  Specifically,
we are going to test the r/cats subreddit at:

https://www.reddit.com/r/cats/

It was chosen because it is a nice safe subreddit which is policed pretty well.  Besides, it's cats.

First, let's start by adding the Selenium IDE browser extension for your web
browser by selecting "Chrome Download" or "Firefox Download" on the below
website:

https://www.selenium.dev/selenium-ide/

Then, open Selenium IDE by clicking on the newly created browser extension with
the "Se" symbol.  You should see a pop up window that looks very similar to the
one shown on the lecture slides.

## Task 1: Write test cases

Write a test case for each requirement listed in
[requirements.md](requirements.md).  Name each test case same as the
requirement name (e.g. FUN-TITLE).  I'm asking you to do this for the purposes
of GradeScope autograding.  Normally, you would use a more descriptive name.

Remember, each test must end with an assertion!  The list of available
assertions and other commands are available at:

https://www.selenium.dev/selenium-ide/docs/en/api/commands

### Tips for Writing assertions for each Test

You will want to use the below commands and assertions to test each of the requirements:

FUN-TITLE: "assert title"

FUN-JOIN-BUTTON-EXISTS - "assert text"

FUN-SIGNUP-LINK - "store attribute" followed by "assert".  You will be
storing the href attribute value to a Selenium variable and asserting on the
value of that variable.  Now the target argument for "store attribute" does
not directly take a locator string.  If you see the Reference tab for the
command, you will see that it takes \<locator string\>@\<attribute name\>
instead, where the attribute name in this case is "href".  Since the target
argument is not a locator string, the target selector button is disabled.
If you want to still use the target selector to at least get the locator
string part, you will have to do a workaround and enter a command such as
"assert text" or "click" which allows you to use the target selector, fill
in the locator string using it, and then revert back to "store attribute".  

FUN-SORT-BY-COMMENTS - "assert text".  Please test this feature by searching
"catnip" on the search box and then sorting by "Most Comments", and then
verifying that the first comment has "287 comments" by using "assert text".
This test is dependent on the current state of the reddit database since
postings and comments are fluid, but let's assume that the database is given as
a precondition ;).

FUN-RULE-3 - "assert text".

FUN-RULES-12-ITEMS - "assert element present" for the 12th item, followed by
a "assert element not present" for the locator for the 13th item.  

### Other Tips

Sometimes your test case will not work as expected.  Here are a few hints on how to debug a problem:

1. Check the **Log window** at the bottom of the Selenium IDE.  It will tell you
   which step failed for what reason (in red).

1. Select the test step that failed in the main test case window, and then
   select the **Reference tab** at the bottom pane of the IDE.  It will display
usage instructions for that command.  Remember always, the first argument goes
to the Target field and the second argument goes to the Value field, regardless
of command.

1. Sometimes the target component of a test step is the problem.  The selector
   button tries to generate a **locator string** as best it can using xpath,
css selector, or id tag.  But it is not fool proof.  The problem is, the web
page may change ever so slightly on the next page load (e.g. due to a new post,
or a new comment) and then the locator will stop working.  You will notice that
there is a small down arrow at the end of the target text box.  If you click on
that arrow, you will see alternative locator strings to the current string.
Select the one that looks specific enough to be able to point to the target but
also general enough to not change between page loads.  You do need to try this
out several times to get a feel of what a good locator string is.  Here is a
list of all the different types of locators available in Selenium:

   https://www.selenium.dev/documentation/webdriver/elements/locators/   

   Here is an in-depth discussion about how to use these locators to uniquely
identify a specific element:  

   https://www.selenium.dev/documentation/webdriver/elements/finders/

1. Sometimes you can use an XPATH position locator string to check that an
   element exists at an expected location ("assert element present") or does
not exist ("assert element not present").  But to do this, you have to select
the XPath position locator string in the drop-down list of optional strings in
the Target field.

## Task 2: Add test cases to test suite and save project

1. Choose "Test Suites" from the left panel drop down menu.

1. There will already be a "Default Suite" there with possibly one or more tests.

1. Right click on "Default Suite", or click on the vertical-3-dot context menu button, and select "Rename" and rename to "RedditCats".

1. Right click on "RedditCats", or click on the vertical-3-dot context menu
   button, and select "Add tests".  Make sure all tests are checked as shown in
the below figure.  Press on the "Select" button.

   <img alt="Test Suite" src=test-suite-selection.png width=700>
   
1. Click on the "Save project" button on the top right corner that looks like a
   floppy disk.  Save to file name "Reddit Cats.side" in the exercise root folder.

## Task 3: Export test suite to JUnit class

### Why export to a JUnit class?

There are many reasons why you would want to export to JUnit.

1. You may have a pre-existing testing framework in JUnit (or Python Pytest, or
   JavaScript Mocha, etc).  And you may want to merge the Selenium IDE testing
script to the language and framework of your choice.

1. Exporting to JUnit really gives you a good sense of what's happening under
   the covers (in terms of the actual calls to the web driver).  Also, if there
is a test case that is particularly hard to nail down just by using Selenium
IDE, you can touch it up in the form of exported Java code.  

1. Selenium IDE also gives the option to export your JUnit test directly to a
   Selenium Grid which can run the test cases in parallel.  This can allow you
to utilize a server farm to finish your testing very quickly, although we will
not explore this option today.

In the end, once you get familiar with the Selenium API and how locator strings
work, you will prefer coding in Java directly to the APIs.  At that stage,
Selenium IDE will feel more like extra baggage rather than a helping hand.  The
Selenium IDE scripting language is Turing complete, but it is primitive
compared to something like Java and the GUI coding interface, while helpful
initially, will start to feel clunky.  Moreover, the full range of Selenium
APIs are not represented in the scripting language (not to mention a few
defects in the subset that it does implement).

This part of the exercise will help you get more familiar with the Selenium API
and get you started on your journey to be a full Selenium programmer.

### How to export to JUnit for Selenium IDE

Once you are done writing your Selenium test suite, let's try exporting the test
suite in Selenium IDE to a Java JUnit test class.  

Follow these instructions:

1. Right click on "RedditCats", or click on the vertical-3-dot context menu
   button, and select "Export".

1. Select "Java JUnit" in the list of language options and optionally check
   "Include step descriptions as a separate comment" to generate more detailed
comments.  Leave other boxes unchecked.

1. Save the resulting file "RedditCatsTest.java" to the
   src/test/java/edu/pitt/cs test source directory.

1. Add the following line to the top of "RedditCatsTest.java":
   ```
   package edu.pitt.cs;
   ```

### JUnit set up (Chrome browser specific)

If you generated your JUnit file using the Chrome browser Selenium IDE
extension, you will have to modify it to do some additional set up.

Please add these line(s) to the beginning of the @Before setUp() method in the
generated RedditCatsTest.java file:

```
System.setProperty("webdriver.chrome.driver", "Chrome/chromedriver-win32.exe");
```

If you are not using Windows, replace chromedriver-win32.exe with the web driver
compatible with your OS.

Note that the Chrome web driver only works if you have Chrome version 98
installed on your computer (the most recent version as of today).  If you have
a different version of Chrome, you may have to download the appropriate
web driver from:

https://chromedriver.chromium.org/downloads

Your Chrome version can be obtained by clicking on the vertical-3-dot menu at
the top right corner of your browser, then Help > About Google Chrome.

### JUnit set up (Firefox browser specific)

If you generated your JUnit file using the Firefox browser Selenium IDE
extension, you will have to modify it as well.

Please add these line(s) to the beginning of the @Before setUp() method:

```
System.setProperty("webdriver.gecko.driver", "Firefox/geckodriver-win64.exe");
System.setProperty("webdriver.firefox.logfile", "/dev/null");
```

Again, if you are not using Windows, replace geckodriver-win64.exe with the
web driver compatible with your OS.  The second logfile property redirects
verbose log messages emitted by the Firefox browser to a null device,
discarding them.  This is done so that those messages don't get interleaved
with JUnit messages and confuse you.

The Firefox web driver should work for all recent versions of Firefox.

### Running the JUnit class

Before starting, let me warn you that your RedditCatsTest.java JUnit test class
is **not going to work**, even after having made the above changes to designate
the webdriver path.  That is because making Selenium work on websites requires
some [massaging](#tips-for-junit--selenium-problem-solving) and the Selenium
IDE code generation just takes you halfway there.  Moreover, Selenium IDE will
sometimes generate code which is flat out incorrect (one glaring example is
switching the locations of the expected and observed values in assertEquals
assertions).  Which is just fine because this is going to be a learning
opportunity for you to solve these problems and gain a deeper understanding of
Selenium Web Driver API.

With full expectation with things will fail, let's invoke the Maven test phase:
```
mvn test
```

If things go properly, you will see the browser pop up repeatedly for each test
case, perform the actions, and close.  In the command line, you should see:

```
...
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.917 s
[INFO] Finished at: 2022-07-14T11:04:13-04:00
[INFO] ------------------------------------------------------------------------
```

Most likely, you will see one or more failed tests, such as this:

```
...
Results :

Tests in error: 
  fUNSORTBYCOMMENTS(edu.pitt.cs.RedditCatsTest): element click intercepted: Element <button data-testid="search-results-filter-sort" class="cmR5BF4NpBUm3DBMZCmJS _2jNQT-6WbFOjX2hdDWV56w _1g3g98ViMb36cM-peU17Jk BZDMD8yWu5imupa73nqYE">...</button> is not clickable at point (138, 149). Other element would receive the click: <div class="_1DK52RbaamLOWw5UPaht_S _3Ig_EsWWVLquWs2yBBQjec _1acwN_tUhJ8w-n7oCp-Aw3">...</div>(..)

Tests run: 6, Failures: 0, Errors: 1, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:35 min
[INFO] Finished at: 2022-07-14T11:00:40-04:00
[INFO] ------------------------------------------------------------------------
...
```

Please read the failure message and the stack trace for each failure to
figure out what went wrong.  Then, modify each test case that fails using
tips listed in the next section.

## Tips for JUnit + Selenium problem solving

**Do not expect the generated JUnit code to work out of the box.**  The IDE
does not do a perfect job in doing a translation, which suites this classroom
because massaging the code to make it work will force you to read Selenium code
and get used to the APIs.  The IDE is more of a starter tool, and you will be
coding against the web driver APIs when you become an expert.

1. One big headache with Selenium is that there is an inherent **race
   condition** in the way it works.  There are three components to this
distributed system: the web browser that renders the web page and runs
JavaScript, the web server that sends web data to the web browser
intermittently, and the web driver that sends commands to the web browser to
control its actions.  These three components will not synchronize with each
other unless you tell them to and events (such as page loads from web server,
DOM element rendering by the web browser, and commands from the web driver) can
happen in arbitrary order.  For example, your web browser may not have finished
rendering a button before your web driver sends a command to click on it.  This
leads to nondeterminism and unreproducible testing.

   Fortunately, Selenium does provide you with a long list of synchronization
APIs that allow you to wait for a particular event to happen.  Details about
the different types of wait APIs available on Selenium are described in:

   https://www.selenium.dev/documentation/webdriver/waits/
   
   Most of the time, setting an **implicit wait** at the beginning is enough to
solve most race conditions.  It ensures that the web driver implicitly waits
for the given amount of time for a target element to be rendered when sending
any command, before flagging a failure. It is flexible in that it will only
wait the given amount of time if the element does not load quickly, and will
proceed immediately if it does.  Insert the following line in the @Before
setUp() method:

   ```
   driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
   ```

   In order to use that line, you will need to also import this library:

   ```
   import java.util.concurrent.TimeUnit;
   ```

   Selenium IDE internally uses an implicit wait time of 30 seconds
when running a script, but when it exports the script to the JUnit test, it
fails to insert that implicit wait in the @Before setUp() method.  So if you
want one, you need to insert it yourself.

   Sometimes, you may have to synchronize on events other than an element
getting rendered.  For that, you will have to do an **explicit wait** on that
event.  Here is an exhaustive list of events that you can wait on:

   https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html

   In some rare cases, the event that you want to wait on is not in the list of
events that Selenium supports.  In that case, you have no choice but to insert
an arbitrary wait on your own:

   ```
   try {
     Thread.sleep(3000);
   } catch (InterruptedException e) {
   }
   ```

   The above code will insert an arbitrary delay of 3000 ms (3 seconds) in your
web driver at the place of insertion.  It is arbitrary because there is no
guarantee that the event you are waiting for will happen within 3 seconds ---
we just hope that it does.  And the catch is, we cannot wait for too long
either because it will slow down testing.

   **You may have to insert the above sleep code in your web driver when you
test FUN-SORT-BY-COMMENT, between the command to type "catnip" and the command
to pressing [Enter].**  There is a race condition here because when you type
"catnip" in the search box, a drop down menu with a list of suggestions
appears, during which the search box is not responsive to user key strokes.  If
the <Enter> key stroke arrives when the search box is in this state, the key
stroke will be ignored.  Waiting for about 3 seconds makes it highly likely
that the key stroke arrives after this brief period of time when the box is
unresponsive.

1. Another common problem is that depending on the browser window size,
   certain elements may disappear.  For example, the Reddit site would hide
the "rules" bar on the right hand side if the windows is too narrow.  One
way to solve this is to uniformly set the window size at the @Before setUp()
method so that all your test cases are tested on the same dimensions (and
remove all calls to setSize in your test cases):

   ```
   driver.manage().window().setSize(new Dimension(1200, 800));
   ```
  
1. Yet another common problem is that some websites have pesky pop-up
   windows that prevents the Selenium Web Driver from interacting with the
website, resulting in test failure.  For example, the reddit.com has a pop
up window asking whether you want to "Show notifications" for the website
when visited for the first time.  Until you click "Block" or "Allow" on the
pop up, the rest of the website is inaccessible.  Once you click on a
choice, reddit.com will store your choice in a cookie and not ask you on
subsequent visits.

   When testing with Selenum IDE, the pop-up will not occur because most
likely this is not our first visit and as a browser extension, Selenium IDE
has access to cookies.  However, when testing with the exported JUnit test,
JUnit launches a standalone web browser instance in its own sandbox so it
will not have access to pre-existing cookies.  That means the notification
pop up will occur on the JUnit test every time.

### How to disable pop-ups (Chrome browser specific)

If you do not want any interference from pop-ups during testing, there is a
simple way to do it.  Replace the following line in the @Before setUp() method:

```
driver = new ChromeDriver();
```

with the following set of lines:


```
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-notifications");
driver = new ChromeDriver(options);
```

In order to use ChromeOptions, you will need to import the class at the top:

```
import org.openqa.selenium.chrome.ChromeOptions;
```

The "Show notifications" pop up is not the only annoying pop up out
there. Most of us are also familiar with the "Know your location" pop up.
To disable this one, simply add this line to the above:

```
options.addArguments("--disable-geolocation");
```

### How to disable pop-ups (Firefox browser specific)

If you do not want pop-ups during testing with Firefox, it.  Replace the
following line in the @Before setUp() method:

```
driver = new FirefoxDriver();
```

with the following set of lines:


```
FirefoxOptions options = new FirefoxOptions();
options.setProfile(new FirefoxProfile());
options.addPreference("dom.webnotifications.enabled", false);
driver = new FirefoxDriver(options);
```

In order to use FirefoxOptions, you will need to import these at the top:

```
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
```

In order to disable the "Know your location" geolocation pop up, simply add
this line to the above changes to FirefoxOptions:

```
options.addPreference("geo.enabled", false);
options.addPreference("geo.provider.use_corelocation", false);
options.addPreference("geo.prompt.testing", false);
options.addPreference("geo.prompt.testing.allow", false);
```

# Submission

Each pairwise group will do a group submission to GradeScope as usual.  The
submitting member must use the "View or edit group" link at the top-right
corner of the assignment page after submission to add his/her partner.  

Submit the repository created by GitHub Classroom for your team to GradeScope
at the **Exercise 3 GitHub** link.  Make sure the files "Reddit Cats.side" and
"RedditCatsTest.java" are in your submission.  Once you submit, GradeScope will
run the autograder to grade you and give feedback.  If you get deductions, fix
your code based on the feedback and resubmit.  Repeat until you don't get
deductions.

# GradeScope Feedback

The GradeScope autograder works in 2 phases:

1. **RedditCatsTest on https://www.reddit.com/r/cats**: This tests your
   RedditCatsTest.java file on the cats subreddit as originally intended.  All
of your tests should pass.

1. **RedditCatsTest on https://www.reddit.com/r/dogs**: This tests your
   RedditCatsTest.java file on the dogs subreddit, repeating the same steps but
for a different webpage.  Now some tests should pass but some should fail.
Specifically, FUN-JOIN-BUTTON-EXISTS and FUN-SEARCH-MEOW should pass and
the rest shold fail.

You may be curious how I was able to run the tests on the GradeScope docker
images when they most likely don't have displays to render the Chrome browser.
The Chrome web driver, as well as other web drivers, can be run in "headless"
mode.  That is, the tests can be performed inside the web engine without having
to actually display the page.  This is common practice since in a work setting,
testers will be running tests on server machines or even on the cloud in Docker
images like I did.  If you need to do this in the future, you can achieve this
by passing additional options when creating the Chrome web driver:

```
options.addArguments("--headless");			// Enable running without a display
options.addArguments("--disable-dev-shm-usage");	// Disable /dev/shm which is limited to 64MB in Docker and use /tmp/ instead to store shared memory files
```

I add the above options by replacing the setUp() method in RedditCatsTest.java
with my own version that looks like the following:

```
@Before
public void setUp() {
    System.setProperty("webdriver.chrome.driver", "Linux/chromedriver");
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");			// Enable running without a display
    options.addArguments("--disable-dev-shm-usage");	// Disable /dev/shm which is limited to 64MB in Docker and use /tmp/ instead to store shared memory files
    options.addArguments("--no-sandbox");		// A quick and dirty way to run Selenium as root, bypassing the OS security model
    driver = new ChromeDriver(options);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().setSize(new Dimension(1200, 800));
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
}
```

**Note: Please do not mess with the method declaration "public void setUp() {"
since it is used for pattern detection in GradeScope to replace the method.**

# Groupwork Plan

I want each member in the group to work on this individually and that is why I
created individual repositories in GitHub Classroom.  Practically, there is
only one single file that you will be modifying the Selenium IDE (.side)
project file, or the RedditCatsTest.java file.  And it would be difficult for
both of you to work on that single file.  Parallel modifications would result
in frequent merge conflicts.  When both of you are done, compare your work and
submit one finalized version to GradeScope.

# Resources

These links are the same ones posted at the end of the slides:

* Selenium IDE Getting Started:
https://www.selenium.dev/selenium-ide/docs/en/introduction/getting-started

* Selenium IDE Command Reference:  
https://www.selenium.dev/selenium-ide/docs/en/api/commands

* Selenium WebDriver Tutorial:
https://www.selenium.dev/documentation/webdriver/

* Official W3C XPath specification:
https://www.w3.org/TR/xpath/

* Unofficial XPath tutorial:
https://www.w3schools.com/xml/xpath_intro.asp.

* Official W3C CSS selector specification:
https://www.w3.org/TR/selectors/

* Unofficial CSS selector tutorial:
https://www.w3schools.com/cssref/css_selectors.asp

# Extra Credit

DUE: October 17 (Monday), 2022 before start of class

## Description

This extra credit is going to be 0.2 points out of 100 points for the entire
course, for anyone who is able to do this.

Previously, the suggested method for testing FUN-RULES-12-ITEMS was to use
"assert element present" for the 12th item, followed by a "assert element not
present" for the 13th item.  

Admittedly, this is clunky.  It would be much cleaner if we could count the
number elements directly and verify that it is 12.

The Selenium IDE command "store xpath count" allows you to count the number of
elements that matches an xpath and store it inside a Selenium variable.  You
can later verify the value of the variable using the "assert" command.  Now,
you will not be able to acquire that xpath using the target selector button in
the IDE.  You will have to inspect the element on your web browser and come up
with a pattern than can match all 12 items in that list.  On both the Chrome
and Firefox browsers, when you right click on an HTML element to bring up the
context menu, there is an "Inspect" menu.  Clicking on the "Inspect" menu
brings up Inspector view.  When you right click on the highlighted element
again, there is a "Copy" menu on the context menu.  This allows you to copy the
XPath of the given element.

You will have to do a little bit of your own research on xpaths to figure out
what actual XPath to pass to the "store xpath count" command.  You can read the
[official W3C specification](https://www.w3.org/TR/xpath/).

But just like most specifications, it is focused on exact specification rather
than readability.  You will find this unofficial tutorial using examples more
digestible: https://www.w3schools.com/xml/xpath_intro.asp.


## Submission

Please do a group submission, like the exercise.  Submit the same repository
that you submitted for the exercise at the **Exercise 3 Extra Credit** link.
You should get a full score on the autograder and have used "store xpath count"
to get credit.  Make sure the files "Reddit Cats.side" and
"RedditCatsTest.java" are in your submission.  
