FUN-USAGE - For the GUI, there shall be two commandline arguments where the
first argument (bean count) shall be a number greater or equal to 0 and the
second argument is either "luck" or "skill".  For the text UI, there shall be
at least three commandline arguments where the first argument (slot count) and
second argument (bean count) shall be a number greater or equal to 0, and the
third argument is either "luck" or "skill".  For the text UI, there shall also
be an optional fourth commandline argument "debug".  Otherwise, the program
shall terminate with a usage help message.  

FUN-TEXTUI - When the text UI is invoked, the output shall be "Slot bean counts:" followed by a row of 10 numbers representing the 10 slots.  The sum of the numbers shall equal to the initial bean count passed into the commandline.

FUN-GUI-INIT - When the GUI is invoked, the window shall display 9 rows of pegs in a triangular formation where the top row has 1 peg and the bottom row has 9 pegs.  Below the pegs, there shall be 10 empty slots numbered from 0-9.  Below the slots, there shall be 8 buttons: "Step", "Slow", "Fast", "Stop", "Lower Half", "Upper Half", "Repeat", "Reset".  There shall be a bean above the top row peg, unless the initial bean count is 0.  The top right corner shall display two strings: "Average = 0" and "Remaining = \<num\>", where \<num\> is the initial bean count minus 1, or 0 if the initial bean count is 0.  

FUN-SLOTS - The bottom slots shall display the number of beans in each slot in the form of a bar graph where the height of the bar in pixels shall equal to the number of beans.

FUN-STEP - When the "Step" button is pressed, the bean counter will advance one step.  That is, each bean displayed on the window shall fall to the next row of pegs.  They shall fall left of the peg or to the right of the peg according to the requirements set forth in either FUN-LUCK or FUN-SKILL.  When a bean is below the bottom row of pegs, it shall fall into one of the slots below it.  When \<num\> displayed in "Remaining = \<num\>" is greater than 0, a new bean shall be inserted above the top row peg.

FUN-SLOW - When the "Slow" button is pressed, the bean counter shall advance steps continuously in a slow manner.

FUN-FAST - When the "Fast" button is pressed, the bean counter shall advance steps continuously in a fast manner.

FUN-STOP - When the "Stop" button is pressed, the beans shall stop advancing.

FUN-LOWER-HALF - When the "Lower Half" button is pressed, all beans except the lower half shall be discarded from the bottom slots.  If there is an odd number of beans, the lower half includes (N+1)/2 beans, where N is the number of beans.

FUN-UPPER-HALF - When the "Upper Half" button is pressed, all beans except the upper half shall be discarded from the bottom slots.  If there is an odd number of beans, the upper half includes (N+1)/2 beans, where N is the number of beans.

FUN-REPEAT - When the "Repeat" button is pressed, all beans in-flight and in the slots shall be added back to the pool of remaining beans.

FUN-RESET - When the "Reset" button is pressed, the machine shall be reset to the initial state when it was started.

FUN-LUCK - When a bean encounters a peg, it shall have equal chances of falling left or right.

FUN-SKILL - At the start of the program, each bean shall be assigned a skill level 0 to 9 according to a normal distribution.  The skill level of a bean shall not change for the duration of the program.  When falling through the machine, a bean shall fall right for the first N pegs it encounters, where N is its skill level.  For the remaining pegs, it shall fall left.
