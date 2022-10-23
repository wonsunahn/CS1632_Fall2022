#include <stdlib.h>
#include <stdio.h>

int main() {
  char *p = malloc(8);
  printf("p = %p\n", p);
  free(p);
  return 0;
}
