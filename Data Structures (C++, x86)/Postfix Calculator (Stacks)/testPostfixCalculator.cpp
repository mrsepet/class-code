// William Emmanuel
// wre9fz
// Feb. 3, 2012
// testPostfixCalculator.cpp

#include <iostream>
#include <stddef.h>
#include <cstdlib>
#include <string> 
#include <istream> 
#include "postfixCalculator.h"
#include "Stack.h"
#include "stackNode.h"
using namespace std; 

int main() {
  postfixCalculator p;
  cout << "Enter postfix notation: "; 
  string pf;
  getline(cin,pf);  
  cout << endl << p.input(pf) << endl; 
  return 0; 
}
