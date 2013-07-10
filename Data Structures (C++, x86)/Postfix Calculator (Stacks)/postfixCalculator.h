// William Emmanuel
// wre9fz
// Feb. 3, 2012
// postfixCalculator.h

#ifndef POSTFIXCALCULATOR_H
#define POSTFIXCALCULATOR_H

#include <iostream>
#include <stddef.h>
#include <string>
#include <istream>
#include "Stack.h"
#include "stackNode.h"
using namespace std; 

class postfixCalculator {
 public :
  postfixCalculator(); 
  ~postfixCalculator(); 
  void add(); 
  void subtract(); 
  void multiply(); 
  void divide(); 
  void negate(); 
  void addNum(int x); 
  void clear(); 
  int getTopValue();
  int input(string pf);  
 private :
  Stack s; 
}; 
#endif 
