FUN-ARGS-NUMBER - The system shall accept four arguments from the command line, in the following order: good option, bad option, number of times, number of threads.  If there are fewer or more than four arguments, the system shall display the usage information for the program and shut down.

FUN-ARGS-INVALID - If an argument is invalid for any reason (such as, the arguments for the number of times or number of threads cannot be parsed as a positive integer), then the system shall explain the reason that it cannot run and shall shut down.  At no point shall the system display a Java exception or stack trace directly to the user.

FUN-DISPLAY-RESULTS - The system shall display the results of Monty Hall simulation to the user, using percentages with up to three places after the decimal, and then stop execution.  This display shall print out the passed-in String versions of the "good" and "bad" options as defined in the arguments.

FUN-DISPLAY-ITERATIONS - The system shall display the number of iterations executed by each thread, where there are as many threads as specified in the arguments.  The sum of the number of iterations shall be equal to the number times specified in the arguments.  Each thread shall execute an equal share of the number of times, or be off by at most 1 if number of times is not a multiple of the number of threads.

FUN-SMALL-NUM - If the "number of times" argument is less than 100, the system shall issue a warning and ask the user if they wish to continue.
