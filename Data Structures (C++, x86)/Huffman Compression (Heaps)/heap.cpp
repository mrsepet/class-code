// William Emmanuel
// wre9fz
// Apr 13, 2013
// hash.cpp

#include <iostream>
#include <stdio.h>
#include <iostream>
#include "heap.h"


using namespace std;

heap::heap() {
  elements = 0;
  size = 1; 
  array = new huffNode*[1](); 
}

heap::~heap() {}

void heap::insert (huffNode* x) {
    if(elements == size)
      resize();
    int hole = elements;
    for( ; hole > 0 && x->getFreq() < array[hole /2]->getFreq(); hole /= 2) 
      array[hole] = array[hole/2];
    array[hole] = x;
    elements++; 
}

void heap::resize() {
  size = size*2; 
  huffNode** temp = new huffNode*[size];
  for (int i = 0; i <= elements; i++)
    temp[i] = array[i];  
  array = temp; 
}

void heap::deleteMin() {
  if(!isEmpty()){
    array[ 0 ] = array[ elements-1 ]; 
    elements--; 
    percolateDown(0); 
  }
}

void heap::percolateDown( int hole ) {
  int child;
  huffNode* tmp = array[hole];  
  for( ; hole * 2 <= elements; hole = child ) {
    child = hole * 2 ;
    if(child != elements && array[child+1]->getFreq() < array[child]->getFreq()) 
      child++;
    if(array[child]->getFreq() < tmp->getFreq()) 
      array[hole] = array[child];
    else
      break;
  }
  array[hole] = tmp;
}

bool heap::isEmpty() {
  if (elements==0) return true; 
  else return false; 
}

huffNode* heap::findMin() {
  if (elements != 0)
    return array[0]; 
  else return NULL; 
}

void heap::print() {
  for (int i = 0; i < elements; i++) {
    cout << "Char " << i << " : " << (*array[i]).getC() << " Freq: " << (*array[i]).getFreq() << endl;
  }
}

huffNode* heap::find(char x) {
  for (int i = 0; i < elements; i++) 
    if ((*array[i]).getC()==x) 
      return (array[i]); 
  return NULL; 
}

bool heap::contains(char x) {
  for (int i = 0; i < elements; i++) {
    if (array[i]->getC()==x) 
      return true; 
  }
  return false; 
}

void heap::buildHeap() {
  for (int i = elements/2-1 ; i >= 0; i-- )
    percolateDown(i); 
}
int heap::getSize() {
  return elements; 
}