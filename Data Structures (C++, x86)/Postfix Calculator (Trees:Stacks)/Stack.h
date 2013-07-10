//William Emmanuel
//wre9fz
//Feb 5, 2012
//Stack.h

#ifndef STACK_H
#define STACK_H

#include <iostream>
#include <stddef.h>
#include "stackNode.h"
#include "TreeNode.h"
using namespace std; 

class stackNode; 

class Stack {
 public: 
  Stack(); 
  ~Stack(); 
  void push(TreeNode *x); 
  void pop();
  TreeNode* top() const;
  bool isEmpty();  
  void clear();
  int size() const;   
 private:
  stackNode *head;
  int count;  
}; 

#endif
