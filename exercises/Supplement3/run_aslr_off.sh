#!/bin/bash

if [ -z "$1" ]
  then
    echo "Usage: run_aslr_off.sh <program to run>"
    exit
fi

echo "setarch `uname -m` -R $1 $2 $3"
setarch `uname -m` -R $1 $2 $3
