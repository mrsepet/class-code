//William Emmanuel
//wre9fz
//Mar 3
//wordPuzzle.cpp

//RUNTIME OF WORDPUZZLE.CPP DICTIONARY INPUT: 0.038997 s
//BigTheta Runtime: Linear

#include <iostream>
#include <string>
#include "hashTable.h"
#include <fstream>
#include <math.h>
#include "timer.h"
#include <sstream>

using namespace std;
//PRIME METHODS
int getNextPrimeNumber (int num);
bool checkprime(int nn); 
int getPrevPrimeNumber (int num);
bool checkforlargeprime (int num);
string switchDir(int dir); 
bool checkprime(int nn) {
  if (nn > 100) 
    return checkforlargeprime(nn);
  else {
    int k=2;
    while (k < nn) {
      int sd = nn%k;
      if ( sd == 0) 
        return false;
      k++;
    }
  }
  return true;
}
int getNextPrimeNumber (int num) {
  int nam = num+1;
  bool das = true;
  while ( das == true ) {
    if (checkprime(nam))
      das = false;
    else
      nam = nam+1;
  }
  return nam;
}
int getPrevPrimeNumber (int num) {
  int nam = num-1;
  int das = true;
  while ( das == true ) {
    if (checkprime(nam))
      das = false;
    else
      nam = nam-1;
  }
  if (nam < 2)
    nam=2;
  return nam;
}
bool checkforlargeprime (int num) {
  if (num > 100) {
    int sss = ((int)(sqrt((double)num)))+1;
    int pn = 2;

    while (pn < sss) {
      if (num%pn == 0) {
        return false;
      }
      pn = getNextPrimeNumber(pn);
    }
    return true;
  } else {
    return false;
  }
}
#define MAXROWS 500
#define MAXCOLS 500
char table[MAXROWS][MAXCOLS];

// Forward declarations
bool readInTable (string filename, int &rows, int &cols);
char* getWordInTable (int startRow, int startCol, int dir, int len,
                      int numRows, int numCols);


bool readInTable (string filename, int &rows, int &cols) {
  string line;
  ifstream file(filename.c_str());
  if ( !file.is_open() )
    return false;
  file >> rows;
  getline (file,line);
  file >> cols;
  getline (file,line);
  getline (file,line);
  file.close();
  int pos = 0;
  for ( int r = 0; r < rows; r++ ) {
    for ( int c = 0; c < cols; c++ ) {
      table[r][c] = line[pos++];
    }
  }
  return true;
}
char* getWordInTable (int startRow, int startCol, int dir, int len,
                      int numRows, int numCols) {
  static char output[256];
  if ( len >= 255 )
    len = 255;
  int pos = 0, r = startRow, c = startCol;
  for ( int i = 0; i < len; i++ ) {
    if ( (c >= numCols) || (r >= numRows) || (r < 0) || (c < 0) )
      break;
    output[pos++] = table[r][c];
    switch (dir) { 
    case 0:
      r--;
      break; // north
    case 1:
      r--;
      c++;
      break; // north-east
    case 2:
      c++;
      break; // east
    case 3:
      r++;
      c++;
      break; // south-east
    case 4:
      r++;
      break; // south
    case 5:
      r++;
      c--;
      break; // south-west
    case 6:
      c--;
      break; // west
    case 7:
      r--;
      c--;
      break; // north-west
    }
  }
  output[pos] = 0;
  return output;
}
int main(int argc, char* argv[])
{  
  string s = "";
  ifstream file;

  //Count words in dict
  file.open(argv[1]);
  int count = 0; 
  while (!file.eof()){
     getline(file, s);
     count++;  
  }
  file.close();

  //Add words in dict
  file.open(argv[1]); 
  count = count*10000; 
  count = getNextPrimeNumber(count); 
  hashTable dict = hashTable(count); 
  while (!file.eof()){
     getline(file, s);
     dict.insert(s); 
  }
  file.close(); 

  //Grid input
 // to hold the number of rows and cols in the input file
  int rows, cols;

  // attempt to read in the file
  bool result = readInTable (argv[2], rows, cols);

  // if there is an error, report it
  if ( !result ) {
    cout << "Error reading in file!" << endl;
    return 1; // requires the <stdlib.h> #include header!
  }
  timer t; 
  t.start(); 
  cout << endl;
  count = 0; 
  string f = ""; 
  int test = 0; 
  stringstream output; 
  for ( int c = 0; c < cols; c++ ) {
    for ( int r = 0; r < rows; r++ ) {
      for ( int i = 0; i < 8; i++) {
	for (int l = 3; l < 22; l++) {
	  string lookup = getWordInTable(r,c,i,l,rows,cols); 
	  if(dict.find(lookup)&&(lookup.length() >= 3)) {  
	    stringstream ins;    
	    ins << switchDir(i) << "(" << r << ", " << c << "):	" << lookup; 
	    if (f != ins.str()) {
	      f = ins.str(); 
	      output << f << endl; 
	      count++; 
	    }
	  }
	}
      }
    }
  } 
  cout << output.str();  
  cout << count << " words found" << endl; 
  t.stop(); 
  cout << "Found all words in " << t.toString() << " seconds" << endl; 
  return 0; 
}
string switchDir(int dir) {
  string s = ""; 
switch (dir) { 
    case 0:
      s = "N ";
      break; // north
    case 1:
      s = "NE";
      break; // north-east
    case 2:
      s = "E "; 
      break; // east
    case 3:
      s = "SE"; 
      break; // south-east
    case 4:
      s = "S "; 
      break; // south
    case 5:
      s = "SW"; 
      break; // south-west
    case 6:
      s = "W ";
      break; // west
    case 7:
      s = "NW"; 
      break; // north-west
    }
 return s; 
}
