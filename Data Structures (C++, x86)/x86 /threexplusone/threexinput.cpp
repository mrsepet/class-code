//William Emmanuel
//wre9fz
//Apr 7
//threexinput.cpp

#include <iostream>
#include <cstdlib>
#include "timer.h"

using namespace std; 

extern "C" int threexplusone(int);

int main() {
  int x, n; 
   cout << "Enter number: " ; 
   cin >> x; 
   cout << "Number of times to run: "; 
   cin >> n; 
   if (n<0) {
     cout << "Enter number above 0" << endl; 
     return 1; 
   }
   timer time; 
   time.start(); 
   for(int i = n; i>0; i--){
     threexplusone(x);   
   } 
   time.stop(); 
   cout << "Steps: " << threexplusone(x) << endl; 
   double total = time.getTime(); 
   total = total * 1000000000; 
   double average = total/n;
   cout << "Average time: " << average << " ns" << endl; 
   return 0; 
}
