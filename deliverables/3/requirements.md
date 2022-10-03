## App homepage: https://cs1632.appspot.com/

FUN-LINKS - Every page shall include five links at the top, to "Catalog", "Rent-A-Cat", "Feed-A-Cat", "Greet-A-Cat", and "Reset".  These shall link to `/`, `/rent-a-cat`, `/feed-a-cat`, `/greet-a-cat`, and `/reset`, respectively.

FUN-LISTING - Every page shall display a listing of three rows under the text "Cats available for rent:".  If all cats are available, the rows shall display "ID 1. Jennyanydots", "ID 2. Old Deuteronomy", and "ID 3. Mistoffelees", respectively.  If a cat is rented out and is no longer available, the corresponding row shall be replaced with "Rented out".

FUN-CATALOG - The Catalog page (`/`) shall display exactly three images of cats in a numbered list.  The three images shall have file paths "/images/cat1.jpg", "/images/cat2.jpg", and "/images/cat3.jpg" respectively.

FUN-RENT-A-CAT - The Rent-A-Cat page (`/rent-a-cat`) shall display an input box to enter the ID of the cat to rent alongside a "Rent" button.  It shall also display an input box to enter the ID of the cat to return alongside a "Return" button.

FUN-RENT - If the cat with the ID in the rent input box is available, pressing the "Rent" button shall result in the cat being rented out and the cat listing on the same page shall immediately reflect that fact.  Also, the text "Success!" shall appear beside the button.  If the cat with the ID in the rent input box is not available, or the input is not a valid ID, then the text "Failure!" shall appear beside the button.
 
FUN-RETURN - If the cat with the ID in the return input box is rented out, pressing the "Return" button shall result in the cat being returned and the cat listing on the same page shall immediately reflect that fact.  Also, the text "Success!" shall appear beside the button.  If the cat with the ID in the return input box is already available, or the input is not a valid ID, then the text "Failure!" shall appear beside the button.

FUN-FEED-A-CAT - The Feed-A-Cat page (`/feed-a-cat`) shall display an input box to enter the number of catnips to feed the available cats alongside a "Feed" button.

FUN-FEED - If the number of catnips entered in the input box is a positive integer and can be evenly divided among the cats that are currently available, pressing the "Feed" button shall result in the text "Nom, nom, nom." appearing beside the button.  If the number is not positive or cannot be evenly divided among available cats, or is otherwise invalid, the text "Cat fight!" shall appear beside the button.

FUN-GREET-A-CAT - The Greet-A-Cat page (`/greet-a-cat`) shall display as many "Meow!"s as there are available cats (e.g. if there are three cats available, it shall display "Meow!Meow!Meow!".).

FUN-GREET-A-CAT-WITH-NAME - If the Greet-A-Cat page is accessed with a trailing `/` and a name (e.g. `/greet-a-cat/Jennyanydots`), then the cat with the given name shall respond with a "Meow!" on the page, if available.  If the cat with the name is not available or such a name does not exist, then the page shall display the text "{name} is not here.".  The name may contain any printable character.

FUN-RESET - The Reset page (`/reset`) shall immediately reset the Rent-A-Cat system such that all cats are returned and available.
