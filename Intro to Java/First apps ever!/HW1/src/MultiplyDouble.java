	/**
	* Name: William Emmanuel
	* Computing ID: wre9fz
	* Lab Section: 1111
	* Date: 1/26/12
	*/
import java.util.Scanner;

public class MultiplyDouble {
    
    public static void main(String[] args) {
        Scanner myKeyboard = new Scanner(System.in);
        
        System.out.print("Input a number: ");
        double a;
        a = myKeyboard.nextDouble();
        
        System.out.print("Input another number: ");
        double b;
        b = myKeyboard.nextDouble();
        
        System.out.print("Input a third number: ");
        double c;
        c = myKeyboard.nextDouble();
        
        double z;
        z = a * b * c;
        System.out.print("The result is ");
        System.out.println(z);
    }
    
}