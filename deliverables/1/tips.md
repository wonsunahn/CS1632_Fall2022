1. Remember that tests should be reproducible - starting with all preconditions
   met, anybody should be able to reproduce the same steps and get the same
results.  Check that your preconditions, execution steps, and postconditions are
precise and easy to follow.

1. "You see the correct value" is not a postcondition (or expected behavior).
   Remember, when testers read your test plan document, they don't have the
requirements document beside them to try to interpret what they think the
"correct value" is.  There should be no ambiguity on when to report something
as a defect.  Same thing for defect reports.  Developers need to know what the
tester expected to understand why the observed behavior was reported as a
defect.  A proper postcondition should look like: "You see the text 'The
answer is 42.' displayed on the screen".

1. A test plan is **not** a document that describes the results of a test run.
   it's quite the opposite.  It is a document that describes what to expect
after performing a test run.  That means you need to obtain postconditions of a
test case from the **requirements** and not from observed behavior.  Let's go
back to the example of the postcondition "You see the text 'The answer is 42'".
The requirement for this feature would be something like: "The system shall
display the text 'The answer is [value]', where [value] is the result of the
computation".  You can see here that the postcondition is not just a simple
carbon copy of the requirement.  The test writer must apply the requirement to
the given set of preconditions and execution steps to come up with the
postcondition specific to that test case.
