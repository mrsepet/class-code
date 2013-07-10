// William Emmanuel
// wre9fz
// Apr 13, 2013
// hash.h

#ifndef HEAP_H
#define HEAP_H

#include <iostream>
#include <stdio.h>
#include <iostream>
#include "huffNode.h"

using namespace std; 

class heap {
 public:
  heap();
  ~heap(); 
  void insert(huffNode* x); 
  void deleteMin(); 
  huffNode* findMin(); 
  bool isEmpty();
  void print(); 
  huffNode* find(char x); 
  bool contains(char x); 
  void buildHeap(); 
  int getSize(); 

 private:
  huffNode** array; 
  void resize();
  int size; 
  int elements;
  void percolateDown(int hole); 
};

#endif
