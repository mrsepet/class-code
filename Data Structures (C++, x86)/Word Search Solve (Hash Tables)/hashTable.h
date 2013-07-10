//William Emmanuel
//wre9fz
//Mar 1
//hashTable.h
#ifndef HASHTABLE_H
#define HASHTABLE_H

#include <iostream>
#include <stddef.h>
#include <string>
#include <list>

using namespace std; 

class hashTable {
 public: 
  hashTable(int inSize); 
  ~hashTable();
  bool insert(string value);  
  bool find(string keySearch); 
  int hashFunction(string str); 
 private:
  string *table;
  int size; 
  unsigned int *primes; 
}; 

#endif 
