You will find two files echo.c and echo2.c.  Using the address sanitizer, try to pinpoint the source code line where the memory error occurs for each file.  Then, try to explain why the memory error happened.  Hint: you can refer to Supplementary Exercise 1 and the Nondeterminism lecture on how to use the address sanitizer.  The commands to compile the ASAN viersion of the binaires would be:
 
```
$ gcc -g -fsanitize=address echo.c -o echo.asan
```
and
```
$ gcc -g -fsanitize=address echo2.c -o echo2.asan
```
