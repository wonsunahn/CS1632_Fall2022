#include <stdio.h>
#include <pthread.h>

int shared = 0;

void *add(void *unused) {
  for(int i=0; i < 1000000; i++) { 
    shared++;
  }
  return NULL;
}

int main() {
  pthread_t t;
  // Child thread starts running add
  pthread_create(&t, NULL, add, NULL);
  // Main thread starts running add
  add(NULL);
  // Wait until child thread t terminates
  pthread_join(t, NULL);
  printf("shared=%d\n", shared);
  return 0;
}
