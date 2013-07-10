// William Emmanuel
// wre9fz
// Feb. 3, 2012
// postfixCalculator.cpp

#include <iostream>
#include <stddef.h>
#include <string>
#include <istream> 
#include <cstdlib>
#include "postfixCalculator.h"
#include "Stack.h"
#include "stackNode.h"
using namespace std; 

postfixCalculator::postfixCalculator() { 
}
postfixCalculator::~postfixCalculator() {
}

int postfixCalculator::input(string pf){ 
  int s = 0, f = 0;  
  while (f<pf.size()) { 
    while (f<pf.size() && pf.at(f) != ' ') f++;  
    string token = pf.substr(s, (f-s));
    if (token.size() == 1 && !isdigit(token.at(0))) {
	if (token.at(0) == '+') add(); 
	else if (token.at(0) == '-') subtract(); 
	else if (token.at(0) == '*') multiply(); 
	else if (token.at(0) == '/') divide(); 
	else if (token.at(0) == '~') negate();  
	else; 
    } else addNum(atoi(token.c_str()));
    f++; 
    s = f; 
  }
  return getTopValue();   
}
void postfixCalculator::clear() {
  while (!s.isEmpty()) s.pop();  
}
void postfixCalculator::addNum(int x) {
  s.push(x); 
}
void postfixCalculator::add(){
  int x = s.top(); 
  s.pop();  
  int y = s.top(); 
  s.pop(); 
  s.push(x+y); 
}
void postfixCalculator::subtract(){
  int x = s.top(); 
  s.pop();  
  int y = s.top(); 
  s.pop(); 
  s.push(y-x); 

}
void postfixCalculator::multiply(){
  int x = s.top(); 
  s.pop();  
  int y = s.top(); 
  s.pop(); 
  s.push(x*y); 

}
void postfixCalculator::divide(){
  int x = s.top(); 
  s.pop();  
  int y = s.top(); 
  s.pop();
  int z = (y/x);  
  s.push(z); 
}
void postfixCalculator::negate(){
  int x = s.top(); 
  s.pop();  
  s.push(-x); 
}
int  postfixCalculator::getTopValue(){
  return s.top(); 
}
