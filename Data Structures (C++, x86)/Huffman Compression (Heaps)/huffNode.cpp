//William Emmanuel
//wre9fz
//Apr 14
//huffNode.cpp

#include <iostream>
#include <stdio.h>
#include <sstream>
#include <iostream>
#include "huffNode.h"

using namespace std; 

huffNode::huffNode() {
	freq = NULL; 
	c = NULL;
	left = NULL; 
	right = NULL; 
	parent = NULL; 
	code.str(""); 
}
huffNode::huffNode(char x) {
	freq = 1; 
	c = x; 
	parent = NULL; 
	left = NULL; 
	right = NULL; 
	code.str("") ;
}
huffNode::~huffNode() {
}
char huffNode::getC() {
	return c; 
}
int huffNode::getFreq() {
	return freq; 
}
void huffNode::count() {
	freq++; 
}
string huffNode::getCode() {
	return code.str(); 
}