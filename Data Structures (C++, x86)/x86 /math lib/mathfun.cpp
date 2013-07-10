//William Emmanuel
//wre9fz
//Mar 25
// mathfun.cpp

#include <iostream>
#include <time.h>
#include <cstdlib>

using namespace std;

extern "C" int product(int, int);
extern "C" int power(int, int); 

// Purpose: This main program produces a vector of random
//          numbers between 0 and 99, then calls the
//          externally defined function 'vecsum' to add
//          up the elements of the vector.
// Author:  Adam Ferrari
int  main () {

  int  sum, x, y, exp;

    cout << "Please enter a number:  ";
    cin >> x;
    cout << "Please enter another number:  ";
    cin >> y;

    // sum up the vector and print out results
    sum = product(x, y);
    exp = power(x,y); 
    cout << "The product is " << sum << endl;
    cout << "The power is " << exp << endl;

    return 0;
}
