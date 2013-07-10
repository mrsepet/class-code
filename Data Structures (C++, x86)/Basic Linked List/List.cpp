/* William Emmanuel
 * wre9fz
 * Jan 27
 * List.cpp
 */

#include "List.h"
#include "ListNode.h"
#include "ListItr.h"
#include <stddef.h>
#include <iostream>
using namespace std; 
 
List::List() 
{
  count = 0; 
  head = new ListNode;
  tail = new ListNode;
 
  head -> next = tail; 
  tail -> previous = head;
  head -> previous = NULL;
  tail -> next = NULL;
}	     
List::~List()
{
  makeEmpty(); 
  delete head; 
  delete tail;    
}		
void List::insertAtTail(int x)
{     
  ListNode *temp = tail -> previous;
  tail -> previous = new ListNode();
  tail -> previous -> value = x;  
  tail -> previous -> next = tail; 
  temp -> next = tail -> previous; 
  tail -> previous -> previous = temp;
  count++;  
}

List::List(const List& source) {//Copy Constructor
  head=new ListNode;
  tail=new ListNode;
  head->next=tail;
  tail->previous=head;
  count=0;
  ListItr iter(source.head->next);
  while (!iter.isPastEnd()) {//deep copy of the list
    insertAtTail(iter.retrieve());
    iter.moveForward();
  }
}
List& List::operator=(const List& source){ //Equals operator
  if (this == &source)
    return *this;
  else {
    makeEmpty();
    ListItr iter(source.head->next);
    while (!iter.isPastEnd()) {
      insertAtTail(iter.retrieve());
      iter.moveForward();
    }
  }
  return *this;
}

bool List::isEmpty() const{
  if (count==0) return true; 
  else return false; 
}	
void List::makeEmpty(){
  ListItr iter = first(); 
  iter.moveForward(); 
  while (!iter.isPastEnd()){
    delete iter.current -> previous; 
    iter.moveForward(); 
  } 
    head -> next = tail; 
    tail -> previous = head; 
    count = 0; 
} 
ListItr List::first(){
  return ListItr(head -> next); 
} 		
ListItr List::last(){
  return ListItr(tail -> previous); 
}
void List::insertAfter(int x, ListItr position){
  if (position.current -> next != NULL){ 
 ListNode *temp = position.current -> next; 
  position.current -> next = new ListNode;
  position.current -> next -> value = x;  
  position.current -> next -> previous = position.current; 
  position.current -> next -> next = temp; 
  temp -> previous = position.current -> next; 
  count++; 
}
}
void List::insertBefore(int x, ListItr position){
  if ( position.current -> previous != NULL){ 
  ListNode *temp = position.current -> previous; 
  position.current -> previous = new ListNode;
  position.current -> previous -> value = x;  
  position.current -> previous -> next  = position.current; 
  position.current -> previous -> previous  = temp; 
  temp -> next = position.current -> previous; 
  count++; 
}
}

void List::remove(int x){
  ListItr del = find(x); 
  if (del.current -> next != NULL) {
    ListNode *tempp = del.current -> previous; 
    ListNode *tempn = del.current -> next; 
    tempp -> next = tempn; 
    tempn -> previous = tempp; 
    delete del.current; 
  }
  count = count -1; 
}
	       
ListItr List::find(int x){
  ListItr search = first();  
  while (!search.isPastEnd()) {
    if (search.retrieve() == x) return search; 
    else search.moveForward(); 
  } 
  return ListItr(); 
}

int List::size() const{
  return count; 
}

void printList(List& source, bool direction){
  if (direction) {
    cout << "List Contents: " << endl; 
    ListItr printer = source.first();
    while (!printer.isPastEnd()) {
      cout << printer.retrieve() << endl; 
      printer.moveForward(); 
    } 
  }
  else {
    cout << "List Contents: " << endl;
    ListItr printer = source.last(); 
    while (!printer.isPastBeginning()) {
      cout << printer.retrieve() << endl;
      printer.moveBackward(); 
    }
  } 
}

