//William Emmanuel
//wre9fz
//Apr 13, 2013
//hashTest.cpp

#include <iostream>
#include <stdio.h>
#include <sstream>
#include <iostream>
#include <map>
#include "huffNode.h"
#include "heap.h"
#include "huffmanenc.h"

using namespace std;

int main (int argc, char **argv) {
    heap x; 
    if ( argc != 2 ) {
        cout << "Must supply the input file name as the one and only parameter" << endl;
        return 1;
    }
    FILE *fp = fopen(argv[1], "r");
    if ( fp == NULL ) {
        cout << "File '" << argv[1] << "' does not exist!" << endl;
        return 2; 
    }
    char g; 

    while ( (g = fgetc(fp)) != EOF ) {
        if (!x.contains(g)) {
            huffNode* y = new huffNode(g); 
            x.insert(y); 
        }
        else 
            x.find(g)->count();  
    }

    //Build the heap
    x.buildHeap();

    //Make the huffman tree in the heap
    huffmanenc encoder; 
    x = encoder.makeTree(x);  
    stringstream file; 

    //Print the huffman codes to file
    encoder.printTree(x, file);

    //Separator
    file << "----------------------------------------\n"; 

    //Make map of char-> huffman code
    map<char,string> codes = encoder.getCodes(x); 
    
    rewind(fp); 
    int bits = 0; 
    int chars = 0; 
    while ( (g = fgetc(fp)) != EOF ) {
         file << codes[g] << " ";   
         bits += codes[g].length(); 
         chars++; 
    }
    file << "\n" << "----------------------------------------\n" ; 
    cout << file.str(); 
    double ratio = (double)(chars*8)/bits; 
    double cost = (double)bits/chars; 
    cout << "There are a total of " << chars << " symbols that are encoded." << endl; 
    cout << "There are " << codes.size() << " distinct symbols used." << endl; 
    cout << "There were " << chars*8 << " bits in the original file." << endl; 
    cout << "There are " << bits << " bits in the compressed file." << endl; 
    cout << "This gives a compression ratio of " << ratio << "." << endl; 
    cout << "The cost of the Huffman tree is " << cost << " bits per character." << endl; 

    fclose(fp);
    return 0; 
}