//William Emmanuel
//wre9fz
//Feb 5, 2012
//Stack.cpp

#include <iostream>
#include <stddef.h>
#include "stackNode.h"
#include "Stack.h"
using namespace std; 

Stack::Stack() {
  head = new stackNode(); 
  count = 0; 
}
Stack::~Stack() {
  clear(); 
  delete head; 
}
void Stack::push(int x) {
  stackNode *temp = head -> next;
  head -> next = new stackNode();
  head -> next -> value = x;  
  if (count != 0) head -> next -> next = temp; 
  count++; 
}
void Stack::pop() {
  if (count != 0) {
    stackNode *temp = head -> next -> next;  
    delete head -> next; 
    head -> next = temp;
    count--;  
  }
  else cout << "List is empty" << endl; 
}
int Stack::top() {
  if (count == 0) {
    cout << "List is empty" << endl; 
  } 
  else  return head -> next -> value; 
}
bool Stack::isEmpty() {
  if (count==0) return true; 
  else return false; 
}
void Stack::clear() {
  while (count!=0){
    pop();  
  } 
}

