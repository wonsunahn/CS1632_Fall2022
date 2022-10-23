#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void send_data(unsigned char *data, int len) {
  printf("[Sent data]\n");
  for (int i=0; i < len; i++) {
    printf("%2x ", data[i]);
  }
  printf("\n");
}

unsigned char* bar() {
  // Array data[8] is deallocated immediately on function return as it falls
  // out of scope.  It is deallocated with the bar()'s stack frame.
  unsigned char data[8] = {1, 2, 3, 4, 5, 6, 7, 8};
  return data;
}

int main() {
  unsigned char *data = bar();
  // At this point, data becomes a dangling pointer!
  send_data(data, 8);
  return 0;
}
