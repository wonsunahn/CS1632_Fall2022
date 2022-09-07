#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void bad(int len) {
  char* notSecret = "open data";
  char* secret = "SECRET DATA HERE! NOBODY SHOULD SEE THIS!";
  printf("Sending data:\n");
  for (int j=0; j < len; j++) {
    printf("%c", notSecret[j]);
  }

}

int main() {
  int l;
  puts("Enter length of data:");
  scanf("%d", &l);
  bad(l);
  puts("");
}
