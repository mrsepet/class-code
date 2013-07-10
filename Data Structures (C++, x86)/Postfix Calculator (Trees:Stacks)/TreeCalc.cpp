
// William Emmanuel
// wre9fz
// Feb 25, 2012
// TreeCalc.cpp:  CS 2150 Tree Calculator method implementations

#include "TreeCalc.h"
#include <iostream>
#include <cstddef>
#include <stdlib.h>
using namespace std;

//Constructor
TreeCalc::TreeCalc()
{
}

//Destructor- frees memory
TreeCalc::~TreeCalc()
{
  while(!mystack.isEmpty()){
    delete mystack.top(); 
    mystack.pop(); 
  }
}

//Deletes tree/frees memory
void TreeCalc::cleanTree(TreeNode* ptr)
{
  if (ptr -> right != NULL) cleanTree(ptr->right); 
  if (ptr -> left != NULL) cleanTree(ptr->left);
  delete ptr; 
}

//Gets data from user
void TreeCalc::readInput()
{
    string response;
    cout << "Enter elements one by one in postfix notation" << endl
    << "Any non-numeric or non-operator character,"
    << " e.g. #, will terminate input" << endl;

    cout << "Enter first element: ";
    cin >> response;

    //while input is legal
    while (isdigit(response[0]) || response[0]=='/' || response[0]=='*'
            || response[0]=='-' || response[0]=='+' )
    {
        insert(response);
        cout << "Enter next element: ";
        cin >> response;
    }
}

//Puts value in tree stack
void TreeCalc::insert(const string& val)
{ 
  if (val.size() == 1 && !isdigit(val.at(0))) {
    TreeNode *tempNode = new TreeNode(val); 
    tempNode -> left = mystack.top(); 
    mystack.pop(); 
    tempNode -> right = mystack.top(); 
    mystack.pop(); 
    mystack.push(tempNode); 
  } else { 
    TreeNode *tempNode = new TreeNode(val); 
    mystack.push(tempNode);
  }   
}

//Prints data in prefix form
void TreeCalc::printPrefix(TreeNode* ptr) const
{
  cout << ptr -> value << " ";
  if (ptr -> right != NULL) printPrefix(ptr->right); 
  if (ptr -> left != NULL) printPrefix(ptr->left);
}

//Prints data in infix form
void TreeCalc::printInfix(TreeNode* ptr) const
{  
  if (ptr -> right != NULL) {
    cout << "("; 
    printInfix(ptr -> right);
  }
    //Is it an operator? if so, print space on either side. Else, print value 
    if((ptr -> value).size() == 1 && !isdigit((ptr->value).at(0))){
       cout << " " <<  ptr -> value << " " ;  
    } else {
       cout << ptr -> value; 
    }

    //Print right subtree/child
    if (ptr -> left != NULL) {
      printInfix(ptr -> left);
      cout << ")"; 
    }
   
}

//Prints data in postfix form
void TreeCalc::printPostfix(TreeNode* ptr) const
{
  if (ptr -> right != NULL) printPostfix(ptr->right); 
  if (ptr -> left != NULL) printPostfix(ptr->left);
  cout << ptr -> value << " "; 
}

// Prints tree in all 3 (pre,in,post) forms
void  TreeCalc::printOutput() const {
    if (mystack.size()!=0 && mystack.top()!=NULL)
    {
        cout << "Expression tree in postfix expression: ";
        printPostfix(mystack.top()); 
        cout << endl;

        cout << "Expression tree in infix expression: ";
        printInfix(mystack.top()); // call your implementation of printInfix()
        cout << endl;

        cout << "Expression tree in prefix expression: ";
        printPrefix(mystack.top()); // call your implementation of printPrefix()
        cout << endl;
    }
    else
        cout<< "Size is 0." << endl;
}

//Evaluates tree, returns value
// private calculate() method
int TreeCalc::calculate(TreeNode* ptr) const
{
 if ((ptr->value).size() == 1 && !isdigit((ptr->value).at(0))) {
	if ((ptr->value).at(0) == '+') return (calculate(ptr->right) + calculate(ptr->left));  
	else if ((ptr->value).at(0) == '-') return (calculate(ptr->right) - calculate(ptr->left)); 
	else if ((ptr->value).at(0) == '*') return (calculate(ptr->right) * calculate(ptr->left)); 
	else return (calculate(ptr->right)/calculate(ptr->left)); 
 } 
 else return atoi((ptr->value).c_str()); 
}

//Calls calculate, sets the stack back to a blank stack
// public calculate() method. Hides private data from user
int TreeCalc::calculate()
{
    int i = calculate(mystack.top()); 
    while(!mystack.isEmpty()) {
      delete mystack.top(); 
      mystack.pop(); 
    }
    return i; 
}
