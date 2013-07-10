	/**
	* Name: William Emmanuel
	* Computing ID: wre9fz
	* Lab Section: 1111
	* Date: 1/26/12
	*/
import java.util.Scanner;

public class Distance {
    
    public static void main(String[] args) {
        Scanner myKeyboard = new Scanner(System.in);
        
        System.out.print("Enter the first coordinate: ");
        double x0 = myKeyboard.nextDouble();
        double y0 = myKeyboard.nextDouble();
        
        System.out.print("Enter the second coordinate: ");
        double x1 = myKeyboard.nextDouble();
        double y1 = myKeyboard.nextDouble();
         
        double rise;
        rise = y1 - y0;
        
        double run;
        run = x1-x0;
        
        double slope;
        slope = (y1 - y0) / (x1 - x0);
        
        double distance;
        distance = Math.sqrt(Math.pow(rise,2) + Math.pow(run,2));
        
        System.out.println("Rise: " + rise);
        System.out.println("Run: " + run);
        System.out.println("Slope: " + slope);
        System.out.println("Length: " + distance);
        
    }
    
}