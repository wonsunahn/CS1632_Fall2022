
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
    int len = strlen(str);
    char *buf = (char *) malloc(len);  
    for (int i = 0; i < len; i++) {
        buf[i] = str[i];
    }
    printf("%s\n", buf);
    return 0;
}
