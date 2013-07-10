/**
* Name: William Emmanuel
* Computing ID: wre9fz
* Lab Section: 1111
* Date: 2/6/12
*/
import java.util.Scanner;
public class Scheduling {	
public static void main(String[] args){
	Scanner myKeyboard = new Scanner(System.in);
	System.out.println("Enter the first appointment: ");
	int one = myKeyboard.nextInt();
	int two = myKeyboard.nextInt();
	System.out.println("Enter the second appointment: ");
	int three = myKeyboard.nextInt();
	int four = myKeyboard.nextInt();
	if (two <= one)
		two = two + 2400;
	if (four <= three)
		four = four + 2400;
	if (((one > three) && (one < four)) || ((three > one) && (three < two)) || ((one == three) && (two == four)))
		System.out.println("Appointments overlap!");
	else 
		System.out.println("Appointments work!");
}
}
