	/**
	* Name: William Emmanuel
	* Computing ID: wre9fz
	* Lab Section: 1111
	* Date: 1/26/12
	*/

	import java.util.Scanner;

	public class Add {
	    
	    public static void main(String[] args) {
	        Scanner myKeyboard = new Scanner(System.in);
	        
	        System.out.print("Input a number: ");
	        int x;
	        x = myKeyboard.nextInt();
	        
	        System.out.print("Input another number: ");
	        int y;
	        y = myKeyboard.nextInt();
	        
	        int z;
	        z = x + y;
	        System.out.print("The sum is ");
	        System.out.println(z);
	    }
	    
	}
