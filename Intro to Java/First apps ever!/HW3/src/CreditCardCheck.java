/**
* Name: William Emmanuel
* Computing ID: wre9fz
* Lab Section: 1111
* Date: 2/11/12
*/
import java.util.Scanner;
public class CreditCardCheck {
	public static void main(String[] args) {
		Scanner keys = new Scanner(System.in);
		System.out.print("Please enter a credit card number with no spaces: ");
		String credit = keys.nextLine();
		int FirstSum = 0;
		int SecondSum = 0;
		int Temp;
		int TotalSum;
		int length = credit.length();
		for (int i=length-1; i>0; i=i-2) FirstSum = FirstSum + credit.charAt(i) - 48;
		for (int i=length-2; i>=0; i=i-2){
			Temp = 2*(credit.charAt(i)-48);
			if(Temp>=10){SecondSum++; Temp = Temp - 10;}
			SecondSum = SecondSum + Temp;
		}
		TotalSum = FirstSum + SecondSum;
		if (TotalSum % 10 == 0) System.out.print("Valid Credit Card!");
		else {
			System.out.println("Invalid Credit Card!");
			int c = TotalSum % 10;
			int last = length - 1;
			c =((credit.charAt(last)-48)-c)+10;
			System.out.print("The check digit should be: "+ c);
		}
	}
}
