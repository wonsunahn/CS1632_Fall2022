echo -e "Running 'time binary_tree 15'"
echo -e "======================================"
time ./binary_tree 10
echo -e "======================================\n"

echo -e "Running 'time binary_tree.asan 15'"
echo -e "======================================"
time ./binary_tree.asan 10
echo -e "======================================\n"

echo -e "Running 'time valgrind binary_tree 15'"
echo -e "======================================"
time valgrind ./binary_tree 10
echo -e "======================================\n"
