#!/bin/bash

# clear the screen
clear

# print the commands as they execute
set -o xtrace

# run a client
java -cp bin Client
