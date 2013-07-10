/**
* Name: William Emmanuel
* Computing ID: wre9fz
* Lab Section: 1111
* Date: 2/6/12
*/
import java.util.Scanner;
public class Caesar {
public static void main(String[] args){
	Scanner myKeyboard = new Scanner(System.in);
	System.out.print("Please give me the ciphertext: ");
	String code = myKeyboard.nextLine();
	int x = 0;
	int y;
	System.out.print("The decoded phrase is: ");
	while (x != 12)
	{
		char a = code.charAt(x);
		y = (int)a;
		y = y - 3;
		a = (char)y;
		System.out.print(a);
		x = x + 1;
	}
}
}
