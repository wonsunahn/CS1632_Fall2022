#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char **argv)
{
    if (argc != 2) {
        printf("Usage: echo <string_you_want_to_echo>\n");
        return 0;
    }
    char *str = argv[1];
    char buf[5];
    strcpy(buf, str);
    printf("%s\n", buf);
    return 0;
}
