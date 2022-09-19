Feature: Rent-A-Cat renting

As a user
I want to rent a cat from my neighborhood rent-a-cat facility
So that I can be less lonely during the pandemic.

Background:
Given a rent-a-cat facility
And a cat with ID 1 and name "Jennyanydots"
And a cat with ID 2 and name "Old Deuteronomy"
And a cat with ID 3 and name "Mistoffelees"

Rule: When a cat is requested for rent and the cat is not available,
the rent is unsuccessful and the list of available cats remains the same.

Scenario: Attempt to rent a cat that does not exist
When I rent cat number 4
Then the rent is unsuccessful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n"

Scenario: Attempt to rent the same cat twice
When I rent cat number 1
Then the rent is successful
When I list the cats
Then the listing is: "ID 2. Old Deuteronomy\nID 3. Mistoffelees\n"
When I rent cat number 1
Then the rent is unsuccessful
When I list the cats
Then the listing is: "ID 2. Old Deuteronomy\nID 3. Mistoffelees\n"

Rule: When a cat is requested for rent and the cat is available,
the rent is successful and the cat is removed from the list of available cats.

Scenario: Rent the first cat out of the list of cats
When I rent cat number 1
Then the rent is successful
When I list the cats
Then the listing is: "ID 2. Old Deuteronomy\nID 3. Mistoffelees\n"

Scenario: Rent the last cat out of the list of cats
When I rent cat number 3
Then the rent is successful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\n"

Scenario: Rent a middling cat out of the list of cats
When I rent cat number 2
Then the rent is successful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 3. Mistoffelees\n"