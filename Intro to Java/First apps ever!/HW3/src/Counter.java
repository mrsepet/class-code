/**
* Name: William Emmanuel
* Computing ID: wre9fz
* Lab Section: 1111
* Date: 2/11/12
*/
import java.util.Scanner;
public class Counter {
	public static void main(String[] args) {
		Scanner keys = new Scanner(System.in);
		double avgtot = 0;
		double avgdiv = 0;
		int pos = 0;
		int neg = 0;
		int x = 1;
		while (x != 0){
			System.out.print("Please enter a number (0 to stop): ");
			x = keys.nextInt();
			if (x > 0)pos++;
			if (x < 0)neg++;
			avgtot = x + avgtot;
			if (x != 0)avgdiv++;
		}
		if (avgdiv == 0) avgdiv++;
		System.out.println("Total Positive: "+ pos);
		System.out.println("Total Negative: "+ neg);
		System.out.println("Average: "+ avgtot/avgdiv);
	}
}
