///William Emmanuel
//wre9fz
//Apr 14
//huffNode.cpp 

#ifndef HUFFNODE_H
#define HUFFNODE_H

#include <iostream>
#include <stdio.h>
#include <iostream>
#include <sstream>

using namespace std; 

class huffNode {
 public:
  huffNode(); 
  huffNode(char x);
  ~huffNode(); 
  char getC(); 
  int getFreq(); 
  void count(); 
  string getCode(); 
 private:
 	char c; 
 	int freq; 
 	huffNode* right; 
 	huffNode* left;  
 	huffNode* parent; 
 	stringstream code; 
 	friend class huffmanenc; 
};

#endif