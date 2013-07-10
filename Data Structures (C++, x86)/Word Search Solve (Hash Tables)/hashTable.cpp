//William Emmanuel
//wre9fz
//Mar 1
//hashTable.cpp

#include <iostream>
#include <string>
#include <stddef.h>
#include "hashTable.h"
#include <math.h>
using namespace std;  
hashTable::hashTable(int inSize) {
  table = new string[inSize]();
  size = inSize;
  unsigned int b=378551;
  unsigned int a=63689;
  primes = new unsigned int[23](); 
  for ( int i = 0; i < 23; i++) {
    primes[i] = a; 
    a = a * b;
  }
}

hashTable::~hashTable() {
}

bool hashTable::insert(string key) {
    int hash = hashFunction(key); 
    if(table[hash] == "") {
      table[hash] = key; 
      return true; 
    }
    else {
      int count = 0; 
      while(table[hash] != ""){
	count++; 
	hash = hash + pow(count, count); 
	if(hash > size)
	  hash = hash % size; 
      }  
    }
    table[hash] = key;
    return true; 
}


bool hashTable::find(string keySearch) {
  int hash = hashFunction(keySearch); 
  if(table[hash] == "") 
    return false; 
  int count = 0; 
  while(table[hash] != ""){
    if (table[hash]==keySearch)
      return true; 
    count++; 
    hash = hash + pow(count, count); 
    if(hash > size)
      hash = hash % size; 
  }
  return false; 
}

int hashTable::hashFunction(string str) {
    unsigned int hash = 0;
    for(int i = 0; i < str.length(); i++)
        hash = hash + primes[i]*str[i];
    return (hash%size); 
 }
