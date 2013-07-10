/**
* Name: William Emmanuel
* Computing ID: wre9fz
* Lab Section: 1111
* Date: 2/11/12
*/
import java.util.Scanner;
public class Prime {
	public static void main(String[] args) {
		Scanner myKeyboard = new Scanner(System.in);
		System.out.print("Enter an integer: ");
		int num;
		int i = 2;
		num = myKeyboard.nextInt();
		if (num == 1){System.out.print(num+" is not prime!"); System.exit(0);}
		while (i != num - 1){
			if (num % i == 0) {System.out.print(num + " is not prime!"); System.exit(0);}
			else i++;
		}
		System.out.print(num + " is prime!");
	}
}
