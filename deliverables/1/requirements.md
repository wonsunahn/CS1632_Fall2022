Requirements for Coffee Maker Quest

FUN-CONFIG - Upon launch, the system shall read in the rooms.config JSON file
in the current directory to initialize rooms in the map.  The rooms shall be
laid out from south to north, starting from the first room.  The player shall
start out at the first southernmost room.

FUN-NORTH - Upon entering "N" for North, the player shall move to the room to
the north, if a door exists to the north.  If a door does not exist to the
north, the move shall be disallowed with the message: "A door in that direction
does not exist."

FUN-SOUTH - Upon entering "S" for South, the player shall move to the room to
the south, if a door exists to the south.  If a door does not exist to the
south, the move shall be disallowed with the message: "A door in that direction
does not exist."

FUN-LOOK - Upon entering "L" for Look, the player shall collect any items in
the room and those items shall be added to the player's inventory.  If an item
is already in the player's inventory, the inventory shall not change.

FUN-INVENTORY - Upon entering "I" for inventory, the player shall be informed
of the items that he/she has already collected (consisting of Coffee, Sugar, or
Cream).

FUN-DRINK - Upon entering "D" for Drink, the player shall drink the beverage
made from ingredients in the inventory.  The player shall win the game if and
only if Coffee, Sugar, and Cream are all in the inventory.  Otherwise, drinking
shall result in the player losing the game.

FUN-HELP - Upon entering "H" for Help, the player shall be shown a listing of
possible commands ("N", "S", "L", "I", "H", "D") and what their effects are.

FUN-LOWERCASE-COMMANDS - The player shall be able to enter lowercase commands
"n", "s", "l", "i", "h", "d" with the same effect as their uppercase
counterparts "N", "S", "L", "I", "H", "D" respectively.

FUN-UNKNOWN-COMMAND - If a player enters an unspecified command, the system
shall respond with the phrase "What?".

FUN-UNIQ-ROOM - Each room in the house shall have a unique adjective describing
it, visible to the user upon entering the room.

FUN-UNIQ-ROOM-FURNISHING - Each room in the house shall have one and only one
unique furnishing visible to the user upon entering the room.
