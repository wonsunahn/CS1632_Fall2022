TARGETS = heap.bin stack.bin stack_overflow.bin stack_pointer_return.bin heap_overflow.bin binary_tree.bin datarace.bin
OBJS = heap.o stack.o stack_overflow.o o stack_pointer_return.o heap_overflow.o binary_tree.o datarace.o
ASANTARGETS = stack_overflow.asan stack_pointer_return.asan heap_overflow.asan binary_tree.asan
ASANOBJS = stack_overflow.asan.o stack_pointer_return.asan.o heap_overflow.asan.o binary_tree.asan.o
TSANTARGETS = datarace.tsan
TSANOBJS = datarace.tsan.o

CC = gcc

COPT = -g -w
LOPT = -lm

ASANCOPT = -fsanitize=address
ASANLOPT = -fsanitize=address

TSANCOPT = -fPIE -fsanitize=thread
TSANLOPT = -pie -fsanitize=thread

all: $(TARGETS) $(ASANTARGETS) $(TSANTARGETS)

%.o: %.c
	$(CC) -c $(COPT) $< -o $@

%.asan.o: %.c
	$(CC) -c $(COPT) $(ASANCOPT) $< -o $@

%.tsan.o: %.c
	$(CC) -c $(COPT) $(TSANCOPT) $< -o $@

%.bin: %.o
	$(CC) $< $(LOPT) -o $@

%.asan: %.asan.o
	$(CC) $< $(LOPT) $(ASANLOPT) -o $@

%.tsan: %.tsan.o
	$(CC) $< $(LOPT) $(TSANLOPT) -o $@

datarace.o: datarace.c
	$(CC) -c $(COPT) -pthread $< -o $@

datarace.bin: datarace.o
	$(CC) $< $(LOPT) -pthread -o $@

datarace.tsan.o: datarace.c
	$(CC) -c $(COPT) $(TSANCOPT) -pthread $< -o $@

datarace.tsan.bin: datarace.o
	$(CC) $< $(LOPT) $(TSANLOPT) -pthread -o $@

.PRECIOUS: $(OBJS) $(ASANOBJS) $(TSANOBJS)

clean:
	rm -f $(TARGETS) $(ASANTARGETS) $(TSANTARGETS) $(OBJS) $(ASANOBJS) $(TSANOBJS)
