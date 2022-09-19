Feature: Rent-A-Cat listing

As a user
I want to see a listing of available cats in the rent-a-cat facility
So that I can see what cats are available for rent.

# This is a comment.  You can put comments anywhere in this file.

Rule: When there are not cats, the listing is an empty string "".

Scenario: List available cats with no cats
Given a rent-a-cat facility
Given no cats
When I list the cats
Then the listing is: ""

Rule: When there are cats, the listing consists of one line per each cat.

Scenario: List available cats with 1 cat
Given a rent-a-cat facility
Given a cat with ID 1 and name "Jennyanydots"
When I list the cats
Then the listing is: "ID 1. Jennyanydots\n"

Scenario: List available cats with 2 cats
Given a rent-a-cat facility
Given a cat with ID 1 and name "Jennyanydots"
Given a cat with ID 2 and name "Old Deuteronomy"
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\n"

Scenario: List available cats with 3 cats
Given a rent-a-cat facility
And a cat with ID 1 and name "Jennyanydots"
And a cat with ID 2 and name "Old Deuteronomy"
And a cat with ID 3 and name "Mistoffelees"
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n"