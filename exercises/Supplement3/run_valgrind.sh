#!/bin/bash

if [ -z "$1" ]
  then
    echo "Usage: run_valgrind.sh <program to run>"
    exit
fi

echo "valgrind $@"
valgrind $@
