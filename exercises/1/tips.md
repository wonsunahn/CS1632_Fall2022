1. Remember that tests should be reproducible - starting with all preconditions
   met, anybody should be able to reproduce the same steps and get the same
results.  Check that your preconditions, execution steps, and postconditions are
precise and easy to follow.

1. "You see the correct value" is not a good postcondition (or expected behavior).
   Remember, when testers read your test plan document, they don't have the
requirements document beside them to try to interpret what they think the
"correct value" is.  A proper postcondition should look like: "You see the text
'The answer is 42.' displayed on the screen".

1.  Let's go back to the example of the postcondition "You see the text 'The
    answer is 42'".  Let's say the requirement for this feature is something
like: "The system shall display the text 'The answer is [value]', where [value]
is the sum of the two input values".  You can see here that the postcondition
is not just a simple carbon copy of the requirement.  For example, the
postcondition does not say: "The system displays the text 'The answer is
[value]', where [value] is the sum of the two input values".  The test writer
must apply the requirement to the given set of preconditions and execution
steps to come up with the postcondition specific to that test case.  In this
case, the test writer should compute the expected value 42 from the two input
values 40 and 2 present in the execution steps.  The less is left to
interpretation during testing, the better.
