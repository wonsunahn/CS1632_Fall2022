TEST FIXTURE:
1. Firefox browser version >= 105, or Chrome browser version >= 105 is installed and launched.
2. The URL https://cs1632.appspot.com/ is open on the web browser.
3. The values of cookies "1", "2", and "3" are set to "false" (no cats are rented).

TEST CASES:

```
IDENTIFIER: TEST-1-LINKS
TEST CASE: Check that the "Reset" link properly points to `/reset`.
PRECONDITIONS: None.
EXECUTION STEPS: None.
POSTCONDITIONS: The href link of the "Reset" menu item points to `/reset`.
```

```
IDENTIFIER: TEST-2-RESET
TEST CASE: Given that cats ID 1, 2, and 3 have been rented out,
           check that resetting the system results in all cats being available.
PRECONDITIONS: The value of cookies "1", "2", and "3" are set to "true" (cat ID 1, 2, 3 are rented).
EXECUTION STEPS:
1. Press the "Reset" link.
POSTCONDITIONS: 
1. The first item in the cat listing is "ID 1. Jennyanydots".
2. The second item in the cat listing is "ID 2. Old Deuteronomy".
3. The third item in the cat listing is "ID 3. Mistoffelees".
```

```
IDENTIFIER: TEST-3-CATALOG
TEST CASE: Check that the second item in the catalog is an image named "cat2.jpg".
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Catalog" link.
POSTCONDITIONS: The source of the second image in the catalog is "/images/cat2.jpg".
```

```
IDENTIFIER: TEST-4-LISTING
TEST CASE: Check that the listing has three cats and the third is "ID 3. Mistoffelees".
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Catalog" link.
POSTCONDITIONS: 
1. There are exactly three items in the listing.
2. The text in the third item is "ID 3. Mistoffelees".
```

```
IDENTIFIER: TEST-5-RENT-A-CAT
TEST CASE: Check that the "Rent" and "Return" buttons exist in the Rent-A-Cat page.
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Rent-A-Cat" link.
POSTCONDITIONS: 
1. A "Rent" button exists on the page.
2. A "Return" button exists on the page.
```

```
IDENTIFIER: TEST-6-RENT
TEST CASE: Check that renting cat ID 1 works as expected.
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Rent-A-Cat" link.
2. Enter "1" into the input box for the rented cat ID.
3. Press the "Rent" button.
POSTCONDITIONS: 
1. The first item in the cat listing is "Rented out".
2. The second item in the cat listing is "ID 2. Old Deuteronomy".
3. The third item in the cat listing is "ID 3. Mistoffelees".
4. The text "Success!" appears beside the "Rent" button.
```

```
IDENTIFIER: TEST-7-RETURN
TEST CASE: Check that returning cat ID 2 works as expected.
PRECONDITIONS: The value of cookie "2" is set to "true" (cat ID 2 is rented).
EXECUTION STEPS:
1. Press the "Rent-A-Cat" link.
2. Enter "2" into the input box for the returned cat ID.
3. Press the "Return" button.
POSTCONDITIONS: 
1. The first item in the cat listing is "ID 1. Jennyanydots".
2. The second item in the cat listing is "ID 2. Old Deuteronomy".
3. The third item in the cat listing is "ID 3. Mistoffelees".
4. The text "Success!" appears beside the "Return" button.
```

```
IDENTIFIER: TEST-8-FEED-A-CAT
TEST CASE: Check that the "Feed" button exists in the Feed-A-Cat page.
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Feed-A-Cat" link.
POSTCONDITIONS: 
1. A "Feed" button exists on the page.
```

```
IDENTIFIER: TEST-9-FEED
TEST CASE: Check that feeding 6 catnips to 3 cats results in "Nom, nom, nom.".
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Feed-A-Cat" link.
2. Enter "6" into the input box for number of catnips.
3. Press the "Feed" button.
POSTCONDITIONS: 
1. The text "Nom, nom, nom." appears beside the "Feed" button.
```

```
IDENTIFIER: TEST-10-GREET-A-CAT
TEST CASE: Check that 3 cats respond with three "Meow!"s in the Greet-A-Cat page.
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Greet-A-Cat" link.
POSTCONDITIONS: 
1. The text "Meow!Meow!Meow!" appears on the page.

```

```
IDENTIFIER: TEST-11-GREET-A-CAT-WITH-NAME
TEST CASE: Check that greeting Jennyanydots results in "Meow!"s in the Greet-A-Cat page.
PRECONDITIONS: None.
EXECUTION STEPS:
1. Navigate to the `/greet-a-cat/Jennyanydots` URL be opening on browser.
POSTCONDITIONS: 
1. The text "Meow! from Jennyanydots." appears on the page.
```
