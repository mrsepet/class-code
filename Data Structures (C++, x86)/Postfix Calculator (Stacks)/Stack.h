//William Emmanuel
//wre9fz
//Feb 5, 2012
//Stack.h

#ifndef STACK_H
#define STACK_H

#include <iostream>
#include <stddef.h>
#include "stackNode.h"
using namespace std; 

class stackNode; 

class Stack {
 public: 
  Stack(); 
  ~Stack(); 
  void push(int x); 
  void pop();
  int top();
  bool isEmpty();  
  void clear();  
 private:
  stackNode *head;
  int count;  
}; 

#endif
