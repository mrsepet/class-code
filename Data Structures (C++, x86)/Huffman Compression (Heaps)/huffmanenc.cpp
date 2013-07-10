///William Emmanuel
//wre9fz
//Apr 14
//huffmanenc.cpp 

#include "huffmanenc.h"
#include <iostream>
#include <stdio.h>
#include <iostream>
#include <sstream>
#include <map>

using namespace std; 

huffmanenc::huffmanenc() {}

heap huffmanenc::makeTree(heap x) {
	while (x.getSize() > 1) {
		huffNode* r = new huffNode();  

		r->right = x.findMin(); 
		r->right->parent = r; 
		r->right->code.str("1"); 
		x.deleteMin();

		r->left = x.findMin(); 
		r->left->parent = r;
		r->left->code.str("0"); 
		x.deleteMin();

		r->freq=(r->right->getFreq()) + (r->left->getFreq()); 
		x.insert(r);
	}
	return x; 
}
void huffmanenc::printTree(heap x, stringstream& file) {
	huffNode* r = x.findMin(); 
	buildCodes(r); 
	printTreeRecursive(r, file); 
}
map<char, string> huffmanenc::getCodes(heap x) {
	codes = map<char,string>();
	getCodesR(x.findMin()); 
	return codes; 
}
void huffmanenc::getCodesR(huffNode* r) {
	if (r->left == NULL && r->right == NULL) 
		codes[r->c] = r->code.str(); 
	if (r->left!=NULL) 
		getCodesR(r->left); 
	if (r->right!=NULL) 
		getCodesR(r->right); 
}
void huffmanenc::buildCodes(huffNode* r) { 
	if (r->parent != NULL) 
		r->code << r->parent->code.str() << r->code.str();
	if (r->left!=NULL) 
		buildCodes(r->left);
	if (r->right!=NULL) 
		buildCodes(r->right); 
} 
void huffmanenc::printTreeRecursive(huffNode *r, stringstream& file) {
	if (r->left == NULL && r->right == NULL) {
		file << r->c << " " << r->code.str() << endl;
	}
	if (r->left!=NULL) 
		printTreeRecursive(r->left, file); 
	if (r->right!=NULL) 
		printTreeRecursive(r->right, file); 
}  