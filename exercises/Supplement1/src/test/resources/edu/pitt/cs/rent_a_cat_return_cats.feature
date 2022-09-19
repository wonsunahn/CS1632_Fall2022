Feature: Rent-A-Cat returning

As a user
I want to return a cat that I rented back to the rent-a-cat facility
So that I can be not accused of cat abduction.

Background:
# TODO: Include your test fixture here

Rule: When a cat is requested for return and the cat has not been rented,
the return is unsuccessful and the list of available cats remains the same.

Scenario: Attempt to return a cat that does not exist
When I return cat number 4
Then the return is unsuccessful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n"

Scenario: Rent a cat and attempt to return the same cat twice
# TODO: complete this scenario

Rule: When a cat is requested for return and the cat has been rented,
the return is successful and the cat is added to the list of available cats.

Scenario: Rent the first cat and then return the first cat
# TODO: complete this scenario

Scenario: Rent the last cat and then return the last cat
# TODO: complete this scenario

Scenario: Rent a middling cat and then return that cat
# TODO: complete this scenario