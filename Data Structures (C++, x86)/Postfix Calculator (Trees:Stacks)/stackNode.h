// William Emmanuel
// wre9fz
// Feb 5, 2012
// stackNode.h

#ifndef STACKNODE_H
#define STACKNODE_H

#include <iostream>
#include <stddef.h>
#include "Stack.h"
#include "TreeNode.h"
using namespace std; 

class stackNode {
 public:
  stackNode();  
 private: 
  stackNode *next;
  TreeNode *value;   
  friend class Stack; 
  ; 
}; 

#endif
