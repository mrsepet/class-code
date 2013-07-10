/* William Emmanuel
 * wre9fz
 * ListItr.cpp
 * Jan 27
 */ 

#include "ListItr.h"
#include <iostream>
using namespace std; 

ListItr::ListItr() 
{
  current = new ListNode; 
}
ListItr::ListItr(ListNode* theNode) 
{
  current = theNode; 
}
bool ListItr::isPastEnd() const
{
  if (current -> next == NULL) return true; 
  else return false; 
}
bool ListItr::isPastBeginning() const 
{
  if (current -> previous == NULL) return true; 
  else return false; 
}
void ListItr::moveForward() 
{
  if (current -> next != NULL) {
    current = current -> next; 
  }
}
void ListItr::moveBackward() 
{
  if (current -> previous != NULL) {
    current = current -> previous;
  } 
}
int ListItr::retrieve() const 
{
  return current->value; 
}

