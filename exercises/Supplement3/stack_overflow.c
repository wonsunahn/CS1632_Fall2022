#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct _Node {
  unsigned char data[8];
  struct _Node *next;
} Node;

unsigned char *first_data = "Hello..";
unsigned char *second_data = "World..";
unsigned char *third_data = ".......";

void send_data(unsigned char *data, int len) {
  printf("[Sent data]\n");
  for (int i=0; i < len; i++) {
    printf("%2x ", data[i]);
  }
  printf("\n");
}

int main(int argc, char **argv) {
  Node first, second, third;

  // Create three nodes and link them together in a list
  memcpy(first.data, first_data, 8);
  first.next = &second;
  memcpy(second.data, second_data, 8);
  second.next = &third;
  memcpy(third.data, third_data, 8);
  third.next = NULL;

  // Optionally print out stack layout in verbose mode
  if (argc == 2 && strcmp(argv[1], "-v") == 0) { 
    printf("[Stack Frame]\n");
    printf("return address = %p\n", *((u_int64_t*)&third.next + 4));
    printf("old base pointer = %p <--- base pointer\n", *((u_int64_t*)&third.next + 3));
    printf("padding (8 bytes)\n");
    printf("padding (8 bytes)\n");
    printf("third.next = %p\n", third.next);
    printf("third.data = %s\n", third.data);
    printf("second.next = %p <--- Sent!\n", second.next);
    printf("second.data = %s <--- Sent!\n", second.data);
    printf("first.next = %p <--- Sent!\n", first.next);
    printf("first.data = %s <--- Sent!\n", first.data);
  }

  // Send contents of first.data to the screen
  send_data(first.data, 32);

  return 0;
}
