/**
* Name: William Emmanuel
* Computing ID: wre9fz
* Lab Section: 1111
* Date: 2/6/12
*/
import java.util.Scanner;
import java.util.Arrays;
public class Sort3 {
public static void main(String[] args){
	Scanner myKeyboard = new Scanner(System.in);
	System.out.print("Please give me three doubles: ");
	double[] three;
	three = new double[3];
	three[0] = myKeyboard.nextDouble();
	three[1] = myKeyboard.nextDouble();
	three[2] = myKeyboard.nextDouble();
	Arrays.sort(three);
	System.out.print("The correct order is: ");
	System.out.print(three[2] + " " + three[1] + " " + three[0]);
}
}
