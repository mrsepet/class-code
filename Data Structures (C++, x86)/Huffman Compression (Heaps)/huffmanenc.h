// William Emmanuel
// wre9fz
// Apr 13, 2013
// huffmanenc.h

#ifndef HUFFMANENC_H
#define HUFFMANENC_H

#include "huffNode.h"
#include "heap.h"
#include <iostream>
#include <stdio.h>
#include <sstream>
#include <map>

using namespace std; 

class huffmanenc {
 public:
  huffmanenc(); 
  heap makeTree(heap x); 
  void printTree(heap x, stringstream& file); 
  void printTreeRecursive(huffNode* r, stringstream& file);
  void buildCodes(huffNode* r); 
  map<char,string> getCodes(heap x); 
  void getCodesR(huffNode* r); 
 private:
   stringstream ins; 
   map<char,string> codes; 
};

#endif
