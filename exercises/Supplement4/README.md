- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Description](#description)
  * [Part 1: CI/CD Pipelines](#part-1--ci-cd-pipelines)
    + [Add Hello World Workflow](#add-hello-world-workflow)
    + [Add Maven CI Workflow](#add-maven-ci-workflow)
    + [Debug Maven CI Workflow maven_test job](#debug-maven-ci-workflow-maven-test-job)
    + [Debug Maven CI Workflow update_dependence_graph job](#debug-maven-ci-workflow-update-dependence-graph-job)
      - [GitHub Caches](#github-caches)
      - [Job Dependencies](#job-dependencies)
      - [Job Permissions](#job-permissions)
    + [Enable Dependabot](#enable-dependabot)
    + [Add Maven Publish Workflow](#add-maven-publish-workflow)
    + [Adding SonarQube 3rd Party CI Test](#adding-sonarqube-3rd-party-ci-test)
  * [Part 2: Dockers](#part-2--dockers)
- [Submission](#submission)
- [Groupwork Plan](#groupwork-plan)
- [Resources](#resources)

# CS 1632 - Software Quality Assurance
Fall Semester 2022 - Supplementary Exercise 4

* DUE: December 2 (Friday), 2022 11:59 PM

## Description

During the semester, we learned various ways in which we can automate testing.
But all that automation is of no use if your software organization as a whole
does not invoke those automated test scripts diligently.  Preferably, those test
scripts should be run before every single source code change to the repository,
and for good measure, regularly every night or every weekend just in case.  Now,
there are many reasons why this does not happen if left to individual
developers:

1. Developers are human beings so they forget.  Or, they remember to run
   some tests, but not all the test suites that are relevant to the changes
they have made.

1. Developers are sometimes on a tight schedule, so they are tempted to skip
   testing that may delay them, especially if they are not automated.  They
justify their actions by telling themselves that they will fix the failing
tests "as soon as possible", or that the test cases are not testing anything
important, or that failing test cases in modules under the purview of
another team "is not my problem".

In Part 1 of this exercise, we will learn how to build an automated
"pipeline" of events that get triggered automatically under certain
conditions (e.g. a source code push).  A pipeline can automate the entire
process from source code push to software delivery to end users, making sure
that a suite of tests are invooked as part of the process before software is
delivered.  Pipelines that are built for this purpose are called CI/CD
(Continuous Integration / Continuous Delivery) pipelines, because they
enable continuous delivery of software to the end user at at high velocity
while still maintaining high quality.  We will learn how to build a fully
functioning pipeline for the (Rent-A-Cat application)[../exercises/2] that
we tested for Exercise 2 on our GitHub repository.

In Part 2, we will learn how to use dockers to both test and deploy
software as part of a CI/CD pipeline.  Dockers are virtualized execution
environments which can emulate the execution environments in the deployment
sites (OS, libraries, webservers, databases, etc.) so that software can be
tested in situ.  In our case, we will create a docker image out of the
(Rent-A-Cat website)[cs1632.appspot.com] that we tested for Deliverable 3
for testing and deployment.

## Part 1: CI/CD Pipelines

**GitHub Classroom Link:** TBD

In Part 1, you will learn how to create a pipeline from scratch based on the
Rent-A-Cat application for Exercise 2, using the CI/CD support provided by
your GitHub repository through GitHub Actions.  GitHub Actions is just one
example CI/CD framework.  Other widely used CI/CD frameworks include GitLab
Pipelines and Jenkins.  Regardless of which you choose, they work in mostly
similar ways: there is a YAML configuration file that describes actions in
the pipeline and the actions are performed on one or more Runner machines
which are typically Docker containers.  The only thing that differs is the
YAML file syntax.  Hence, by learning GitHub Actions, you will be able to
translate that knowledge to other frameworks as well.  In GitHub Actions
lingo, pipelines are called **workflows**, and the two terms will be used
interchangeably.

### Add Hello World Workflow

Let's first start with a very basic workflow which prints "Hello World"
inside the Runner.  Click on the "Actions" menu on your GitHub repository
webpage (it is near the top).  Then click on the "New workflow" button on
the left hand side.  You will be presented with a plethora of "starter"
workflows for different purposes.  Search for "manual" in the search box and
you should see a single workflow named "Manual workflow" by GitHub Actions
in the search results.  Click on the "Configure" button on the workflow.
Then click on the "Start commit" button once you are done reviewing the
workflow and then commit the file.  Note that this creates a workflow YAML
file ".github/workflows/manual.yml" under your repository.

Please refer to the following tutorial to see exactly where to click:
https://docs.github.com/en/actions/using-workflows/using-starter-workflows

Now let's take a close look at the manual.yml YAML file.  At below are the
file contents:

```
# This is a basic workflow that is manually triggered

name: Manual workflow

# Controls when the action will run. Workflow runs when manually triggered using the UI or API.
on:
  workflow_dispatch:
    # Inputs the workflow accepts.
    inputs:
      name:
        # Friendly description to be shown in the UI instead of 'name'
        description: 'Person to greet'
        # Default value if no value is explicitly provided
        default: 'World'
        # Input has to be provided for the workflow to run
        required: true

# A workflow run is made up of one or more jobs that can run sequentially or in parallel
jobs:
  # This workflow contains a single job called "greet"
  greet:
    # The type of runner that the job will run on
    runs-on: ubuntu-latest

    # Steps represent a sequence of tasks that will be executed as part of the job
    steps:
    # Runs a single command using the runners shell
    - name: Send greeting
      run: echo "Hello ${{ github.event.inputs.name }}"
```

Please read the comments starting with # carefully.  In essence, a workflow
is composed of one or more jobs.  Jobs are run on individual Runners (in
parallel by default).  A job is composed of one or more sequential steps
which are performed on the same Runner.  The "Manual workflow" is composed
of a single job named "greet" which is in turn composed of a single step
"Send greeting".

Now let's put this workflow into action by executing it on a Runner!  click
on the "Actions" menu again and you should see a new workflow named "Manual
workflow" on your lefthand side.  Click on it.  Then click on the "Run
workflow" button.  You will get a pop up with an option to change "Person to
greet".  Leave everything as=is and click on the green "Run workflow" button
again.  After a couple of seconds, you will see a new "Manual workflow" run
appear on the list of runs with an orange dot and the orange dot will soon
turn into a green checkmark.  The orange dot indicates that the workflow is
under execution and the green checkmark indicates that it completed
successfully.  A failure is indicated by a red crossmark (which we will
encounter later).  Now click on the "Manual workflow" run link to see the
details of the run.  You should see a screen that looks like this:

<img alt="Manual workflow run" src=img/manual_workflow_1.png>

The screen shows an overview of the workflow.  The workflow is composed of a
single "greet" job as configured in the YAML file.  The green checkmark
beside the job indicates success.  Now click on the "greet" job to peek into
the job and you should see the below screen:

<img alt="Manual workflow greet job" src=img/manual_workflow_2.png>

As you see, the job is composed of 3 steps: "Set up job", "Send greeting",
and "Complete job".  The steps "Setup job" and "Complete job" are implicitly
inserted into every job even though they are not specified in the YAML file.
I've expanded the first two steps for viewing.  The purpose of "Set up job"
is to set up the Runner Docker container within which the job is to run.
You can see that the container was created using the ubuntu-20.04 Docker
image, and that is because "run-on: ubuntu-latest" was specified on the YAML
file and 20.04 happened to tbe latest version.  The "Send greeting" step
runs the commandline specified in the "run:" entry in the YAML file.

The reason that we were able to trigger this workflow manually was because
of the following lines on the YAML file:

```
on:
  workflow_dispatch:
```

Typically, workflows are triggered automatically in response to some
repository event but it is useful to be able to sometimes trigger them
manually.

Again, refer to the below tutorial if you are confused on where to click:
https://docs.github.com/en/actions/quickstart

### Add Maven CI Workflow

Now let's get down to business and try writing a CI workflow for our Maven
project.  This time we are going to have two jobs: 1) A "maven_test" job for
invoking "mvn test" on our project and 2) A "update_dependence_graph" job
for updating the package dependence graph in your GitHub repository.  The
dependence graph is used by a GitHub bot called Dependabot to notify the
repository owner of packages used by the repository that have become stale
or have outstanding security vulnerabilities.

Click on the "Actions" menu again and then click on the "New workflow"
button.  Now, instead of choosing a pre-existing "starter" workflow like
before, click on the "set up a workflow yourself" link.  This will take you
to a page for editing ".github/workflows/main.yml" from a blank slate.
Please change the file name to "maven-ci.yml" instead and then paste the
following into the content box before commiting the file:

```
name: Maven CI

# Triggers manually or on push or pull request on the main branch
# (in other words, on a code integration event.)
on:
  workflow_dispatch:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

jobs:
  
  # Runs the Maven test phase on the project
  maven_test:

    runs-on: ubuntu-latest

    permissions:
      contents: read

    steps:

    # The uses: keyword invokes a GitHub action:
    # https://github.com/marketplace/actions/checkout
    - name: Checkout repository
      uses: actions/checkout@v3

    # This invokes the Setup Java JDK GitHub action:
    # https://github.com/marketplace/actions/setup-java-jdk
    - name: Set up JDK 8
      uses: actions/setup-java@v3
      with:
        java-version: '8'
        distribution: 'temurin'
        cache: maven

    - name: Test with Maven
      run: mvn test

    # https://github.com/marketplace/actions/upload-a-build-artifact
    - name: Upload jacoco results as artifact
      uses: actions/upload-artifact@v2
      with:
        name: Jacoco coverage results
        path: target/site/jacoco

  # Uploads dependency graph to GitHub to receive Dependabot alerts 
  update_dependence_graph:

    runs-on: ubuntu-latest

    permissions:
      contents: read

    steps:

    # https://github.com/marketplace/actions/maven-dependency-tree-dependency-submission
    - name: Update dependency graph
      uses: advanced-security/maven-dependency-submission-action@v2

```

Alternatively, you could have just created and committed the file
".github/workflows/maven-ci.yml" and it would have had the same effect.  Now
go ahead and click on "Actions" and you will see the Maven CI workflow
created and already running!  Soon, it will show a failure:

<img alt="Maven CI workflow failure" src=img/maven_ci_workflow_1.png>

We will get to the failure soon, but first let's try to understand why the
workflow was triggered without you manually running it.  Note the phrase
"Maven CI #1: Commit 8e12112 pushed by wonsunahn" below the commit message
"Create maven-ci.yml".  It is clear that this workflow was triggered due to
a pushed commit.  These lines at the top of maven-ci.yml file are what
automatically triggered the workflow on a push:

```
# Triggers manually or on push or pull request on the main branch
# (in other words, on a code integration event.)
on:
  workflow_dispatch:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]
```

CI pipelines are those that run in response to a code integration event.
Going forward, any push or creation of a pull request will trigger this
workflow.  A pull request is a request to merge a branch into the main trunk
of the repository and is in that sense also a code integration event.

### Debug Maven CI Workflow maven_test job

Now let's click on the failed run link and see exactly which job(s) failed:

<img alt="Maven CI workflow run" src=img/maven_ci_workflow_2.png>

It looks like both of our jobs failed!  Let's first look at the "maven_test"
job by clicking on it:

<img alt="Maven CI workflow maven_test" src=img/maven_ci_workflow_3.png>

And then let's expand the failing "Test with Maven" step and scroll to the
end to see what the failure was:

<img alt="Maven CI workflow maven_test failure" src=img/maven_ci_workflow_4.png>

You can see that the "mvn test" command failed because of a code coverage
error from the Jacoco plugin.  This is the same code coverage error we
suffered in Exercise 2 at the beginning, if you remember.  To solve this
issue, copy over the "src" directory from your completed Exercise 2 and
overwrite the existing "src" directory.  Then commit and push your changes.
This will automatically trigger another CI run:

<img alt="Maven CI workflow second run" src=img/maven_ci_workflow_5.png>

The workflow failed again so let's click on the run to take a look:

<img alt="Maven CI workflow second run" src=img/maven_ci_workflow_6.png>

This time, the "maven_test" job passed, yay!  That means our code passed all
our JUnit tests with at least 20\% coverage.  

### Debug Maven CI Workflow update_dependence_graph job

Now time to look at the still failing "update_dependence_graph" job:

<img alt="Maven CI workflow second run" src=img/maven_ci_workflow_7.png>

Note the phrase "Goal requires a project to execute but there is no POM in
this directory".  The depgraph Maven plugin needs the pom.xml file to
extract the dependence graph but it looks like there is none under the
current directory.  Why would this be?  Let's take a closer look at the job
definition on our maven-ci.yml file once more:

```
  # Uploads dependency graph to GitHub to receive Dependabot alerts 
  update_dependence_graph:

    runs-on: ubuntu-latest

    permissions:
      contents: read

    steps:

    # https://github.com/marketplace/actions/maven-dependency-tree-dependency-submission
    - name: Update dependency graph
      uses: advanced-security/maven-dependency-submission-action@v2
```

Note that there is nowhere that we bring in the pom.xml file!  But how about
the previous checkout action?  

```
    - name: Checkout repository
      uses: actions/checkout@v3
```

Besides, weren't we able to successfully run "mvn test" because there was a
pom.xml file present?  The answer is again, you need to remember that these
are two different jobs which run on different Docker containers.  The
"update_dependence_graph" job builds a new container from scratch based on
the ubuntu-latest image, and hence will not have the pom.xml file.  One way
to solve this issue is to checkout the repository again within this job as
well, but that feels like a waste of work since we are repeating the same
work over again.  A better solution is to use GitHub caches.

#### GitHub Caches

A cache is a temporary key-value storage on the cloud that can store file(s)
or folder(s) that are accessible by later jobs, as long as they have the
correct key.  Any cache entries that have not been access more than 7 days
are automatically deleted by GitHub.  Let's see what is in our current
cache:

<img alt="Current cache entries" src=img/maven_ci_caches.png>

We already have a cache entry!  Where did this come from?  Focus on this
part of the maven-ci.yml file:

```
    # This invokes the Setup Java JDK GitHub action:
    # https://github.com/marketplace/actions/setup-java-jdk
    - name: Set up JDK 8
      uses: actions/setup-java@v3
      with:
        java-version: '8'
        distribution: 'temurin'
        cache: maven
```

Note the entry "cache: maven".  To find out what it does, you can read the
following section on the Setup Java JDK action page:
https://github.com/marketplace/actions/setup-java-jdk#caching-packages-dependencies

Essentially, it caches all Maven package dependencies for JDK 8 and
associates all those files with the key "setup-java-${{ platform }}-${{
packageManager }}-${{ fileHash }}", where the fileHash is the hash of the
pom.xml file.  It makes sense to associate the files with the hash of
pom.xml since the package files would not change unless pom.xml changes.  We
are caching those files so that the next time a job needs to install JDK 8,
it can just restore the files from the cache.  Try manually running the
"Maven CI" workflow and you can confirm that is indeed what happens:

<img alt="setup-java cache restored" src=img/maven_ci_caches_restored.png>

One use of caches is to make job executions more efficient in this way.
Another use of caches is to communicate data between jobs and this second
use is what we will leverage to transfer the pom.xml file from our first
"maven_test" job to second "update_dependence_graph" job.  Do the following:

1. Add the below lines to maven-ci.yml at the end of the "maven_test" job:

   ```
   - name: Cache build
     uses: actions/cache@v3
     with:
       key: cached-build-${{github.sha}}
       path: .
   ```

   This action will store all the files in the current path (which will
include pom.xml as well as all source files and class files built under the
target/ folder) and associate them with the key
"cached-build-${{github.sha}}".  The ${{github.sha}} pre-defined GitHub
variable gives you the current commit hash.  Essentially, you are
associating the build with the commit that triggered the build, which makes
sense, right?

1. Add the below lines to maven-ci.yml at the beginning of the steps of the
   "update_dependence_graph" job:

   ```
   - name: Restore cached build
     uses: actions/cache@v3
     with:
       key: cached-build-${{github.sha}}
       path: .
   ```

   This action will be able to find the previously cached build files using
the same key so that they can be used for dependence graph analysis.

After making these changes, commit and push.  This will trigger a new Maven
CI workflow as before.  Peek inside the "update_dependence_graph" job and
you will see that is still fails, and the reason is because the cache entry
was not found:

<img alt="Maven CI workflow cache not found" src=img/maven_ci_workflow_8.png>

Was this because the cache entry was not properly created?  Nope, we do see
that exact cache entry with the key:

<img alt="cache-build cache entry is created" src=img/maven_ci_caches_2.png>

#### Job Dependencies

So then what happened?  Remember I said that jobs run in parallel by
default?  The "maven_test" job and the "update_dependency_graph" job run
concurrently so there is no synchronization between the cache write step
and the cache read step.  Depending on which job runs faster, the cache
might be there or might not be there (essentially a race condition).  To
solve this issue, insert a needs entry to enforce a dependence on the
"maven_test" job:

```
  update_dependence_graph:

    needs: [maven_test]  # Enforces that maven_test runs first

    runs-on: ubuntu-latest
```

Again, commit and push maven-ci.yml, which will trigger the Maven CI
workflow.  If you peek inside the run, this is how it looks like now:

<img alt="Maven CI workflow with sequential jobs" src=img/maven_ci_workflow_9.png>

Note how now the "update_dependence_graph" job happens sequentially after
the "maven_test" job, solving our race condition.  But why is the job still
failing?  Let's take a peek inside the job:

<img alt="Maven CI workflow cache found" src=img/maven_ci_workflow_10.png>

As you can see, this time the cache is successfully restored so that isn't
the problem.  

#### Job Permissions

The issue is actually with resource accessibility:

<img alt="Maven CI workflow cache found" src=img/maven_ci_workflow_11.png>

The issue is that the job needs write permissions to the repository to
update the dependence graph, but currently it is set up for only read
permissions.  Note these lines for the "update_dependence_graph" job:

```
    permissions:
      contents: read
```

If you expand the "Set up job" step in the job run, you can plainly see that
"GITHUB_TOKEN Permissions" only include read access for the Contents.  

<img alt="Maven CI workflow GITHUB_TOKEN permissions" src=img/maven_ci_workflow_12.png>

As a a matter of security, it is good policy to have as restrictive
permissions as possible for any GitHub job.  Jobs often rely on 3rd party
actions and if those action scripts are compromised, the last line of
defense is the permissions.  By setting it to read-only, we can prevent
malicious action scripts from furtively updating parts of the repository.

In this case, we clearly need write permissions for this job, so let's
update the permissions as such:

```
    permissions:
      contents: write
```

Write permissions always includes read permissions as well.  This will again
trigger a Maven CI workflow and this time, the run is finally successful!

<img alt="Maven CI workflow success!" src=img/maven_ci_workflow_13.png>

The reason that I created a separate job for updating the dependence graph
was exactly because it needed write permissions and I didn't want to relax
the permission restrictions for the original "maven_test" job.

### Enable Dependabot

Now that the Maven CI workflow has run successfully, let's check that our
dependency graph is properly updated in our repository.  Go to the
"Insights" menu and then click on "Dependency graph", and you should be able
to see all dependencies defined in your pom.xml file and all your workflows:

<img alt="Dependency graph after upload" src=img/dependency_graph.png>

The Dependabot whose job is to scour these dependencies is not enable by
default however, and you need to go into Settings (the "Code security and
analysis" page) to change that:

<img alt="Dependabot settings before changing" src=img/settings_dependabot_before.png>

Click "Enable" on all the buttons that have to do with Dependabot.  When you
click on Enable for "Dependabot version updates", you will be asked to
create ".github/ dependabot.yml" with the following content:

```
# To get started with Dependabot version updates, you'll need to specify which
# package ecosystems to update and where the package manifests are located.
# Please see the documentation for all configuration options:
# https://docs.github.com/github/administering-a-repository/configuration-options-for-dependency-updates

version: 2
updates:
  - package-ecosystem: "maven" # See documentation for possible values
    directory: "/" # Location of package manifests
    schedule:
      interval: "weekly"
```

The only thing you need to do here is type "maven" for the package-ecosystem
(as seen above).  This configures Dependabot to run a weekly schedule to
check for updates and security vulnerabilities for dependent packages.
After enabling all options, the settings page should look like this:

<img alt="Dependabot settings before changing" src=img/settings_dependabot_after.png>

As soon as you commit dependabot.yml, Dependabot will kick into action and
within a few seconds creates three branches to patch up pom.xml to update
some package versions, and three pull requests for those three branches:

<img alt="Pull requests created by Dependabot" src=img/pull_requests.png>

Those orange dots beside each pull request means that the Maven CI workflow
to verify that pull request is still running.  Soon enough those orange dots
will turn into green check marks if the CI tests pass.

Try clicking on the first pull request and you will see what I mean.  Scroll
down until you see the "Merge pull request" button, and above it you will
see that two successful checks have been performed, all part of our Maven CI
workflow:

<img alt="Pull request checks have all passed" src=img/pull_request_checks.png>

You will see similar on all three pull requests.  Since Maven CI has
verified that the updated package dependencies did not break our build, we
can safely press the "Merge pull request" to merge the branch into our main
trunk.  The third pull request may ask you to "Resolve conflicts".  This
happened because previous merges caused a conflict with the current merge.
View the merged file and locate where the conflict occurred.  This would be
how it looks like:

```
...
<<<<<<< dependabot/maven/org.apache.maven.plugins-maven-javadoc-plugin-3.4.1
    <jacoco-maven-plugin.version>0.8.4</jacoco-maven-plugin.version>
    <maven-javadoc-plugin.version>3.4.1</maven-javadoc-plugin.version>
=======
    <jacoco-maven-plugin.version>0.8.8</jacoco-maven-plugin.version>
    <maven-javadoc-plugin.version>3.0.0</maven-javadoc-plugin.version>
>>>>>>> main
...
```

Manually merge the above lines into these lines:

```
    <jacoco-maven-plugin.version>0.8.8</jacoco-maven-plugin.version>
    <maven-javadoc-plugin.version>3.4.1</maven-javadoc-plugin.version>
```

Then click on "Mark as resolved", which will display o "Commit merge" button
that you can click.  There might be a message saying something like "the
page is out of date".  You can ignore that message and continue and click on
"Merge pull request".  After all is said and done, you should see three
closed pull requests:

<img alt="Pull request checks are all closed" src=img/pull_requests_closed.png>

Now we can be confident that all our dependent packages are up-to-date and
do not contain any outstanding vulnerabilities.

Dependabot was instrumental in mitigating the damage due to the recent Log4j
vulnerability by [alerting thousands of repositories and creating thousands
of pull
requests](https://github.blog/2021-12-14-using-githubs-security-features-identify-log4j-exposure-codebase/).

### Add Maven Publish Workflow

Now that we have a robust CI pipeline in place, we are ready to CD
(Continuous Delivery) as well.  We could choose to automatically publish our
package every time there is a code integration event, but typically software
organizations wait for a major release to redeploy.  The below YAML file
does exactly that:

```
name: Maven Package

on:
  workflow_dispatch:
  release:
    types: [created]

jobs:
  build:

    runs-on: ubuntu-latest

    permissions:
      contents: read
      packages: write

    steps:

    - name: Checkout repository
      uses: actions/checkout@v3

    - name: Set up JDK 8
      uses: actions/setup-java@v3
      with:
        java-version: '8'
        distribution: 'temurin'
        cache: maven

    - name: Build with Maven
      run: mvn -B package

    - name: Publish to GitHub Packages Apache Maven
      run: mvn deploy
      env:
        GITHUB_TOKEN: ${{ github.token }}
```

Create a new workflow file maven-publish.yml using the above text in exactly
the same way you created maven-ci.yml previously.  Note that this workflow
is now triggered when a new release is created.  It uses the ${{
github.token }} to authenticate and publish the package.  The GitHub
pre-defined variable ${{ github.token }} is automatically generated on each
workflow run and is given the permissions specified in the "permissions:"
entry.

After the maven-publish.yml is committed and pushed, you also need to edit
the pom.xml file and add the below text after the <scm>...</scm> element:

```
  <distributionManagement>
    <repository>
      <id>github</id>
      <name>GitHub Apache Maven Packages</name>
      <url>https://maven.pkg.github.com/CS1632/supplementary-exercise-4-ci-cd-pipelines-wonsunahn</url>
    </repository>
  </distributionManagement>
```

Replace the repository name in the <url>...</url> element with your own
repository name.

Now we are ready to create a new release to see if this works!  Go to the
"<> Code" tab on your GItHub repository and then click on the "Create a new
release" link under the "Releases" section at the bottom right:

<img alt="Click on create new release" src=img/create_new_release_1.png>

The click on "Choose a tag", and then type "v1.0" in the box and click on "+
Create new tag: v1.0 on publish".  The should create the "v1.0" tag.  Then
on the "Release title" box, write "Release v1.0".  Then click on the
"Publish release" button.  This should create your first release:

<img alt="Click on publish release" src=img/create_new_release_2.png>

This triggers the "Maven Package" workflow which publishes the package:

<img alt="Result of the Maven Package action" src=img/create_new_release_3.png>

In the end, you should see a new package registered on your github page:

<img alt="Package published on GitHub" src=img/create_new_release_4.png>

If you click on the package on the bottom right, you should see the contents
of your newly published package:

<img alt="Package details" src=img/create_new_release_5.png>

### Adding SonarQube 3rd Party CI Test 

This time, let's try adding a 3rd party CI test to the maven-ci.yml
workflow.  SonarQube is a widely used static testing tool Navigate to
[https://sonarcloud.io](https://sonarcloud.io) and login using your GitHub
account.  If this is your first time using SonarCloud, GitHub will ask you
to uuthorize SonarCloud.  Click on the "Authorize SonarCloud" button.

Once you are logged in, click on the "My Projects" tab.  And then click on
"Analyze new project":

<img alt="Analyze new project" src=img/sonarcloud_1.png>

Then click on the "Import an organization from GitHub" button.  Then click
on the "CS1632" organization.  Then select "All repositories" and click on
the "Install" button.  Then find your repository and add it to the list of
repositories to monitor.

Once you have your repository registered, go into your repository and follow
these steps:

1. Go to Administration > Analysis Method.  On the page, turn of "Automatic
   Analysis".

1. Go to "My Account" by clicking on the account icon at the top right.  Go
   to the "Security" tab and click on the "Generate" button after entering a
token name:

   <img alt="Generate token" src=img/sonarcloud_2.png>

   Copy the generated token.

1. Now head over to your GitHub repository and go to Settings > Secrets >
   Actions.  Then click on the "New repository secret" button:

   <img alt="New repository secret" src=img/sonarcloud_3.png>

   Paste your generated token in the secret box and name it SONAR_TOKEN.

   <img alt="Register SONAR_TOKEN on GitHub" src=img/sonarcloud_4.png>

1. On SonarCloud, go to the "Information" menu and copy the "Project Key":

   <img alt="Copy project key" src=img/sonarcloud_5.png>

1. Head back to your GitHub repository again and open sonar-project.properties:

   ```
   # The Project Key and Organization Key generated when setting up the project on SonarQube
   # Avaiable on the "Information" menu on the sonarcloud.io project page
   sonar.projectKey=
   sonar.organization=cs1632

   # relative paths to source directories. More details and properties are described
   # in https://docs.sonarqube.org/latest/project-administration/narrowing-the-focus/ 
   sonar.sources=src
   sonar.java.binaries=target/classes
   ```
   
   Paste the Project Key that you copied from SonarCloud after the
"sonar.projectKey=" entry and then commit and push.  This will trigger a new
Maven CI run:

   <img alt="Maven CI with SonarQube" src=img/sonarcloud_6.png>

   Note how both the "update_dependence_graph" job and "sonarqube_test" jobs
are dependent open the "maven_test" job (because maven_test generates the
build cache which is needed by both jobs).  But these two jobs can run in
parallel.

1. Now head over to SonarCloud and view the report generated by the run:

   <img alt="SonarQube report" src=img/sonarcloud_7.png>

## Part 2: Dockers

**GitHub Classroom Link:** TBD

**UNDER CONSTRUCTION.  Will be released on Wednesday.**

# Submission

Please complete Part 1 and Part 2 questions in
[ReportTemplate.docx](ReportTemplate.docx).  These is a [PDF
version](ReportTemplate.pdf) as well.  

Wnen you are done, submit to the "Supplementary Exercise 4 Report" link on
GradeScope.  I want each member in the group to have gone through the
exercise on his/her/their own before submitting.

# Groupwork Plan

I expect each group member to experience CI/CD pipelines.  I created
individual repositories for each of you, so please work on your own
repositories to implement the pipelines.  After both of you are done,
compare the YAML files that each of you wrote.  Discuss, resolve any
differences, and submit the GitHub repository of your choice.

# Resources
