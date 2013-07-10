#!/bin/bash

#William Emmanuel
#wre9fz
#March 5
#averagetime.sh

clear
echo "Average time calculator for wordPuzzle...assuming a.out is in this directory"
echo -n "Input dictionary file: "
read DICT
echo -n "Input grid file: "
read GRID
echo -n "Running $GRID for 1st time..."
RUNNING_TIME_1=`./a.out $DICT $GRID | tail -1`
echo " $RUNNING_TIME_1 ms"

echo -n "Running $GRID for 2nd time..."
RUNNING_TIME_2=`./a.out $DICT $GRID | tail -1`
echo " $RUNNING_TIME_2 ms"

echo -n "Running $GRID for 3rd time..."
RUNNING_TIME_3=`./a.out $DICT $GRID | tail -1`
echo " $RUNNING_TIME_3 ms"

echo -n "Running $GRID for 4th time..."
RUNNING_TIME_4=`./a.out $DICT $GRID | tail -1`
echo " $RUNNING_TIME_4 ms"

echo -n "Running $GRID for 5th time..."
RUNNING_TIME_5=`./a.out $DICT $GRID | tail -1`
echo " $RUNNING_TIME_5 ms"

SUM=`expr $RUNNING_TIME_1 + $RUNNING_TIME_2 + $RUNNING_TIME_3 + $RUNNING_TIME_4 + $RUNNING_TIME_5`
AVERAGE=`expr $SUM / 5`
echo "Average runtime: $AVERAGE ms"
exit 0
