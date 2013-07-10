import java.util.Scanner;
public class test{
	
	public static void main(String[] args){
		
		Scanner myKeyboard = new Scanner(System.in);
		System.out.print("Enter name: ");
		String firstname = myKeyboard.nextLine();
		System.out.print("Hello, "+firstname);
		
	}
}
